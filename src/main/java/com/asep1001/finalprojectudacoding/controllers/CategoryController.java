package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.services.CategoryService;
import com.asep1001.finalprojectudacoding.services.dto.CategoryDTO;
import com.asep1001.finalprojectudacoding.services.dto.ResponseCategory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String welcome() {
        return "Welcome User!";
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseCategory> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(
            @Valid @RequestBody Category request
    ) {
        return categoryService.createCategory(request);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateAnCategoryById(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable(value = "id") Long id) {
        return categoryService.updateCategory(categoryDto, id);
    }
    @DeleteMapping("/categories")
    public void deleteCategory(@RequestParam(value = "categoryId") Long id){
        categoryService.deleteCategory(id);
    }
}
