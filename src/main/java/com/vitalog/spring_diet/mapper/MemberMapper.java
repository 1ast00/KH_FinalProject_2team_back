package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {
    MemberDTO findByMemberid(String userid);

    void registerMember(MemberDTO newMember);

    MemberDTO findByid(int mno);

    //현재 체중 갱신 //0825 sss_log
    int updateWeight(Map<String, Object> p);
}
