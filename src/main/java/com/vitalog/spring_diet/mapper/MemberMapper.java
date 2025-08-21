package com.vitalog.spring_diet.mapper;

import com.vitalog.spring_diet.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberDTO findByMemberid(String userid);

    void registerMember(MemberDTO newMember);
}
