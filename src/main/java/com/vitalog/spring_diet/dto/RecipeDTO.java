package com.vitalog.spring_diet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("recipe")
public class RecipeDTO {
    private int recipe_id;
    private String recipe_nm_ko;
    private String sumry; // 소개
    private String nation_nm; // 유형 분류
    private String ty_nm; // 음식 분류
    private String cooking_time;
    private String level_nm;
    private String qnt; // 분량
}
