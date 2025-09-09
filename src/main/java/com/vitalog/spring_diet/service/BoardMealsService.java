package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.BMCommentDTO;
import com.vitalog.spring_diet.dto.BMFileDTO;
import com.vitalog.spring_diet.dto.BoardMealsDTO;
import com.vitalog.spring_diet.mapper.BoardMealsMapper;
import com.vitalog.spring_diet.vo.PagingVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardMealsService {

    private final BoardMealsMapper boardMealsMapper; //Mapper

    // 전체 게시글 목록 조회
    public Map<String, Object> getMealsList() {
        int totalCount = boardMealsMapper.selectMealTotalCount(); // ◀ Mapper 호출 변경
        List<BoardMealsDTO> list = boardMealsMapper.selectMealsList(); // ◀ DTO 및 Mapper 호출 변경

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("mealsList", list); //반환
        return resultMap;
    }

    // 내가 쓴 글 목록 조회
    public Map<String, Object> getMyMealsList(int mno, int pageNo, int pageContentEa) {
        int totalCount = boardMealsMapper.selectMyMealTotalCount(mno);
        PagingVo pagingVo = new PagingVo(totalCount, pageNo, pageContentEa);
        pagingVo.setMno(mno);

        List<BoardMealsDTO> list = boardMealsMapper.selectMealsList();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("mealsList", list); // ◀ 반환 key 변경
        resultMap.put("paging", pagingVo);
        return resultMap;
    }

    // 게시판 상세조회
    @Transactional
    public Map<String, Object> getMealDetail(int bmno) {
        boardMealsMapper.updateViewCount(bmno);

        BoardMealsDTO meal = boardMealsMapper.selectMeal(bmno);
        int awesomeCount = boardMealsMapper.selectMealAwesomeCount(bmno);
        List<Integer> awesomeMemberIds = boardMealsMapper.selectAwesomeMemberIds(bmno);

        Map<String, Object> map = new HashMap<>();
        map.put("meal", meal);
        map.put("awesomeCount", awesomeCount);
        map.put("awesomeMemberIds", awesomeMemberIds);
        return map;
    }

    // 게시글 좋아요 토글
    public String toggleMealAwesome(int bmno, long mno) {
        Map<String, Object> map = new HashMap<>();
        map.put("bmno", bmno);
        map.put("mno", mno);

        if (boardMealsMapper.checkMealAwesome(map) > 0) {
            boardMealsMapper.deleteMealAwesome(map);
            return "deleted";
        } else {
            boardMealsMapper.insertMealAwesome(map);
            return "added";
        }
    }

    // 게시판 신고
    @Transactional
    public void handleDanger(int bmno) {
        boardMealsMapper.updateDanger(bmno);
        boardMealsMapper.updateStatusToDanger(bmno);
    }

    // 댓글 좋아요 토글
    public void toggleCommentAwesome(int bmcno, long mno) {
        Map<String, Object> map = new HashMap<>();
        map.put("bmcno", bmcno);
        map.put("mno", mno);

        if (boardMealsMapper.checkCommentAwesome(map) > 0) {
            boardMealsMapper.deleteCommentAwesome(map);
        } else {
            boardMealsMapper.insertCommentAwesome(map);
        }
    }

    // 게시글 작성 (파일 포함)
    @Transactional
    public int writeMeal(BoardMealsDTO meal, List<BMFileDTO> fileList) {
        int result = boardMealsMapper.insertMeal(meal);
        if (fileList != null && !fileList.isEmpty()) {
            for (BMFileDTO file : fileList) {
                file.setBmno(meal.getBmno());
                boardMealsMapper.insertFile(file);
            }
        }
        return result;
    }

    // 파일 정보를 DB에 저장
    public void saveFile(BMFileDTO bmFileDTO) {
        boardMealsMapper.insertBMFile(bmFileDTO);
    }

    // 게시글 수정
    public int updateMeal(BoardMealsDTO meal) {
        return boardMealsMapper.updateMeal(meal);
    }

    // 게시글 삭제
    @Transactional
    public void deleteMeal(int bmno) {
        List<BMFileDTO> fileList = boardMealsMapper.selectFileList(bmno);
        for (BMFileDTO file : fileList) {
            File f = new File(file.getBmfpath());
            if (f.exists()) {
                f.delete();
            }
        }
        boardMealsMapper.deleteMeal(bmno);
    }

    // 특정 댓글 신고
    @Transactional
    public void handleCommentDanger(int bmcno) {
        boardMealsMapper.updateCommentDanger(bmcno);
    }

    //Mapper 호출
    public BoardMealsDTO getMeal(int bmno) {
        return boardMealsMapper.selectMeal(bmno);
    }
    public int getMealAwesomeCount(int bmno) {
        return boardMealsMapper.selectMealAwesomeCount(bmno);
    }
    public BMCommentDTO getComment(int bmcno) {
        return boardMealsMapper.selectComment(bmcno);
    }
    public void deleteComment(int cno) {
        boardMealsMapper.deleteComment(cno);
    }
    public List<BMCommentDTO> getCommentList(int bmno) {
        return boardMealsMapper.selectCommentList(bmno);
    }
    public int insertComment(BMCommentDTO comment) {
        return boardMealsMapper.insertComment(comment);
    }
    public int updateComment(BMCommentDTO comment) {
        return boardMealsMapper.updateComment(comment);
    }
}