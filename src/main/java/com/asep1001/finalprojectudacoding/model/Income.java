package com.asep1001.finalprojectudacoding.model;

import com.asep1001.finalprojectudacoding.model.converter.YearMonthIntegerAttributeConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.time.YearMonth;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Table(name = "income")
@Audited
@AuditTable("income_audit")

public class Income extends BaseEntity<String> implements Serializable {

    private static final long serialVersionUID = 1l;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name = "ammount", nullable =false)
    private Double ammount;

    @Column(name = "transaction_date", nullable = false)
    @Convert(converter = YearMonthIntegerAttributeConverter.class)
    private YearMonth transaction_date;

    @JsonBackReference("category")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getIncomeId(){
        return id;
    }
}
