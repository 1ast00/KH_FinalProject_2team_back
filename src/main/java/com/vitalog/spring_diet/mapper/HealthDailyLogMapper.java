package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.HealthDailyLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HealthDailyLogMapper {
    List<HealthDailyLogDTO> selectMyLogs(Map<String, Object> params); // mno, cursor, limit, date
    int selectNextHno();
    int insertHealthDailyLog(HealthDailyLogDTO dto);
    int updateHealthDailyLog(HealthDailyLogDTO dto);
    int deleteHealthDailyLog(Map<String, Object> params); // hno, mno

    //가장 최근 일지(HDATE desc, HNO desc)의 HNO
    Integer selectLatestHno(int mno);
}