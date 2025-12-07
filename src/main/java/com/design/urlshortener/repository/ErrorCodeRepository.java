package com.design.urlshortener.repository;

import com.design.urlshortener.entity.ErrorCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorCodeRepository extends JpaRepository<ErrorCodes, String> {
    public List<ErrorCodes> getByCode(String code);
}