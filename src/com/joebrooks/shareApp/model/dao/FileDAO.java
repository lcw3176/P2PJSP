package com.joebrooks.shareApp.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.joebrooks.shareApp.model.dto.FileDTO;

public class FileDAO {
	Connection conn;
	PreparedStatement ppst;
	ResultSet rs;

	public static FileDAO instance;
	private DBConnector connector = DBConnector.getInstance();
	
	public static FileDAO getInstance() {
		if(instance == null) {
			instance = new FileDAO();
		}
		
		return instance;
	}
	
	private FileDAO() {}

	// 파일 삭제
	public void delete(FileDTO dto) {
		String query = null;

		if (dto.getPath().equals("")) {
			query = "DELETE FROM filelist "
				   + "WHERE id=? AND filename=? AND filetype=? AND filepath is null";
		} else {
			query = "DELETE FROM filelist "
				   + "WHERE id=? AND filename=? AND filetype=? AND filepath=?";
		}

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, dto.getId());
			ppst.setString(2, dto.getName());
			ppst.setString(3, dto.getType());

			if (!dto.getPath().equals("")) {
				ppst.setString(4, dto.getPath());
			}
			ppst.execute();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			closeDB();
		}

	}

	// 파일 업로드
	public void insert(FileDTO dto) {

		String query = "INSERT INTO filelist "
				     + "VALUES(?, ?, ?, ?, ?, ?)";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, dto.getId());
			ppst.setString(2, dto.getName());
			ppst.setString(3, dto.getSize());
			ppst.setString(4, dto.getType());
			ppst.setString(5, dto.getPath());
			ppst.setString(6, dto.getAuth());

			ppst.execute();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			closeDB();
		}

	}

	// 파일 업로드시 해당 폴더 용량 업데이트
	public void update(FileDTO dto) {
		String query;

		if (dto.getPath().equals("")) {
			query = "UPDATE filelist "
					 + "SET filesize=? "
			       + "WHERE id=? and filename=? and filetype=? and filepath is null";
		} else {
			query = "UPDATE filelist "
					 + "SET filesize=? "
				   + "WHERE id=? and filename=? and filetype=? and filepath=?";
		}

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, dto.getSize());
			ppst.setString(2, dto.getId());
			ppst.setString(3, dto.getName());
			ppst.setString(4, dto.getType());

			if (!dto.getPath().equals("")) {
				ppst.setString(5, dto.getPath());
			}

			ppst.execute();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			closeDB();
		}

	}

	// 파일 가져오기
	public ArrayList<FileDTO> select(String id, String path, String page) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		String name, size, type, query, auth;
		int pagination = Integer.parseInt(page);
		int startPage = pagination * 10 - 9;
		int endPage = pagination * 10;

		if (path != null && !path.equals("")) {
			query = "SELECT rnum , T.* FROM "
					+ "( "
					  + "SELECT ROWNUM AS rnum , S.* "
					    + "FROM filelist S "
					   + "where S.id=? and S.filepath=?"
					+ ") T "
				   + "WHERE rnum BETWEEN ? AND ?";
		} else {
			query = "SELECT rnum , T.* FROM "
					+ "( "
					  + "SELECT ROWNUM AS rnum , S.* "
					    + "FROM filelist S "
					   + "where S.id=? and S.filepath is null"
					+ ") T "
				   + "WHERE rnum BETWEEN ? AND ?";
		}

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);

			if (path != null && !path.equals("")) {
				ppst.setString(1, id);
				ppst.setString(2, path);
				ppst.setInt(3, startPage);
				ppst.setInt(4, endPage);
			} else {
				ppst.setString(1, id);
				ppst.setInt(2, startPage);
				ppst.setInt(3, endPage);
			}

			rs = ppst.executeQuery();

			while (rs.next()) {
				name = rs.getString("FILENAME");
				size = rs.getString("FILESIZE");
				type = rs.getString("FILETYPE");
				auth = rs.getString("AUTH");
				
				FileDTO temp = new FileDTO(name, size, type);
				temp.setAuth(auth);
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

	// 게스트 파일 가져오기(외부 링크로 게시판에 공유 시 사용됨)
	public ArrayList<FileDTO> selectGuestFile(String id, String folderName) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		String name, size, type, path;
		String query = "SELECT * "
				       + "FROM filelist "
				      + "WHERE id=? and filepath "
				       + "LIKE ?";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);

			ppst.setString(1, id);
			ppst.setString(2, "%" + folderName + "%");
			rs = ppst.executeQuery();

			while (rs.next()) {
				name = rs.getString("FILENAME");
				size = rs.getString("FILESIZE");
				type = rs.getString("FILETYPE");
				path = rs.getString("FILEPATH");

				FileDTO temp = new FileDTO(name, size, type);
				temp.setPath(path);
				temp.setId(id);
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

	// 게스트 폴더 가져오기 (게시글 작성시 공유 가능한 폴더 표시에 사용됨)
	public ArrayList<FileDTO> selectGuestFolder(String id) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		String name;
		String query = "SELECT filename "
				       + "FROM filelist "
				      + "WHERE id=? AND auth='guest' AND filetype='folder'";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, id);
			rs = ppst.executeQuery();

			while (rs.next()) {
				name = rs.getString("FILENAME");

				FileDTO temp = new FileDTO();
				temp.setName(name);
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
	
	// 파일 전체 길이 가져오기(페이지네이션에 사용)
	public int getLength(String id, String path) {
		int count = 0;
		String query;

		if (path != null && !path.equals("")) {
			query = "SELECT COUNT(*) "
					+ "FROM filelist "
				   + "WHERE id=? AND filepath=?";
		} else {
			query = "SELECT COUNT(*) "
					+ "FROM filelist "
				   + "WHERE id=? AND filepath is null";
		}

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);

			if (path != null && !path.equals("")) {
				ppst.setString(1, id);
				ppst.setString(2, path);
			} else {
				ppst.setString(1, id);
			}

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
	
	// 특정 타입의 파일들만 선택할 때 사용 (음악, 사진, 동영상)
	public ArrayList<FileDTO> selectFromType(String id, String page, String type) {
		ArrayList<FileDTO> list = new ArrayList<FileDTO>();
		String name, size, path;
		int pagination = Integer.parseInt(page);
		int startPage = pagination * 10 - 9;
		int endPage = pagination * 10;

		String query = "SELECT rnum , T.* "
				       + "FROM ( "
				           + "SELECT ROWNUM AS rnum , S.* "
				             + "FROM filelist S "
				            + "WHERE S.id=? and S.filetype=?"
				            + ") T "
				      + "WHERE rnum "
				    + "BETWEEN ? AND ?";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, id);
			ppst.setString(2, type);
			ppst.setInt(3, startPage);
			ppst.setInt(4, endPage);

			rs = ppst.executeQuery();

			while (rs.next()) {
				name = rs.getString("FILENAME");
				size = rs.getString("FILESIZE");
				type = rs.getString("FILETYPE");
				path = rs.getString("FILEPATH");

				FileDTO temp = new FileDTO(name, size, type, path);
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

	// 특정 타입의 파일들 길이 가져오기, 페이지네이션에 사용(음악, 사진, 동영상)
	public int getLengthFromType(String id, String type) {
		int count = 0;
		String query = "SELECT COUNT(*) "
				       + "FROM filelist "
				      + "WHERE id=? AND filetype=?";

		try {
			conn = connector.getConnection();
			ppst = conn.prepareStatement(query);
			ppst.setString(1, id);
			ppst.setString(2, type);

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
