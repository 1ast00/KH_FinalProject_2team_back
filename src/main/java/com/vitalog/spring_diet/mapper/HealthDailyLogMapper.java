package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.HealthDailyLogDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HealthDailyLogMapper {
    List<HealthDailyLogDTO> selectMyLogs(Map<String, Object> params); // mno, cursor, limit, date
    int selectNextHno();
    int insertHealthDailyLog(HealthDailyLogDTO dto);
    int updateHealthDailyLog(HealthDailyLogDTO dto);
    int deleteHealthDailyLog(Map<String, Object> params); // hno, mno

    Integer selectLatestHno(int mno);

    int countByDate(Map<String, Object> params); // {mno, date}
    int countByDate(@Param("mno") int mno, @Param("hdate") String hdate);

    // 0903 최신 체중 조회 추가 - 시작
    /** 회원의 가장 최근 건강일지의 hweight 반환 (없으면 null) */
    Double selectLatestWeight(@Param("mno") int mno);
    // 0903 최신 체중 조회 추가 - 끝
}
