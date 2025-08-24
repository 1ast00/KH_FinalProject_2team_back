package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.ExerciseDTO; // ExerciseDTO import
import com.vitalog.spring_diet.service.ExerciseService; // ExerciseService import
import lombok.RequiredArgsConstructor; // final 필드 생성자를 자동으로 만들어주는 Lombok 어노테이션
import org.springframework.web.bind.annotation.CrossOrigin; // 다른 출처의 요청을 허용 (CORS 설정)

import org.springframework.web.bind.annotation.GetMapping; // HTTP GET 요청 처리 어노테이션
import org.springframework.web.bind.annotation.RequestMapping; // 클래스 레벨에서 공통 URL 경로를 지정
import org.springframework.web.bind.annotation.RestController; // REST API 컨트롤러임을 선언

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