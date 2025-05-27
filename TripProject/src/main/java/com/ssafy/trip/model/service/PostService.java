package com.ssafy.trip.model.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.PostDao;
import com.ssafy.trip.model.dto.Page;
import com.ssafy.trip.model.dto.Post;
import com.ssafy.trip.model.dto.SearchCondition;
import com.ssafy.trip.util.DBUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	private final PostDao dao;
        
    public Post getPost(int no)  {  	
    		return dao.getPost(no);
    }
    
    public Page<Post> search(SearchCondition condition) {
    	
    		int totalItems = dao.getTotalCount(condition);
    		List<Post> list = dao.search(condition);
    		Page<Post> page = new Page<>(condition, totalItems, list);
    		return page;
    	
    }
    
    public Page<Post> searchByContent(SearchCondition condition) {
    
    	int totalItems = dao.getTotalCount(condition);
    		char[] pattern = condition.getWord().toCharArray();
    		// 패턴 찾기
    		int plen = pattern.length;
    		int[] pi = new int[plen];
    		for (int i = 1, j = 0; i < plen; i++) {
    			// i번째 문자열까지 탐색 시작
    			while (j > 0 && pattern[i] != pattern[j]) {
    				j = pi[j - 1];
    			}

    			if (pattern[i] == pattern[j])
    				pi[i] = ++j;
    			else
    				pi[i] = 0;
    		}
    		
    		List<Post> list = dao.serachAll();
    		List<Post> newList = new ArrayList<>();
    		for(int k=0;k<list.size();k++) {
    			Post p = list.get(k);
    			char[] content = p.getContent().toCharArray();
    			for (int i = 0, j = 0; i < content.length; i++) {
    				while (j > 0 && content[i] != pattern[j])
    					j = pi[j - 1];

    				if (content[i] == pattern[j]) {
    					if (j == plen - 1) { // 패턴과 전체 일치
    						newList.add(p);
    						j = pi[j]; // 맞았으므로 pi[j] 호출
    						break;
    					} else {
    						++j;
    					}
    				}
    			}
    				
    		}
    		Page<Post> page = new Page<>(condition, totalItems, newList);
    		return page;
    	 
    }
    
    public void regist(Post post)  {  	
    		dao.regist(post);   	
    }
    
    public void update(Post post, int no)  {    	
    		dao.update(post, no);
    }
    
    public void delete(int no)  {
    		dao.delete(no);
    }
}
