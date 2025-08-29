package com.vitalog.spring_diet.dto.admindashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderRatioDTO {
    @JsonProperty("M")
    private int M;
    @JsonProperty("F")
    private int F;
    @JsonProperty("ETC")
    private int ETC;
}
