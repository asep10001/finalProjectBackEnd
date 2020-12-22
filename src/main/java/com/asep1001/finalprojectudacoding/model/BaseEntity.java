package com.asep1001.finalprojectudacoding.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties(
        value = {"created", "lastModified"},
        allowGetters = true
)
public abstract class BaseEntity<U> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @CreatedDate
    protected LocalDateTime created;

    @CreatedBy
    protected U createdBy;

    @LastModifiedDate
    protected LocalDateTime lastModified;

    @LastModifiedBy
    protected U lastModifiedBy;
}
