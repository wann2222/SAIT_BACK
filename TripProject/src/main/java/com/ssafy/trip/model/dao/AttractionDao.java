package com.ssafy.trip.model.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Plan;

@Mapper
public interface AttractionDao {

    public List<Attraction> searchAttraction(String sido, String sigungu, String contentType);

    public List<String> getSidoList();

    // 특정 시/도의 군/구 목록 가져오기
    public List<String> getSigunguList(String sido);
    
    public List<Attraction> findBySido(String sido_name);
    
    public List<Attraction> findBySidoContent(String sido_name, String content_type_name);
    
    public List<Attraction> findByGugun(int no);
    
    public List<Attraction> findByGugunContent(int no, String content_type_name);
    
    public Attraction selectAttractionByAno(int ano);
    
    public void makePlan(int uid, Plan plan);


    public List<Plan> findTripPlanD(int uid);

    
    public Plan findTripPlanByPid(int pid);

    
    public int findLastOrder(int pid);

    
    public void addToPlan(int pid, int ano);

    
    public List<Attraction> planDetail(int pid);

	public void insertPlan(Plan plan);

	public void insertPlanDetail(int pid,Plan plan);

	public Plan getPlan(int pid);

	public List<Attraction> getBysido(String sido);

	public List<Attraction> getByTitle(String title);

    public void updatePlan(Plan plan);         // 수정
    public void deletePlan(int pid);           // 삭제
    public List<Plan> selectPlansByUser(int mno);

}
