package com.mystudy.burgerproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common_util.JDBC_Close;

public class login {
	
	private static final String DRIVER = "oracle.jdbc.OracleDriver"; 
	private static final String URL = "jdbc:oracle:thin:@203.236.209.174:1521:xe"; 
	private static final String USER = "burger"; 
	private static final String PASSWORD = "burger"; 
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//static 초기화 구문
		static {
			try {
				Class.forName(DRIVER);
				System.out.println(">> 오라클 JDBC 드라이버 로딩 성공!!");
			} catch (ClassNotFoundException e) {
				System.out.println("[예외발생] 드라이버 로딩 실패!!!");
			}
		}
		
		private Connection getConnection() {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e) {
				System.out.println("[예외발생] DB연결 실패!!!");
			}
			return conn;
		}
		
		
		
		public String login() {
			String result =  null;
			
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			///////////////////////////////////////////////////////////////////
			try {
			StringBuilder sb = new StringBuilder();
			
			sb.append("select * from customer where custid =? and password =?");
			
			pstmt = conn.prepareStatement(sb.toString());				
			
			Scanner choice = new Scanner(System.in);
			
			System.out.println("아이디를 입력해주세요1");
			String custId = choice.nextLine();
			System.out.println("비밀번호를 입력해주세요1");
			String password = choice.nextLine();
													
				pstmt.setString(1, custId);
				pstmt.setString(2, password);
				
				rs=pstmt.executeQuery();
				
				if(rs.next()==true) {
					result=custId;
					System.out.println("접속에 성공하셨습니다.");
				} else {
					System.out.println("접속에 실패하셨습니다.");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			} return result;	

		}
		
		public void createAccount() {
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
					
				StringBuilder sb = new StringBuilder();
				sb.append("INSERT INTO CUSTOMER ");
				sb.append("		(CUSTID, PASSWORD, PHONE) ");
				sb.append("VALUES(?, ?, ?)");
				pstmt = conn.prepareStatement(sb.toString());
				
				Scanner choice = new Scanner(System.in);
				
				int i=0;
				System.out.println("회원가입히겠습니까");
				System.out.println("아이디를 입력해주세요.");
				String custid = choice.nextLine();
				System.out.println("비밀번호를 설정해주세요.");
				String password = choice.nextLine();
				
				System.out.println("핸드폰 번호를 입력해주세요.");
				String phone = choice.nextLine();
				
				pstmt.setString(1, custid);
				pstmt.setString(2, password);
				pstmt.setString(3, phone);
				pstmt.executeUpdate();
				System.out.println("설정이 되었습니다.");
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			}
		}
		
}
