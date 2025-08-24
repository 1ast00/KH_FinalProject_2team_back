package com.vitalog.spring_diet.service;

import com.fasterxml.jackson.core.type.TypeReference; // JSON 배열을 자바 리스트로 변환
import com.fasterxml.jackson.databind.JsonNode; // JSON 데이터를 트리 구조로 다룰 때 사용
import com.fasterxml.jackson.databind.ObjectMapper; // JSON과 자바 객체 변환의 핵심 클래스

import com.vitalog.spring_diet.dto.ExerciseDTO; // ExerciseDTO 클래스 import

import lombok.RequiredArgsConstructor; // final 필드 생성자를 자동으로 만들어주는 Lombok 어노테이션
import org.springframework.beans.factory.annotation.Value; // properties 파일 값을 변수로 가져오는 어노테이션

import org.springframework.http.HttpEntity; // HTTP 요청의 본문과 헤더를 묶는 클래스
import org.springframework.http.HttpHeaders; // HTTP 헤더 정보를 담는 클래스
import org.springframework.http.HttpMethod; // HTTP 요청 방식(GET, POST 등)을 정의
import org.springframework.http.ResponseEntity; // HTTP 응답 전체를 담는 클래스

import org.springframework.stereotype.Service; // 이 클래스가 서비스 Bean임을 선언
import org.springframework.web.client.RestTemplate; // 외부 API 통신에 사용하는 HTTP 클라이언트

import java.util.List; // List import

@Service
@RequiredArgsConstructor
public class ExerciseService {

    // 의존성 주입으로 RestTemplate Bean을 받음
    private final RestTemplate restTemplate;

    // properties 파일에서 api.service-key 값 주입(운동 정보 API 인증키)
    @Value("${api.service-key}")
    private String serviceKey;

    // ✨ 운동 데이터 가져오는 메서드
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