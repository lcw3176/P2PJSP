package com.joebrooks.shareApp.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.joebrooks.shareApp.model.dto.MemberDTO;

public class MemberDAO {
	Connection conn;
	PreparedStatement ppst;
	ResultSet rs;
	
	public static MemberDAO instance;
	private DBConnector connector = DBConnector.getInstance();

	public static MemberDAO getInstance() {
		if (instance == null) {
			instance = new MemberDAO();
		}

		return instance;
	}

	private MemberDAO() {
	}

	// 회원가입
	public Boolean insert(MemberDTO dto) {
		String query = "INSERT INTO member "
				     + "VALUES(?, ?)";
		Boolean result = false;

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, dto.getId());
			ppst.setString(2, dto.getPw());

			result = ppst.execute();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			closeDB();
		}

		return result;
	}

	// 로그인
	public boolean login(MemberDTO dto) {

		String id = null, pw = null;
		String query = "SELECT * "
			  	       + "FROM member "
			  	      + "WHERE id=? AND pw=?";

		try {

			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, dto.getId());
			ppst.setString(2, dto.getPw());
			
			rs = ppst.executeQuery();
			rs.next();
			
			id = rs.getString("ID");
			pw = rs.getString("PW");
			
		}

		catch (Exception e) {
			e.getStackTrace();
		}

		finally {
			closeDB();
		}

		
		if(id.equals(dto.getId()) && pw.equals(dto.getPw())) {
			return true;
		} else {
			return false;
		}

	}
	
	// 회원가입시 중복검사에 사용
	public ArrayList<MemberDTO> select() {

		ArrayList<MemberDTO> arr = new ArrayList<MemberDTO>();
		String id , pw;
		String query = "SELECT * "
				       + "FROM member";

		try {

			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			
			rs = ppst.executeQuery();
			while(rs.next()) {
				id = rs.getString("ID");
				pw = rs.getString("PW");
				
				MemberDTO dto = new MemberDTO(id, pw);
				arr.add(dto);
			}
			
			
			
		}

		catch (Exception e) {
			e.getStackTrace();
		}

		finally {
			closeDB();
		}

		return arr;
	}
	

	public void closeDB() {
		try {
			if (conn != null)
				conn.close();
			if (ppst != null)
				ppst.close();
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
