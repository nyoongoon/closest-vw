package com.example.closestv2.domain.feed;

import com.example.closestv2.domain.blog.BlogRoot;
import com.example.closestv2.domain.blog.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class Feed {
    private URL rssUrl;
    private URL blogUrl;
    private String blogTitle;
    private String author;
    private List<FeedItem> feedItems;

    public static Feed create(
            URL rssUrl,
            URL blogUrl,
            String blogTitle,
            String author,
            List<FeedItem> feedItems
    ){
        if(CollectionUtils.isEmpty(feedItems)){
            feedItems = new ArrayList<>();
        }
        return Feed.builder()
                .rssUrl(rssUrl)
                .blogUrl(blogUrl)
                .blogTitle(blogTitle)
                .author(author)
                .feedItems(feedItems)
                .build();
    }

    public BlogRoot toBlogRoot(){
        BlogRoot blogRoot = BlogRoot.create(
                rssUrl,
                blogUrl,
                blogTitle,
                author
        );
        Map<URL, Post> posts = blogRoot.getPosts();
        for (FeedItem feedItem : feedItems){
            Post post = blogRoot.createPost(
                    feedItem.getPostUrl(),
                    feedItem.getPostTitle(),
                    feedItem.getPublishedDateTime()
            );
            posts.put(post.getPostUrl(), post);
        }
        return blogRoot;
    }
}
