package com.vitalog.spring_diet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("todo")
public class TodoListDTO {
    private int tno;
    private int mno;
    private String tdate;
    private String tcontent;
    private int tcheck;
}