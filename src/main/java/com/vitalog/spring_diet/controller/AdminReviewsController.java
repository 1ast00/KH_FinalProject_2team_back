package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.admindashboard.AdminReviewDetailDTO;
import com.vitalog.spring_diet.dto.admindashboard.AdminReviewsPageDTO;
import com.vitalog.spring_diet.service.AdminReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/reviews")
public class AdminReviewsController {

    private final AdminReviewsService service;

    // ===== 목록 =====
    @GetMapping
    public ResponseEntity<AdminReviewsPageDTO> list(
            @RequestParam(defaultValue = "1") int p,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String status // ALL | POSTED | HIDDEN | "1" | "0"
    ) {
        Integer statusNum = null;
        if (status != null) {
            switch (status.toUpperCase()) {
                case "1":
                case "POSTED": statusNum = 1; break;
                case "0":
                case "HIDDEN": statusNum = 0; break;
                default: statusNum = null; // ALL or 기타
            }
        }

        int page = Math.max(p, 1);
        int pageSize = Math.max(1, Math.min(size, 100));

        return ResponseEntity.ok(service.getPage(q, statusNum, page, pageSize));
    }

    // ===== 상세 =====
    @GetMapping("/{brno}")
    public ResponseEntity<?> detail(@PathVariable Long brno) {
        AdminReviewDetailDTO dto = service.getDetail(brno);
        return (dto == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }

    // ===== 상태 변경(게시/숨김) =====
    @PatchMapping(value = "/{brno}/status", params = "posted")
    public ResponseEntity<Void> setStatusByQuery(
            @PathVariable long brno,
            @RequestParam boolean posted
    ) {
        boolean ok = service.toggleStatus(brno, posted);
        return ok ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }


    public static class StatusReq { public boolean posted; }
    @PatchMapping(value = "/{brno}/status", consumes = "application/json")
    public ResponseEntity<Void> setStatusByBody(
            @PathVariable long brno,
            @RequestBody StatusReq req
    ) {
        boolean ok = service.toggleStatus(brno, req.posted);
        return ok ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
