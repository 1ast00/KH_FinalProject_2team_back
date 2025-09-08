package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.admindashboard.AdminReviewsPageDTO;
import com.vitalog.spring_diet.dto.admindashboard.AdminReviewDetailDTO;
import com.vitalog.spring_diet.service.AdminReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/reviews")
public class AdminReviewsController {

    private final AdminReviewsService service;

    // 목록
    @GetMapping
    public ResponseEntity<AdminReviewsPageDTO> list(
            @RequestParam(defaultValue = "1") int p,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String status // "ALL" | "1" | "0" | null
    ) {
        Integer statusNum = null;
        if ("0".equals(status) || "1".equals(status)) statusNum = Integer.valueOf(status);

        int page = Math.max(p, 1);
        int pageSize = Math.max(1, Math.min(size, 100));

        return ResponseEntity.ok(service.getPage(q, statusNum, page, pageSize));
    }


    @GetMapping("/{brno}")
    public ResponseEntity<?> detail(@PathVariable Long brno) {
        AdminReviewDetailDTO dto = service.getDetail(brno);
        return (dto == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }
}
