package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.BRCommentDTO;
import com.vitalog.spring_diet.dto.BRFileDTO;
import com.vitalog.spring_diet.dto.BoardReviewDTO;
import com.vitalog.spring_diet.mapper.BoardReviewMapper;
import com.vitalog.spring_diet.vo.PagingVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;

@Service
@RequiredArgsConstructor
public class BoardReviewService {

    private final BoardReviewMapper boardReviewMapper;
// dec+25.08.29수정

    //전체 게시글 목록 조회 -페이징 포함
    public Map<String, Object> getReviewList(int pageNo, int pageContentEa) {
        int totalCount = boardReviewMapper.selectReviewTotalCount();
        PagingVo pagingVo = new PagingVo(totalCount, pageNo, pageContentEa);

    // PagingVo 객체를 Mapper에 직접 전달 (수정된 부분)
        List<BoardReviewDTO> list = boardReviewMapper.selectReviewList(pagingVo);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("reviewList", list);
        resultMap.put("paging", pagingVo);

        return resultMap;
    }

    //내가 쓴 글 목록 조회 -페이징 포함
    public Map<String, Object> getMyReviewList(int mno, int pageNo, int pageContentEa) {
        int totalCount = boardReviewMapper.selectMyReviewTotalCount(mno);
        PagingVo pagingVo = new PagingVo(totalCount, pageNo, pageContentEa);
        pagingVo.setMno(mno); // PagingVo에 사용자 번호 설정

        List<BoardReviewDTO> list = boardReviewMapper.selectReviewList(pagingVo);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("reviewList", list);
        resultMap.put("paging", pagingVo);

        return resultMap;
    }
 
    //게시글 상세 정보 조회 -조회수 증가 포함
    @Transactional
    public Map<String, Object> getReviewDetail(int brno) {
        boardReviewMapper.updateHitCount(brno);

        BoardReviewDTO review = boardReviewMapper.selectReview(brno);
        List<BRCommentDTO> commentList = boardReviewMapper.selectCommentList(brno);
        List<BRFileDTO> fileList = boardReviewMapper.selectFileList(brno);

        Map<String, Object> map = new HashMap<>();
        map.put("review", review);
        map.put("commentList", commentList);
        map.put("fileList", fileList);

        return map;
    }

    //게시글 좋아요 토글
    public void toggleReviewAwesome(int brno, long mno) {
        Map<String, Object> map = new HashMap<>();
        map.put("brno", brno);
        map.put("mno", mno);

        if (boardReviewMapper.checkReviewAwesome(map) > 0) {
            boardReviewMapper.deleteReviewAwesome(map);
        } else {
            boardReviewMapper.insertReviewAwesome(map);
        }
    }


     //댓글 좋아요 토글
    public void toggleCommentAwesome(int cno, long mno) {
        Map<String, Object> map = new HashMap<>();
        map.put("cno", cno);
        map.put("mno", mno);

        if (boardReviewMapper.checkCommentAwesome(map) > 0) {
            boardReviewMapper.deleteCommentAwesome(map);
        } else {
            boardReviewMapper.insertCommentAwesome(map);
        }
    }

     //게시글 작성 (파일 포함) - dec_25.08.27 test 안됨.
    @Transactional
    public int writeReview(BoardReviewDTO review, List<BRFileDTO> fileList) {
        int result = boardReviewMapper.insertReview(review);
        if (fileList != null && !fileList.isEmpty()) {
            for (BRFileDTO file : fileList) {
                file.setBrno(review.getBrno());
                boardReviewMapper.insertFile(file);
            }
        }
        return result;
    }

    //게시글삭제
    @Transactional
    public void deleteReview(int brno) {
        List<BRFileDTO> fileList = boardReviewMapper.selectFileList(brno);
        for (BRFileDTO file : fileList) {
            File f = new File(file.getBrfpath());
            if (f.exists()) {
                f.delete();
            }
        }
        boardReviewMapper.deleteReview(brno);
    }

    // mapper 호출
    public BoardReviewDTO getReview(int brno) {
        return boardReviewMapper.selectReview(brno);
    }
    public int getReviewAwesomeCount(int brno) {
        return boardReviewMapper.selectReviewAwesomeCount(brno);
    }
   public BRCommentDTO getComment(int brcno) {
       return boardReviewMapper.selectComment(brcno);
   }
    public void deleteComment(int cno) {
        boardReviewMapper.deleteComment(cno);
    }
   public List<BRCommentDTO> getCommentList(int brno) {
        return boardReviewMapper.selectCommentList(brno);
    }
    public int insertComment(BRCommentDTO comment) {
        return boardReviewMapper.insertComment(comment);
    }
    public int updateComment(BRCommentDTO comment) {
        return boardReviewMapper.updateComment(comment);
    }

}