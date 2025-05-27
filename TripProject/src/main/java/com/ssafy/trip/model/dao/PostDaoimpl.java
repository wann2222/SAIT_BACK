package com.ssafy.trip.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.ssafy.trip.model.dto.Hotplace;
import com.ssafy.trip.model.dto.Post;
import com.ssafy.trip.model.dto.SearchCondition;

public class PostDaoimpl {
//	private static PostDaoimpl dao = new PostDaoimpl();

//	private PostDaoimpl() {
//	}
//
//	public static PostDaoimpl getDao() {
//		return dao;
//	}
//	
//	public Post getPost(Connection con, int no) throws SQLException {
//		String sql = "select * from posts where no=?";
//		try(PreparedStatement pstmt = con.prepareStatement(sql)){
//			pstmt.setInt(1, no);
//			ResultSet rs = pstmt.executeQuery();
//			if(rs.next()) {
//				Post post = new Post();
//				post.setNo(rs.getInt("no"));
//				post.setMid(rs.getInt("mid"));
//				post.setmname(getName(con,post.getMid()));
//				post.setTitle(rs.getString("title"));
//				post.setContent(rs.getString("content"));
//				post.setDate(rs.getString("date"));
//				return post;
//			}
//		}
//		return null;
//	}
//	
//	public void regist(Connection con, Post post) throws SQLException {
//		String sql = "insert into posts(mid,title,content,date) values(?,?,?,?)";
//		try (PreparedStatement pstmt = con.prepareStatement(sql)){
//			pstmt.setInt(1, post.getMid());
//			pstmt.setString(2, post.getTitle());
//			pstmt.setString(3, post.getContent());
//			pstmt.setString(4, post.getDate());
//			pstmt.executeUpdate();
//			System.out.println("게시물 등록 완료");
//		}
//		
//	}
//	
//	public void update(Connection con, Post post, int no) throws SQLException {
//		String sql = "update posts set title=?, content=?, date = ? where no=?";
//        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//            pstmt.setString(1, post.getTitle());
//            pstmt.setString(2, post.getContent());
//            pstmt.setString(3, post.getDate());
//            pstmt.setInt(4, no);
//            pstmt.executeUpdate();
//            System.out.println("수정 완료");
//        }
//	}
//	
//    public void delete(Connection con, int no) throws SQLException {
//        String sql = "delete from posts where no=?";
//        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//            pstmt.setInt(1, no);
//            pstmt.executeUpdate();
//            System.out.println("삭제 완료");
//        }
//    }
//
//	public int getTotalCount(Connection con, SearchCondition condition) throws SQLException {
//		int result = 0;
//		String sql = null;
//		boolean hasKeyWord = condition.hasKeyword();
//		String key = condition.getKey();
//
//		sql = hasKeyWord ? key.equals("name")?
//				"select count(*) from posts where mid in (select id from members where name like ?)"
//				: key.equals("title")? "select count(*) from posts where title like ?"
//				: "select count(*) from posts where content like ? "
//				: "select count(*) from posts";
//		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//			if (hasKeyWord) {
//				pstmt.setString(1, "%" + condition.getWord() + "%");
//			}
//			System.out.println(pstmt.toString());
//			ResultSet rs = pstmt.executeQuery();
//			if (rs.next()) {
//				result = rs.getInt(1);
//			}
//		}
//		return result;
//	}
//	
//	public String getName(Connection con, int id) throws SQLException {
//		String sql = "select name from members where id=?";
//		try(PreparedStatement pstmt = con.prepareStatement(sql)){
//			pstmt.setInt(1, id);
//			ResultSet rs = pstmt.executeQuery();
//			if(rs.next()) return rs.getString(1);
//		}
//		return null;
//	}
//	
//	public List<Post> serachAll(Connection con) throws SQLException {
//		List<Post> posts = new ArrayList<>();
//		String sql = "select * from posts";
//		try(PreparedStatement pstmt = con.prepareStatement(sql)){
//			ResultSet rs = pstmt.executeQuery();
//			while(rs.next()) {
//				Post post = new Post();
//				post.setNo(rs.getInt("no"));
//				post.setMid(rs.getInt("mid"));
//				post.setmname(getName(con,post.getMid()));
//				post.setTitle(rs.getString("title"));
//				post.setContent(rs.getString("content"));
//				post.setDate(rs.getString("date"));
//				posts.add(post);
//			}
//		}
//		return posts;
//	}
//	
//	public List<Post> search(Connection con, SearchCondition condition) throws SQLException {
//		List<Post> posts = new ArrayList<>();
//		String sql = null;
//		boolean hasKeyWord = condition.hasKeyword();
//		String key = condition.getKey();
//		System.out.println(condition);
//		sql = hasKeyWord
//				? key.equals("name") ? "select * from Posts where mid in (select id from members where name like ?)"
//						+ "order by no desc limit ?,?"
//				: "select * from Posts where title like ?  order by no desc limit ?,?"
//				: "select * from Posts order by no desc limit ?,?";
//		System.out.println(sql);
//		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
//			int idx = 1;
//			if (hasKeyWord) {
//				pstmt.setString(idx++, "%" + condition.getWord() + "%");
//			}
//			pstmt.setInt(idx++, condition.getOffset());
//			pstmt.setInt(idx++, condition.getItemsPerPage());
//
//			ResultSet rs = pstmt.executeQuery();
//			while (rs.next()) {
//				Post post = new Post();
//				post.setNo(rs.getInt("no"));
//				post.setMid(rs.getInt("mid"));
//				post.setmname(getName(con,post.getMid()));
//				post.setTitle(rs.getString("title"));
//				post.setContent(rs.getString("content"));
//				post.setDate(rs.getString("date"));
//				posts.add(post);
//			}
//		}
//		return posts;
//	}


}
