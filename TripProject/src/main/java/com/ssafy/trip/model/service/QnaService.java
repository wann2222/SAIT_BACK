package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.QnaDao;
import com.ssafy.trip.model.dto.Qna;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaDao qnaDao;

    // 전체 조회
    public List<Qna> searchAll() {
        return qnaDao.searchAll();
    }

    // 등록
    public void regist(Qna qna) {
        qnaDao.regist(qna);
    }

    // 상세 조회
    public Qna searchByNo(int no) {
        return qnaDao.searchByNo(no);
    }

    // 수정
    public void update(Qna qna) {
        qnaDao.update(qna);
    }

    // 삭제
    public void delete(int no) {
        qnaDao.delete(no);
    }
}
