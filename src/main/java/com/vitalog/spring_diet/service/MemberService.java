package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.MemberDTO;
import com.vitalog.spring_diet.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberService {
    private final MemberMapper memberMapper;

    public MemberService(MemberMapper memberMapper){
        this.memberMapper = memberMapper;
    }

    public MemberDTO findByMemberid(String userid) {
        return memberMapper.findByMemberid(userid);
    }

    public void registerMember(MemberDTO newMember) {
        memberMapper.registerMember(newMember);
    }

    public MemberDTO findByid(int mno) {
        return memberMapper.findByid(mno);
    }

    //현재 체중 갱신 //0825 sss_log
    public void updateWeight(int mno, double weight) {
        memberMapper.updateWeight(Map.of("mno", mno, "weight", weight));
    }
}
