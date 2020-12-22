package com.asep1001.finalprojectudacoding.services;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.model.Income;
import com.asep1001.finalprojectudacoding.repository.CategoryRepostitory;
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
    private final CategoryRepostitory categoryRepostitory;


    public IncomeService(IncomeRepository incomeRepository, CategoryRepostitory categoryRepostitory) {
        this.incomeRepository = incomeRepository;
        this.categoryRepostitory = categoryRepostitory;
    }

    private Function<List<Income>, List<IncomeDTO>> toDtos() {
        return (x) -> IncomeMapper.INSTANCE.toDtos(x);
    }

    private Function<List<Income>, ResponseEntity<List<IncomeDTO>>> getAll() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    private Function<Income, ResponseEntity<IncomeDTO>> getAnIncome() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    private Function<Income, IncomeDTO> toDto() {
        return (x) -> IncomeMapper.INSTANCE.toDto(x);
    }

    public ResponseEntity<List<IncomeDTO>> getAllIncomes() {
        return this.getAll().apply(incomeRepository.findAll());
    }

    public ResponseEntity<IncomeDTO> createAnIncome(Income request, Long categoryId) {

        Category cEntity = categoryRepostitory.findById(categoryId).get();
        Income iEntity = Income.builder()
                .name(request.getName())
                .ammount(request.getAmmount())
                .transaction_date(request.getTransaction_date())
                .category(cEntity)
                .build();

        incomeRepository.save(iEntity);

        return this.getAnIncome().apply(iEntity);

    }
}
