// src/main/java/com/vitalog/spring_diet/controller/AdminReportController.java
package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.admindashboard.AdminReportDetailDTO;
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
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String q) {
        return ResponseEntity.ok(service.getPage(status, type, q, p, size));
    }

    @PatchMapping("/{reportId}/status")
    public ResponseEntity<?> updateStatus(@PathVariable long reportId,
                                          @RequestParam String status) {
        return service.updateStatus(reportId, status) ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    //  상세
    @GetMapping("/{reportId}")
    public ResponseEntity<?> detail(@PathVariable long reportId) {
        AdminReportDetailDTO dto = service.getDetail(reportId);
        return dto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    //  처리 완료(프론트에서 /resolve 를 호출하므로 매핑만 맞춰줌)
    @PatchMapping("/{reportId}/resolve")
    public ResponseEntity<?> resolve(@PathVariable long reportId,
                                     @RequestParam(required = false) Long resolverMno) {
        return service.updateStatus(reportId, "DONE") ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }

    // 삭제 (프론트에서 사용)
    @DeleteMapping("/{reportId}")
    public ResponseEntity<?> delete(@PathVariable long reportId) {
        return service.delete(reportId) ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().build();
    }
}
