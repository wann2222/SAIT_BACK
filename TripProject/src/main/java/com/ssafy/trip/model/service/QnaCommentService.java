package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.dao.QnaCommentDao;
import com.ssafy.trip.model.dto.QnaComment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class QnaCommentService {
	private final QnaCommentDao dao;
	
	public List<QnaComment> searchAll(){
		return dao.searchAll();
	}
	
	public void regist(QnaComment qnacomment) {
		dao.regist(qnacomment);
	}
	
	public List<QnaComment> searchByQnaId(int qna_id){
		return dao.searchByQnaId(qna_id);
	}
	
	public void delete(int cid) {
		dao.delete(cid);
	}
}
