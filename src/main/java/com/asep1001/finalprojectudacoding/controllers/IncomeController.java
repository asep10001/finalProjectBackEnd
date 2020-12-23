package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.model.Income;
import com.asep1001.finalprojectudacoding.services.IncomeService;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class IncomeController {
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<IncomeDTO>> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @PostMapping("/incomes")
    public ResponseEntity<IncomeDTO> saveIncome(
            @Valid @RequestBody Income request, @RequestParam(value = "categoryId") Long id
    ) {
        return incomeService.createAnIncome(request, id);
    }

    @PutMapping("/incomes/{id}")
    public ResponseEntity<IncomeDTO> updateAnIncomeById(
            @Valid @RequestBody IncomeDTO incomeDto,
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "categoryId") Long categoryId) {
        return incomeService.updateIncome(incomeDto, id, categoryId);
    }

    @DeleteMapping("/incomes")
    public void deleteExpenses(@RequestParam(value = "incomeId") Long incomeId) {
        incomeService.deleteIncome(incomeId);
    }
}
