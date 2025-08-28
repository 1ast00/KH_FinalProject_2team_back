package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("BRFile")
public class BRFileDTO {
    private Number brfno;
    private String brfname;
    private String brfpath;
}
