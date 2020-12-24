package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.model.Expenses;
import com.asep1001.finalprojectudacoding.services.ExpensesService;
import com.asep1001.finalprojectudacoding.services.dto.ExpensesDTO;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
import com.asep1001.finalprojectudacoding.services.dto.ResponseActions;
import com.asep1001.finalprojectudacoding.services.dto.ResponseExpenses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ExpensesController {
    private final ExpensesService expensesService;

    public ExpensesController(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<ResponseExpenses> getAll() {
        return expensesService.getAllExpenses();
    }

    @PostMapping("/expenses")
    public ResponseEntity<ResponseActions> saveExpenses(
            @Valid @RequestBody Expenses request, @RequestParam(value = "categoryId") Long id
    ) {
        return expensesService.createAnExpenses(request, id);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<ResponseActions> updateAnExpensesById(
            @Valid @RequestBody ExpensesDTO expensesDto,
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "categoryId") Long categoryId) {
        return expensesService.updateExpenses(expensesDto, id, categoryId);
    }

    @DeleteMapping("/expenses")
    public ResponseEntity<ResponseActions> deleteExpenses(@RequestParam(value = "expensesId") Long expensesId) {
        return expensesService.deleteExpenses(expensesId);
    }
}
