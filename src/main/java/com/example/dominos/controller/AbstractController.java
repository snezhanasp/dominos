package com.example.dominos.controller;

import com.example.dominos.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public abstract class AbstractController {

    @ExceptionHandler
    public ErrorDTO handleNotFound(Exception e){
        return buildErrorInfo(e, HttpStatus.NOT_FOUND);
    }

    private ErrorDTO buildErrorInfo(Exception e, HttpStatus httpStatus) {
        e.printStackTrace();
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatus(httpStatus.value());
        errorDTO.setTime(LocalDateTime.now());
        return errorDTO;
    }
}
