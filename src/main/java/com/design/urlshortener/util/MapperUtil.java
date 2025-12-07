package com.design.urlshortener.util;

import com.design.urlshortener.entity.ErrorCodes;
import com.design.urlshortener.entity.ShortUrl;
import com.design.urlshortener.model.ErrorCodeModel;
import com.design.urlshortener.model.ShortUrlModel;

public class MapperUtil {
    public static ErrorCodeModel convertEntityToModel(ErrorCodes entity) {
        ErrorCodeModel model = new ErrorCodeModel();
        if(entity != null) {
            model.setCode(entity.getCode());
            model.setMessage(entity.getMessage());
        }
        return model;
    }

    public static ErrorCodes convertModelToEntity(ErrorCodeModel model) {
        ErrorCodes entity = new ErrorCodes();
        if(model != null) {
            entity.setCode(model.getCode());
            entity.setMessage(model.getMessage());
        }
        return entity;
    }

    public static ShortUrlModel convertEntityToModel(ShortUrl entity) {
        ShortUrlModel model = new ShortUrlModel();
        if(entity != null) {
            model.setId(entity.getId());
            model.setCreatedAt(entity.getCreatedAt());
            model.setClickCount(entity.getClickCount());
            model.setIsPrivate(entity.getIsPrivate());
            model.setCreatedBy(entity.getCreatedBy());
            model.setShortKey(entity.getShortKey());
            model.setOriginalUrl(entity.getOriginalUrl());
            model.setExpiresAt(entity.getExpiresAt());
        }
        return model;
    }

    public static ShortUrl convertModelToEntity(ShortUrlModel model) {
        ShortUrl entity = new ShortUrl();
        if(model != null) {
            entity.setId(model.getId());
            entity.setCreatedAt(model.getCreatedAt());
            entity.setClickCount(model.getClickCount());
            entity.setIsPrivate(model.getIsPrivate());
            entity.setCreatedBy(model.getCreatedBy());
            entity.setShortKey(model.getShortKey());
            entity.setOriginalUrl(model.getOriginalUrl());
            entity.setExpiresAt(model.getExpiresAt());
        }
        return entity;
    }
}
