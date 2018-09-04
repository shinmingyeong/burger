package com.mystudy.burgerproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common_util.JDBC_Close;

public class Burger implements BurgerListCUD {
	
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private	static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private	static final String USER = "Burger";
	private	static final String PASSWORD = "burger";
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	static {
		try {
			Class.forName(DRIVER);
			System.out.println(">> 오라클 JDBC 드라이버 로딩 성공!");
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생]");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ArrayList<BurgerVO> printDataAll() {
		ArrayList<BurgerVO> list = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT BURGERID, BURGERNAME, PRICE ");
			sb.append("  FROM BURGER ");
			sb.append(" ORDER BY BURGERID ");
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<BurgerVO>();
			while (rs.next()) {
				list.add(new BurgerVO(rs.getInt("burgerid"),
									  rs.getString("burgername"),
									  rs.getInt("price")));
				
				if (list.size() < 1) {
					list = null;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
		
	}

	@Override
	public int insertListBurger(ArrayList<BurgerVO> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertListCust(ArrayList<CustomerVO> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertListDrink(ArrayList<DrinkVO> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertListDessert(ArrayList<DessertVO> list) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
