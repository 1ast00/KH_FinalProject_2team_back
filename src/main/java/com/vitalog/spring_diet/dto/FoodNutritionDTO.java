package com.vitalog.spring_diet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("food")
public class FoodNutritionDTO {

    //DTO의 field명과 Table의 column명이 다르므로 확인할 것
    //대소문자 차이말하는 듯 받아오는 API명세서 참고하기
    //수정 바람
    //주키: 품목 보고번호
    private String prdlstReportNo;

    private int rnum;
    private String productGb;
    private String prdlstNm;
    private String rawmtrl;
    private String allergy;
    private String nutrient;
    private String prdkind;
    private String manufacture;
    private String imgurl1;
    private String imgurl2;
}