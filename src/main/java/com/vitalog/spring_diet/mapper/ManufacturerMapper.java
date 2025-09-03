package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.ManufacturerDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManufacturerMapper {
    void insertManufacturer(ManufacturerDTO manufacturer);
}
