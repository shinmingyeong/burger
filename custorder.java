package com.mystudy.burgerproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

import common_util.JDBC_Close;

public class custorder {
	
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
			System.out.println(">> ����Ŭ JDBC ����̹� �ε� ����!");
		} catch (ClassNotFoundException e) {
			System.out.println("[���ܹ߻�]");
			e.printStackTrace();
		}
	}
	
	Scanner choice = new Scanner(System.in);
	int pick;
	ArrayList list = new ArrayList();
	CustomerVO cvo = new CustomerVO();
	
	BurgerVO bvo = new BurgerVO();
	DrinkVO dvo = new DrinkVO();
	DessertVO desvo = new DessertVO();
	SetVO setvo = new SetVO();
	OrderVO ord = new OrderVO();
	Burger burger = new Burger();
	login log = new login();
	
	public void start() {
		System.out.println("�������. �ܹ����Դϴ�!");
		System.out.println("ȸ���̽ø� < 1 >, ��ȸ���̽ø� < 2 >�� �����ּ���!");
		
		pick = choice.nextInt();
		choice.nextLine();
	
		if(pick == 1) {
			login();
		} else if (pick == 2) {
			addCust();
		} 
	}

	//ȸ���ֹ�
	public String id;
	
	public String login() {
		
		String result =  null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("select * from customer where custid =? and password =?");
			
			pstmt = conn.prepareStatement(sb.toString());				
			
			System.out.println("���̵� �Է����ּ���");
			String custId = choice.nextLine();
			System.out.println("��й�ȣ�� �Է����ּ���");
			String password = choice.nextLine();
				
			pstmt.setString(1, custId);
			pstmt.setString(2, password);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()==true) {
				result=custId;
				id = custId;
				System.out.println("���ӿ� �����ϼ̽��ϴ�.");
				whereEat();
			} else {
				System.out.println("���ӿ� �����ϼ̽��ϴ�.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	//��ȸ���ֹ�
	public void addCust() {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO CUSTOMER ");
			sb.append("		(CUSTID, PASSWORD, PHONE) ");
			sb.append("VALUES(?, ?, ?)");
			pstmt = conn.prepareStatement(sb.toString());
			
			int i=0;
			System.out.println("[ȸ������]" + ++i + " �� ������ �ֹ�");
			System.out.println("���̵� �������ּ���.");
			String custid = choice.nextLine();
			System.out.println("��й�ȣ�� �������ּ���.");
			String password = choice.nextLine();
			
			System.out.println("�ڵ��� ��ȣ�� �Է����ּ���.");
			String phone = choice.nextLine();
			
			pstmt.setString(1, custid);
			pstmt.setString(2, password);
			pstmt.setString(3, phone);
			pstmt.executeUpdate();
			System.out.println("������ �Ǿ����ϴ�.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			whereEat();
		}
	}
	
	////////////���� or ����ũ�ƿ�?
	public void whereEat() {
		System.out.println("���忡�� ��ø� < 1 >, ����ũ�ƿ� �Ͻø� < 2 > �� �����ּ���!");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) { //�������� ����
			cvo.setWhereEat("����");
		} else if (pick == 2) { //�������� ����
			cvo.setWhereEat("����ũ�ƿ�");
		} else if (pick == 3) {
			start();
		} else {
		System.out.println("�Ф� �ٽ� �������ּ���.");
		whereEat();
		}
		//whatBg();
		selectBurger(id);
	}
	
	//��ǰ/��Ʈ ����
	public void pick() {
		
	}
	
	
	//ȸ���� �����ֹ�
	public void selectBurger(String id) {
        
        try {
           conn = DriverManager.getConnection(URL, USER, PASSWORD);
           
           StringBuffer sql = new StringBuffer();
           
           sql.append("update ORDERS ");
           sql.append(" set burgerid = ? ");
           sql.append(" , burgernum =  ");
           sql.append(" WHERE custid = ? ");
           pstmt = conn.prepareStatement(sql.toString());
           
           System.out.println("**���Ÿ޴��� �������ּ���.");
           burger.printDataBurger();
           int selectbur = choice.nextInt();
           
           pstmt.setInt(1, selectbur);
           pstmt.setString(2, id);
           
           pstmt.executeUpdate();
           System.out.println("���ż����� �Ϸ�Ǿ����ϴ�.");
           System.out.println("�ֹ��� �������� < 1 >,");
           System.out.println("����ȭ������ �Ѿ�÷��� < 2 > �� �����ּ���.");
           
           pick = choice.nextInt();
           choice.nextLine();
   	
           if(pick == 1) {
        	   burger.whatBg(id);
        	   bag();
           } else if (pick == 2) {
        	   selectDrink(id); 
           }
           
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
           JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
        }
     }
	
	//ȸ���� �����ֹ�
	public void selectDrink(String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update ORDERS ");
			sql.append(" set drinkid = ? ");
			sql.append(" WHERE custid = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			System.out.println("**����޴��� �������ּ���.");
			burger.printDataDrink();
			int selectdrk = choice.nextInt();
			
			pstmt.setInt(1, selectdrk);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			System.out.println("���ἱ���� �Ϸ�Ǿ����ϴ�.");
			System.out.println("�ֹ��� �������� < 1 >,");
	        System.out.println("����ȭ������ �Ѿ�÷��� < 2 > �� �����ּ���.");
	        
	        pick = choice.nextInt();
	        choice.nextLine();
	        
	        if(pick == 1) {
	        	bag();
	        } else if (pick == 2) {
	        	selectDs(id);
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	//ȸ���� ����Ʈ�ֹ�
	public void selectDs(String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update ORDERS ");
			sql.append(" set dessertid = ? ");
			sql.append(" where custid = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			System.out.println("**����Ʈ �޴��� �������ּ���.");
			burger.printDataDessert();
			int selectds = choice.nextInt();
			
			pstmt.setInt(1, selectds);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			System.out.println("����Ʈ������ �Ϸ�Ǿ����ϴ�.");
			System.out.println("�ֹ��� �������� < 1 >,");
			System.out.println("�߰��ֹ��� ���ϽŴٸ� < 2 > �� �����ּ���.");
	           
	        pick = choice.nextInt();
	        choice.nextLine();
	   	
	        if(pick == 1) {
	        	bag();
	        } else if (pick == 2) {
	        	bag();
	        	//whatBg(id); 
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}

	//��ٱ���ȣ���� ���
	public void bag() {
		System.out.println("*~~~~* �ֹ� ����Ʈ *~~~~*");
		burger.printDataBag();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("�ֹ��Ͻ� ������ �´��� Ȯ�����ּ���.");
			System.out.println("����Ͻ÷��� < 1 >, ���θ޴��� ���ư��÷��� < 2 >"
					+ ", ������ ���Ͻø� < 3 > �� �����ּ���!");
			
			String pick = choice.nextLine();
			burger.cancel("0");
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			whereCheck();
		}
		
	}
	
	//�������
	public void whereCheck() {
		int sum=0;
		
		System.out.println("��������� �����ϼ���.");
		System.out.println("1.ī�� 2.����");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) {			
			System.out.println(sum + "���� ī��� ������ �Ǿ����ϴ�."
					+ " �����մϴ�!");
			System.out.println("�α��� ȭ������ ���ư��ϴ�.");
			cvo.setWhereCheck("ī��");
		} else if (pick == 2) {
			System.out.println(sum + "���� �������� ������ �Ǿ����ϴ�."
					+ " �����մϴ�!");
			cvo.setWhereCheck("����");
		} else {
			System.out.println("�ٽ� �Է����ּ���");
			whereCheck();
		}
	}

}
