// src/main/java/com/vitalog/spring_diet/controller/ExerciseApiController.java

package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.ExerciseDTO;
import com.vitalog.spring_diet.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // @RequestParam import 추가
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
@RequiredArgsConstructor
public class ExerciseApiController {

    private final ExerciseService exerciseService;

    // getRecommendedExercises 메소드에 @RequestParam 파라미터 추가
    @GetMapping("/recommendations")
    public List<ExerciseDTO> getRecommendedExercises(@RequestParam("exerciseType") String exerciseType) {
        return exerciseService.getRecommendedExercises(exerciseType);
    }

    @GetMapping("/data")
    public List<ExerciseDTO> getExerciseData() {
        return exerciseService.getExerciseData();
    }
}