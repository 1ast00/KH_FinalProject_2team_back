package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("boardWeekly")
public class BoardWeeklyDTO {
    private int bwno;
    private int mno;
    private int bwage_group;
    private Date bwcreated_at;
    private int bwdanger;
}
