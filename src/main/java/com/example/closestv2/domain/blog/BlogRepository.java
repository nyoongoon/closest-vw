package com.example.closestv2.domain.blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.net.URL;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<BlogRoot, Long> {
    Optional<BlogRoot> findByBlogInfoBlogUrl(URL blogUrl);
    Optional<BlogRoot> findByBlogInfoRssUrl(URL rssUrl);
}
