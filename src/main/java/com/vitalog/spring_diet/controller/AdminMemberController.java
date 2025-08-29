package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.admindashboard.MemberDetailDTO;
import com.vitalog.spring_diet.dto.admindashboard.MemberListItemDTO;
import com.vitalog.spring_diet.service.AdminMemberService;
import com.vitalog.spring_diet.vo.PagedResult;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    private final AdminMemberService service;

    @GetMapping
    public PagedResult<MemberListItemDTO> list(
            @RequestParam(name = "p",     defaultValue = "1")  int p,
            @RequestParam(name = "size",  defaultValue = "10") int size,
            @RequestParam(name = "q",     required = false)    String q,
            @RequestParam(name = "role",  defaultValue = "ALL") String role,
            @RequestParam(name = "status", defaultValue = "ALL") String status, // 미사용
            @RequestParam(name = "sort",  defaultValue = "mno,asc") String sort
    ) {
        return service.searchMembers(q, role, sort, p, size);
    }

    @GetMapping("/{mno}")
    public ResponseEntity<?> detail(@PathVariable Long mno) {
        MemberDetailDTO dto = service.getMemberDetail(mno);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{mno}/role")
    public ResponseEntity<?> changeRole(@PathVariable Long mno, @RequestBody RoleBody body) {
        boolean ok = service.updateRole(mno, body.getRole());
        return ok ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{mno}")
    public ResponseEntity<?> delete(@PathVariable Long mno) {
        boolean ok = service.deleteMember(mno);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Getter @NoArgsConstructor @AllArgsConstructor
    static class RoleBody { private String role; }
}
}

