package com.vitalog.spring_diet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews") // URL 경로를 /api/reviews로 변경
public class BoardReviewController {
/*  //dec_25.08.27_ 아직 안돌아가는데욤.
    private final BoardReviewService boardReviewService;

    public BoardReviewController(BoardReviewService boardReviewService) {
        this.boardReviewService = boardReviewService;
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public Map<String, Object> getReviewList(@RequestParam(defaultValue = "1") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageContentEa) {
        return boardReviewService.getReviewList(pageNo, pageContentEa);
    }

    // 게시글 상세 조회
    @GetMapping("/detail/{brno}")
    public Map<String, Object> getReviewDetail(@PathVariable int brno) {
        return boardReviewService.getReviewDetail(brno);
    }

    // 게시글 좋아요 토글
    @GetMapping("/awesome/{brno}")
    public Map<String, Object> toggleReviewAwesome(@PathVariable int brno,
                                                   @RequestAttribute String authenticatedUserMno) { // JWT 필터에서 mno를 넘겨준다고 가정
        Map<String, Object> map = new HashMap<>();
        long mno = Long.parseLong(authenticatedUserMno);

        boardReviewService.toggleReviewAwesome(brno, mno);

        map.put("msg", "요청이 처리되었습니다.");
        map.put("awesomeCount", boardReviewService.getReviewAwesomeCount(brno));
        return map;
    }

    // 댓글 좋아요 토글
    @GetMapping("/comment/awesome/{cno}")
    public Map<String, Object> toggleCommentAwesome(@PathVariable int cno,
                                                    @RequestAttribute String authenticatedUserMno) {
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
                                            @RequestAttribute String authenticatedUserMno) {
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
                                             @RequestAttribute String authenticatedUserMno) {
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
                                             @RequestAttribute String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        BRCommentDTO originalComment = boardReviewService.getComment(comment.getCno());

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
                                            @RequestAttribute String authenticatedUserMno) {
        Map<String, Object> map = new HashMap<>();
        comment.setMno(Long.parseLong(authenticatedUserMno));

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

    // 게시글 작성
    @PostMapping("/write")
    public Map<String, Object> writeReview(@RequestAttribute String authenticatedUserMno,
                                           @RequestPart("params") String params,
                                           @RequestPart(value = "files", required = false) MultipartFile[] files) throws IOException {
        Map<String, Object> map = new HashMap<>();
        long mno = Long.parseLong(authenticatedUserMno);

        // 1. JSON 파라미터 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        BoardReviewDTO review = objectMapper.readValue(params, BoardReviewDTO.class);
        review.setMno(mno);

        // 2. 파일 업로드 처리
        List<BRFileDTO> fileList = new ArrayList<>();
        if (files != null) {
            File root = new File("c:\\fileupload\\review"); // review 폴더 추가
            if (!root.exists()) root.mkdirs();

            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String fileName = file.getOriginalFilename();
                String filePath = root.getAbsolutePath() + File.separator + fileName;
                file.transferTo(new File(filePath));

                BRFileDTO fileDTO = new BRFileDTO();
                fileDTO.setBrfname(fileName);
                fileDTO.setBrfpath(filePath);
                fileList.add(fileDTO);
            }
        }

        // 3. 서비스 호출하여 DB에 저장
        int result = boardReviewService.writeReview(review, fileList);

        if (result > 0) {
            map.put("brno", review.getBrno()); // 서비스에서 세팅된 brno
            map.put("code", 1);
            map.put("msg", "게시글 쓰기 성공");
        } else {
            map.put("code", 2);
            map.put("msg", "게시글 쓰기 실패");
        }
        return map;
    }

    // 파일 다운로드 제공 안할거임.
*/
}