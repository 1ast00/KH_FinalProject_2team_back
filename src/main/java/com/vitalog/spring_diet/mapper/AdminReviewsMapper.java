package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.admindashboard.AdminReviewDetailDTO;
import com.vitalog.spring_diet.dto.admindashboard.AdminReviewsListItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminReviewsMapper {

    List<AdminReviewsListItemDTO> selectReviewsPage(@Param("q") String q,
                                                    @Param("status") Integer status,
                                                    @Param("offset") int offset,
                                                    @Param("size") int size);

    int countReviewsPage(@Param("q") String q,
                         @Param("status") Integer status);

    AdminReviewDetailDTO selectReviewDetail(@Param("brno") Long brno);

    List<AdminReviewDetailDTO.CommentItem> selectRecentReviewComments(@Param("brno") Long brno,
                                                                      @Param("limit") int limit);

    int updateReviewStatus(@Param("brno") long brno,
                           @Param("status") int status);
}
