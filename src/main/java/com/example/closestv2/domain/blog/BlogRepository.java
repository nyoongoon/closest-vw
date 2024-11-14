package com.example.closestv2.domain.blog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogRoot, Long> {
}
