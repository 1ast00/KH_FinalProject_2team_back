// src/main/java/com/vitalog/spring_diet/mapper/AdminReportMapper.java
package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.admindashboard.AdminReportDetailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminReportMapper {

    // 목록
    List<Map<String, Object>> selectReportsPage(@Param("status") String status,
                                                @Param("type") String type,
                                                @Param("q") String q,
                                                @Param("offset") int offset,
                                                @Param("size") int size);

    int countReportsPage(@Param("status") String status,
                         @Param("type") String type,
                         @Param("q") String q);

    int updateReportStatus(@Param("reportId") long reportId,
                           @Param("status") String status);

    int insertReport(Map<String, Object> params);

    // 상세
    AdminReportDetailDTO selectReportDetail(@Param("reportId") long reportId);

    // 삭제
    int deleteReport(@Param("reportId") long reportId);
}
