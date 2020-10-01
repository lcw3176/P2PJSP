package com.joebrooks.shareApp.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.joebrooks.shareApp.model.dto.BoardDTO;

public class BoardDAO {
	private static BoardDAO instance;
	private DBConnector connector = DBConnector.getInstance();
	
	Connection conn;
	PreparedStatement ppst;
	ResultSet rs;
	
	private BoardDAO() {}
	
	public static BoardDAO getInstance() {
		if(instance == null) {
			instance = new BoardDAO();
		}
		
		return instance;
	}
	
	// 게시글 작성
	public Boolean insert(BoardDTO dto) {
		String query = "INSERT INTO board(id, title, content, reg_date, view_count, idx, sharefolder) "
				     + "VALUES(?, ?, ?, ?, ?, BOARD_SEQ.NEXTVAL, ?)";
		Boolean result = false;

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, dto.getId());
			ppst.setString(2, dto.getTitle());
			ppst.setNString(3, dto.getContent());
			ppst.setDate(4, dto.getRegDate());
			ppst.setInt(5, dto.getViewCount());
			ppst.setString(6, dto.getShare());

			result = ppst.execute();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
		
		}

		return result;
	}
	
	
	// 페이지 넘길때 게시글 가져오기
	public ArrayList<BoardDTO> selectBoardList(int page) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		int startPage = page * 10 - 9;
		int endPage = page * 10;
		
		
		String query = "SELECT rnum, T.* "
				       + "FROM ("
				           + "SELECT ROWNUM AS rnum, S.* "
				             + "FROM ( "
				                 + "SELECT * "
				                   + "FROM BOARD "
				                  + "ORDER BY idx DESC"
				                  + ") S "
				             + ") T "
				      + "WHERE rnum "
				    + "BETWEEN ? AND ?";
		
		String title, id;
		Date date;
		int idx, viewCount;

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setInt(1, startPage);
			ppst.setInt(2, endPage);
			
			rs = ppst.executeQuery();
			while (rs.next()) {
				title = rs.getString("TITLE");
				id = rs.getString("ID");
				date = rs.getDate("REG_DATE");
				viewCount = rs.getInt("VIEW_COUNT");
				idx = rs.getInt("IDX");

				BoardDTO temp = new BoardDTO();
				temp.setId(id);
				temp.setTitle(title);
				temp.setRegDate(date);
				temp.setViewCount(viewCount);
				temp.setIdx(idx);

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
	
	// 게시글 검색했을때 가져오기
	public ArrayList<BoardDTO> selectSearchResult(String searchTitle, int page) {
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		int startPage = page * 10 - 9;
		int endPage = page * 10;
		
		
		String query = "SELECT rnum, T.* "
				       + "FROM ( "
				           + "SELECT rownum AS rnum, S.* "
				             + "FROM ("
				                 + "SELECT * "
				                   + "FROM board "
				                  + "WHERE title LIKE ? "
				               + "ORDER BY board.idx DESC) S "
				            + ") T "
				      + "WHERE rnum "
				    + "BETWEEN ? AND ?";

		String title, id;
		Date date;
		int viewCount, idx;

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, "%" + searchTitle + "%");
			ppst.setInt(2, startPage);
			ppst.setInt(3, endPage);
			
			rs = ppst.executeQuery();
			while (rs.next()) {
				title = rs.getString("TITLE");
				id = rs.getString("ID");
				date = rs.getDate("REG_DATE");
				viewCount = rs.getInt("VIEW_COUNT");
				idx = rs.getInt("IDX");

				BoardDTO temp = new BoardDTO();
				temp.setId(id);
				temp.setTitle(title);
				temp.setRegDate(date);
				temp.setViewCount(viewCount);
				temp.setIdx(idx);

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

	
	// 게시글 선택
	public BoardDTO select(int idx, String title) {
		String query = "SELECT * "
				       + "FROM board "
				      + "WHERE idx=? AND title=?";
		
		BoardDTO temp = null;

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setInt(1, idx);
			ppst.setString(2, title);

			rs = ppst.executeQuery();
			while (rs.next()) {
				temp = new BoardDTO();
				
				temp.setId(rs.getString("ID"));
				temp.setTitle(rs.getString("TITLE"));
				temp.setContent(rs.getNString("CONTENT"));
				temp.setRegDate(rs.getDate("REG_DATE"));
				temp.setViewCount(rs.getInt("VIEW_COUNT"));
				temp.setIdx(rs.getInt("IDX"));
				temp.setShare(rs.getString("SHAREFOLDER"));

			}
		}

		catch (Exception e) {
			e.getStackTrace();
		}
		
		finally {
			closeDB();
		}
		
		return temp;
	}
	
	
	// 조회수 업데이트
	public void updateViewCount(BoardDTO dto) {
		String query = "UPDATE board "
				        + "SET view_count=? "
				      + "WHERE id=? AND title=?";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setInt(1, dto.getViewCount());
			ppst.setString(2, dto.getId());
			ppst.setString(3, dto.getTitle());

			ppst.execute();
		}

		catch (Exception e) {
			e.getStackTrace();
		}
		
		finally {
			closeDB();
		}

	}
	
	// 글 삭제
	public void delete(BoardDTO dto) {
		String query = "DELETE FROM board "
				      + "WHERE idx=? AND id=?";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setInt(1, dto.getIdx());
			ppst.setString(2, dto.getId());

			ppst.execute();
		}

		catch (Exception e) {
			e.getStackTrace();
		}
		
		finally {
			closeDB();
		}

	}
	
	// 게시글 전체 길이 얻어오기 
	public int getBoardListLength() {
		int count = 0;
		String query = "SELECT COUNT(*) "
				       + "FROM board";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);

			rs = ppst.executeQuery();
			rs.next();

			count = rs.getInt(1);
		}

		catch (Exception e) {
			e.getStackTrace();
		}
		
		finally {
			closeDB();
		}
		
		return count;
	}
	
	// 검색한 게시글 전체 길이 얻아오기
	public int getSearchResultLength(String searchTitle) {
		int count = 0;
		String query = "SELECT COUNT(*) "
				       + "FROM board "
				      + "WHERE title LIKE ?";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, "%" + searchTitle + "%");
			
			rs = ppst.executeQuery();
			rs.next();

			count = rs.getInt(1);
		}

		catch (Exception e) {
			e.getStackTrace();
		}
		
		finally {
			closeDB();
		}
		
		return count;
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
