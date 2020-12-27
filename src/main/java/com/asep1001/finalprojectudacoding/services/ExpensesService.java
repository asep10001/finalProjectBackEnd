package com.asep1001.finalprojectudacoding.services;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.model.Expenses;
import com.asep1001.finalprojectudacoding.repository.CategoryRepostitory;
import com.asep1001.finalprojectudacoding.repository.ExpensesRepository;
import com.asep1001.finalprojectudacoding.services.dto.ExpensesDTO;
import com.asep1001.finalprojectudacoding.services.dto.ResponseActions;
import com.asep1001.finalprojectudacoding.services.dto.ResponseExpenses;
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

    private Function<List<ExpensesDTO>, ResponseEntity<ResponseExpenses>> getAllFunction() {
        return (x) -> {
            ResponseExpenses rexEntity = null;
            if (x.isEmpty()) {
                rexEntity = ResponseExpenses.builder()
                        .isSuccess(false)
                        .message("Failed to retrieve Expenses Data")
                        .data(x)
                        .build();

            } else {
                rexEntity = ResponseExpenses.builder()
                        .isSuccess(true)
                        .message("Successfully retrieved Expenses Data")
                        .data(x)
                        .build();
            }

            return new ResponseEntity<>(rexEntity, HttpStatus.OK);
        };
    }

    private Function<Expenses, ExpensesDTO> toDto() {
        return (x) -> ExpensesMapper.INSTANCE.toDto(x);
    }

    public ResponseEntity<ResponseExpenses> getAllExpenses() {
        List<ExpensesDTO> expensesDTOS = this.toDtos().apply(expensesRepository.findAll());
        return this.getAllFunction().apply(expensesDTOS);
    }

    public ResponseEntity<ResponseExpenses> getAllExpensesByName(String name) {
        List<ExpensesDTO> expensesDTOS = this.toDtos().apply(expensesRepository.findAllByNameContaining(name));
        return this.getAllFunction().apply(expensesDTOS);
    }
    public ResponseEntity<ResponseExpenses> getAllExpensesByCategoryName(String name) {
        List<ExpensesDTO> expensesDTOS = this.toDtos().apply(expensesRepository.findAllByCategory_NameContaining(name));
        return this.getAllFunction().apply(expensesDTOS);
    }

    public ResponseEntity<ResponseActions> createAnExpenses(Expenses request, Long categoryId) {
        Category cEntity = categoryRepostitory.findById(categoryId).get();

        Expenses eEntity = Expenses.builder()
                .name(request.getName())
                .ammount(request.getAmmount())
                .image(request.getImage())
                .transaction_date(request.getTransaction_date())
                .category(cEntity)
                .build();
        expensesRepository.save(eEntity);

        if (eEntity != null) {
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Inserting 1 expenses data successfully").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Failed to insert expenses data").build(), HttpStatus.OK);

    }

    public ResponseEntity<ResponseActions> deleteExpenses(Long expensesId) {
        return expensesRepository.findById(expensesId).map(entity -> {
            expensesRepository.delete(entity);
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Deleted 1 data successfully").build(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Expenses with id " + expensesId + " is not found").build(), HttpStatus.OK));
    }

    public ResponseEntity<ResponseActions> updateExpenses(ExpensesDTO expensesDto, Long id, Long categoryId) {
        Category cEntity = categoryRepostitory.findById(categoryId).orElseThrow(() -> new NullPointerException("Category with id " + categoryId + " is not found"));
        return expensesRepository.findById(id).map(expenses -> {
            expenses.setName(expensesDto.getName());
            expenses.setAmmount(expensesDto.getAmmount());
            expenses.setImage(expensesDto.getImage());
            expenses.setTransaction_date(expensesDto.getTransaction_date());
            expenses.setCategory(cEntity);

            expensesRepository.save(expenses);
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Updating expenses with id " + id + " successfully").build(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Failed to update. Expenses with id" + id + " is not found").build(), HttpStatus.OK));
    }
}
