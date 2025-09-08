package com.vitalog.spring_diet.vo;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedResult<T> {
    private PagingVo paging;
    private List<T> items;
}