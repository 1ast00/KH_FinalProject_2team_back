package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.RecipeDTO;
import com.vitalog.spring_diet.mapper.RecipeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeMapper mapper;

    public RecipeService(RecipeMapper mapper) {
        this.mapper = mapper;
    }

    public List<RecipeDTO> selectRecipe() {
        return mapper.selectRecipe();
    }
}
