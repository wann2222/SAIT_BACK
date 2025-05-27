package com.ssafy.trip.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.MemberDao;

import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.security.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService  {
	
	private final MemberDao mDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = mDao.getMemberInfo(username);
		if(member == null || !"active".equalsIgnoreCase(member.getStatus())) {
			throw new UsernameNotFoundException(username);
			
		}
		
		return new CustomUserDetails(member);
	}
	
	
}