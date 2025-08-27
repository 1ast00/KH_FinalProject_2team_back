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

    public int nextHno() { return mapper.selectNextHno(); }

    public int create(HealthDailyLogDTO dto) {
        int count = mapper.insertHealthDailyLog(dto);

        // 가장 최근 일지라면 member.weight 갱신
        if (count > 0 && dto.getWeight() != null) {
            Integer latestHno = mapper.selectLatestHno(dto.getMno());
            if (latestHno != null && latestHno == dto.getHno()) {
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
            if (latestHno != null && latestHno == dto.getHno()) {
                memberService.updateWeight(dto.getMno(), dto.getWeight());
            }
        }
        return count;
    }

    public int delete(int hno, int mno) {
        return mapper.deleteHealthDailyLog(Map.of("hno", hno, "mno", mno));
    }
}