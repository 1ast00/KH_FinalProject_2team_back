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
    private String brcontent;  //update 25.08.29
    private Date brwrite_date;
    private Date brwrite_update;
    private int brdanger;
    private int brviewcount;
    private String mname;;//update 25.09.03
}
