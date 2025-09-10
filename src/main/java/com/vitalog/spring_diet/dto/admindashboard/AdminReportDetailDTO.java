// src/main/java/com/vitalog/spring_diet/dto/admindashboard/AdminReportDetailDTO.java
package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminReportDetailDTO {
<<<<<<< HEAD
    private Long reportId;
    private String targetType;     // MEAL_POST / MEAL_COMMENT / REVIEW_POST / REVIEW_COMMENT
    private Long targetId;
    private Long reporterMno;
    private String status;

    // 스냅샷(저장 컬럼 없이 실시간 조회)
    private String targetWriter;   // 작성자 닉/아이디
    private String postTitle;      // 글인 경우
    private String commentExcerpt; // 댓글인 경우
=======
    private Long   reportId;
    private String targetType;        // MEAL_POST / MEAL_COMMENT / REVIEW_POST / REVIEW_COMMENT
    private Long   targetId;          // (원본) 게시글/댓글 PK
    private Long   reporterMno;       // 신고자 회원번호
    private String status;            // PENDING / RESOLVED / DONE

    /* === 신고자 정보 === */
    private String reporterUser;      // NVL(nickname, userid) (기존 호환 필드)
    private String reporterUserId;    // 신고자 아이디 (신규)
    private String reporterNick;      // 신고자 닉네임 (신규, null이면 아이디 사용)

    /* === 신고 ‘대상 작성자’ 정보 (실제 신고 당한 회원) === */
    private Long   targetOwnerMno;    // 대상 작성자 회원번호
    private String targetOwnerUser;   // NVL(nickname, userid) (기존 호환 필드)
    private String targetOwnerUserId; // 대상 작성자 아이디 (신규)
    private String targetOwnerNick;   // 대상 작성자 닉네임 (신규)


    private String postTitle;         // 글인 경우 제목
    private String commentExcerpt;    // 댓글인 경우 내용


    private String statusKo;          // 대기 / 처리완료 (서비스에서 세팅)
>>>>>>> main
}
