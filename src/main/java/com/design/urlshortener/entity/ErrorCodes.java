package com.design.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="ERROR_CODES")
@Data
@NamedQueries({
        @NamedQuery(name="ErrorCodes.getByCode" , query = "SELECT e FROM ErrorCodes e WHERE e.code = :code")
})
public class ErrorCodes {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "message")
    private String message;
}
