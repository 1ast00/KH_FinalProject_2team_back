package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.ExerciseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExerciseMapper {
    List<ExerciseDTO> selectRecommendedExercises();
}