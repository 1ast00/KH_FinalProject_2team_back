package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("BoardWeekly")
public class BoardWeeklyDTO {
    private Number bwno;
    private Number age_goup;
    private Date bwcreated_at;
    private Number bwdanger;
}
