package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

@Data
@Alias("boardReview")
public class BoardReviewDTO {
    private long brno;
    private String brtitle;
    private String brcontent;
    private Date brwrite_date;
    private Date brwrite_update;
    private long brhitcount;
    private long brdanger;
}
