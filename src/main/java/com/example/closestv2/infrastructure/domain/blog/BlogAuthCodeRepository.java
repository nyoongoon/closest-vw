package com.example.closestv2.infrastructure.domain.blog;

import com.example.closestv2.domain.blog.BlogAuthCode;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import static com.example.closestv2.api.exception.ExceptionMessageConstants.FAIL_BLOG_AUTHENTICATE;

@Repository
public class BlogAuthCodeRepository {

    @Cacheable(value = "blogAuthCode", key = "#memberId")
    public BlogAuthCode findByMemberId(long memberId){
        // 없는 경우 InvalidDataAccessApiUsageException 발생
        return null;
    }

    @CachePut(value = "blogAuthCode", key = "#blogAuthCode.memberId()")
    public BlogAuthCode save(BlogAuthCode blogAuthCode){
        return blogAuthCode;
    }
}
