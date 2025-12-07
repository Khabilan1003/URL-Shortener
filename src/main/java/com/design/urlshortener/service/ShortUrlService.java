package com.design.urlshortener.service;

import com.design.urlshortener.model.ResponseModel;
import com.design.urlshortener.model.ShortUrlModel;

import java.util.Optional;

public interface ShortUrlService {
    public ResponseModel accessShortUrl(String shortUrl);

    public ResponseModel generateShortUrl(ShortUrlModel shortUrlModel);
}
