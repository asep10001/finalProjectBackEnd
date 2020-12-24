package com.asep1001.finalprojectudacoding.services;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.model.Income;
import com.asep1001.finalprojectudacoding.repository.CategoryRepostitory;
import com.asep1001.finalprojectudacoding.repository.IncomeRepository;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
import com.asep1001.finalprojectudacoding.services.dto.ResponseActions;
import com.asep1001.finalprojectudacoding.services.dto.ResponseIncome;
import com.asep1001.finalprojectudacoding.services.mapper.IncomeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerTypePredicate;

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

    private Function<Income, ResponseEntity<IncomeDTO>> getAnIncome() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    private Function<Income, IncomeDTO> toDto() {
        return (x) -> IncomeMapper.INSTANCE.toDto(x);
    }

    public ResponseEntity<ResponseIncome> getAllIncomes() {
        List<IncomeDTO> incomes = this.toDtos().apply(incomeRepository.findAll());
        ResponseIncome responseIncome;
        ResponseIncome riEntity;
        if(incomes.isEmpty()){
            riEntity = ResponseIncome.builder()
                    .isSuccess(false)
                    .message("Data not found")
                    .data(incomes)
                    .build();

        } else{
            riEntity = ResponseIncome.builder()
                    .isSuccess(true)
                    .message("Success Retrieving Incomes data")
                    .data(incomes)
                    .build();
        }

        responseIncome = riEntity;
        return new ResponseEntity<>(responseIncome, HttpStatus.OK);
    }

    public ResponseEntity<ResponseActions> createAnIncome(Income request, Long categoryId) {

        Category cEntity = categoryRepostitory.findById(categoryId).get();
        Income iEntity = Income.builder()
                .name(request.getName())
                .ammount(request.getAmmount())
                .transaction_date(request.getTransaction_date())
                .category(cEntity)
                .build();

        incomeRepository.save(iEntity);

        if(iEntity != null){
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Inserting 1 income data successfully").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Failed to insert income data").build(), HttpStatus.OK);
    }

    public ResponseEntity<ResponseActions> deleteIncome(Long incomeId) {
       return incomeRepository.findById(incomeId).map(entity -> {
            incomeRepository.delete(entity);
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Deleted 1 data successfully").build(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Income with id "+incomeId+" is not found").build(), HttpStatus.OK));
    }

    public ResponseEntity<ResponseActions> updateIncome(IncomeDTO incomeDto, Long id, Long categoryId) {
        Category cEntity = categoryRepostitory.findById(categoryId).orElseThrow(() ->
                new NullPointerException("Category with id" + categoryId + " is not found"));

        return incomeRepository.findById(id).map(income -> {
            income.setName(incomeDto.getName());
            income.setAmmount(incomeDto.getAmmount());
            income.setTransaction_date(incomeDto.getTransaction_date());
            income.setCategory(cEntity);

            incomeRepository.save(income);
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Updating income with id " +id+" successfully").build(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Failed to update. Income with id"+id+" is not found").build(), HttpStatus.OK));
    }
}
