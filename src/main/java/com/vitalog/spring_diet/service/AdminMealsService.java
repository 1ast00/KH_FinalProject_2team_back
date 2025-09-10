// src/main/java/com/vitalog/spring_diet/service/AdminMealsService.java
package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.admindashboard.AdminMealsPageDTO;
import com.vitalog.spring_diet.dto.admindashboard.AuthorActivityDTO;
import com.vitalog.spring_diet.mapper.AdminMealsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminMealsService {

    private final AdminMealsMapper mapper;

    public AdminMealsPageDTO getPage(int p, int size, String q) {
        return getPage(p, size, q, "ALL");
    }

    public AdminMealsPageDTO getPage(int p, int size, String q, String status) {
        int page = Math.max(p, 1);
        int offset = (page - 1) * size;
        int total = mapper.countMealsPage(q, status);
        int totalPage = Math.max((int) Math.ceil(total / (double) size), 1);

        return AdminMealsPageDTO.builder()
                .items(mapper.selectMealsPage(q, offset, size, status))
                .currentPage(page)
                .totalPage(totalPage)
                .build();
    }

    public AuthorActivityDTO getAuthorActivityByBmno(long bmno) {
        Long mno = mapper.selectAuthorMnoByBmno(bmno);
        if (mno == null) throw new IllegalArgumentException("게시글이 존재하지 않습니다.");

        Map<String, Object> info = mapper.selectAuthorInfo(mno);
        AuthorActivityDTO.AuthorInfo author = AuthorActivityDTO.AuthorInfo.builder()
                .mno(((Number) info.getOrDefault("MNO", info.get("mno"))).longValue())
                .userid((String) info.getOrDefault("USERID", info.get("userid")))
                .nickname((String) info.getOrDefault("NICKNAME", info.get("nickname")))
                .postCount(((Number) info.getOrDefault("POSTCOUNT", info.get("postCount"))).intValue())
                .totalPostReports(((Number) info.getOrDefault("TOTALPOSTREPORTS", info.get("totalPostReports"))).intValue())
                .totalCommentReports(((Number) info.getOrDefault("TOTALCOMMENTREPORTS", info.get("totalCommentReports"))).intValue())
                .build();

        return AuthorActivityDTO.builder()
                .author(author)
                .posts(mapper.selectAuthorPosts(mno))
                .selfCommentsOnOwnPosts(mapper.selectSelfCommentsOnOwnPosts(mno))
                .build();
    }

    //  posted=true  1(게시), false → 0(숨김)
    public int toggleMealStatus(long bmno, boolean posted) {
        return mapper.updateMealStatus(bmno, posted ? 1 : 0);
    }
}
