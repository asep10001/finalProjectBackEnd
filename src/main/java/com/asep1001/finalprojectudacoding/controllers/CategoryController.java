package com.asep1001.finalprojectudacoding.controllers;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.services.CategoryService;
import com.asep1001.finalprojectudacoding.services.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> saveAuthor(
            @Valid @RequestBody Category request
    ) {
        return categoryService.createCategory(request);
    }
}
