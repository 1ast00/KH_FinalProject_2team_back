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

    int updateUser(String mname, String nickname, int goalweight, String userid);
  
    //현재 체중 갱신 //0825 sss_log
    int updateWeight(Map<String, Object> p);
}
