package com.vitalog.spring_diet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data // Lombok: getter, setter, toString 등을 자동 생성
@NoArgsConstructor // 파라미터 없는 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성
@Alias("exercise") // MyBatis: XML에서 'exercise'라는 별명으로 사용 가능
public class ExerciseDTO {
    // JSON의 '운동명' 키를 이 변수에 매핑
    @JsonProperty("운동명")
    private String exerciseName;

    // JSON의 '단위체중당에너지소비량' 키를 이 변수에 매핑
    @JsonProperty("단위체중당에너지소비량")
    private double energyConsumption;
}