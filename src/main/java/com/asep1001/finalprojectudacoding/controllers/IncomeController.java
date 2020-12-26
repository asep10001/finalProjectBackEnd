package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.model.Income;
import com.asep1001.finalprojectudacoding.services.IncomeService;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
import com.asep1001.finalprojectudacoding.services.dto.ResponseActions;
import com.asep1001.finalprojectudacoding.services.dto.ResponseIncome;
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
    public ResponseEntity<ResponseIncome> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @GetMapping("/incomes/search/name")
    public ResponseEntity<ResponseIncome> seacrhIncomesByName(@RequestParam(value = "incomeName") String incomeName){
        return incomeService.getAllIncomesByName(incomeName);
    }

    @GetMapping("/incomes/search/category")
    public ResponseEntity<ResponseIncome> seacrhIncomesByCategory(@RequestParam(value = "categoryName") String categoryName){
        return incomeService.getAllIncomesByCategoryName(categoryName);
    }

    @PostMapping("/incomes")
    public ResponseEntity<ResponseActions> saveIncome(
            @Valid @RequestBody Income request, @RequestParam(value = "categoryId") Long id
    ) {
        return incomeService.createAnIncome(request, id);
    }

    @PutMapping("/incomes/{id}")
    public ResponseEntity<ResponseActions> updateAnIncomeById(
            @Valid @RequestBody IncomeDTO incomeDto,
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "categoryId") Long categoryId) {
        return incomeService.updateIncome(incomeDto, id, categoryId);
    }

    @DeleteMapping("/incomes")
    public ResponseEntity<ResponseActions> deleteExpenses(@RequestParam(value = "incomeId") Long incomeId) {
        return incomeService.deleteIncome(incomeId);
    }
}
