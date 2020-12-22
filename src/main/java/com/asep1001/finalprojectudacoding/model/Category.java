package com.asep1001.finalprojectudacoding.model;

import com.asep1001.finalprojectudacoding.services.dto.ExpensesDTO;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
import com.asep1001.finalprojectudacoding.services.mapper.ExpensesMapper;
import com.asep1001.finalprojectudacoding.services.mapper.IncomeMapper;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "category")
@Audited
@AuditTable("category_audit")
public class Category extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonManagedReference("category")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "category",
            orphanRemoval = true
    )
    private List<Income> incomes = new ArrayList<>();

    @JsonManagedReference("category")
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "category",
            orphanRemoval = true
    )
    private List<Expenses> expenses = new ArrayList<>();

    private Function<List<Income>, List<IncomeDTO>> toInDtos() {
        return (x) -> IncomeMapper.INSTANCE.toDtos(x);
    }

    private Function<List<Expenses>, List<ExpensesDTO>> toExDtos() {
        return (x) -> ExpensesMapper.INSTANCE.toDtos(x);
    }

    public List<IncomeDTO> changeToIncomeDto() {
        return this.toInDtos().apply(incomes);
    }

    public List<ExpensesDTO> changeToExpensesDto() {
        return this.toExDtos().apply(expenses);
    }

    public Long getCategoryId() {
        return id;
    }

    public void addExpenses(Expenses item){
        this.expenses.add(item);
        item.setCategory(this);
    }

    public void addIncome(Income item){
        this.incomes.add(item);
        item.setCategory(this);
    }
}
