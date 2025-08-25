package com.vitalog.spring_diet.service;

import com.vitalog.spring_diet.dto.MemberDTO;
import com.vitalog.spring_diet.mapper.MemberMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public MemberDTO findID(String mname, String nickname) {
        Map<String, String> map = new HashMap<>();
        map.put("mname", mname);
        map.put("nickname", nickname);

        return memberMapper.findID(map);
    }

    public MemberDTO findPW(String userid, String mname) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("mname", mname);

        return memberMapper.findPW(map);
    }

    public int updatePW(String userid, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", userid);
        map.put("password", password);
        return memberMapper.updatePW(map);
    }

    //현재 체중 갱신
    public void updateWeight(int mno, double weight) {
        memberMapper.updateWeight(Map.of("mno", mno, "weight", weight));
    }
}
