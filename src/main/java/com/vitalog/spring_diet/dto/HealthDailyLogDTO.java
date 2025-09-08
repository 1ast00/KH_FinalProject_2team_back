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

    // 0903 hweight 컬럼 추가 - 시작
    // 실제 테이블 컬럼(Health_Daily_Log.hweight)
    private Double hweight;
    // 프론트 기존 호환(카드에서 item.weight 사용): 조회 시 hweight를 weight에도 매핑
    private Double weight;
    // 0903 hweight 컬럼 추가 - 끝

    private Double wateramount;
    private String exercise;
    private String food;

    /* 0908 DB 색상 저장 전환 - 시작 */
    // Health_Daily_Log.CARD_COLOR 매핑
    private String bgcolor; // 프론트에선 item.bgcolor 로 사용
    /* 0908 DB 색상 저장 전환 - 끝 */
}
