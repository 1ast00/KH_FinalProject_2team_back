package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.admindashboard.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMemberMapper {

    // 목록/카운트
    List<MemberListItemDTO> selectMembers(Map<String,Object> p);
    int countMembers(Map<String,Object> p);

    // 상세 기본
    MemberBasicDTO selectMemberBasic(@Param("mno") Long mno);

    // 요약 카운트
    int countDietPostsByMember(@Param("mno") Long mno);
    int countReviewPostsByMember(@Param("mno") Long mno);
    int countDietCommentsByMember(@Param("mno") Long mno);
    int countReviewCommentsByMember(@Param("mno") Long mno);

    // 최근 N개
    List<PostItemDTO> selectRecentDietPosts(@Param("mno") Long mno, @Param("limit") int limit);
    List<PostItemDTO> selectRecentReviewPosts(@Param("mno") Long mno, @Param("limit") int limit);
    List<CommentItemDTO> selectRecentDietComments(@Param("mno") Long mno, @Param("limit") int limit);
    List<CommentItemDTO> selectRecentReviewComments(@Param("mno") Long mno, @Param("limit") int limit);
  
    // 업데이트
    int updateMemberRole(@Param("mno") Long mno, @Param("role") String role);

    // 삭제
    int deleteMember(@Param("mno") Long mno);
}
