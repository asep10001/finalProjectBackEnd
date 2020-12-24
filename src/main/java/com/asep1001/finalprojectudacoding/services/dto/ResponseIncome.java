package com.asep1001.finalprojectudacoding.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class ResponseIncome {
    private Boolean isSuccess;
    private String message;
    private List<IncomeDTO> data;
}
