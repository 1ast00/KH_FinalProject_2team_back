package com.vitalog.spring_diet.dto;

import com.fasterxml.jackson.annotation.JsonProperty; // JSON 필드명과 자바 변수명 매핑
import lombok.AllArgsConstructor; // 모든 필드를 포함하는 생성자를 만드는 Lombok 어노테이션
import lombok.Data; // getter, setter 등을 자동으로 만드는 Lombok 어노테이션
import lombok.NoArgsConstructor; // 기본 생성자 만드는 Lombok 어노테이션
import org.apache.ibatis.type.Alias; // MyBatis에서 사용할 별명을 지정

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