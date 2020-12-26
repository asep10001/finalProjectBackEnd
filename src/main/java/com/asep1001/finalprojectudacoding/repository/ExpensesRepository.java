package com.asep1001.finalprojectudacoding.repository;

import com.asep1001.finalprojectudacoding.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    List<Expenses> findAllByCategory_NameContaining(String categoryName);
    List<Expenses> findAllByNameContaining(String expensesName);
}
