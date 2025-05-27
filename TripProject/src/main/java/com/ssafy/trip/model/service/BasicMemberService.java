package com.ssafy.trip.model.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.dao.MemberDao;
import com.ssafy.trip.model.dto.Member;


import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BasicMemberService {
    private final MemberDao dao;

    @Transactional
    public int registMember(Member member) {
        return dao.insert(member);
    }

    @Transactional
    public Member login(String email, String password){
        return dao.loginMember(email, password);
    }

    @Transactional
    public List<Member> getMemberList(){
        return dao.getMemberList();
    }

    @Transactional
    public Member getMemberInfo(String email)  {
        return dao.getMemberInfo(email);
    }

    @Transactional
    public void modifyMember(Member member) {
        dao.modifyMember(member);
    }

    @Transactional
    public void deleteMember(String email)  {
        dao.deleteMember(email);
    }

    @Transactional
    public int findPassword(String email, String password) {
        return dao.findPassword(email, password);
    }
    @Transactional
    public Member getMemberById(int mid) {
        return dao.getMemberById(mid);
    }
    
}