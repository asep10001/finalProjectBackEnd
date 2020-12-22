package com.asep1001.finalprojectudacoding.services.mapper;

import com.asep1001.finalprojectudacoding.model.Category;
import com.asep1001.finalprojectudacoding.model.Income;
import com.asep1001.finalprojectudacoding.services.dto.IncomeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = CategoryMapper.class)
public interface IncomeMapper {

    IncomeMapper INSTANCE = Mappers.getMapper(IncomeMapper.class);

    @Mapping(target = "id", expression = "java(entity.getIncomeId())")
    @Mapping(source = "category", target = "category_name", qualifiedByName = "getCategoryName")
    IncomeDTO toDto(Income entity);
//    Income toEntity(IncomeDTO dto);

    List<IncomeDTO> toDtos(List<Income> entities);
//    List<Income> toEntities(List<IncomeDTO> dtos);

    @Named("getCategoryName")
    default String categoryEntityGetCategoryName(Category categoryEntity) {
        return categoryEntity.getCategory_name();
    }
}
