package com.asep1001.finalprojectudacoding.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class ResponseDto {
    private Boolean isSuccess;
    private String message;
    private List<CategoryDTO> data;
}
