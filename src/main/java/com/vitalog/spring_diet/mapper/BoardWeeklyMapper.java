package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.BoardWeeklyDTO;
import com.vitalog.spring_diet.dto.BoardWeeklyParticipantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardWeeklyMapper {

    List<BoardWeeklyParticipantDTO> selectWeeklyChampions();
    int checkParticipation(int mno);
    void insertParticipation(BoardWeeklyDTO participation);
    void deleteParticipation(int mno);
}