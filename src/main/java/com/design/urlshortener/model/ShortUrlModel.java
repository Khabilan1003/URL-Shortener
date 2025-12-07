package com.design.urlshortener.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShortUrlModel {
    private Long id;
    private String shortKey;
    private String originalUrl;
    private String createdBy;
    private Boolean isPrivate;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Long clickCount;
}