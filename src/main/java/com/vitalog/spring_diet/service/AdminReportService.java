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
        int offset = (Math.max(p, 1) - 1) * size;
        List<Map<String, Object>> items = mapper.selectReportsPage(status, type, q, offset, size);
        int total = mapper.countReportsPage(status, type, q);

        Map<String, Object> paging = new HashMap<>();
        paging.put("currentPage", p);
        paging.put("pageSize", size);
        paging.put("totalCount", total);
        paging.put("totalPage", (int)Math.ceil((double)total / size));

        Map<String, Object> res = new HashMap<>();
        res.put("items", items);
        res.put("paging", paging);
        return res;
    }

    public boolean updateStatus(long reportId, String status) {
        return mapper.updateReportStatus(reportId, status) > 0;
    }

    //  생성
    public long createReport(String targetType, long targetId, long reporterMno) {
        mapper.insertReport(targetType, targetId, reporterMno, "PENDING");
        // report_id는 시퀀스/트리거로 생성. 필요하다면 반환값을 SELECT MAX … 로 가져오지만
        // 관리자 화면에서는 목록 재조회로 충분.
        return 0L;
    }

    //  상세
    public AdminReportDetailDTO getDetail(long reportId) {
        return mapper.selectReportDetail(reportId);
    }

    //  삭제
    public boolean delete(long reportId) {
        return mapper.deleteReport(reportId) > 0;
    }
}
