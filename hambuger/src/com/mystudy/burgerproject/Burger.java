package com.mystudy.burgerproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.xml.crypto.Data;

import common_util.JDBC_Close;

public class Burger {
	
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
	
	//장바구니 )전체 데이터 출력
	public ArrayList<OrderVO> printDataBag(){
		ArrayList<OrderVO> list = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "SELECT ORDERID, CUSTID, BURGERID, "
					+ "DRINKID, DESSERTID, SETID, PRICETOT FROM ORDERS ";
			sql += " ORDER BY ORDERID ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<OrderVO>();
			while (rs.next()) {
				list.add(new OrderVO(
						rs.getInt("orderid"),
					    rs.getInt("custid"),
					    rs.getInt("burgerid"),
					    rs.getInt("drinkid"),
					    rs.getInt("dessertid"),
					    rs.getInt("setid"),
					    rs.getInt("pricetot")
					    ));
				
				if (list.size() < 1) {
					list = null;
				}
			}
			
			for(OrderVO ovo : list) {
				System.out.println(ovo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
		
	}
	
	//버거)전체데이터 출력
	public ArrayList<BurgerVO> printDataBurger() {
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
			
			for(BurgerVO bvo : list) {
				System.out.println(bvo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
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

	//음료)전체데이터 출력		
	public ArrayList<DrinkVO> printDataDrink() {
		ArrayList<DrinkVO> list = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT DRINKID, DRINKNAME, PRICE ");
			sb.append("  FROM DRINK ");
			sb.append(" ORDER BY DRINKID ");
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<DrinkVO>();
			while (rs.next()) {
				list.add(new DrinkVO(rs.getInt("drinkid"),
									 rs.getString("drinkname"),
									 rs.getInt("price")));
				
				if (list.size() < 1) {
					list = null;
				}
			}
			
			for(DrinkVO dvo : list) {
				System.out.println(dvo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
		
	}
	
	//디저트)전체데이터 출력
	public ArrayList<DessertVO> printDataDessert() {
		ArrayList<DessertVO> list = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT DESSERTID, DESSERTNAME, PRICE ");
			sb.append("  FROM DESSERT ");
			sb.append(" ORDER BY DESSERTID ");
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<DessertVO>();
			while (rs.next()) {
				list.add(new DessertVO(rs.getInt("dessertid"),
									   rs.getString("dessertname"),
									   rs.getInt("price")));
				
				if (list.size() < 1) {
					list = null;
				}
			}
			
			for(DessertVO desvo : list) {
				System.out.println(desvo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
		
	}
	
	//세트)전체데이터 출력
	public ArrayList<SetVO> printDataSet() {
		ArrayList<SetVO> list = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT SETID, SETNAME, PRICE ");
			sb.append("  FROM SETMENU ");
			sb.append(" ORDER BY SETID ");
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<SetVO>();
			while (rs.next()) {
				list.add(new SetVO(rs.getInt("setid"),
								   rs.getString("setname"),
								   rs.getInt("price")));
				
				if (list.size() < 1) {
					list = null;
				}
			}
			
			for(SetVO setvo : list) {
				System.out.println(setvo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
		
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
	
	//고객이 선택한 버거 입력
	public CustomerVO pickCust () {
		// TODO Auto-generated method stub
		return null;
	}
	
	//수량측정
	public void count () {
		
		
		
	}

	
	//장바구니 수량 취소
	public void cancel(String burgerid) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("update orders set burgerid = ? where burgerid = ? and custid = ? and list = ?");
											
			pstmt = conn.prepareStatement(sb.toString());
			
			System.out.println("수량을 선택해주십시오.");
			
			Scanner choice = new Scanner(System.in);
			int j = choice.nextInt();
			choice.nextLine();
			
			for(int i=1; i<=j; i++) {
				
			pstmt.setInt(1, 0);							
			pstmt.setInt(2, 22);							
			pstmt.setString(3, "1"); //custid =3						
			pstmt.setInt(4, i);									
			
			
			
			int result = pstmt.executeUpdate();
			}
			System.out.println(11212);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//돈계산
	

}
