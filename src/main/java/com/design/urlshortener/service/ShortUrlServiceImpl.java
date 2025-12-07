package com.design.urlshortener.service;

import com.design.urlshortener.entity.ShortUrl;
import com.design.urlshortener.model.ResponseModel;
import com.design.urlshortener.model.ShortUrlModel;
import com.design.urlshortener.repository.ShortUrlRepository;
import com.design.urlshortener.util.ErrorUtil;
import com.design.urlshortener.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class ShortUrlServiceImpl implements ShortUrlService{
    private static final Logger log = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Autowired
    private MemcachedService memcachedService;

    @Autowired
    private ErrorUtil errorUtil;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_KEY_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public ResponseModel accessShortUrl(String shortUrl) {
        ResponseModel responseModel = new ResponseModel();
        try {
            String originalUrlCached = (String) memcachedService.get(shortUrl);
            log.info("Original Url Cached -> " + originalUrlCached);
            if(originalUrlCached != null) {
                ShortUrlModel shortUrlModel1 = new ShortUrlModel();
                shortUrlModel1.setOriginalUrl(originalUrlCached);
                responseModel.setData(shortUrlModel1);
                return responseModel;
            }
        } catch (Exception e) {
            log.error("Cache Get Exception : {}", e.getMessage());
        }

        List<ShortUrl> shortUrlList = shortUrlRepository.findByShortUrl(shortUrl);
        if(shortUrlList.isEmpty()) {
            responseModel.getError().add(errorUtil.resolveErrorCode("UD_001"));
            return responseModel;
        }

        ShortUrl shortUrl1 = shortUrlList.get(0);
        if(shortUrl1.getExpiresAt() != null && shortUrl1.getExpiresAt().isBefore(LocalDateTime.now())) {
            responseModel.getError().add(errorUtil.resolveErrorCode("UD_002"));
            return responseModel;
        }

        try {
            log.info("Setting up Original Url in Cache");
            memcachedService.set(shortUrl , shortUrl1.getOriginalUrl() , (int) Math.min(3600, Duration.between(LocalDateTime.now() , shortUrl1.getExpiresAt()).getSeconds()));
        } catch (Exception e) {
            log.error("Cache Set Exception : {}", e.getMessage());
        }
        responseModel.setData(MapperUtil.convertEntityToModel(shortUrl1));
        return responseModel;
    }

    @Override
    public ResponseModel generateShortUrl(ShortUrlModel shortUrlModel) {
        ResponseModel responseModel = new ResponseModel();
        boolean isValid = isValidOriginalUrl(shortUrlModel.getOriginalUrl());
        if(!isValid) {
            responseModel.getError().add(errorUtil.resolveErrorCode("UD_003"));
            return responseModel;
        }

        ShortUrl shortUrl = MapperUtil.convertModelToEntity(shortUrlModel);
        if(shortUrl.getIsPrivate() == null)
            shortUrl.setIsPrivate(false);
        shortUrl.setClickCount(0L);
        shortUrl.setCreatedAt(LocalDateTime.now());
        shortUrl.setExpiresAt(LocalDateTime.now().plusDays(30));

        String shortKey = generateRandomShortKey();
        while(shortUrlRepository.isShortKeyPresent(shortKey)) {
            shortKey = generateRandomShortKey();
        }

        shortUrl.setShortKey(shortKey);
        shortUrlRepository.save(shortUrl);

        List<ShortUrlModel> shortUrlModelList = shortUrlRepository.findByShortUrl(shortKey).stream().map(MapperUtil::convertEntityToModel).toList();

        responseModel.setData(shortUrlModelList.get(0));
        return responseModel;
    }

    private boolean isValidOriginalUrl(String url) {
        if (url == null || url.isBlank()) return false;
        try {
            String formattedUrl = url.matches("^(?i)https?://.*") ? url : "https://" + url;
            new java.net.URL(formattedUrl).toURI();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private static String generateRandomShortKey() {
        StringBuilder stringBuilder = new StringBuilder(SHORT_KEY_LENGTH);
        for(int i = 0 ; i < SHORT_KEY_LENGTH ; i++) {
            stringBuilder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return stringBuilder.toString();
    }
}