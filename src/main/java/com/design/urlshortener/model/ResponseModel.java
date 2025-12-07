package com.design.urlshortener.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseModel {
    private List<ErrorCodeModel> error;
    private Object data;
    private String status;

    public ResponseModel() {
        this.error = new ArrayList<>();
    }
}
