package com.asep1001.finalprojectudacoding.repository;

import com.asep1001.finalprojectudacoding.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepostitory extends JpaRepository<Category, Long> {
    List<Category> findAllByNameContaining(String categoryName);
}
