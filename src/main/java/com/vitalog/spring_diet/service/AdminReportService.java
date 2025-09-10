// src/main/java/com/vitalog/spring_diet/service/AdminReportService.java
package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.admindashboard.AdminReportDetailDTO;
import com.vitalog.spring_diet.mapper.AdminReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminReportService {

    private final AdminReportMapper mapper;

    public Map<String, Object> getPage(String status, String type, String q, int p, int size) {
        int page = Math.max(p, 1);
        int pageSize = Math.max(1, size);
        int offset = (page - 1) * pageSize;

        List<Map<String, Object>> items = mapper.selectReportsPage(status, type, q, offset, pageSize);
        int total = mapper.countReportsPage(status, type, q);

        Map<String, Object> paging = new HashMap<>();
        paging.put("currentPage", page);
        paging.put("pageSize", pageSize);
        paging.put("totalCount", total);
        paging.put("totalPage", (int) Math.ceil((double) total / pageSize));

        Map<String, Object> res = new HashMap<>();
        res.put("items", items);
        res.put("paging", paging);
        return res;
    }

    public boolean updateStatus(long reportId, String status) {
        return mapper.updateReportStatus(reportId, status) > 0;
    }

    public long createReport(String targetType, long targetId, long reporterMno) {
        Map<String, Object> param = new HashMap<>();
        param.put("targetType", targetType);
        param.put("targetId", targetId);
        param.put("reporterMno", reporterMno);
        param.put("status", "PENDING");
        mapper.insertReport(param); // <selectKey order="AFTER">가 reportId를 세팅
        return ((Number) param.get("reportId")).longValue();
    }

    public AdminReportDetailDTO getDetail(long reportId) {
        AdminReportDetailDTO dto = mapper.selectReportDetail(reportId);
        if (dto == null) return null;

        // 한글 상태 라벨 (PENDING → 대기, RESOLVED/DONE → 처리완료)
        String s = dto.getStatus();
        dto.setStatusKo(("RESOLVED".equalsIgnoreCase(s) || "DONE".equalsIgnoreCase(s)) ? "처리완료" : "대기");
        return dto;
    }

    public boolean delete(long reportId) {
        return mapper.deleteReport(reportId) > 0;
    }
}
