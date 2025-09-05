package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.BRCommentDTO;
import com.vitalog.spring_diet.dto.BoardReviewDTO;
import com.vitalog.spring_diet.service.BoardReviewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews") // URL 경로를 /api/reviews로 변경
public class BoardReviewController {

    @Value("${file.upload-dir}") // 설정 파일의 경로를 읽어옵니다.
    private String uploadPath;

    private final BoardReviewService boardReviewService;

   public BoardReviewController(BoardReviewService boardReviewService) {
        this.boardReviewService = boardReviewService;
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public Map<String, Object> getReviewList() {
    System.out.println("list com");
        return boardReviewService.getReviewList();
    }

    // 게시글 상세 조회
    @GetMapping("/detail/{brno}")
    public Map<String, Object> getReviewDetail(@PathVariable("brno") int brno) {
        return boardReviewService.getReviewDetail(brno);
    }

    // 게시글 좋아요 토글 ok
    @PostMapping("/awesome")
    public Map<String, Object> toggleReviewAwesome(
            @RequestBody Map<String, Integer> payload,
            @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) { // JWT 필터에서 mno를 넘겨줌
        // mno가 null인지 확인
        if (authenticatedUserMno == null || authenticatedUserMno.isEmpty()) {
            // null일 경우 처리 (예: Unauthorized 에러 반환)
            throw new IllegalArgumentException("사용자 정보(mno)가 누락되었습니다.");
        }
        int brno = payload.get("brno");
        long mno = Long.parseLong(authenticatedUserMno); // JWT 필터에서 가져온 mno 사용

        String action = boardReviewService.toggleReviewAwesome(brno, mno);

        Map<String, Object> map = new HashMap<>();
        map.put("msg", "AWESOME을 반영하였습니다");
        map.put("awesomeCount", boardReviewService.getReviewAwesomeCount(brno));
        map.put("action", action);
        return map;
    }

    // 댓글 좋아요 토글
    @GetMapping("/comment/awesome/{cno}")
    public Map<String, Object> toggleCommentAwesome(@PathVariable int cno,
                                                    @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        long mno = Long.parseLong(authenticatedUserMno);

        boardReviewService.toggleCommentAwesome(cno, mno);

        map.put("msg", "요청이 처리되었습니다.");
        // map.put("awesomeCount", boardReviewService.getCommentAwesomeCount(cno)); // 필요 시 추가
        return map;
    }

    // 게시글 삭제
    @DeleteMapping("/{brno}")
    public Map<String, Object> deleteReview(@PathVariable int brno,
                                            @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BoardReviewDTO review = boardReviewService.getReview(brno);

        if (review.getMno() == Long.parseLong(authenticatedUserMno)) {
            boardReviewService.deleteReview(brno);
            map.put("code", 1);
            map.put("msg", "게시글을 삭제했습니다.");
        } else {
            map.put("code", 2);
            map.put("msg", "삭제 권한이 없습니다.");
        }
        return map;
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{cno}")
    public Map<String, Object> deleteComment(@PathVariable int cno,
                                             @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BRCommentDTO comment = boardReviewService.getComment(cno);

        if (comment.getMno() == Long.parseLong(authenticatedUserMno)) {
            boardReviewService.deleteComment(cno);
            map.put("code", 1);
            map.put("msg", "댓글을 삭제했습니다.");
        } else {
            map.put("code", 2);
            map.put("msg", "삭제 권한이 없습니다.");
        }
        return map;
    }

    // 댓글 수정
    @PatchMapping("/comment")
    public Map<String, Object> updateComment(@RequestBody BRCommentDTO comment,
                                             @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BRCommentDTO originalComment = boardReviewService.getComment((Integer) comment.getBrcno());

        if (originalComment.getMno() == Long.parseLong(authenticatedUserMno)) {
            boardReviewService.updateComment(comment);
            map.put("msg", "댓글 수정 완료");
        } else {
            map.put("msg", "댓글 수정 실패");
        }
        return map;
    }

    // 댓글 작성
    @PostMapping("/comment")
    public Map<String, Object> writeComment(@RequestBody BRCommentDTO comment,
                                            @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        comment.setMno(Integer.parseInt(authenticatedUserMno));

        try {
            boardReviewService.insertComment(comment);
            map.put("code", 1);
            map.put("msg", "댓글 추가 완료");
            map.put("commentList", boardReviewService.getCommentList(comment.getBrno()));
        } catch (Exception e) {
            map.put("code", 2);
            map.put("msg", "댓글 추가 실패");
        }
        return map;
    }

    // 토스트hook사용해서파일업로드
    @PostMapping("/write")
    public Map<String, Object> writeReviewe(@RequestBody BoardReviewDTO review,
                                                      @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        try {
            review.setMno(Integer.parseInt(authenticatedUserMno));
            int result = boardReviewService.writeReview(review, null); // 파일 없이 서비스 호출

            if (result > 0) {
                map.put("code", 1);
                map.put("msg", "게시글 쓰기 성공 (파일 없음)");
            } else {
                map.put("code", 2);
                map.put("msg", "게시글 쓰기 실패");
            }
        } catch (Exception e) {
            map.put("code", 2);
            map.put("msg", "게시글 쓰기 실패: " + e.getMessage());
        }
        return map;
    }

    @PostMapping("/uploadImage")
    public Map<String, String> uploadImage(@RequestPart("file") MultipartFile file) {
        Map<String, String> map = new HashMap<>();
        try {
            // 실제 파일 저장 경로를 지정하세요. (예: C:/upload/images/)
            String uploadPath = "C:/upload/images/";

            // 파일 이름 중복 방지를 위해 UUID 사용 - uuid가 뭐에욤?
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String filename = uuid + "_" + originalFilename;

            // 파일 저장
            File saveFile = new File(uploadPath, filename);
            file.transferTo(saveFile);

            // client 반환할 이미지 URL 생성 - 실제운영환경 URL
            // 여기서는 임시로 파일 경로를 반환합니다.
            String imageUrl = "/images/" + filename; // ex) http://localhost:9999/images/uuid_파일명.jpg

            map.put("imageUrl", imageUrl);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", "이미지 업로드 실패");
            return map;
        }
    }
    // 파일 다운로드 제공 안할거임.
}