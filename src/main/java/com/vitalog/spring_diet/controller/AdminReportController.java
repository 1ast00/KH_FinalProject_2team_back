package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.service.AdminReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/reports")
public class AdminReportController {

    private final AdminReportService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") int p,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,   // PENDING/DONE/ALL
            @RequestParam(required = false) String type,     // MEAL_POST/...
            @RequestParam(required = false) String q
    ) {
        return ResponseEntity.ok(service.getPage(status, type, q, p, size));
    }

    @PatchMapping("/{reportId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable long reportId,
                                          @RequestParam String status) {
        boolean ok = service.updateStatus(reportId, status);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
