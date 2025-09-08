package com.vitalog.spring_diet.dto.admindashboard;

import lombok.*;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AdminMealsPageDTO {
    private List<AdminMealsListItemDTO> items;
    private int currentPage;
    private int totalPage;
}
