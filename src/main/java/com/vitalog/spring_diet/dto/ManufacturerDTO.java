package com.vitalog.spring_diet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("manufacturer")
public class ManufacturerDTO {
    private int id;
    private byte[] image;
    private String manufacture;
}
