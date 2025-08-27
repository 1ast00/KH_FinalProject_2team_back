package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("hlog")
public class HealthDailyLogDTO {
    private Integer hno;
    private Integer mno;

    // 조회용 문자열
    private String hdateStr;      // TO_CHAR(HDATE,'YYYY.MM.DD')
    private String sleeptimeStr;  // TO_CHAR(SLEEPTIME,'HH24:MI')

    // 입력용
    private String hdate;         // 'YYYY-MM-DD'
    private String sleeptime;     // 'HH:MM'

    // 일지 테이블에 저장하지 않지만 응답/요청으로 주고받는다.
    private Double weight;        // ← member.weight 로 채워서 응답
    private Double wateramount;
    private String exercise;
    private String food;
}