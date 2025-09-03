package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("bMFile")
public class BMFileDTO {
    private int bmfno;
    private int bmmo; //식단게시판 일련번호
    private int mno; //회원일련번호
    private String bmfname;
    private String bmfpath;
}
