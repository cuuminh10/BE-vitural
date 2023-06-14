package com.alibou.security.repository;

import com.alibou.security.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

@Repository
@PreAuthorize("hasRole('ADMIN')")
public interface LinkRepository extends JpaRepository<Link, Long> {
}
