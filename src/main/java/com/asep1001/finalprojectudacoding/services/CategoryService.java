package com.asep1001.finalprojectudacoding.services;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.model.Expenses;
import com.asep1001.finalprojectudacoding.model.Income;
import com.asep1001.finalprojectudacoding.repository.CategoryRepostitory;
import com.asep1001.finalprojectudacoding.services.dto.CategoryDTO;
import com.asep1001.finalprojectudacoding.services.dto.ResponseDto;
import com.asep1001.finalprojectudacoding.services.mapper.CategoryMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class CategoryService {

    private final CategoryRepostitory categoryRepostitory;


    public CategoryService(CategoryRepostitory categoryRepostitory) {
        this.categoryRepostitory = categoryRepostitory;
    }


    private Function<List<Category>, List<CategoryDTO>> toDtos() {
        return (x) -> CategoryMapper.INSTANCE.toDtos(x);
    }

    private Function<List<Category>, ResponseEntity<List<CategoryDTO>>> getAll() {
        return (x) -> new ResponseEntity<>(this.toDtos().apply(x), HttpStatus.OK);
    }

    private Function<Category, CategoryDTO> toDto() {
        return (x) -> CategoryMapper.INSTANCE.toDto(x);
    }

    private Function<Category, ResponseEntity<CategoryDTO>> getACategory() {
        return (x) -> new ResponseEntity<>(this.toDto().apply(x), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> getAllCategories() {
        List<CategoryDTO> listCategory = this.toDtos().apply(categoryRepostitory.findAll());
        ResponseDto responseDto;
        ResponseDto rEntity;
        if (listCategory.isEmpty()) {
            rEntity = ResponseDto.builder()
                    .isSuccess(false)
                    .message("Data not found")
                    .categoryDtos(this.toDtos().apply(categoryRepostitory.findAll()))
                    .build();
        } else {
            rEntity = ResponseDto.builder()
                    .isSuccess(true)
                    .message("Success get data")
                    .categoryDtos(this.toDtos().apply(categoryRepostitory.findAll()))
                    .build();
        }
        responseDto = rEntity;
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    public ResponseEntity<CategoryDTO> createCategory(Category request) {
        List<Expenses> tempExpenses = new ArrayList<>();
        List<Income> tempIncomes = new ArrayList<>();
        Category cEntity = Category.builder()
                .name(request.getName())
                .expenses(tempExpenses)
                .incomes(tempIncomes)
                .build();
        categoryRepostitory.save(cEntity);
        return this.getACategory().apply(cEntity);
    }

    public ResponseEntity<String> deleteCategory(Long categoryId) {
        return categoryRepostitory.findById(categoryId).map(entity -> {
            categoryRepostitory.delete(entity);
            return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
        }).orElseThrow(() -> new NullPointerException("Category with id" + categoryId + "is not found"));
    }

    public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDto, Long id) {
        return categoryRepostitory.findById(id).map(category -> {
            category.setName(categoryDto.getName());
            categoryRepostitory.save(category);
            return this.getACategory().apply(category);
        }).orElse(null);
    }
}
