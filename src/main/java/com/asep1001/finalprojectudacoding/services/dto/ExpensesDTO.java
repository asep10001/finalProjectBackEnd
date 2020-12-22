package com.asep1001.finalprojectudacoding.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.YearMonth;

@Value
@Builder
@AllArgsConstructor
public class ExpensesDTO {

    private Long id;
    private String name;
    private Double ammount;
    private YearMonth transaction_date;
    private String category_name;
}
