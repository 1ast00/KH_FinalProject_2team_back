// src/main/java/com/vitalog/spring_diet/controller/ReportController.java
package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.service.AdminReportService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

    private final AdminReportService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateReportReq req) {
        // 타입 간단 검증
        String t = req.getTargetType();
        if (t == null || !(t.equals("MEAL_POST") || t.equals("MEAL_COMMENT")
                || t.equals("REVIEW_POST") || t.equals("REVIEW_COMMENT"))) {
            return ResponseEntity.badRequest().body("invalid targetType");
        }
        long id = service.createReport(req.getTargetType(), req.getTargetId(), req.getReporterMno());
        return ResponseEntity.ok().body(id); // 생성된 report_id 반환
    }

    @Data
    public static class CreateReportReq {
        private String targetType; // MEAL_POST | MEAL_COMMENT | REVIEW_POST | REVIEW_COMMENT
        private Long targetId;     // 대상 PK
        private Long reporterMno;  // 신고자
    }
}
