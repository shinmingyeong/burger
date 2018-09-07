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
	
	BurgerVO bvo = new BurgerVO();
	DrinkVO dvo = new DrinkVO();
	DessertVO desvo = new DessertVO();
	SetVO setvo = new SetVO();
	OrderVO ord = new OrderVO();
	login log = new login();
	
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
	
	//장바구니 )전체 데이터 출력
	public ArrayList<OrderVO_name> printDataBag(){
		ArrayList<OrderVO_name> list = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "SELECT ORDERID, CUSTID "
					+ ", (select burgername from burger where burgerid = o.burgerid) "
					+ ", (select drinkname from drink where drinkid = o.drinkid) "
					+ ", (select dessertname from dessert where DESSERTID = o.DESSERTID) "
					+ ", (select setname from setmenu where SETID = o.SETID) ";
			sql += "FROM ORDERS O ";
			sql += " ORDER BY ORDERID ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<>();
			while (rs.next()) {
				list.add(new OrderVO_name(rs.getInt(1),
									 rs.getString(2),
									 rs.getString(3),
									 rs.getString(4),
									 rs.getString(5),
									 rs.getString(6)
									 ));
			
			}
			
			for(OrderVO_name ovo : list) {
				System.out.println(ovo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return list;
		
	}
	
	//장바구니)아이디검색해서 출력(버거)
	public OrderVO myorderlistbug(String id) {
			OrderVO ovo = null;
		
			try {			
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
				String sql = "";
				sql += "SELECT B.BURGERID, B.BURGERNAME, COUNT(*) ";
				sql += "  FROM ORDERS O, BURGER B ";
				sql += " WHERE O.BURGERID = B.BURGERID ";
				sql += " AND CUSTID = ? ";
				sql += " GROUP BY B.BURGERID, B.BURGERNAME ";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				
				System.out.println("버거번호\t 이름\t 수량");
				if (rs.next()) {
					String str = "";
					str += rs.getString(1) + "\t";
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
			
			return ovo;
		
	}
	
	//장바구니)아이디검색해서 출력(음료)
	public OrderVO myorderlistdk(String id) {
		OrderVO ovo = null;
		
		try {			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "SELECT D.DRINKID, D.DRINKNAME, COUNT(*) ";
			sql += "  FROM ORDERS O, DRINK D ";
			sql += " WHERE O.BURGERID = D.DRINKID ";
			sql += " AND CUSTID = ? ";
			sql += " GROUP D.DRINKID, D.DRINKNAME ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			System.out.println("음료번호\t 이름\t 수량");
			if (rs.next()) {
				String str = "";
				str += rs.getString(1) + "\t";
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
		
		return ovo;
		
	}
	
	//장바구니)아이디검색해서 출력(디저트)
	public OrderVO myorderlistdes(String id) {
		OrderVO ovo = null;
		
		try {			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "SELECT DE.DESSERTID, DE.DESSERTNAME, COUNT(*) ";
			sql += "  FROM ORDERS O, DESSERT DE ";
			sql += " WHERE O.BURGERID = DE.DESSERTID ";
			sql += " AND CUSTID = ? ";
			sql += " GROUP DE.DESSERTID, DE.DESSERTNAME ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			System.out.println("음료번호\t 이름\t 수량");
			if (rs.next()) {
				String str = "";
				str += rs.getString(1) + "\t";
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
		
		return ovo;
		
	}
	
	//장바구니)아이디검색해서 출력(세트)
	public OrderVO myorderlistset(String id) {
		OrderVO ovo = null;
		
		try {			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "SELECT S.SETID, S.SETNAME, COUNT(*) ";
			sql += "  FROM ORDERS O, SETMENU S ";
			sql += " WHERE O.BURGERID = S.SETID ";
			sql += " AND CUSTID = ? ";
			sql += " GROUP S.SETID, S.SETNAME ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			System.out.println("음료번호\t 이름\t 수량");
			if (rs.next()) {
				String str = "";
				str += rs.getString(1) + "\t";
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
		
		return ovo;
		
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
	
	
}
	
	
	