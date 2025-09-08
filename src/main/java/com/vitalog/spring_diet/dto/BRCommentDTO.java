package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("bRComment")
public class BRCommentDTO {
    private int brcno; //리뷰하기답글일련번호
    private int brno; //리뷰하기일련번호
    private int mno; //회원일련번호
    private String brccontent;
    private Date brcwrite_date;
    private Date brcwrite_update;
    private int brcdanger;
    private String nickname;
}
