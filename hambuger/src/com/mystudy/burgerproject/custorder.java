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
			System.out.println(">> 오라클 JDBC 드라이버 로딩 성공!");
		} catch (ClassNotFoundException e) {
			System.out.println("[예외발생]");
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
	
	public String id;
	
	public void start() {
		System.out.println("어서오세요. 햄버거입니다!");
		System.out.println("회원이시면 < 1 >, 비회원이시면 < 2 >를 눌러주세요!");
		
		pick = choice.nextInt();
		choice.nextLine();
	
		if(pick == 1) {
			login();
		} else if (pick == 2) {
			addCust();
		} 
	}

	//회원주문
	public String login() {
		String result =  null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("select * from customer where custid =? and password =?");
			
			pstmt = conn.prepareStatement(sb.toString());				
			
			System.out.println("아이디를 입력해주세요");
			String custId = choice.nextLine();
			System.out.println("비밀번호를 입력해주세요");
			String password = choice.nextLine();
				
			pstmt.setString(1, custId);
			pstmt.setString(2, password);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()==true) {
				result=custId;
				System.out.println("접속에 성공하셨습니다.");
				whereEat();
			} else {
				System.out.println("접속에 실패하셨습니다.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	//비회원주문
	public void addCust() {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO CUSTOMER ");
			sb.append("		(CUSTID, PASSWORD, PHONE) ");
			sb.append("VALUES(?, ?, ?)");
			pstmt = conn.prepareStatement(sb.toString());
			
			int i=0;
			System.out.println("[회원가입]" + ++i + " 번 고객으로 주문");
			System.out.println("아이디를 설정해주세요.");
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
			whereEat();
		}
	}
	
	////////////매장 or 테이크아웃?
	public void whereEat() {
		System.out.println("매장에서 드시면 < 1 >, 테이크아웃 하시면 < 2 > 를 눌러주세요!");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) { //직원에게 전달
			cvo.setWhereEat("매장");
		} else if (pick == 2) { //직원에게 전달
			cvo.setWhereEat("테이크아웃");
		} else if (pick == 3) {
			start();
		} else {
		System.out.println("ㅠㅠ 다시 선택해주세요.");
		whereEat();
		}
		whatBg();
	}	
	
	//버거단품/음료/디저트 메뉴선택
	public void whatBg() {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "INSERT INTO ORDERS ";
			sql += "	(ORDERID, CUSTID, BURGERID, DRINKID,"
					+ " DESSERTID, SETID, PRICETOT, ORDERDATE, LIST ) ";
			sql += "VALUES((SELECT NVL(MAX(ORDERID), 0)+1 FROM ORDERS), "
					+ "0, "
					+ "?, ?, ?, ?, ?, sysdate, "
					+ "(SELECT NVL(MAX(LIST), 0)+1 FROM ORDERS)) ";
			pstmt = conn.prepareStatement(sql);
			
			//---------------------------------
		
			int pricetot= bvo.getBgPrice() + dvo.getDkPrice()
						+ desvo.getDsPrice() + setvo.getSetPrice();
			
			System.out.println("**버거메뉴를 선택해주세요.");
			burger.printDataBurger();
			int burgerid = choice.nextInt();
			System.out.println("**음료메뉴를 선택해주세요.");
			burger.printDataDrink();
			int drinkid = choice.nextInt();
			System.out.println("**디저트 메뉴를 선택해주세요.");
			burger.printDataDessert();
			int dessertid = choice.nextInt();
			System.out.println("**세트 메뉴를 선택해주세요.");
			burger.printDataSet();
			int setid = choice.nextInt();
			
			
			System.out.println("***주문이 완료가 되었습니다.***");

			//pstmt.setString(1, custid);
			pstmt.setInt(1, burgerid);
			pstmt.setInt(2, drinkid);
			pstmt.setInt(3, dessertid);
			pstmt.setInt(4, setid);
			pstmt.setInt(5, pricetot);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			ord.title();
			bag();
		}

	}
	
	//장바구니호출후 취소
	public void bag() {
		System.out.println("*~~~~* 주문 리스트 *~~~~*");
		burger.printDataBag();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("주문하신 정보가 맞는지 확인해주세요.");
			System.out.println("취소하시려면 < 1 >, 메인메뉴로 돌아가시려면 < 2 >"
					+ ", 결제를 원하시면 < 3 > 을 눌러주세요!");
			
			String pick = choice.nextLine();
			burger.cancel("0");
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			whereCheck();
		}
		
	}
	
	//결제방법
	public void whereCheck() {
		int sum=0;
		
		System.out.println("결제방법을 선택하세요.");
		System.out.println("1.카드 2.현금");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) {			
			System.out.println(sum + "원이 카드로 결제가 되었습니다."
					+ " 감사합니다!");
			System.out.println("로그인 화면으로 돌아갑니다.");
			cvo.setWhereCheck("카드");
		} else if (pick == 2) {
			System.out.println(sum + "원이 현금으로 결제가 되었습니다."
					+ " 감사합니다!");
			cvo.setWhereCheck("현금");
		} else {
			System.out.println("다시 입력해주세요");
			whereCheck();
		}
	}

	
	

}
