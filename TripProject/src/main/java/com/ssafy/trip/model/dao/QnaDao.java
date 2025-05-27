package com.ssafy.trip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.Qna;

@Mapper
public interface QnaDao {
    public List<Qna> searchAll();
    public void regist(Qna qna);
    public Qna searchByNo(int no);
    public void update(Qna qna);
    public void delete(int no);
}
