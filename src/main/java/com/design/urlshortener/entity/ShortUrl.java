package com.design.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="SHORT_URL")
@Data
@NamedQueries({
        @NamedQuery(name="ShortUrl.getPublicShortUrls" , query = "SELECT e FROM ShortUrl e WHERE e.isPrivate = false"),
        @NamedQuery(name="ShortUrl.findByShortUrl" , query = "SELECT e FROM ShortUrl e WHERE e.shortKey = :shortKey"),
        @NamedQuery(name="ShortUrl.isShortKeyPresent", query = "SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM ShortUrl u WHERE u.shortKey = :shortKey")
})
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="short_key")
    private String shortKey;

    @Column(name="original_url")
    private String originalUrl;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name="is_private")
    private Boolean isPrivate;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="expires_at")
    private LocalDateTime expiresAt;

    @Column(name="click_count")
    private Long clickCount;
}
