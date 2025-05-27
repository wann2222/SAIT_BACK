package com.ssafy.trip.model.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Hotplace;

@Mapper
public interface HotplaceDao {

    public List<Hotplace> searchHotplace();
    
    public int getContentTypeId(String content);
    
    public void regist(Hotplace p, String content, int content_type_id);

	public List<Attraction> getHotplaceBycontetnt(String sido, String content, int offset);


	public boolean likeExist(int uid, int ano);

	public void decrementLikeCount(int ano);

	public void incrementLikeCount(int ano);

	public void deleteLike(int uid, int ano);

	public void insertLike(int uid, int ano);

	public List<Attraction> getHotplaceBysido(String sido, int offset);
	
	public List<Attraction> getHotplaceBygugun(int no, int offset);
	
	

	public List<Attraction> getHotplaces(int offset);
	
	public List<Attraction> searchLike(int mno);

	public int getLikeCount(int ano);

	public List<Integer> getLikedAnoListMno(int uid);
	
    
}
