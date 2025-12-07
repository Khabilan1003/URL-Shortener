package com.design.urlshortener.web;

import com.design.urlshortener.entity.ShortUrl;
import com.design.urlshortener.model.ResponseModel;
import com.design.urlshortener.model.ShortUrlModel;
import com.design.urlshortener.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping("/s/{shortUrl}")
    public ResponseEntity<?> getRedirectShortUrl(@PathVariable("shortUrl") String shortUrl) {
        ResponseModel responseModel = shortUrlService.accessShortUrl(shortUrl);

        if(!responseModel.getError().isEmpty()) {
            return new ResponseEntity<>(responseModel , HttpStatus.BAD_REQUEST);
        }

        ShortUrl shortUrl1 = (ShortUrl) responseModel.getData();

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, shortUrl1.getOriginalUrl())
                .build();
    }

    @PostMapping("/shortUrl/generate")
    public ResponseEntity<?> generateShortUrl(@RequestBody ShortUrlModel shortUrlModel) {
        ResponseModel model = shortUrlService.generateShortUrl(shortUrlModel);
        return new ResponseEntity<>(model , HttpStatus.OK);
    }
}
