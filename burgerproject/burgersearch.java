package com.mystudy.burgerproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import common_util.JDBC_Close;

public class burgersearch {
	
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private	static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private	static final String USER = "Burger";
	private	static final String PASSWORD = "burger";
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	BurgerVO bvo = new BurgerVO();
	DrinkVO dvo = new DrinkVO();
	DessertVO desvo = new DessertVO();
	SetVO setvo = new SetVO();
	OrderVO ord = new OrderVO();
	Burger burger = new Burger();
	
	Scanner choice = new Scanner(System.in);
	int pick;
	
	static {
		try {
			Class.forName(DRIVER);
			System.out.println(">> 오라클 JDBC 드라이버 로딩 성공!");
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생]");
			e.printStackTrace();
		}
		
	}
	
	//버거)이름으로 조회
		public BurgerVO pickBurger (String burgername) {
			BurgerVO bvo = null;
			
			try {			
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
				String sql = "";
				sql += "SELECT BURGERID, BURGERNAME, PRICE ";
				sql += "  FROM BURGER ";
				sql += " WHERE BURGERNAME = ? ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, burgername);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					String str = "";
					str += rs.getInt(1) + "\t";
					str += rs.getString(2) + "\t";
					str += rs.getInt(3);
					System.out.println(str);
				} else {
					System.out.println("데이터 없음!");
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			}
			
			return bvo;
			
		}
		
		//버거)아이디로 조회
		public BurgerVO pickburger (int burgerid) {
			BurgerVO burgervo = null;
			
			try {			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "SELECT BURGERID, BURGERNAME, PRICE ";
			sql += "  FROM BURGER ";
			sql += " WHERE BURGERID = ? ";
			pstmt = conn.prepareStatement(sql);
					
			pstmt.setInt(1, burgerid);
										
			rs = pstmt.executeQuery();
				
			if (rs.next()) {
				burgervo = new BurgerVO(rs.getInt(1), rs.getString(2), rs.getInt(3));
				String str = "";
				str += rs.getInt(1) + "\t";
				str += rs.getString(2) + "\t";
				str += rs.getInt(3);
				System.out.println(str);
				} else {
					System.out.println("데이터 없음!");
				}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
				}
					
					return burgervo;
					
				}
		
		//세트)아이디로 조회
		public SetVO pickSet (int setid) {
			SetVO setvo = null;
		
			try {			
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
					
				String sql = "";
				sql += "SELECT SETID, SETNAME, PRICE ";
				sql += "  FROM SETMENU ";
				sql += " WHERE SETID = ? ";
				pstmt = conn.prepareStatement(sql);
					
				pstmt.setInt(1, setid);
								
				rs = pstmt.executeQuery();
					
				if (rs.next()) {
					setvo = new SetVO(rs.getInt(1), rs.getString(2), rs.getInt(3));
					String str = "";
					str += rs.getInt(1) + "\t";
					str += rs.getString(2) + "\t";
					str += rs.getInt(3);
					System.out.println(str);
				} else {
					System.out.println("데이터 없음!");
				}
				
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
				}
				
				return setvo;
				
			}

}
