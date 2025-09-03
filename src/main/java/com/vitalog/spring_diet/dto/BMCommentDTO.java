package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("bMComment")
public class BMCommentDTO {
    private int bmcno;
    private int mno;
    private String brccontent;
    private Date brcwrite_date;
    private Date brcwrite_update;
    private Number brcdanger;
}
