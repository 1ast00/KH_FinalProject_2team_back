package com.vitalog.spring_diet.dto.admindashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderRatioDTO {
    private int M;
    private int F;
    private int ETC;
}
