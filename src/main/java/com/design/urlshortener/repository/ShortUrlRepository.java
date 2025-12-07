package com.design.urlshortener.repository;

import com.design.urlshortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    public List<ShortUrl> findByShortUrl(String shortKey);
    public Boolean isShortKeyPresent(String shortKey);
}
