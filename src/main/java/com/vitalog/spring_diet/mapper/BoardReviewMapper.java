package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.BRCommentDTO;
import com.vitalog.spring_diet.dto.BRFileDTO;
import com.vitalog.spring_diet.dto.BoardReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardReviewMapper {
//  dec_25.08.27 안돌아간거 찾는 중.

    //게시판-게시글목록조회-페이징포함
//    List<BoardReviewDTO> selectReviewList(PagingVo pagingVo);
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
    //brdanger 1 증가
    int updateDanger(int brno);

    //BRcomment
    List<BRCommentDTO> selectCommentList(int brno);
    int insertComment(BRCommentDTO comment);
    int updateComment(BRCommentDTO comment);
    int deleteComment(int cno);


    // BRFile
    //지정 게시글의 첨부파일 목록조회 - 추후 빠질 수 있음.
    List<BRFileDTO> selectFileList(int brno);
    //게시글등록시 첨부파일 정보 저장
    void insertFile(BRFileDTO file);
    //특정파일정보삭제 - 게시글 수정시 파일삭제 사용- 파일일련번호 기준
    int deleteFile(int brfno);


    // BoardReview Awesome
    //좋아요추가
    int insertReviewAwesome(Map<String, Object> map);
    //게시글좋아요 취소 - BRAwesome table에서 delete - 1 -> 0 으로 변경할곳
    int deleteReviewAwesome(Map<String, Object> map);
    // BRAwesome - 특정게시글 좋아요 총 개수 조회 -
    int selectReviewAwesomeCount(int brno);
    // 특정회원 해당게시글 좋아요 했는지 확인
    int checkReviewAwesome(Map<String, Object> map);

    //BRComment Awesome
    int insertCommentAwesome(Map<String, Object> map);
    int deleteCommentAwesome(Map<String, Object> map);
    //특정 답글 좋아요 총개수 조회
    int selectCommentAwesomeCount(int cno);
    //특정 회원 해당댓글 좋아요 했는지 확인
    int checkCommentAwesome(Map<String, Object> map);

    //getcomment용
    BRCommentDTO selectComment(int brcno);

}
