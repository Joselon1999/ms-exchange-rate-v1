package com.example.exchange_rate.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Setter
@Getter
public class CustomException extends RuntimeException{

    private final String code;
    private final String message;
    private final String status;

    public CustomException(String message, String code, String status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }
}
