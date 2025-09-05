package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminReportsPageDTO {
    private List<AdminReportListItemDTO> items;
    private int currentPage;
    private int totalPage;
}
