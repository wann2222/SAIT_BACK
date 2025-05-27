package com.ssafy.trip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.QnaComment;

@Mapper
public interface QnaCommentDao {
	public List<QnaComment> searchAll();
	
	public void regist(QnaComment qnacomment);
	
	public List<QnaComment> searchByQnaId(int qna_id);
	
	public void delete(int cid);
}
