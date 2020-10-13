package main.java.services;
import java.sql.*;


public class CreateConnection {
	
	Connection con;
	
	public Connection createConnecton() {
		try {
			 con=DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/o6guoqkU7U", "o6guoqkU7U","fflIqlLQwL");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

}
