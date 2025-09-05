package com.vitalog.spring_diet.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminReportMapper {

    List<Map<String, Object>> selectReportsPage(
            @Param("status") String status,
            @Param("type") String type,
            @Param("q") String q,
            @Param("offset") int offset,
            @Param("size") int size);

    int countReportsPage(
            @Param("status") String status,
            @Param("type") String type,
            @Param("q") String q);

    int updateReportStatus(@Param("reportId") long reportId,
                           @Param("status") String status);

    int insertReport(@Param("reportId") long reportId,
                     @Param("targetType") String targetType,
                     @Param("targetId") long targetId,
                     @Param("reporterMno") long reporterMno,
                     @Param("status") String status);
}
