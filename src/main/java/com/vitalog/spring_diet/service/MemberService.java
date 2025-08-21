package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.MemberDTO;
import com.vitalog.spring_diet.mapper.MemberMapper;
import org.springframework.stereotype.Service;

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
}
