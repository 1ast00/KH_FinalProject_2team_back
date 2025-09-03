// 날짜 중복(code 3), 입력검증(code 4)
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
        result.put("nextCursor", nextCursor);
        return result;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestAttribute String authenticatedUsermno,
            @Valid @RequestBody HealthDailyLogRequest req
    ) {
        int mno = Integer.parseInt(authenticatedUsermno);

        // [검증] 몸무게 필수
        if (req.getWeight() == null) {
            return ResponseEntity.ok(Map.of("code", 4, "msg", "몸무게를 입력해 주세요."));
        }
        // [중복] 같은 날짜 이미 있음
        if (req.getHdate() != null && service.existsDate(mno, req.getHdate())) {
            return ResponseEntity.ok(Map.of("code", 3, "msg", "같은 날짜의 건강일지가 이미 있습니다."));
        }

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
        private Double weight;       // ← 프론트 입력명 유지(백엔드에선 hweight로 저장)
        private Double wateramount;
        private String exercise;     // (폼에서 배열 → '\n'로 합쳐서 들어옴)
        private List<String> foods;  // 개행 join
    }

    private static HealthDailyLogDTO toDTO(HealthDailyLogRequest r) {
        var d = new HealthDailyLogDTO();
        d.setHdate(r.getHdate());
        d.setSleeptime(r.getSleeptime());
        // 0903 hweight 매핑(프론트 weight → DTO hweight & weight 동시 세팅) - 시작
        d.setHweight(r.getWeight());
        d.setWeight(r.getWeight());
        // 0903 hweight 매핑(프론트 weight → DTO hweight & weight 동시 세팅) - 끝
        d.setWateramount(r.getWateramount());
        d.setExercise(r.getExercise());
        if (r.getFoods() != null)
            d.setFood(String.join("\n", r.getFoods().stream()
                    .filter(s -> s != null && !s.trim().isEmpty())
                    .map(String::trim).toList()));
        return d;
    }
}
