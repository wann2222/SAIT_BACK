package com.ssafy.trip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.Member;

@Mapper
public interface MemberDao {
    public int insert(Member member);

    public Member loginMember(String email, String password);

    public List<Member> getMemberList();

    public Member getMemberInfo(String email);

    public void modifyMember(Member member);

    public void deleteMember(String email);

    public int findPassword(String email, String password);
    
    public Member getMemberById(int mid);


}