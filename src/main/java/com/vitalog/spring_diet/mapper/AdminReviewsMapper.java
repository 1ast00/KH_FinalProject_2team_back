package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.admindashboard.AdminReviewsListItemDTO;
import com.vitalog.spring_diet.dto.admindashboard.AdminReviewDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminReviewsMapper {

    // 목록 / 카운트
    List<AdminReviewsListItemDTO> selectReviewsPage(@Param("q") String q,
                                                    @Param("status") Integer status,
                                                    @Param("offset") int offset,
                                                    @Param("size") int size);
    int countReviewsPage(@Param("q") String q,
                         @Param("status") Integer status);


    AdminReviewDetailDTO selectReviewDetail(@Param("brno") Long brno);
    List<AdminReviewDetailDTO.CommentItem> selectRecentReviewComments(@Param("brno") Long brno,
                                                                      @Param("limit") int limit);
}
