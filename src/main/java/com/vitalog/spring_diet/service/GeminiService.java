package com.vitalog.spring_diet.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey; // 기존 헤더용 API 키

    // [추가] Exercise 전용 API 키를 담을 변수
    @Value("${exercise.gemini.api.key}")
    private String exerciseApiKey;

    public String getResponseFromGemini(String prompt) {
        return callGeminiApi(prompt, this.apiKey);
    }

    // [추가] Exercise 전용 API 키를 사용하는 새로운 메소드
    public String getResponseFromGeminiForExercise(String prompt) {
        return callGeminiApi(prompt, this.exerciseApiKey);
    }

    private String callGeminiApi(String prompt, String keyToUse) {
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + keyToUse;

        try {
            // 1. 요청할 JSON 바디(Body)를 문자열로 생성
            Map<String, Object> requestBodyMap = Map.of(
                    "contents", new Object[]{
                            Map.of(
                                    "parts", new Object[]{
                                            Map.of("text", prompt)
                                    }
                            )
                    }
            );
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            // 2. HttpClient 객체 생성
            HttpClient client = HttpClient.newHttpClient();

            // 3. POST 방식의 HttpRequest 객체 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // 4. 요청 전송 및 응답 수신
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 5. 응답받은 JSON에서 원하는 텍스트만 추출
            JsonNode root = objectMapper.readTree(response.body());
            String responseText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            if (responseText.isEmpty()) {
                log.warn("Gemini API로부터 비어있는 응답을 받았습니다. JSON: {}", response.body());
                return "AI가 응답을 생성하지 못했습니다. 질문을 바꿔서 시도해 보세요.";
            }

            return responseText;

        } catch (Exception e) {
            log.error("Gemini API 호출 중 오류 발생", e);
            return "AI 응답을 가져오는 데 실패했습니다.";
        }
    }
}