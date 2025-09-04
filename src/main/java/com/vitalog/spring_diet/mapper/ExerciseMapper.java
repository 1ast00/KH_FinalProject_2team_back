// src/main/java/com/vitalog/spring_diet/mapper/ExerciseMapper.java

package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.ExerciseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExerciseMapper {
    // selectRecommendedExercises 메소드에 String 파라미터 추가
    List<ExerciseDTO> selectRecommendedExercises(String exerciseType);
}