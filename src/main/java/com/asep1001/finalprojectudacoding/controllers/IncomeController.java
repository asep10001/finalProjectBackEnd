package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.services.IncomeService;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<IncomeDTO>> getAllIncomes(){
        return incomeService.getAllIncomes();
    }
}
