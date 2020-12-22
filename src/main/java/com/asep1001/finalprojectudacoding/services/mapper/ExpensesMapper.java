package com.asep1001.finalprojectudacoding.services.mapper;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.model.Expenses;
import com.asep1001.finalprojectudacoding.services.dto.ExpensesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = CategoryMapper.class)
public interface ExpensesMapper {

    ExpensesMapper INSTANCE = Mappers.getMapper(ExpensesMapper.class);

    @Mapping(target = "id", expression = "java(getExpensesId())")
    @Mapping(source = "category", target="category_name", qualifiedByName = "getCategoryName")
    ExpensesDTO toDto(Expenses entity);
    Expenses toEntity(ExpensesDTO dto);

    List<ExpensesDTO> toDtos(List<Expenses> entities);
    List<Expenses> toEntities(List<ExpensesDTO> dtos);

    @Named("getCategoryName")
    default String categoryEntityGetCategoryName(Category categoryEntity) {
        return categoryEntity.getCategory_name();
    }
}
