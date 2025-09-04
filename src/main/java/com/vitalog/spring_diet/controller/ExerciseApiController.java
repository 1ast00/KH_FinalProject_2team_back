package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.ExerciseDTO;
import com.vitalog.spring_diet.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseApiController {

    private final ExerciseService exerciseService;

    // '/api/exercise/recommendations' 경로의 GET 요청 처리(운동 추천) + 0904 추가
    @GetMapping("/recommendations")
    public List<ExerciseDTO> getRecommendedExercises() {
        return exerciseService.getRecommendedExercises();
    }

    @GetMapping("/data")
    public List<ExerciseDTO> getExerciseData() {
        return exerciseService.getExerciseData();
    }
}