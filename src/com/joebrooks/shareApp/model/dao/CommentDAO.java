package com.joebrooks.shareApp.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.joebrooks.shareApp.model.dto.CommentDTO;

public class CommentDAO {

	Connection conn;
	PreparedStatement ppst;
	ResultSet rs;

	public static CommentDAO instance;
	private DBConnector connector = DBConnector.getInstance();
	
	public static CommentDAO getInstance() {
		if(instance == null) {
			instance = new CommentDAO();
		}
		
		return instance;
	}
	
	private CommentDAO() {}
	
	// 댓글 작성
	public void insert(CommentDTO dto) {
		String query = "INSERT INTO reply(idx, id, content, reg_date)"
				   + " VALUES(?, ?, ?, ?)";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setInt(1, dto.getIdx());
			ppst.setString(2, dto.getId());
			ppst.setString(3, dto.getContent());
			ppst.setString(4, dto.getReg_date());

			ppst.execute();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			closeDB();
		}

	}

	// 댓글 가져오기
	public ArrayList<CommentDTO> select(int idx, int pagination) {
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		int startPage = pagination * 10 - 9;
		int endPage = pagination * 10;

		String query = "SELECT rnum, T.* "
				       + "FROM ("
				           + "SELECT ROWNUM AS rnum, S.* "
				             + "FROM ( "
				                 + "SELECT * "
				                   + "FROM reply "
				               + "ORDER BY reg_date"
				                   + ") S "
				             + "WHERE S.idx = ? "
				             + ") T "
				      + "WHERE rnum "
				    + "BETWEEN ? AND ?";
		
		String content, id, date;

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setInt(1, idx);
			ppst.setInt(2, startPage);
			ppst.setInt(3, endPage);

			rs = ppst.executeQuery();
			while (rs.next()) {
				content = rs.getString("CONTENT");
				id = rs.getString("ID");
				date = rs.getString("REG_DATE");

				CommentDTO temp = new CommentDTO();
				temp.setId(id);
				temp.setContent(content);
				temp.setReg_date(date);

				list.add(temp);
			}
		}

		catch (Exception e) {
			e.getStackTrace();
		}
		
		finally {
			closeDB();
		}

		return list;
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
