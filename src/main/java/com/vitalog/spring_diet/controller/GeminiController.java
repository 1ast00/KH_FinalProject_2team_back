package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.GeminiDTO;
import com.vitalog.spring_diet.service.GeminiService;
import com.vitalog.spring_diet.service.HealthDailyLogGeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;
    private final HealthDailyLogGeminiService healthDailyLogGeminiService; // 0907 추가

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> getAiResponse(@RequestBody GeminiDTO geminiDTO) {
        String prompt = geminiDTO.getPrompt();
        String response = geminiService.getResponseFromGemini(prompt);
        return ResponseEntity.ok(Map.of("response", response));
    }

    // [추가] ExerciseDetailPage 전용 AI 채팅 경로
    @PostMapping("/ExerciseChat")
    public ResponseEntity<Map<String, String>> getExerciseAiResponse(@RequestBody GeminiDTO geminiDTO) {
        String prompt = geminiDTO.getPrompt();
        // [수정] exerciseApiKey를 사용하는 새 메소드 호출
        String response = geminiService.getResponseFromGeminiForExercise(prompt);
        return ResponseEntity.ok(Map.of("response", response));
    }

    @PostMapping("/foodChat")
    public ResponseEntity<Map<String,String>> getFoodAiResponse(@RequestBody GeminiDTO geminiDTO){
        String prompt = geminiDTO.getPrompt();
        String response = geminiService.getResponseFromGemini(prompt);
        return ResponseEntity.ok(Map.of("response",response));
    }

    /* 0907 sss_log 추가 - 시작 */
    @PostMapping("/healthdailylogchat")
    public ResponseEntity<Map<String,String>> getHealthDailyLogAiResponse(@RequestBody GeminiDTO geminiDTO) {
        String prompt = geminiDTO.getPrompt();
        String response = healthDailyLogGeminiService.getResponseFromGemini(prompt);
        return ResponseEntity.ok(Map.of("response", response));
    }
    /* 0907 sss_log 추가 - 끝 */
}