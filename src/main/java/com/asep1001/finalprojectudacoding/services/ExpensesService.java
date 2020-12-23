package com.asep1001.finalprojectudacoding.services;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.model.Expenses;
import com.asep1001.finalprojectudacoding.repository.CategoryRepostitory;
import com.asep1001.finalprojectudacoding.repository.ExpensesRepository;
import com.asep1001.finalprojectudacoding.services.dto.ExpensesDTO;
import com.asep1001.finalprojectudacoding.services.mapper.ExpensesMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ExpensesService {
    private final ExpensesRepository expensesRepository;
    private final CategoryRepostitory categoryRepostitory;

    public ExpensesService(ExpensesRepository expensesRepository, CategoryRepostitory categoryRepostitory) {
        this.expensesRepository = expensesRepository;
        this.categoryRepostitory = categoryRepostitory;
    }

    private Function<List<Expenses>, List<ExpensesDTO>> toDtos() {
        return (x) -> ExpensesMapper.INSTANCE.toDtos(x);
    }

    private Function<List<Expenses>, ResponseEntity<List<ExpensesDTO>>> getAll() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    private Function<Expenses, ResponseEntity<ExpensesDTO>> getAnExpenses() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    private Function<Expenses, ExpensesDTO> toDto() {
        return (x) -> ExpensesMapper.INSTANCE.toDto(x);
    }

    public ResponseEntity<List<ExpensesDTO>> getAllExpenses() {
        return this.getAll().apply(expensesRepository.findAll());
    }

    public ResponseEntity<ExpensesDTO> createAnExpenses(Expenses request, Long categoryId) {
        Category cEntity = categoryRepostitory.findById(categoryId).get();

        Expenses eEntity = Expenses.builder()
                .name(request.getName())
                .ammount(request.getAmmount())
                .transaction_date(request.getTransaction_date())
                .category(cEntity)
                .build();
        expensesRepository.save(eEntity);

        return this.getAnExpenses().apply(eEntity);

    }

    public void deleteExpenses(Long expensesId) {
        expensesRepository.findById(expensesId).map(entity -> {
            expensesRepository.delete(entity);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new NullPointerException("expenses with id  " + expensesId + " is not found"));
    }

    public ResponseEntity<ExpensesDTO> updateExpenses(ExpensesDTO expensesDto, Long id, Long categoryId) {
        Category cEntity = categoryRepostitory.findById(categoryId).orElseThrow(() -> new NullPointerException("Category with id " + categoryId + " is not found"));
        return this.getAnExpenses().apply(expensesRepository.findById(id).map(expenses -> {
            expenses.setName(expensesDto.getName());
            expenses.setAmmount(expensesDto.getAmmount());
            expenses.setTransaction_date(expensesDto.getTransaction_date());
            expenses.setCategory(cEntity); 

            return expensesRepository.save(expenses);

        }).orElseThrow(() -> new NullPointerException("Expenses with id " + id + " is not found")));
    }
}
