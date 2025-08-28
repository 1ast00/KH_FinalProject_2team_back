package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("BMFile")
public class BMFileDTO {
    private String bmfname;
    private String bmfpath;
}
