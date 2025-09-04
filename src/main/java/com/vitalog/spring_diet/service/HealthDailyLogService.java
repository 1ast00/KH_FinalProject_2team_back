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

    public boolean existsDate(int mno, String hdate) {
        if (hdate == null || hdate.isBlank()) return false;
        return mapper.countByDate(mno, hdate) > 0;
    }

    public int create(HealthDailyLogDTO dto) {
        if (existsDate(dto.getMno(), dto.getHdate())) return 0;

        int count = mapper.insertHealthDailyLog(dto);

        // 최신 일지라면 member.weight 갱신(hweight)
        if (count > 0 && dto.getHweight() != null) {
            Integer latestHno = mapper.selectLatestHno(dto.getMno());
            if (latestHno != null && latestHno.equals(dto.getHno())) {
                memberService.updateWeight(dto.getMno(), dto.getHweight());
            }
        }
        return count;
    }

    public int update(HealthDailyLogDTO dto) {
        int count = mapper.updateHealthDailyLog(dto);

        // 최신 일지라면 member.weight 갱신(hweight)
        if (count > 0 && dto.getHweight() != null) {
            Integer latestHno = mapper.selectLatestHno(dto.getMno());
            if (latestHno != null && latestHno.equals(dto.getHno())) {
                memberService.updateWeight(dto.getMno(), dto.getHweight());
            }
        }
        return count;
    }

    public int delete(int hno, int mno) {
        int count = mapper.deleteHealthDailyLog(Map.of("hno", hno, "mno", mno));

        // 0903 최신 글 삭제 시 다음 최신 체중으로 회원 체중 갱신 - 시작
        if (count > 0) {
            Double latestW = mapper.selectLatestWeight(mno); // 없으면 null
            if (latestW != null) {
                memberService.updateWeight(mno, latestW);
            }
        }
        // 0903 최신 글 삭제 시 다음 최신 체중으로 회원 체중 갱신 - 끝

        return count;
    }
}
