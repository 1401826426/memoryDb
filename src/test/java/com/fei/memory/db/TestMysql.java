package com.fei.memory.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestMysql {
	
	private static String url = "jdbc:mysql://127.0.0.1:3306/data_ay?useUnicode=true&characterEncoding=utf8&useSSL=false";
	private static String user = "root" ; 
	private static String password = "123qwert" ; 
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection connection = DriverManager.getConnection(url, user, password);
		connection.setAutoCommit(false); 
		selectAll(connection) ; 
		PreparedStatement ps = connection.prepareStatement("insert into book(id,name) values(4,4) ;") ;
		ps.execute() ; 
		System.out.println("============================================") ; 
		selectAll(connection);
		System.out.println("============================================") ;
//		connection.commit(); 
		connection.rollback(); 
		selectAll(connection);
		
	}
	
	private static void selectAll(Connection connection) throws SQLException{
		PreparedStatement ps = connection.prepareStatement("select * from book") ;
		ResultSet rs = ps.executeQuery() ;
		while(rs.next()){
			System.out.println(rs.getInt("id")) ; 
		}
	}
	
}
