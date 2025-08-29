// (참고) src/main/java/com/vitalog/spring_diet/dto/admindashboard/AdminDashboardDTO.java
// 기존 그대로 사용 가능 (SummaryDTO 내부에 roles 포함되므로 변경 불필요)
package com.vitalog.spring_diet.dto.admindashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class AdminDashboardDTO {
    private SummaryDTO summary;
    private GenderRatioDTO genderRatio;
}
