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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews") // URL 경로를 /api/reviews로 변경
public class BoardReviewController {

    @Value("${file.upload-dir}") // 설정 파일의 경로를 읽어옵니다.
    private String uploadPath = this.uploadPath;
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



    //게시글 수정
    @PatchMapping("/update/{brno}")
    public Map<String, Object> updateReview(@PathVariable int brno,
                                            @RequestBody BoardReviewDTO review,
                                            @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BoardReviewDTO originalReview = boardReviewService.getReview(brno);

        if (originalReview.getMno() == Long.parseLong(authenticatedUserMno)) {
            review.setBrno(brno); // URL의 brno를 DTO에 설정
            boardReviewService.updateReview(review); //
            map.put("code", 1);
            map.put("msg", "게시글 수정 완료");
            map.put("brno", brno);
        } else {
            map.put("code", 2);
            map.put("msg", "수정 권한이 없습니다.");
        }
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

    // 게시글 신고
    @PatchMapping("/danger/{brno}")
    public Map<String, Object> updateDanger(@PathVariable int brno,
                                            @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        try {
            boardReviewService.handleDanger(brno);
            map.put("code", 1);
            map.put("msg", "게시글이 신고되었습니다.");
        } catch (Exception e) {
            map.put("code", 2);
            map.put("msg", "신고 실패");
            e.printStackTrace();
        }
        return map;
    }

    // 댓글 목록 조회
    @GetMapping("/comment/{brno}")
    public List<BRCommentDTO> getCommentList(@PathVariable int brno) {
        return boardReviewService.getCommentList(brno);
    }
    // 댓글 삭제
    @DeleteMapping("/comment/{brcno}")
    public Map<String, Object> deleteComment(@PathVariable int brcno,
                                             @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BRCommentDTO comment = boardReviewService.getComment(brcno);

        if (comment.getMno() == Long.parseLong(authenticatedUserMno)) {
            boardReviewService.deleteComment(brcno);
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
        BRCommentDTO originalComment = boardReviewService.getComment(comment.getBrcno());

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
            e.printStackTrace();//25.09.08
            map.put("code", 2);
            map.put("msg", "댓글 추가 실패");
        }
        return map;
    }
    // 댓글 좋아요 토글
    @PostMapping("/comment/awesome/{brcno}")
    public Map<String, Object> toggleCommentAwesome(@PathVariable int brcno,
                                                    @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        System.out.println("백엔드에 전달된 brcno 값: " + brcno);// 25.09.08 확인후주석
       Map<String, Object> map = new HashMap<>();
        long mno = Long.parseLong(authenticatedUserMno);

        if (brcno == 0) { // int 타입이므로 null 대신 0으로 확인
            // 에러 처리
            throw new IllegalArgumentException("댓글 번호가 유효하지 않습니다.");
        }
        boardReviewService.toggleCommentAwesome(brcno, mno);

        map.put("msg", "좋아요 완료");
        return map;
    }
    // 댓글 신고 API
    @PatchMapping("/comment/danger/{brcno}")
    public Map<String, Object> reportComment(@PathVariable int brcno) {
        Map<String, Object> map = new HashMap<>();
        try {
            boardReviewService.handleCommentDanger(brcno);
            map.put("code", 1);
            map.put("msg", "댓글이 신고되었습니다.");
        } catch (Exception e) {
            map.put("code", 2);
            map.put("msg", "댓글 신고에 실패했습니다.");
            e.printStackTrace();
        }
        return map;
    }

    // 토스트hook사용해서파일업로드
    @PostMapping("/write")
    public Map<String, Object> writeReviewe(@RequestBody BoardReviewDTO review,
                                                      @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        try {
            // review.setMno(Integer.parseInt(authenticatedUserMno));  25.09.05
            int mno = Integer.parseInt(authenticatedUserMno);
            review.setMno(mno);

            int result = boardReviewService.writeReview(review, null); // 파일 없이 서비스 호출

            if (result > 0) {
                map.put("code", 1);
                map.put("msg", "게시글 쓰기 성공");
                map.put("brno", review.getBrno()); //25.09.06
            } else {
                map.put("code", 2);
                map.put("msg", "게시글 쓰기 실패");
            }
        } catch (Exception e) {
            map.put("code", 2);
            map.put("msg", "게시글 쓰기 실패: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    //이미지업로드인데 안해봄.
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