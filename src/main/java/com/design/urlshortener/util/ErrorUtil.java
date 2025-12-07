package com.design.urlshortener.util;

import com.design.urlshortener.model.ErrorCodeModel;
import com.design.urlshortener.repository.ErrorCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ErrorUtil {

    private final ErrorCodeRepository errorCodeRepository;

    @Autowired
    public ErrorUtil(ErrorCodeRepository errorCodeRepository) {
        this.errorCodeRepository = errorCodeRepository;
    }

    public ErrorCodeModel resolveErrorCode(String errorCode) {
        List<ErrorCodeModel> list = errorCodeRepository.getByCode(errorCode)
                .stream()
                .map(MapperUtil::convertEntityToModel)
                .toList();

        return list.isEmpty() ? null : list.get(0);
    }
}