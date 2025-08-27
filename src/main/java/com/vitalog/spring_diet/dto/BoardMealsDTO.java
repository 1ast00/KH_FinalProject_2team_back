package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Clob;
import java.sql.Date;

@Data
@Alias("BoardMeals")
public class BoardMealsDTO {
    private Number bmmo;
    private String bmtitle;
    private Clob bmcontent;
    private Date bmwrite_date;
    private Date bmwrite_update;
    private Number bmdanger;
    private Number bmhitcount;
}
