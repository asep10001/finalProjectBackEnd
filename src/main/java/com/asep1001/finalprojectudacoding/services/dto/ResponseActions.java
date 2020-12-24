package com.asep1001.finalprojectudacoding.services.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class ResponseActions {
    private Boolean isSuccess;
    private String message;
}
