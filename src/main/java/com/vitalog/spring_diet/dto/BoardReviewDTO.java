package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;


@Data
@Alias("boardReview")
public class BoardReviewDTO {
    private int brno;
    private int mno;
    private String brtitle;
    private String brcontent;
    private Date brwrite_date;
    private Date brwrite_update;
    private int brdanger;
    private int brviewcount;
    private String nickname;
    private int awesomeCount;
}
