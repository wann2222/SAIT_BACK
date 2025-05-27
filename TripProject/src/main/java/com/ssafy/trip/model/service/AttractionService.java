package com.ssafy.trip.model.service;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.dao.AttractionDao;
import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Plan;
import com.ssafy.trip.model.dto.PlanDetail;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AttractionService {

	private final  AttractionDao dao;
    
    public List<Attraction> searchAttractions(String sido, String sigungu, String contentType){
        return dao.searchAttraction(sido, sigungu, contentType);
    }
    
    public List<String> getSidoList(){
            return dao.getSidoList();
    }
    
    public List<String> getSigunguList(String sido){
            return dao.getSigunguList(sido);

    }
    
    public List<Attraction> findBySido(String sido_name){
    	return dao.findBySido(sido_name);
    }
    
    public List<Attraction> findBySidoContent(String sido_name, String content_type_name){
    	return dao.findBySidoContent(sido_name, content_type_name);
    }
    
    public List<Attraction> findByGugun(int no){
    	return dao.findByGugun(no); 
    }
    
    public List<Attraction> findByGugunContent(int no, String content_type_name){
    	return dao.findByGugunContent(no, content_type_name);
    }
    
	public void insertPlan(Plan plan) {
    		dao.insertPlan(plan);
    		int pid = plan.getPid();
    	    dao.insertPlanDetail(pid,plan);
	}
	
    public Plan getPlan (int pid) {
    	return dao.getPlan(pid);
    }
    
	public List<Attraction> searchBySido(String sido) {
		return dao.getBysido(sido);
	}
	
	public List<Attraction> searchAttractionsByTitle(String title) {
		// TODO Auto-generated method stub
		return dao.getByTitle(title);
	}
	
    public Attraction getAttractionByAno(int ano) {
        return dao.selectAttractionByAno(ano);
    }
    
	public List<Plan> getPlansByUser(int mno) {
	    return dao.selectPlansByUser(mno);
	}
    

    @Transactional
    public void updatePlan(Plan plan) {
        dao.updatePlan(plan);
    }

    @Transactional
    public void deletePlan(int pid) {
        dao.deletePlan(pid);
    }
    
    
    
      
    // plan 기능 완성 시삭제 해야함.
    @Transactional
    public void addToPlan(int pid, int ano) {
            dao.addToPlan( pid, ano);
    }
    
    @Transactional
    public List<Attraction> planDetail(int pid) {
            return dao.planDetail(pid);
    }
    
    @Transactional
    public void makePlan(int uid, Plan plan) {
            dao.makePlan(uid, plan);

    }

    @Transactional
    public List<Plan> findTripPlanD(int uid)  {
            return dao.findTripPlanD(uid);
    }
    
    @Transactional
    public Plan findTripPlanByPid(int pid) {
            return dao.findTripPlanByPid(pid);

    }






    

    
}