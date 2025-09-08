package com.vitalog.spring_diet.service;

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
}
