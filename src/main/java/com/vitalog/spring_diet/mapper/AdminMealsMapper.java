package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.admindashboard.AdminMealsListItemDTO;
import com.vitalog.spring_diet.dto.admindashboard.AuthorActivityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMealsMapper {

    // 기존 시그니처 + (선택) status 파라미터를 쿼리에서 null 허용으로 처리했으므로 추가는 선택
    List<AdminMealsListItemDTO> selectMealsPage(@Param("q") String q,
                                                @Param("offset") int offset,
                                                @Param("size") int size,
                                                @Param("status") String status /* nullable: "ALL"/"POSTED"/"HIDDEN" */);

    int countMealsPage(@Param("q") String q,
                       @Param("status") String status /* nullable */);

    Map<String,Object> selectAuthorInfo(@Param("mno") long mno);
    List<AuthorActivityDTO.PostBrief> selectAuthorPosts(@Param("mno") long mno);
    List<AuthorActivityDTO.SelfCommentBrief> selectSelfCommentsOnOwnPosts(@Param("mno") long mno);
    Long selectAuthorMnoByBmno(@Param("bmno") long bmno);

    int updateMealStatus(@Param("bmno") long bmno, @Param("status") int status);
}
