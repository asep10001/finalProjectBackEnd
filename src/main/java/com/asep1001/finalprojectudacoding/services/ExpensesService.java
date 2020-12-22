package com.asep1001.finalprojectudacoding.services;

import com.asep1001.finalprojectudacoding.model.Expenses;
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

    public ExpensesService(ExpensesRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }

    private Function<List<Expenses>, List<ExpensesDTO>> toDtos() {
        return (x) -> ExpensesMapper.INSTANCE.toDtos(x);
    }

    private Function<List<Expenses>, ResponseEntity<List<ExpensesDTO>>> getAll() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    private Function<Expenses, ExpensesDTO> toDto() {
        return (x) -> ExpensesMapper.INSTANCE.toDto(x);
    }

    public ResponseEntity<List<ExpensesDTO>> getAllExpenses(){
        return this.getAll().apply(expensesRepository.findAll());
    }
}
