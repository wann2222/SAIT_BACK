package com.ssafy.trip.model.service;



import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.dao.HotplaceDao;
import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Hotplace;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HotplaceService {
    private final HotplaceDao dao;
    
    
    @Transactional
    public List<Hotplace> searchHotplace(){
        return dao.searchHotplace();
    }
    
    @Transactional
    public int getContentTypeId(String content) {
        return dao.getContentTypeId(content);
    }

    @Transactional
    public void regist(Hotplace p, String content, int content_type_id) {
        dao.regist(p, content, content_type_id);

    }
        
    // 전체에서 조회
    @Transactional
    public List<Attraction> searchHotplaces(int offset) {
    	offset = (offset-1)*8;
    	System.out.println(offset);
    	return dao.getHotplaces(offset);
    }    
    // 시도 -> 컨텐츠 내에서 조회
    @Transactional
    public List<Attraction> searchHotplaceBycontetnt(String sido ,String content, int offset) {
    	offset = (offset-1)*8;
    	System.out.println(offset);
    	return dao.getHotplaceBycontetnt(sido,content,offset);
    }
    

    // 시도 내에서 조회
    @Transactional
    public List<Attraction> searchHotplaceBygugun(int no, int offset) {
    	offset = (offset-1)*8;
    	return dao.getHotplaceBygugun(no,offset);
    }
	
    // 시도 내에서 조회
    @Transactional
    public List<Attraction> searchHotplaceBysido(String sido, int offset) {
    	offset = (offset-1)*8;
    	return dao.getHotplaceBysido(sido,offset);
    }
    
    @Transactional
    public String  toggleLike(int uid, int ano) {
        if(dao.likeExist(uid, ano)) {
            System.out.println("좋아요 존재 O");
            dao.decrementLikeCount(ano);
            dao.deleteLike(uid, ano);
            return "unlikeed";
        } else {
            System.out.println("좋아요 존재 X");
            dao.incrementLikeCount(ano);
            dao.insertLike(uid, ano);
            return "likeed";
        }
        
  
    }
    @Transactional
    public List<Attraction> searchLike(int mno){
    	return dao.searchLike(mno);
    }
    
    @Transactional
	public boolean exsistList(int uid, int ano) {
		
		return dao.likeExist(uid, ano);
	}

    @Transactional
	public int getLikeCount(int ano) {

		return dao.getLikeCount(ano);
	}

	public List<Integer> searchAnoList(int uid) {
		// TODO Auto-generated method stub
		return dao.getLikedAnoListMno(uid);
	}

    
}