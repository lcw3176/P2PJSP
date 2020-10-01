package com.joebrooks.shareApp.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:chan3176";

	private static DBConnector instance;
	
	private DBConnector() {}
	
	public static DBConnector getInstance() {
		if(instance == null) {
			instance = new DBConnector();
		}
		
		return instance;
	}
	
	// DB 연결하기
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, "joebrooks", "joebrooks");
		return conn;
	}
}
