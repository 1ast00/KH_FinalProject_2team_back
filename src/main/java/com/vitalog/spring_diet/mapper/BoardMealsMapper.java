package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.BMCommentDTO;
import com.vitalog.spring_diet.dto.BMFileDTO;
import com.vitalog.spring_diet.dto.BoardMealsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMealsMapper {
    //게시글 관련
    List<BoardMealsDTO> selectMealsList();
    int selectMyMealTotalCount(int mno);
    List<Integer> selectAwesomeMemberIds(int bmno);
    int selectMealTotalCount();
    BoardMealsDTO selectMeal(int bmno);
    int insertMeal(BoardMealsDTO meal);
    int updateMeal(BoardMealsDTO meal);
    int deleteMeal(int bmno);
    void updateViewCount(int bmno);
    int updateDanger(int bmno);
    void updateStatusToDanger(int bmno);

    //댓글 관련
    List<BMCommentDTO> selectCommentList(int bmno);
    int insertComment(BMCommentDTO comment);
    int updateComment(BMCommentDTO comment);
    int deleteComment(int bmcno);
    BMCommentDTO selectComment(int bmcno);

    //파일 관련
    List<BMFileDTO> selectFileList(int bmno);
    void insertFile(BMFileDTO file);
    int deleteFile(int bmfno);
    void insertBMFile(BMFileDTO bmFileDTO);

    //게시글 좋아요 관련
    int insertMealAwesome(Map<String, Object> map);
    int deleteMealAwesome(Map<String, Object> map);
    int selectMealAwesomeCount(int bmno); // ◀ 파라미터 변경
    int checkMealAwesome(Map<String, Object> map);

    //댓글 좋아요 관련
    int insertCommentAwesome(Map<String, Object> map);
    int deleteCommentAwesome(Map<String, Object> map);
    int selectCommentAwesomeCount(int bmcno);
    int checkCommentAwesome(Map<String, Object> map);

    //댓글 신고 관련
    int updateCommentDanger(int bmcno);
}