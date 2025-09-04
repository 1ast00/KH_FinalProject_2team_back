package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Clob;
import java.sql.Date;

@Data
@Alias("boardMeals")
public class BoardMealsDTO {
    private int bmmo; //식단공유 일련번호
    private int mno;  //회원 일련번호
    private String bmtitle;
    private Clob bmcontent;
    private Date bmwrite_date;
    private Date bmwrite_update;
    private int bmdanger;
    private int bmviewcount;
    private String mname;
}
