package com.vitalog.spring_diet.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitalog.spring_diet.dto.ExerciseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    // 의존성 주입으로 RestTemplate Bean을 받음
    private final RestTemplate restTemplate;

    // properties 파일에서 exercise.api.service-key 값 주입(운동 정보 API 인증키)
    @Value("${exercise.api.service-key}")
    private String serviceKey;

    // 운동 데이터 가져오는 메서드
    public List<ExerciseDTO> getExerciseData() {

        try {
            // 호출할 API URL 주소
            String apiUrl = "https://api.odcloud.kr/api/15068730/v1/uddi:e57a5dba-bbbf-414e-a5cd-866c48378daa?page=1&perPage=1000";

            // HTTP 요청 헤더 생성
            HttpHeaders headers = new HttpHeaders();

            // 헤더에 인증키 추가(헤더로 인증키 보낼 때 Key : Authorization, Value : Infuser로 보내라고 되어있음)
            headers.set("Authorization", "Infuser " + this.serviceKey);

            // 헤더를 HTTP 요청에 포함
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            // RestTemplate으로 API를 호출하고 응답을 받음
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            // JSON 변환을 위한 ObjectMapper 생성
            ObjectMapper mapper = new ObjectMapper();

            // 응답받은 JSON을 트리 구조로 읽음
            JsonNode root = mapper.readTree(response.getBody());

            // JSON에서 'data' 부분만 추출
            JsonNode dataNode = root.path("data");

            // 'data' 부분을 ExerciseDTO 리스트로 변환하여 반환
            return mapper.readValue(dataNode.toString(), new TypeReference<List<ExerciseDTO>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}