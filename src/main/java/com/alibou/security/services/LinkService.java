package com.alibou.security.services;

;
import com.alibou.security.dto.LinkDTO;
import com.alibou.security.model.Link;
import com.alibou.security.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class LinkService {

    private final LinkRepository linkRepository;

    @Value("${client.url}")
    private String urlClient;

    /**
     * get all Links
     * @return
     */
    @PreAuthorize("hasAuthority('admin:read')")
    public List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    /**
     * add new link
     * @param linkDTO
     * @return
     */
//    @Transactional(rollbackFor = { RuntimeException.class, Error.class })
    @Transactional
    public List<Link> addNewLinks(LinkDTO linkDTO) throws Exception {

        try {
            List<Link> links = new ArrayList<>();

            if (!linkDTO.getLink().isEmpty()) {
                for (int i = 0 ; i < 10 ; i++) {
                    String shortLink = randomPath(urlClient);
                    Link link = new Link();
                    link.setLink(linkDTO.getLink());
                    link.setShortLink(shortLink);

                    links.add(link);
                }
            }

            return linkRepository.saveAll(links);
        } catch (Exception e) {
            throw new Exception("fail in add new link");
        }
    }

    public Link updateLink(LinkDTO linkDTO, Long id) {

        Optional<Link> link = linkRepository.findById(id);
        Link result = new Link();
        if (link.isPresent()) {
            link.get().setLink(linkDTO.getLink());
            link.get().setShortLink(linkDTO.getAlias());
            result = linkRepository.save(link.get());
        }

        return result;
    }

    public boolean deleteLink(Long id) throws Exception {

        Optional<Link> link = linkRepository.findById(id);
        Link result = new Link();
        try {
            if (link.isPresent()) {
                linkRepository.delete(link.get());
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return true;
    }

    public String randomPath(String urlClient) {
        String aToZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        Random rand=new Random();
        StringBuilder res=new StringBuilder();
        res.append(urlClient);

        for (int i = 0; i < 17; i++) {
            int randIndex=rand.nextInt(aToZ.length());
            res.append(aToZ.charAt(randIndex));
        }

        return res.toString();
    }
}
