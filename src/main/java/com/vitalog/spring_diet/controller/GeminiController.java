// src/main/java/com/vitalog/spring_diet/controller/GeminiController.java

package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.GeminiDTO;
import com.vitalog.spring_diet.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> getAiResponse(@RequestBody GeminiDTO geminiDTO) {
        String prompt = geminiDTO.getPrompt();

        // 서비스에서 에러 처리까지 모두 담당하므로, 컨트롤러는 호출하고 결과만 받으면 됨
        String response = geminiService.getResponseFromGemini(prompt);

        // 프론트엔드에 JSON 형태로 응답 반환
        return ResponseEntity.ok(Map.of("response", response));
    }
}