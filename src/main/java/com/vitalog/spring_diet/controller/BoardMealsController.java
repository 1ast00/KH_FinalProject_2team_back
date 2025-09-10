package com.vitalog.spring_diet.controller;

import com.vitalog.spring_diet.dto.BMCommentDTO;
import com.vitalog.spring_diet.dto.BoardMealsDTO;
import com.vitalog.spring_diet.service.BoardMealsService;
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
@RequestMapping("/api/meals") //  URL 경로
public class BoardMealsController {

    @Value("${file.upload-dir}")
    private String uploadPath;
    private final BoardMealsService boardMealsService; //Service

    public BoardMealsController(BoardMealsService boardMealsService) { // ◀ Service 변경
        this.boardMealsService = boardMealsService;
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public Map<String, Object> getMealsList() { //메소드명
        //System.out.println("list com"); //게시글목록 확인용
        return boardMealsService.getMealsList(); //Service 호출
    }

    // 게시글 상세 조회
    @GetMapping("/detail/{bmno}") // ◀ 경로 변수 변경
    public Map<String, Object> getMealDetail(@PathVariable("bmno") int bmno) { // ◀ 파라미터 변경
        return boardMealsService.getMealDetail(bmno); //Service 호출
    }

    // 게시글 좋아요 토글
    @PostMapping("/awesome")
    public Map<String, Object> toggleMealAwesome( //메소드명
                                                  @RequestBody Map<String, Integer> payload,
                                                  @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        if (authenticatedUserMno == null || authenticatedUserMno.isEmpty()) {
            throw new IllegalArgumentException("사용자 정보(mno)가 누락되었습니다.");
        }
        int bmno = payload.get("bmno"); //payload key
        long mno = Long.parseLong(authenticatedUserMno);

        String action = boardMealsService.toggleMealAwesome(bmno, mno); // ◀ Service 호출 변경

        Map<String, Object> map = new HashMap<>();
        map.put("msg", "AWESOME을 반영하였습니다");
        map.put("awesomeCount", boardMealsService.getMealAwesomeCount(bmno)); // ◀ Service 호출 변경
        map.put("action", action);
        return map;
    }

    //게시글 수정
    @PatchMapping("/update/{bmno}") // ◀ 경로 변수 변경
    public Map<String, Object> updateMeal(@PathVariable int bmno, //파라미터
                                          @RequestBody BoardMealsDTO meal, //DTO
                                          @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BoardMealsDTO originalMeal = boardMealsService.getMeal(bmno); //DTO 및 Service

        if (originalMeal.getMno() == Long.parseLong(authenticatedUserMno)) {
            meal.setBmno(bmno); // ◀ DTO 메소드 변경
            boardMealsService.updateMeal(meal); //Service
            map.put("code", 1);
            map.put("msg", "게시글 수정 완료");
            map.put("bmno", bmno); // ◀ 반환 key 변경
        } else {
            map.put("code", 2);
            map.put("msg", "수정 권한이 없습니다.");
        }
        return map;
    }

    // 게시글 삭제
    @DeleteMapping("/{bmno}") //경로 변수
    public Map<String, Object> deleteMeal(@PathVariable int bmno, //파라미터
                                          @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BoardMealsDTO meal = boardMealsService.getMeal(bmno); //DTO및Service

        if (meal.getMno() == Long.parseLong(authenticatedUserMno)) {
            boardMealsService.deleteMeal(bmno); //Service호출
            map.put("code", 1);
            map.put("msg", "게시글을 삭제했습니다.");
        } else {
            map.put("code", 2);
            map.put("msg", "삭제 권한이 없습니다.");
        }
        return map;
    }

    // 게시글 신고
    @PatchMapping("/danger/{bmno}") //경로
    public Map<String, Object> updateDanger(@PathVariable int bmno, //파라미터
                                            @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        try {
            boardMealsService.handleDanger(bmno); //Service
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
    @GetMapping("/comment/{bmno}") //경로
    public List<BMCommentDTO> getCommentList(@PathVariable int bmno) { //DTO 및 파라미터
        return boardMealsService.getCommentList(bmno); //Service
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{bmcno}") //경로 변수
    public Map<String, Object> deleteComment(@PathVariable int bmcno, //파라미터
                                             @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BMCommentDTO comment = boardMealsService.getComment(bmcno); //DTO 및 Service

        if (comment.getMno() == Long.parseLong(authenticatedUserMno)) {
            boardMealsService.deleteComment(bmcno); //Service
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
    public Map<String, Object> updateComment(@RequestBody BMCommentDTO comment, //DTO
                                             @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BMCommentDTO originalComment = boardMealsService.getComment(comment.getBmcno()); //DTO 및 Service

        if (originalComment.getMno() == Long.parseLong(authenticatedUserMno)) {
            boardMealsService.updateComment(comment); //Service
            map.put("msg", "댓글 수정 완료");
        } else {
            map.put("msg", "댓글 수정 실패");
        }
        return map;
    }

    // 댓글 작성
    @PostMapping("/comment")
    public Map<String, Object> writeComment(@RequestBody BMCommentDTO comment, //DTO
                                            @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        comment.setMno(Integer.parseInt(authenticatedUserMno));

        try {
            boardMealsService.insertComment(comment); //Service
            map.put("code", 1);
            map.put("msg", "댓글 추가 완료");
            map.put("commentList", boardMealsService.getCommentList(comment.getBmno())); //DTO및 Service
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 2);
            map.put("msg", "댓글 추가 실패");
        }
        return map;
    }

    // 댓글 좋아요 토글
    @PostMapping("/comment/awesome/{bmcno}") //경로 변수 변경
    public Map<String, Object> toggleCommentAwesome(@PathVariable int bmcno, //파라미터 변경
                                                    @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        long mno = Long.parseLong(authenticatedUserMno);

        if (bmcno == 0) {
            throw new IllegalArgumentException("댓글 번호가 유효하지 않습니다.");
        }
        boardMealsService.toggleCommentAwesome(bmcno, mno); //Service

        map.put("msg", "좋아요 완료");
        return map;
    }

    // 댓글 신고 API
    @PatchMapping("/comment/danger/{bmcno}") // 변수
    public Map<String, Object> reportComment(@PathVariable int bmcno) { //파라미터
        Map<String, Object> map = new HashMap<>();
        try {
            boardMealsService.handleCommentDanger(bmcno); //Service
            map.put("code", 1);
            map.put("msg", "댓글이 신고되었습니다.");
        } catch (Exception e) {
            map.put("code", 2);
            map.put("msg", "댓글 신고에 실패했습니다.");
            e.printStackTrace();
        }
        return map;
    }

    // 게시글 작성
    @PostMapping("/write")
    public Map<String, Object> writeMeal(@RequestBody BoardMealsDTO meal, //DTO
                                         @RequestAttribute("authenticatedUsermno") String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        try {
            int mno = Integer.parseInt(authenticatedUserMno);
            meal.setMno(mno);

            int result = boardMealsService.writeMeal(meal, null); //Service

            if (result > 0) {
                map.put("code", 1);
                map.put("msg", "게시글 쓰기 성공");
                map.put("bmno", meal.getBmno()); //반환 key 및 DTO
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

    //이미지 업로드
    @PostMapping("/uploadImage")
    public Map<String, String> uploadImage(@RequestPart("file") MultipartFile file) {
        Map<String, String> map = new HashMap<>();
        try {
            String uploadDir = this.uploadPath; // @Value로 주입받은 경로 사용
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs(); // 경로가 없으면 생성
            }

            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String filename = uuid + "_" + originalFilename;

            File saveFile = new File(uploadDir, filename);
            file.transferTo(saveFile);

            String imageUrl = "/images/" + filename; // 웹 접근 경로

            map.put("imageUrl", imageUrl);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error", "이미지 업로드 실패");
            return map;
        }
    }
}