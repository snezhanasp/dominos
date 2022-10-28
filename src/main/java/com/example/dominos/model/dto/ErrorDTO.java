package com.example.dominos.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {

    private int status;
    private LocalDateTime time;
    private String message;
}
