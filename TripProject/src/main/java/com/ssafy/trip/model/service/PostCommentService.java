package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.dao.PostCommentDao;
import com.ssafy.trip.model.dto.PostComment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommentService {
	private final PostCommentDao dao;
	
	public List<PostComment> searchAll(){
		return dao.searchAll();
	}
	
	public void regist(PostComment postcomment) {
		dao.regist(postcomment);
	}
	
	public List<PostComment> searchByPostId(int post_id){
		return dao.searchByPostId(post_id);
	}
	
	public void delete(int cid) {
		dao.delete(cid);
	}
	
}
