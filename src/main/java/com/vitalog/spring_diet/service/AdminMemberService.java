package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.admindashboard.*;
import com.vitalog.spring_diet.mapper.AdminMemberMapper;
import com.vitalog.spring_diet.vo.PagedResult;
import com.vitalog.spring_diet.vo.PagingVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminMemberService {

    private final AdminMemberMapper mapper;

    public PagedResult<MemberListItemDTO> searchMembers(String q, String role,
                                                        String sort, int p, int size) {
        Map<String,Object> param = new HashMap<>();
        param.put("q", q);
        param.put("role", role);
        param.put("sort", sort);
        param.put("p", p);
        param.put("size", size);

        List<MemberListItemDTO> items = mapper.selectMembers(param);
        int total = mapper.countMembers(param);

        PagingVo paging = new PagingVo(total, p, size);
        return PagedResult.<MemberListItemDTO>builder()
                .paging(paging)
                .items(items)
                .build();
    }

    public MemberDetailDTO getMemberDetail(Long mno) {
        MemberBasicDTO basic = mapper.selectMemberBasic(mno);
        if (basic == null) return null;

        ActivitySummaryDTO summary = ActivitySummaryDTO.builder()
                .dietPostCount(mapper.countDietPostsByMember(mno))
                .reviewPostCount(mapper.countReviewPostsByMember(mno))
                .dietCommentCount(mapper.countDietCommentsByMember(mno))
                .reviewCommentCount(mapper.countReviewCommentsByMember(mno))
                .build();

        List<PostItemDTO> posts = new ArrayList<>();
        posts.addAll(mapper.selectRecentDietPosts(mno, 5));
        posts.addAll(mapper.selectRecentReviewPosts(mno, 5));

        List<CommentItemDTO> comments = new ArrayList<>();
        comments.addAll(mapper.selectRecentDietComments(mno, 5));
        comments.addAll(mapper.selectRecentReviewComments(mno, 5));

        return MemberDetailDTO.builder()
                .basic(basic)
                .summary(summary)
                .recentPosts(posts)
                .recentComments(comments)
                .build();
    }

    public boolean updateRole(Long mno, String role) {
        return mapper.updateMemberRole(mno, role) > 0;
    }

    public boolean deleteMember(Long mno) {
        return mapper.deleteMember(mno) > 0;
    }
}
