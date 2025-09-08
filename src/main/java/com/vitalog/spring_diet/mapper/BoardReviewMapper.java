package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.BRCommentDTO;
import com.vitalog.spring_diet.dto.BRFileDTO;
import com.vitalog.spring_diet.dto.BoardReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardReviewMapper {
    //게시판-게시글목록조회-페이징 가능할까?
    List<BoardReviewDTO> selectReviewList();
    //특정 회원이 작성한 리뷰 총 개수 조회
    int selectMyReviewTotalCount(int mno);
    // 좋아요를 누른 회원 ID 목록을 반환하는 메서드
    List<Integer> selectAwesomeMemberIds(int brno);
    //게시판-전체게시글개수조회
    int selectReviewTotalCount();
    //게시판-특정게시글 상세정보조회
    BoardReviewDTO selectReview(int brno);

    //새 게시글 등록
    int insertReview(BoardReviewDTO review);
    //게시글 수정 - 수정할게시글정보/ 영향받은 행의 수
    int updateReview(BoardReviewDTO review);
    //게시글 삭제 - 삭제할게시글번호/ 영향받은 행의수
    int deleteReview(int brno);
    //게시글조회수 1증가
    void updateViewCount(int brno);

    //brdanger 1 증가 25.09.05
    int updateDanger(int brno);
    void updateStatusToDanger(int brno);

    //BRcomment
    List<BRCommentDTO> selectCommentList(int brno);

    int insertComment(BRCommentDTO comment);
    int updateComment(BRCommentDTO comment);
    int deleteComment(int brcno); //

    //게시글에달린 모든 댓글조회
    BRCommentDTO selectComment(int brcno);

    // BRFile
    List<BRFileDTO> selectFileList(int brno);

    void insertFile(BRFileDTO file);
    int deleteFile(int brfno);
    void insertBRFile(BRFileDTO brFileDTO);

    // BoardReview Awesome
    int insertReviewAwesome(Map<String, Object> map);
    int deleteReviewAwesome(Map<String, Object> map);
    int selectReviewAwesomeCount(int brno);
    int checkReviewAwesome(Map<String, Object> map);

    //BRComment Awesome
    int insertCommentAwesome(Map<String, Object> map);
    int deleteCommentAwesome(Map<String, Object> map);

    //특정댓글 좋아요 총개수
    int selectCommentAwesomeCount(int brcno);
    int checkCommentAwesome(Map<String, Object> map);

    //특정댓글 신고
    int updateCommentDanger(int brcno);
}