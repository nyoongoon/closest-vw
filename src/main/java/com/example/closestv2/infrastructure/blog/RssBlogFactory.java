package com.example.closestv2.infrastructure.blog;

import com.example.closestv2.clients.RssFeedClient;
import com.example.closestv2.domain.blog.BlogFactory;
import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RssBlogFactory implements BlogFactory {

    @Override
    public BlogRoot createRecentBlogRoot(SyndFeed syndFeed) throws MalformedURLException {
        BlogRoot blogRoot = translate(syndFeed);

        if (CollectionUtils.isEmpty(syndFeed.getEntries())) {
            return blogRoot;
        }

        List<Post> posts = blogRoot.getPosts();
        for (SyndEntry entry : syndFeed.getEntries()) {
            URL postUrl = URI.create(entry.getLink()).toURL();
            String postTitle = entry.getTitle();
            LocalDateTime publishedDate = toLocalDateTime(entry.getPublishedDate());
            Post post = blogRoot.createPost(
                    postUrl,
                    postTitle,
                    publishedDate
            );
            posts.add(post);
        }

        return blogRoot;
    }

    private BlogRoot translate(SyndFeed syndFeed) throws MalformedURLException {
        URL blogUrl = URI.create(syndFeed.getLink()).toURL();
        String blogTitle = syndFeed.getTitle();
        String author = syndFeed.getAuthor();
        LocalDateTime blogPublishedDateTime = toLocalDateTime(syndFeed.getPublishedDate());
        return BlogRoot.create(
                blogUrl,
                blogTitle,
                author,
                blogPublishedDateTime
        );
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
