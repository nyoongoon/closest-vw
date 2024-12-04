package com.example.closestv2.domain.feed;

import com.example.closestv2.domain.blog.BlogRoot;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        return BlogRoot.create(
                rssUrl,
                blogUrl,
                blogTitle,
                author
        );
    }
}
