package com.ssafy.trip.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.trip.model.dto.Member;
import com.ssafy.trip.model.dto.Plan;

public class BasicMemberDao  {
//	private static BasicMemberDao dao = new BasicMemberDao();
//
//	private BasicMemberDao() {
//	}
//
//	public static BasicMemberDao getDao() {
//		return dao;
//	}
//
//	@Override
//	public int insert(Connection con, Member member) throws SQLException {
//		String sql = "insert into members (name, email, password, address,x,y,lat,lon) "
//				+ "values(?,?,?,?,?,?,?,?)";
//		int result = -1;
//
//		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//			pstmt.setString(1, member.getName());
//			pstmt.setString(2, member.getEmail());
//			pstmt.setString(3, member.getPassword());
//			pstmt.setString(4, member.getAddress());
//			double[] xy = member.getUtmk();
//			double[] latlon = member.getLatLon();
//			pstmt.setDouble(5, xy[0]);
//			pstmt.setDouble(6, xy[1]);
//			pstmt.setDouble(7, latlon[0]);
//			pstmt.setDouble(8, latlon[1]);
//			
//			result = pstmt.executeUpdate();
//		}
//		// END
//		return result;
//	}
//
//	// 로그인 수정.
//	public Member loginMember(Connection conn, String email, String password) throws Exception {
//		String sql = "select * from members where email = ? and password = ?";
//		System.out.println("yes");
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setString(1, email);
//			pstmt.setString(2, password);
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				Member member = new Member();
//				member.setMno(rs.getInt("id"));
//				member.setName(rs.getString("name"));
//				member.setEmail(rs.getString("email"));
//				member.setPassword(rs.getString("password"));
//				member.setAddress(rs.getString("address"));
//				member.setLocation(rs.getDouble("x"), rs.getDouble("y"), 
//						rs.getDouble("lat"), rs.getDouble("lon"));
//				return member;
//			} else {
//				throw new RuntimeException("해당 회원이 없습니다.");
//			}
//		}
//	}
//
//	public List<Member> getMemberList(Connection conn) throws Exception {
//		List<Member> memberList = new ArrayList<>();
//		String sql = "select name, email, password from members";
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Member member = new Member();
//				member.setName(rs.getString("name"));
//				member.setEmail(rs.getString("email"));
//				member.setPassword(rs.getString("password"));
//				memberList.add(member);
//			}
//		}
//		return memberList;
//	}
//
//	public Member getMemberInfo(Connection conn, String email) throws Exception {
//		String sql = "select id,name, email, password from members where email=?";
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setString(1, email);
//			ResultSet rs = pstmt.executeQuery();
//			Member member = new Member();
//			if (rs.next()) {
//				member.setMno(rs.getInt("id"));
//				member.setName(rs.getString("name"));
//				member.setEmail(rs.getString("email"));
//				member.setPassword(rs.getString("password"));
//			}
//			return member;
//		}
//	}
//
//	public void modifyMember(Connection conn, Member member) throws Exception {
//		String sql = "update members set name=?, password=? where email=?";
//
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setString(1, member.getName());
//			pstmt.setString(2, member.getPassword());
//			pstmt.setString(3, member.getEmail());
//			pstmt.executeUpdate();
//			System.out.println(member.getEmail());
//			System.out.println("�닔�젙 �셿猷�");
//		}
//		return;
//	}
//
//	public void deleteMember(Connection conn, String email) throws Exception {
//		String sql = "delete from members where email = ?";
//
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setString(1, email);
//			pstmt.executeUpdate();
//			System.out.println("�쉶�썝 �깉�눜 �셿猷�");
//		}
//	}
//
//	@Override
//	public int findPassword(Connection conn, String email, String tmpPassword) throws SQLException {
//
//		//String sql = "select password from members where email = ?";
//		String sql = "update members set password=? where email=?";
//		String result = "";
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setString(1, tmpPassword);
//			pstmt.setString(2, email);
//			int rs = pstmt.executeUpdate();
//			if (rs != 0) {
//				return rs;
//			} else {
//				throw new RuntimeException("해당 회원이 없습니다.");
//			}
//		}
//
//	}



}
