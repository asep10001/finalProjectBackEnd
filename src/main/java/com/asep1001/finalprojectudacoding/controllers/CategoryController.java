package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.services.CategoryService;
import com.asep1001.finalprojectudacoding.services.dto.CategoryDTO;
import com.asep1001.finalprojectudacoding.services.dto.ResponseActions;
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

    @GetMapping("/categories/search")
    public  ResponseEntity<ResponseCategory> getAllCategoriesByName(@RequestParam(value = "categoryName") String name){
        return categoryService.getAllCategoriesByName(name);
    }

    @PostMapping("/categories")
    public ResponseEntity<ResponseActions> createCategory(
            @Valid @RequestBody Category request
    ) {
        return categoryService.createCategory(request);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ResponseActions> updateAnCategoryById(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable(value = "id") Long id) {
        return categoryService.updateCategory(categoryDto, id);
    }
    @DeleteMapping("/categories")
    public ResponseEntity<ResponseActions> deleteCategory(@RequestParam(value = "categoryId") Long id){
        return categoryService.deleteCategory(id);
    }
}
