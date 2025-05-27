package com.ssafy.trip.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.trip.model.dto.Attraction;
import com.ssafy.trip.model.dto.Plan;


public class AttractionDaoimpl {
//	private static AttractionDaoimpl dao = new AttractionDaoimpl();
//
//	private AttractionDaoimpl() {
//	}
//
//	public static AttractionDaoimpl getDao() {
//		return dao;
//	}
//
//	public List<Attraction> searchAttraction(Connection con, String sido, String sigungu, String contentType) throws SQLException {
//		String sql = "SELECT a.* "
//				+ "FROM attractions a "
//				+ "JOIN sidos s ON a.area_code = s.sido_code "
//				+ "JOIN guguns g ON a.si_gun_gu_code = g.gugun_code and a.area_code = g.sido_code "
//				+ "JOIN contenttypes c on c.content_type_id = a.content_type_id "
//				+ "WHERE s.sido_name = ? "
//				+ "  AND g.gugun_name = ? "
//				+ "  AND content_type_name = ? "
//				+ "LIMIT 20;";
//		List<Attraction> list = new ArrayList<>();
//		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//			pstmt.setString(1, sido);
//			pstmt.setString(2, sigungu);
//			pstmt.setString(3, contentType);
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Attraction at = new Attraction();
//				at.setAno(rs.getInt("no"));
//				at.setName(rs.getString("title"));
//				at.setLat(rs.getDouble("latitude"));
//				at.setLon(rs.getDouble("longitude"));
//				at.setAddress(rs.getString("addr1"));
//				at.setOverview(rs.getString("overview"));
//				at.setImg(rs.getString("first_image1"));
//				list.add(at);
//			}
//		}
//		return list;
//	}
//
//	public List<String> getSidoList(Connection con) throws Exception {
//		List<String> sidoList = new ArrayList<>();
//		String sql = "SELECT sido_name FROM sidos";
//
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				sidoList.add(rs.getString("sido_name"));
//			}
//		}
//		return sidoList;
//	}
//
//	// 특정 시/도의 군/구 목록 가져오기
//	public List<String> getSigunguList(Connection con, String sido) throws Exception {
//		List<String> sigunguList = new ArrayList<>();
//		String sql = "SELECT gugun_name FROM guguns WHERE sido_code = "
//				+ "(select sido_code from sidos where sido_name=?)";
//		try (PreparedStatement ps = con.prepareStatement(sql)) {
//			ps.setString(1, sido);
//			try (ResultSet rs = ps.executeQuery()) {
//				while (rs.next()) {
//					sigunguList.add(rs.getString("gugun_name"));
//				}
//			}
//		}
//		return sigunguList;
//	}
//	
//	public void makePlan(Connection conn, int uid, Plan plan) throws SQLException {
//
//		String sql = "insert into plan(id, name, start, end) values(?,?,?,?)";
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setInt(1, uid);
//			pstmt.setString(2, plan.getName());
//			pstmt.setString(3, plan.getStart());
//			pstmt.setString(4, plan.getEnd());
//			pstmt.executeUpdate();
//			System.out.println("여행 계획 추가 완료");
//		}
//
//	}
//
//	public List<Plan> findTripPlanD(Connection conn, int uid) throws SQLException {
//
//		String sql = "select * from plan where id=?";
//		List<Plan> list = new ArrayList<>();
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setInt(1, uid);
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Plan plan = new Plan();
//				plan.setPid(rs.getInt("pid"));
//				plan.setMno(rs.getInt("id"));
//				plan.setName(rs.getString("name"));
//				plan.setStart(rs.getString("start"));
//				plan.setEnd(rs.getString("end"));
//				list.add(plan);
//			}
//			return list;
//		}
//	}
//	
//	public Plan findTripPlanByPid(Connection conn, int pid) throws SQLException {
//
//		String sql = "select * from plan where pid=?";
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setInt(1, pid);
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				Plan plan = new Plan();
//				plan.setPid(rs.getInt("pid"));
//				plan.setMno(rs.getInt("id"));
//				plan.setName(rs.getString("name"));
//				plan.setStart(rs.getString("start"));
//				plan.setEnd(rs.getString("end"));
//				return plan;
//			}
//			return null;
//		}
//	}
//	
//	public int findLastOrder(Connection con, int pid) throws SQLException{
//		String sql = "select max(seq) from planDetail where pid=?";
//		try(PreparedStatement pstmt = con.prepareStatement(sql)){
//			pstmt.setInt(1, pid);
//			ResultSet rs = pstmt.executeQuery();
//			if(rs.next()) {
//				return rs.getInt(1);
//			}
//		}
//		return 0;
//	}
//	
//	public void addToPlan(Connection con, int pid, int ano) throws SQLException {
//		String sql = "insert into planDetail(pid, ano, seq) values(?,?,?)";
//		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//			pstmt.setInt(1, pid);
//			pstmt.setInt(2, ano);
//			pstmt.setInt(3, findLastOrder(con, pid)+1); // 마지막 번호보다 1 큰 번호를 순서로 저장
//			pstmt.executeUpdate();
//		}
//	}
//	
//	public List<Attraction> planDetail(Connection con, int pid) throws SQLException {
//		String sql = "select * from plandetail p join attractions a on p.ano=a.no where pid=?";
//		List<Attraction> list = new ArrayList<>();
//		try(PreparedStatement pstmt = con.prepareStatement(sql)){
//			pstmt.setInt(1, pid);
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Attraction at = new Attraction();
//				at.setAno(rs.getInt("no"));
//				at.setName(rs.getString("title"));
//				at.setLat(rs.getDouble("latitude"));
//				at.setLon(rs.getDouble("longitude"));
//				at.setAddress(rs.getString("addr1"));
//				at.setOverview(rs.getString("overview"));
//				at.setImg(rs.getString("first_image1"));
//				list.add(at);
//			}
//		}
//		return list;
//	}
}
