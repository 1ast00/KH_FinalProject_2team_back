package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.ExerciseDTO;
import com.vitalog.spring_diet.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // http://localhost:3000 요청 허용
@RequestMapping("/api/exercise") // 이 컨트롤러의 요청은 /api/exercise로 시작
@RequiredArgsConstructor
public class ExerciseApiController {

    // 서비스 로직을 사용하기 위해 ExerciseService를 주입받음
    private final ExerciseService exerciseService;

    // '/api/exercise/data' 경로의 GET 요청 처리
    @GetMapping("/data")
    // 운동 데이터 리스트를 반환하는 메서드
    public List<ExerciseDTO> getExerciseData() {
        // 서비스의 메서드 호출하고 결과를 바로 반환
        return exerciseService.getExerciseData();
    }
}