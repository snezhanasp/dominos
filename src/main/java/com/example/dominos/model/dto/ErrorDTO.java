package com.example.dominos.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDTO {

    private int status;
    private LocalDateTime time;
    private String message;
}
