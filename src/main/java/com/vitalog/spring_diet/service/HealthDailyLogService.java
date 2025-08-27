package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.HealthDailyLogDTO;
import com.vitalog.spring_diet.mapper.HealthDailyLogMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HealthDailyLogService {
    private final HealthDailyLogMapper mapper;
    // 타입 명시 간소화 (동일 클래스)
    private final MemberService memberService;

    public HealthDailyLogService(HealthDailyLogMapper mapper, MemberService memberService) {
        this.mapper = mapper;
        this.memberService = memberService;
    }

    public List<HealthDailyLogDTO> list(int mno, Integer cursor, int limit, String date) {
        Map<String, Object> p = new HashMap<>();
        p.put("mno", mno);
        p.put("cursor", cursor);
        p.put("limit", limit);
        p.put("date", date);
        return mapper.selectMyLogs(p);
    }

    public int nextHno() {
        return mapper.selectNextHno();
    }

    //  날짜 중복 체크: Map 호출 -> 파라미터 호출로 변경
    public boolean existsDate(int mno, String hdate) {
        if (hdate == null || hdate.isBlank()) return false;
        return mapper.countByDate(mno, hdate) > 0;
    }
    //

    //  create 전에 날짜 중복 방지 체크 추가
    public int create(HealthDailyLogDTO dto) {
        // 같은 날짜 이미 있으면 막기
        if (existsDate(dto.getMno(), dto.getHdate())) {
            return 0; // 컨트롤러에서 code/msg 처리
        }

        int count = mapper.insertHealthDailyLog(dto);

        // 가장 최근 일지라면 member.weight 갱신
        if (count > 0 && dto.getWeight() != null) {
            Integer latestHno = mapper.selectLatestHno(dto.getMno());
            if (latestHno != null && latestHno.equals(dto.getHno())) {
                memberService.updateWeight(dto.getMno(), dto.getWeight());
            }
        }
        return count;
    }

    public int update(HealthDailyLogDTO dto) {
        int count = mapper.updateHealthDailyLog(dto);

        // 가장 최근 일지라면 member.weight 갱신
        if (count > 0 && dto.getWeight() != null) {
            Integer latestHno = mapper.selectLatestHno(dto.getMno());
            if (latestHno != null && latestHno.equals(dto.getHno())) {
                memberService.updateWeight(dto.getMno(), dto.getWeight());
            }
        }
        return count;
    }

    public int delete(int hno, int mno) {
        return mapper.deleteHealthDailyLog(Map.of("hno", hno, "mno", mno));
    }
}
