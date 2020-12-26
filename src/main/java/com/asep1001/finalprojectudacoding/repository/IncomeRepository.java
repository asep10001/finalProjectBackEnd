package com.asep1001.finalprojectudacoding.repository;

import com.asep1001.finalprojectudacoding.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByNameContaining(String incomeName);
    List<Income> findAllByCategory_NameContaining(String categoryName);
}
