package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.services.ExpensesService;
import com.asep1001.finalprojectudacoding.services.dto.ExpensesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpensesController {
    private final ExpensesService expensesService;

    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<ExpensesDTO>> getAll() {
        return  expensesService.getAllExpenses();
    }
}
