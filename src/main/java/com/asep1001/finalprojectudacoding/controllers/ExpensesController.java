package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.model.Expenses;
import com.asep1001.finalprojectudacoding.services.ExpensesService;
import com.asep1001.finalprojectudacoding.services.dto.ExpensesDTO;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
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
    public ResponseEntity<List<ExpensesDTO>> getAll() {
        return expensesService.getAllExpenses();
    }

    @PostMapping("/expenses")
    public ResponseEntity<ExpensesDTO> saveExpenses(
            @Valid @RequestBody Expenses request, @RequestParam(value = "categoryId") Long id
    ) {
        return expensesService.createAnExpenses(request, id);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<ExpensesDTO> updateAnExpensesById(
            @Valid @RequestBody ExpensesDTO expensesDto,
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "categoryId") Long categoryId) {
        return expensesService.updateExpenses(expensesDto, id, categoryId);
    }
    @DeleteMapping("/expenses")
    public void deleteExpenses(@RequestParam(value = "expensesId") Long expensesId) {
        expensesService.deleteExpenses(expensesId);
    }
}
