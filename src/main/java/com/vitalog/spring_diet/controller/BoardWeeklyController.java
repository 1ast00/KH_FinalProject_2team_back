package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.BoardWeeklyRankingDTO;
import com.vitalog.spring_diet.service.BoardWeeklyService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // [수정] RequestBody를 포함한 전체 import

import java.util.Map;

@RestController
@RequestMapping("/api/weekly")
@RequiredArgsConstructor
public class BoardWeeklyController {

    private final BoardWeeklyService boardWeeklyService;

    //챌린지 참여 요청 시 ageGroup 값을 받기 위한 DTO
    @Data
    private static class JoinRequestDto {
        private int ageGroup;
    }

    //연령대별 챔피언 목록 조회 API
    @GetMapping("/champions")
    public ResponseEntity<Map<Integer, BoardWeeklyRankingDTO>> getWeeklyChampions() {
        System.out.println(">>> 컨트롤러: /api/weekly/champions 요청 접수");
        Map<Integer, BoardWeeklyRankingDTO> champions = boardWeeklyService.getWeeklyChampions();
        System.out.println(">>> 컨트롤러: 서비스로부터 결과 받고 응답 보냄");
        return ResponseEntity.ok(champions);
    }

    //현재 사용자의 참여 상태 확인 API
    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> getParticipationStatus(@RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        int mno = Integer.parseInt(authenticatedUserMno);
        boolean hasParticipated = boardWeeklyService.checkParticipationStatus(mno);
        return ResponseEntity.ok(Map.of("hasParticipated", hasParticipated));
    }

    //챌린지 참여 요청 API
    @PostMapping("/participate")
    public ResponseEntity<Map<String, Object>> joinChallenge(
            @RequestAttribute("authenticatedUsermno") String authenticatedUserMno,
            @RequestBody JoinRequestDto request) { // <-- @RequestBody 추가 및 DTO 적용

        int mno = Integer.parseInt(authenticatedUserMno);
        int ageGroup = request.getAgeGroup(); // <-- 요청 본문에서 ageGroup 값 추출

        // 서비스 호출 시 ageGroup
        Map<String, Object> response = boardWeeklyService.joinChallenge(mno, ageGroup);
        return ResponseEntity.ok(response);
    }

    //챌린지 참여 취소 API
    @DeleteMapping("/participate")
    public ResponseEntity<Map<String, Object>> cancelChallenge(@RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        int mno = Integer.parseInt(authenticatedUserMno);
        Map<String, Object> response = boardWeeklyService.cancelChallenge(mno);
        return ResponseEntity.ok(response);
    }
}