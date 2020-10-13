package main.java.services;
import java.sql.Connection;
import java.util.*;
import main.java.services.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.models.*;

import main.java.models.*;
public class OperationsOnUser {
	
	private String name;
	private String username;
	private String password;
	private String type;
	private Connection con=null;
	private ResultSet rs;
	private CreateConnection connection=new CreateConnection();
	private User userData;
	private User allUsersData;
	private ArrayList<User> allUsersList=new ArrayList();
	
	private boolean status;
	private int count=0;
	
	public OperationsOnUser(User u) {
		name=u.getName();
		username=u.getUsername();
		password=u.getPassword();
		type=u.getType();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=connection.createConnecton();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	

	
	
	
//	Function to insert user in database
	
	public boolean insertUser() {
		status=false;
		String query="insert into users values(?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setString(1,name);
			ps.setString(2,username);
			ps.setString(3,password);
			ps.setString(4,type);
			count=ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
		}
		
		
		
		if (count>0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	
	
//	Function to delete user from database

	public boolean deleteUser(String name) {
		String query="delete from users where name=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1,name);
			count=ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count>0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
//	Function to display user details  
	
	public User displayUser(String name) {
		
		String query="select * from users where name='"+name+"'";
		Statement st;
		try {
			st = con.createStatement();
			rs=st.executeQuery(query);
			
			if(rs.next()) {

				userData=new User(rs.getNString(1),rs.getNString(2),rs.getNString(3),rs.getNString(4));
				
				
			}else {
				
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userData;
		
	}
	
	
	
	public ArrayList<User> displayAllUsers() {
		
		String query="select * from users";
		try {
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(query);
			
			while(rs.next()) {
				allUsersList.add(new User(rs.getNString(1),rs.getNString(2),rs.getNString(3),rs.getNString(4)));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		System.out.println(allUsersList);
		return allUsersList;
	}
	
	
	
	//Function to update name
	
	public boolean updateUserName(String name,String username) {
		String query="update users set name='"+name+"' where username='"+username+"'";
		try {
			Statement st=con.createStatement();
			count=st.executeUpdate(query);
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (count>0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	//Function to change password
	
	public boolean changePassword(String username,String password) {
		String query="update users set password=? where username=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(query);
			ps.setString(1,password);
			ps.setString(2,username);
			count=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (count>0) {
			return true;
		} else {
			return false;
		}
	}

}
