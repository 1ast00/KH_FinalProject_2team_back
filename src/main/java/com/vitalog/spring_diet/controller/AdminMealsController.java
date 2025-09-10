package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.admindashboard.AdminMealsPageDTO;
import com.vitalog.spring_diet.dto.admindashboard.AuthorActivityDTO;
import com.vitalog.spring_diet.service.AdminMealsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/meals")
public class AdminMealsController {

    private final AdminMealsService service;

    // 목록 + 검색(제목/작성자) + 상태 필터(ALL | POSTED | HIDDEN)
    @GetMapping
    public AdminMealsPageDTO list(@RequestParam(defaultValue = "1") int p,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(required = false) String q,
                                  @RequestParam(defaultValue = "ALL") String status) {
        return service.getPage(p, size, q, status);
    }

    // 상세: 해당 게시글의 작성자 활동 요약
    @GetMapping("/{bmno}/author-activity")
    public AuthorActivityDTO authorActivity(@PathVariable("bmno") long bmno) {
        return service.getAuthorActivityByBmno(bmno);
    }


    // PATCH /api/admin/meals/{bmno}/status?posted=true|false
    @PatchMapping("/{bmno}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable("bmno") long bmno,
                                             @RequestParam boolean posted) {
        return service.toggleMealStatus(bmno, posted) > 0
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().build();
    }
}
