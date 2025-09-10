package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.service.AdminReportService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports") // 팀원 것과 충돌 안 하게 하위 경로만 사용
public class UserReportCreateController {

    private final AdminReportService service;

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody CreateReportReq req) {
        String t = req.getTargetType();
        if (t == null || !(t.equals("MEAL_POST") || t.equals("MEAL_COMMENT")
                || t.equals("REVIEW_POST") || t.equals("REVIEW_COMMENT"))) {
            return ResponseEntity.badRequest().build();
        }
        long id = service.createReport(req.getTargetType(), req.getTargetId(), req.getReporterMno());
        return ResponseEntity.ok(id);
    }

    @Data
    public static class CreateReportReq {
        private String targetType;
        private Long targetId;
        private Long reporterMno;
    }
}
