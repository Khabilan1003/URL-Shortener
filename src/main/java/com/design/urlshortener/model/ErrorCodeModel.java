package com.design.urlshortener.model;

import lombok.Data;

@Data
public class ErrorCodeModel {
    private String code;
    private String message;
}
