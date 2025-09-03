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
    private String apiKey;

    public GeminiService() {
    }

    public String getResponseFromGemini(String prompt) {
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        try {
            // 1. 요청할 JSON 바디(Body)를 문자열로 생성
            // ObjectMapper를 사용해 Map을 JSON 문자열로 변환
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

            // 2. HttpClient 객체 생성
            HttpClient client = HttpClient.newHttpClient();

            // 3. POST 방식의 HttpRequest 객체 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json") // 헤더 설정
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // POST 메서드와 바디 설정
                    .build();

            // 4. 요청 전송 및 응답 수신
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 5. 응답받은 JSON에서 원하는 텍스트만 추출
            JsonNode root = objectMapper.readTree(response.body());
            String responseText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            // 응답이 비어있으면 AI가 부적절한 답변 등으로 응답 생성을 거부한 경우일 수 있음
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