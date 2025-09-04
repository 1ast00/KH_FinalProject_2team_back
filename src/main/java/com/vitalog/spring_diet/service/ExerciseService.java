package com.vitalog.spring_diet.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitalog.spring_diet.dto.ExerciseDTO;
import com.vitalog.spring_diet.mapper.ExerciseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    @Value("${exercise.api.service-key}")
    private String serviceKey;

    // DB 연동(ExerciseMapper를 의존성으로 주입받음)
    private final ExerciseMapper exerciseMapper;

    // MyBatis를 사용하여 DB에서 운동 추천 데이터를 가져오는 메서드
    public List<ExerciseDTO> getRecommendedExercises() {
        return exerciseMapper.selectRecommendedExercises();
    }

    public List<ExerciseDTO> getExerciseData() {
        try {
            String apiUrl = "https://api.odcloud.kr/api/15068730/v1/uddi:e57a5dba-bbbf-414e-a5cd-866c48378daa?page=1&perPage=1000";

            // 1. HttpClient 객체 생성
            HttpClient client = HttpClient.newHttpClient();

            // 2. HttpRequest 객체 생성 (요청 내용 설정)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl)) // 요청 URL 설정
                    .header("Authorization", "Infuser " + this.serviceKey) // 헤더 설정
                    .GET() // GET 메서드 사용
                    .build();

            // 3. 요청 전송 및 응답 수신
            // client.send(request, 응답 본문을 받을 방법)
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. 응답 본문(JSON 문자열) 파싱
            String responseBody = response.body();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            JsonNode dataNode = root.path("data");

            return mapper.readValue(dataNode.toString(), new TypeReference<List<ExerciseDTO>>() {});

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}