package com.ssafy.trip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.PostComment;

@Mapper
public interface PostCommentDao {
	public List<PostComment> searchAll();
	
	public void regist(PostComment postcomment);
	
	public List<PostComment> searchByPostId(int post_id);
	
	public void delete(int cid);
}
