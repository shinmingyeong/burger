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
	Burger burger = new Burger();
	
	Scanner choice = new Scanner(System.in);
	int pick;
	
	static {
		try {
			Class.forName(DRIVER);
			System.out.println(">> ����Ŭ JDBC ����̹� �ε� ����!");
		} catch (ClassNotFoundException e) {
			System.out.println("[���ܹ߻�]");
			e.printStackTrace();
		}
		
	}
	
	//��ٱ��� )��ü ������ ���
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
	
	//����)��ü������ ���
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
	

	//����)��ü������ ���		
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
	
	//����Ʈ)��ü������ ���
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
	
	//��Ʈ)��ü������ ���
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
	
	
	
	//���� ������ ���� �Է�
	public CustomerVO pickCust () {
		// TODO Auto-generated method stub
		return null;
	}
	
	//��������
	public void count () {
		
		
		
	}

	//�߰��ֹ�
	
	public void whatBg(String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "INSERT INTO ORDERS ";
			sql += "(ORDERID, CUSTID, BURGERID, ORDERDATE, LIST) ";
			sql += "VALUES ((SELECT NVL(MAX(ORDERID),0)+1 ";
			sql += "FROM ORDERS), ";
			sql += "?, ?, sysdate, ";
			sql += "(SELECT NVL(MAX(LIST),0)+1\r\n" + 
					"        FROM ORDERS WHERE CUSTID = 1)) ";
			pstmt = conn.prepareStatement(sql);
			
			//---------------------------------
			
			
			System.out.println("**���Ÿ޴��� �������ּ���.");
			burger.printDataBurger();
			int burgerid = choice.nextInt();
//			System.out.println("**����޴��� �������ּ���.");
//			burger.printDataDrink();
//			int drinkid = choice.nextInt();
//			System.out.println("**����Ʈ �޴��� �������ּ���.");
//			burger.printDataDessert();
//			int dessertid = choice.nextInt();
//			System.out.println("**��Ʈ �޴��� �������ּ���.");
//			burger.printDataSet();
//			int setid = choice.nextInt();
			
			
			System.out.println("***�ֹ��� �Ϸᰡ �Ǿ����ϴ�.***");

			pstmt.setString(1, id);
			pstmt.setInt(2, burgerid);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			ord.title();
			//bag();
		}

	}
	
	
	
	
	
	
	//��ٱ��� ���� ���
	public void cancel(String burgerid) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("update orders set burgerid = ? "
					+ "where burgerid = ? and custid = ? and list = ?");
											
			pstmt = conn.prepareStatement(sb.toString());
			
			System.out.println("������ �������ֽʽÿ�.");
			
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
			e.printStackTrace();
		}
	}
	
	
	//�����
	

}
