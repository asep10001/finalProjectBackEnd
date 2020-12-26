package com.asep1001.finalprojectudacoding.services;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.model.Expenses;
import com.asep1001.finalprojectudacoding.model.Income;
import com.asep1001.finalprojectudacoding.repository.CategoryRepostitory;
import com.asep1001.finalprojectudacoding.services.dto.CategoryDTO;
import com.asep1001.finalprojectudacoding.services.dto.ResponseActions;
import com.asep1001.finalprojectudacoding.services.dto.ResponseCategory;
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

    public ResponseEntity<ResponseCategory> getAllCategoriesByName(String categoryName) {
        List<CategoryDTO> listCategories = this.toDtos().apply(categoryRepostitory.findAllByNameContaining(categoryName));
        ResponseCategory rEntity;
        if (listCategories.isEmpty()) {
            rEntity = ResponseCategory.builder()
                    .isSuccess(false)
                    .message("There is no such categories")
                    .data(listCategories)
                    .build();
        } else {
            rEntity = ResponseCategory.builder()
                    .isSuccess(true)
                    .message("Success get data!")
                    .data(listCategories)
                    .build();
        }
        return new ResponseEntity<>(rEntity, HttpStatus.OK);
    }


    public ResponseEntity<ResponseCategory> getAllCategories() {
        List<CategoryDTO> listCategory = this.toDtos().apply(categoryRepostitory.findAll());
        ResponseCategory responseCategory;
        ResponseCategory rEntity;
        if (listCategory.isEmpty()) {
            rEntity = ResponseCategory.builder()
                    .isSuccess(false)
                    .message("Data not found")
                    .data(listCategory)
                    .build();
        } else {
            rEntity = ResponseCategory.builder()
                    .isSuccess(true)
                    .message("Success get data")
                    .data(listCategory)
                    .build();
        }
        responseCategory = rEntity;
        return new ResponseEntity<>(responseCategory, HttpStatus.OK);
    }


    public ResponseEntity<ResponseActions> createCategory(Category request) {
        List<Expenses> tempExpenses = new ArrayList<>();
        List<Income> tempIncomes = new ArrayList<>();
        Category cEntity = Category.builder()
                .name(request.getName())
                .expenses(tempExpenses)
                .incomes(tempIncomes)
                .build();
        categoryRepostitory.save(cEntity);

        if (cEntity.getName() != null) {
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Inserting 1 data successfully").build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Cannot insert data").build(), HttpStatus.OK);
    }

    public ResponseEntity<ResponseActions> deleteCategory(Long categoryId) {
        return categoryRepostitory.findById(categoryId).map(entity -> {
            categoryRepostitory.delete(entity);
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Deleted 1 data successfully").build(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Cannot find category with id " + categoryId).build(), HttpStatus.OK));
    }

    public ResponseEntity<ResponseActions> updateCategory(CategoryDTO categoryDto, Long id) {
        return categoryRepostitory.findById(id).map(category -> {
            category.setName(categoryDto.getName());
            categoryRepostitory.save(category);
            return new ResponseEntity<>(ResponseActions.builder().isSuccess(true).message("Updated category with id " + id + " successfully").build(), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(ResponseActions.builder().isSuccess(false).message("Updating category with id  " + id + " failed").build(), HttpStatus.OK));
    }
}
