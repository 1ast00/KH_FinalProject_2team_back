package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("bMComment")
public class BMCommentDTO {
    private int bmcno;
    private int bmno; //리뷰하기일련번호
    private int mno; //회원일련번호
    private String bmccontent;
    private Date bmcwrite_date;
    private Date bmcwrite_update;
    private int bmcdanger;
    private String nickname;
    private int awesomeCount;// 좋아요개수
}
