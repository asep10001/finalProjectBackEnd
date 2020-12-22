package com.asep1001.finalprojectudacoding.services.mapper;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.services.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {IncomeMapper.class})
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", expression = "java(entity.getCategoryId())" )
    @Mapping(target = "incomes", expression = "java(entity.changeToIncomeDto())")
    @Mapping(target = "expenses", expression = "java(entity.changeToExpensesDto())")
    CategoryDTO toDto(Category entity);
//    Category toEntity(CategoryDTO dto);

    List<CategoryDTO> toDtos(List<Category> entities);
//    List<Category> toEntitites(List<CategoryDTO> dtos);
}
