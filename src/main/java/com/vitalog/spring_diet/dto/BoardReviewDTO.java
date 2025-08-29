package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Clob;
import java.util.Date;

@Data
@Alias("boardReview")
public class BoardReviewDTO {
    private Number brno;
    private String brtitle;
    private Clob brcontent;
    private Date brwrite_date;
    private Date brwrite_update;
    private Number brhitcount;
    private Number brdanger;
}
