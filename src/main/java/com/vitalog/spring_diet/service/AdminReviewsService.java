package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.admindashboard.*;
import com.vitalog.spring_diet.mapper.AdminReviewsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewsService {

    private final AdminReviewsMapper mapper;

    public AdminReviewsPageDTO getPage(String q, Integer status, int p, int size) {
        int page = Math.max(p, 1);
        int pageSize = Math.max(size, 1);
        int offset = (page - 1) * pageSize;

        List<AdminReviewsListItemDTO> items =
                mapper.selectReviewsPage(q, status, offset, pageSize);
        int total = mapper.countReviewsPage(q, status);
        int totalPage = (int) Math.ceil((double) total / pageSize);

        return AdminReviewsPageDTO.builder()
                .items(items)
                .currentPage(page)
                .totalPage(totalPage)
                .build();
    }


    public AdminReviewDetailDTO getDetail(Long brno) {
        AdminReviewDetailDTO d = mapper.selectReviewDetail(brno);
        if (d == null) return null;
        d.setComments(mapper.selectRecentReviewComments(brno, 5)); // 최근 5개
        return d;
    }
}
