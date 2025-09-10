package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.BoardWeeklyDTO;
import com.vitalog.spring_diet.dto.BoardWeeklyParticipantDTO;
import com.vitalog.spring_diet.dto.BoardWeeklyRankingDTO;
import com.vitalog.spring_diet.mapper.BoardWeeklyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardWeeklyService {

    private final BoardWeeklyMapper boardWeeklyMapper;

    // [수정] 람다와 스트림을 사용하지 않는 일반 for문 로직으로 변경
    public Map<Integer, BoardWeeklyRankingDTO> getWeeklyChampions() {
        // 1. DB에서 이번 주 챌린지 참여자 목록을 가져옵니다.
        List<BoardWeeklyParticipantDTO> participants = boardWeeklyMapper.selectWeeklyChampions();

        // 2. 참여자들을 연령대별로 그룹화합니다.
        //    (결과물: {10=[10대 A, 10대 B], 20=[20대 C], ...})
        Map<Integer, List<BoardWeeklyParticipantDTO>> participantsByAgeGroup = new HashMap<>();
        for (BoardWeeklyParticipantDTO p : participants) {
            int ageGroup = p.getAgeGroup();
            // 해당 연령대 그룹이 맵에 아직 없으면 새로 만들어줍니다.
            if (!participantsByAgeGroup.containsKey(ageGroup)) {
                participantsByAgeGroup.put(ageGroup, new ArrayList<>());
            }
            // 해당 연령대 그룹 리스트에 참여자를 추가합니다.
            participantsByAgeGroup.get(ageGroup).add(p);
        }

        // 3. 각 연령대 그룹별로 챔피언(1위)을 찾아 최종 결과 맵을 만듭니다.
        Map<Integer, BoardWeeklyRankingDTO> championsMap = new HashMap<>();
        // participantsByAgeGroup 맵의 각 항목(연령대와 참여자 리스트)을 순회합니다.
        for (Map.Entry<Integer, List<BoardWeeklyParticipantDTO>> entry : participantsByAgeGroup.entrySet()) {
            int ageGroup = entry.getKey();
            List<BoardWeeklyParticipantDTO> groupParticipants = entry.getValue();

            // 해당 그룹에 참여자가 없으면 건너뜁니다.
            if (groupParticipants.isEmpty()) {
                continue;
            }

            // 첫 번째 참여자를 일단 챔피언으로 가정합니다.
            BoardWeeklyParticipantDTO champion = groupParticipants.get(0);

            // 두 번째 참여자부터 마지막 참여자까지 반복하며 진짜 챔피언을 찾습니다.
            for (int i = 1; i < groupParticipants.size(); i++) {
                BoardWeeklyParticipantDTO challenger = groupParticipants.get(i); // 도전자

                // [비교 기준 1] 목표 체중과의 차이
                double championDiff = Math.abs(champion.getWeight() - champion.getGoalWeight());
                double challengerDiff = Math.abs(challenger.getWeight() - challenger.getGoalWeight());

                if (challengerDiff < championDiff) {
                    // 도전자의 목표 체중 차이가 더 작으면, 도전자를 새로운 챔피언으로 설정
                    champion = challenger;
                } else if (challengerDiff == championDiff) {
                    // [비교 기준 2] 목표 체중 차이가 같다면, 참여 시간으로 비교
                    if (challenger.getParticipationTime().isBefore(champion.getParticipationTime())) {
                        // 도전자의 참여 시간이 더 빠르면, 도전자를 새로운 챔피언으로 설정
                        champion = challenger;
                    }
                }
            }

            // 최종적으로 선출된 챔피언을 프론트엔드로 보낼 DTO로 변환합니다.
            BoardWeeklyRankingDTO championDTO = convertToRankingDTO(champion);
            // 최종 결과 맵에 추가합니다.
            championsMap.put(ageGroup, championDTO);
        }

        return championsMap;
    }

    // --- 아래 헬퍼 메소드 및 다른 메소드들은 변경 없음 ---

    private BoardWeeklyRankingDTO convertToRankingDTO(BoardWeeklyParticipantDTO p) {
        BoardWeeklyRankingDTO dto = new BoardWeeklyRankingDTO();
        dto.setNickname(p.getNickname());
        dto.setBmi(calculateBmi(p.getWeight(), p.getHeight()));
        dto.setGoalWeight(p.getGoalWeight());
        dto.setAgeGroup(p.getAgeGroup());
        return dto;
    }

    private double calculateBmi(double weight, double height) {
        if (height == 0) return 0;
        double heightInMeter = height / 100.0;
        return Math.round((weight / (heightInMeter * heightInMeter)) * 100) / 100.0;
    }

    public boolean checkParticipationStatus(int mno) {
        return boardWeeklyMapper.checkParticipation(mno) > 0;
    }

    @Transactional
    public Map<String, Object> joinChallenge(int mno, int ageGroup) {
        if (checkParticipationStatus(mno)) {
            return Map.of("success", false, "message", "이미 이번 주 챌린지에 참여하셨습니다.");
        }
        BoardWeeklyDTO participation = new BoardWeeklyDTO();
        participation.setMno(mno);
        participation.setBwage_group(ageGroup);

        boardWeeklyMapper.insertParticipation(participation);
        return Map.of("success", true, "message", "참여가 완료되었습니다.");
    }

    @Transactional
    public Map<String, Object> cancelChallenge(int mno) {
        boardWeeklyMapper.deleteParticipation(mno);
        return Map.of("success", true, "message", "참여가 취소되었습니다.");
    }
}