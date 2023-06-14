package com.alibou.security.controller;


import com.alibou.security.dto.LinkDTO;
import com.alibou.security.model.Link;
import com.alibou.security.repository.LinkRepository;
import com.alibou.security.services.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/link")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;
    private final LinkRepository linkRepository;


    @GetMapping(value = "/getLinks")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<Link>> getAddLinks() throws Exception {
        List<Link> links;

        try {
            links = linkService.getAllLinks();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok(links);
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity addNewLink(@RequestBody LinkDTO linkDTO) throws Exception {
        List<Link> link = null;

        try {
            link = linkService.addNewLinks(linkDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Year of birth cannot be in the future",
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(link);
    }

    @PutMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Link> updateNewLink(@PathVariable Long id ,@RequestBody LinkDTO linkDTO) throws Exception {
        Link link;

        try {
            link = linkService.updateLink(linkDTO, id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok(link);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Boolean> deleteLink(@PathVariable Long id) throws Exception {
        boolean link;

        try {
            link = linkService.deleteLink(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok(link);
    }
}
