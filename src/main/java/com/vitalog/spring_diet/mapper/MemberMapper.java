package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MemberMapper {
    MemberDTO findByMemberid(String userid);

    void registerMember(MemberDTO newMember);

    MemberDTO findByid(int mno);

    MemberDTO findID(Map<String, String> map);

    MemberDTO findPW(Map<String, String> map);

    int updatePW(Map<String, String> map);
}
