package com.vitalog.spring_diet.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    // ExerciseConfig에 이미 Bean으로 등록된 RestTemplate을 주입받기
    private final RestTemplate restTemplate;

    // application.properties에서 API 키를 주입받기
    @Value("${gemini.api.key}")
    private String apiKey;

    public String getResponseFromGemini(String prompt) {
        // 1. Gemini API 호출을 위한 URL
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        // 2. HTTP 헤더 설정 (Postman에서 했던 것과 동일)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3. HTTP 바디(Body) 생성 (Postman에서 raw JSON으로 넣었던 부분)
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of(
                                "parts", new Object[] {
                                        Map.of("text", prompt)
                                }
                        )
                }
        );

        // 4. 헤더와 바디를 합쳐서 HTTP 요청 객체 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 5. RestTemplate을 사용해 POST 요청 보내고 응답(JSON) 받기
            String jsonResponse = restTemplate.postForObject(apiUrl, entity, String.class);

            // 6. 응답받은 JSON에서 원하는 텍스트만 추출하기 (ExerciseService에서 했던 방식)
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            // JSON 경로: candidates -> 0 -> content -> parts -> 0 -> text
            String responseText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            return responseText;

        } catch (Exception e) {
            log.error("Gemini API 호출 중 오류 발생", e);
            return "AI 응답을 가져오는 데 실패했습니다.";
        }
    }
}