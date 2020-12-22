package com.asep1001.finalprojectudacoding.services;

import com.asep1001.finalprojectudacoding.model.Income;
import com.asep1001.finalprojectudacoding.repository.IncomeRepository;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
import com.asep1001.finalprojectudacoding.services.mapper.IncomeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;


    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    private Function<List<Income>, List<IncomeDTO>> toDtos() {
        return (x) -> IncomeMapper.INSTANCE.toDtos(x);
    }

    private Function<List<Income>, ResponseEntity<List<IncomeDTO>>> getAll() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    private Function<Income, IncomeDTO> toDto() {
        return (x) -> IncomeMapper.INSTANCE.toDto(x);
    }

    public ResponseEntity<List<IncomeDTO>> getAllIncomes() {
        return this.getAll().apply(incomeRepository.findAll());
    }
}
