package com.asep1001.finalprojectudacoding.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String image;
    private List<ExpensesDTO> expenses;
    private List<IncomeDTO> incomes;
}
