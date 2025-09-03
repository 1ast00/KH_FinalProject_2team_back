package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.RecipeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeMapper {
    List<RecipeDTO> selectRecipe();
}
