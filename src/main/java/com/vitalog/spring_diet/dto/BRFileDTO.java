package com.vitalog.spring_diet.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("bRFile")
public class BRFileDTO {
    private int brfno;
    private int brno;
    private int mno;
    private String brfname;
    private String brfpath;
}
