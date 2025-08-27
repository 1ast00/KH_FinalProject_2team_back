package com.vitalog.spring_diet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodNutritionResponseDTO<T> {
    private int statusCode; // 상태코드
    private String message; //메세지
    private T data; //모든 종류의 데이터를 범용적으로 받을 수 있음
}