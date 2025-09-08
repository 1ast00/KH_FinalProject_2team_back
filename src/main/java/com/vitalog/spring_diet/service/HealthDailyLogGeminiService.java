// src/main/java/com/vitalog/spring_diet/service/HealthDailyLogGeminiService.java
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
public class HealthDailyLogGeminiService {

    // 0907 전용 키(.env / 환경변수 / properties 모두 지원) - 시작
    @Value("${HEALTH_DAILY_LOG_GEMINI_API_KEY:#{null}}")
    private String apiKeyProp;
    private String resolveApiKey() {
        String env = System.getenv("HEALTH_DAILY_LOG_GEMINI_API_KEY");
        return (apiKeyProp != null && !apiKeyProp.isBlank()) ? apiKeyProp : env;
    }
    // 0907 전용 키 - 끝

    public String getResponseFromGemini(String prompt) {
        String apiKey = resolveApiKey();
        if (apiKey == null || apiKey.isBlank()) {
            log.error("HEALTH_DAILY_LOG_GEMINI_API_KEY 가 설정되지 않았습니다.");
            return "AI 키가 설정되지 않아 피드백을 생성할 수 없습니다.";
        }

        // 0907 모델: gemini-2.5-flash - 시작
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        // 0907 모델 - 끝

        try {
            Map<String, Object> requestBodyMap = Map.of(
                    "contents", new Object[] {
                            Map.of(
                                    "parts", new Object[] {
                                            Map.of("text", prompt)
                                    }
                            )
                    }
            );
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(requestBodyMap);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonNode root = objectMapper.readTree(response.body());
            String responseText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            if (responseText.isEmpty()) {
                log.warn("Gemini(API H-DL) 빈 응답. JSON: {}", response.body());
                return "간단 피드백을 만들지 못했어요. 내용을 조금만 바꿔 다시 시도해 주세요.";
            }
            return responseText;

        } catch (Exception e) {
            log.error("Gemini(API H-DL) 호출 오류", e);
            return "AI 피드백 생성 중 오류가 발생했어요.";
        }
    }
}

