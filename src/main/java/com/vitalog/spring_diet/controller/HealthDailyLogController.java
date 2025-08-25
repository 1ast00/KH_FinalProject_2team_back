package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.HealthDailyLogDTO;
import com.vitalog.spring_diet.service.HealthDailyLogService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/healthdailylog")
@RequiredArgsConstructor
public class HealthDailyLogController {
    private final HealthDailyLogService service;

    @GetMapping("/list")
    public Map<String, Object> list(
            @RequestAttribute String authenticatedUsermno,
            @RequestParam(defaultValue = "0") Integer cursor,
            @RequestParam(defaultValue = "12") Integer limit,
            @RequestParam(defaultValue = "") String date
    ) {
        int mno = Integer.parseInt(authenticatedUsermno);
        var items = service.list(mno, cursor, limit, date);
        Integer nextCursor = (items.size() == limit) ? items.get(items.size() - 1).getHno() : null;

        Map<String, Object> result = new HashMap<>();
        result.put("items", items);
        result.put("nextCursor", nextCursor); // HashMap은 null OK
        return result;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestAttribute String authenticatedUsermno,
            @Valid @RequestBody HealthDailyLogRequest req
    ) {
        int mno = Integer.parseInt(authenticatedUsermno);
        var dto = toDTO(req);
        dto.setHno(service.nextHno());
        dto.setMno(mno);
        int count = service.create(dto);
        return ResponseEntity.ok(Map.of("code", count>0?1:2, "msg", count>0?"등록 성공":"등록 실패", "hno", dto.getHno()));
    }

    @PatchMapping("/{hno}")
    public ResponseEntity<?> update(
            @PathVariable int hno,
            @RequestAttribute String authenticatedUsermno,
            @Valid @RequestBody HealthDailyLogRequest req
    ) {
        int mno = Integer.parseInt(authenticatedUsermno);
        var dto = toDTO(req);
        dto.setHno(hno);
        dto.setMno(mno);
        int count = service.update(dto);
        return ResponseEntity.ok(Map.of("code", count>0?1:2, "msg", count>0?"수정 성공":"수정 실패"));
    }

    @DeleteMapping("/{hno}")
    public ResponseEntity<?> delete(@PathVariable int hno, @RequestAttribute String authenticatedUsermno) {
        int mno = Integer.parseInt(authenticatedUsermno);
        int count = service.delete(hno, mno);
        return ResponseEntity.ok(Map.of("code", count>0?1:2, "msg", count>0?"삭제 완료":"삭제 실패"));
    }

    // ----
    @Data
    public static class HealthDailyLogRequest {
        private String hdate;        // YYYY-MM-DD
        private String sleeptime;    // HH:MM
        private Double weight;       // ← 일지 저장 X, member.weight 갱신용
        private Double wateramount;
        private String exercise;
        private List<String> foods;  // 개행 join
    }

    private static HealthDailyLogDTO toDTO(HealthDailyLogRequest r) {
        var d = new HealthDailyLogDTO();
        d.setHdate(r.getHdate());
        d.setSleeptime(r.getSleeptime());
        d.setWeight(r.getWeight());
        d.setWateramount(r.getWateramount());
        d.setExercise(r.getExercise());
        if (r.getFoods() != null)
            d.setFood(String.join("\n", r.getFoods().stream()
                    .filter(s -> s != null && !s.trim().isEmpty())
                    .map(String::trim).toList()));
        return d;
    }
}
