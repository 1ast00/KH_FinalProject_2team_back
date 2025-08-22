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
    
    //주키
    private int num;
    
    private String foodNMKR;
    private String servingSize;
    private String energy;
    private String protein;
    private String fat;
    private String carbonhydrate;
    private String fiber;
    private String calcium;
    private String iron;
    private String phosporous;
    private String natrium;
    private String vitaminB1;
    private String vitaminB6;
    private String vitaminB12;
    private String water;

}
