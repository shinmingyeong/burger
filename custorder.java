package com.mystudy.burgerproject;

import java.nio.channels.SelectionKey;
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
	Burger burger = new Burger();
	
	CustomerVO cvo = new CustomerVO();
	
	BurgerVO bvo = new BurgerVO();
	DrinkVO dvo = new DrinkVO();
	DessertVO desvo = new DessertVO();
	SetVO setvo = new SetVO();
	
	OrderVO ord = new OrderVO();
	login log = new login();
	checkbill check = new checkbill();
	
	public void start() {
		System.out.println("어서오세요. 햄버거입니다!");
		System.out.println("회원이시면 < 1 >, 비회원이시면 < 2 >를 눌러주세요!");
		
		pick = choice.nextInt();
		choice.nextLine();
	
		if(pick == 1) {
			login();
		} else if (pick == 2) {
			addCust();
		} else {
			System.out.println("< 1~2 > 다시 선택해주세요.(T_T)");
			System.out.println("-----------------------------------------------");
			start();
		}
	}

	//회원주문
	public String id;
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
				id = custId;
				System.out.println("접속에 성공하셨습니다.");
				System.out.println("로 딩 중 ~");
				
				JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
				whereEat();
				
			} else {
				System.out.println("접속에 실패하셨습니다.");
				System.out.println("재시도를 원하시면 <1>, 회원가입을 원하시면 <2> 를 눌러주세요.");
				
				pick = choice.nextInt();
				choice.nextLine();
				
				if(pick==1) {
					login();
				} else if (pick==2) {
					addCust();
				} else {
					System.out.println("< 1~2 > 다시 선택해주세요.(T_T)");
					System.out.println("-----------------------------------------------");
					login();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
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
			System.out.println("가입하신 아이디로 로그인 시도해주세요!");
			System.out.println("-----------------------------------------------");
			
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			login();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			
		}
	}
	
	////////////매장 or 테이크아웃?
	public void whereEat() {
		System.out.println("매장에서 드시면 < 1 >, 테이크아웃 하시면 < 2 > 를 눌러주세요!");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) { //직원에게 전달
			pick();
		} else if (pick == 2) { //직원에게 전달
			pick();
		} else {
			System.out.println("< 1~2 > 다시 선택해주세요.(T_T)");
			System.out.println("-----------------------------------------------");
			whereEat();
		}
		
	}
	
	//메뉴
	public void pick() {
		System.out.println("< 메 뉴 >");
		System.out.println("원하시는 번호를 눌러주세요!");
		System.out.println("1.버거  2.음료  3.디저트  4.세트  5.종료");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) {
			selectBurger(id);
		} else if (pick==2) {
			selectDrink(id);
		} else if (pick==3) {
			selectDs(id);
		} else if (pick==4) {
			selectSet(id);
		} else if (pick==5){
			System.out.println("감사합니다.");
			System.out.println("***********************************************");
			start();
		} else {
			System.out.println("< 1~5 > 다시 선택해주세요.(T_T)");
			System.out.println("-----------------------------------------------");
			pick();
		}
		
	}
	
	//회원의 세트주문
	public void selectSet(String id) {
		
		try {
	           conn = DriverManager.getConnection(URL, USER, PASSWORD);
	           
	           String sql = "";
				sql += "INSERT INTO ORDERS ";
				sql += "(ORDERID, CUSTID, SETID, ORDERDATE, LIST) ";
				sql += "VALUES ((SELECT NVL(MAX(ORDERID),0)+1 ";
				sql += "FROM ORDERS), ";
				sql += "?, ?, sysdate, ";
				sql += "(SELECT NVL(MAX(LIST),0)+1\r\n" + 
						"        FROM ORDERS WHERE CUSTID = ?)) ";
				pstmt = conn.prepareStatement(sql);
	           
	           System.out.println("**세트메뉴를 선택해주세요.");
	           burger.printDataSet();
	           int selectset = choice.nextInt();
	           
	           pstmt.setString(1, id);
	           pstmt.setInt(2, selectset);
	           pstmt.setString(3, id);
	           pstmt.executeUpdate();
	           
	           pstmt.executeUpdate();
	           add(id);
	           
	          	           
	        } catch (SQLException e) {
	           e.printStackTrace();
	        } finally {
	           JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
	        }
		
	}
	
	public void add(String id) {
		
		 System.out.println("주문이 끝났으면 < 1 >,");
         System.out.println("추가주문을 원하시면 < 2 > 를 눌러주세요.");
         
         pick = choice.nextInt();
         choice.nextLine();
         
		
         if(pick == 1) {
      	   bag();
         } else if (pick == 2) {
      	  System.out.println("추가주문을 원하시는 메뉴를 선택해주세요.");
      	  System.out.println("1.버거  2.음료  3.디저트  4.세트  5.이전으로");
      	  
      	  pick = choice.nextInt();
	          choice.nextLine();
	          
	          if(pick==1) {
	        	  selectBurger(id);
	          } else if(pick==2) {
	        	  selectDrink(id);
	          } else if(pick==3) {
	        	  selectDs(id);
	          } else if(pick==4) {
	        	  selectSet(id);
	          } else if(pick==5){
	        	  selectSet(id);
	          } else {
	        	  System.out.println("< 1~5 > 다시 선택해주세요.(T_T)");
	        	  System.out.println("-----------------------------------------------");
	        	  selectSet(id);
	          }
	          
         } else {
      	   System.out.println("< 1~2 > 다시 선택해주세요.(T_T)");
      	   System.out.println("-----------------------------------------------");
      	   add(id);
      	   
         }
		
	}
	
	//회원의 버거주문
	public void selectBurger(String id) {
        
        try {
           conn = DriverManager.getConnection(URL, USER, PASSWORD);
           
           String sql = "";
			sql += "INSERT INTO ORDERS ";
			sql += "(ORDERID, CUSTID, BURGERID, ORDERDATE, LIST) ";
			sql += "VALUES ((SELECT NVL(MAX(ORDERID),0)+1 ";
			sql += "FROM ORDERS), ";
			sql += "?, ?, sysdate, ";
			sql += "(SELECT NVL(MAX(LIST),0)+1\r\n" + 
					"        FROM ORDERS WHERE CUSTID = ?)) ";
			pstmt = conn.prepareStatement(sql);
			
           System.out.println("**버거메뉴를 선택해주세요.");
           burger.printDataBurger();
           int selectbur = choice.nextInt();
           
           System.out.println("수량을 선택해주세요.(0~9)");
           
           int num = choice.nextInt();
           choice.nextLine();
           
           for(int i=1; i<=num; i++) {
	           pstmt.setString(1, id);
	           pstmt.setInt(2, selectbur);
	           pstmt.setString(3, id);
	           pstmt.executeUpdate();
           }
           
           System.out.println("버거선택이 완료되었습니다~~~~!^0^");
           System.out.println("-----------------------------------------------");
           System.out.println("주문이 끝났으면 < 1 >,");
           System.out.println("추가주문을 원하시면 < 2 >,");
           System.out.println("다음화면(>음료선택)으로 넘어가시려면 < 3 > 를 눌러주세요.");
           System.out.println("-----------------------------------------------");
           
           pick = choice.nextInt();
           choice.nextLine();
   	
           if(pick == 1) {
        	   bag();
           } else if (pick == 2) {
        	   selectBurger(id);
           } else if (pick == 3) {
        	   selectDrink(id); 
           } else {
        	   System.out.println("< 1~3 > 다시 선택해주세요.(T_T)");
        	   selectBurger(id);
           }
           
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
           JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
        }
     }
	
	//회원의 음료주문
	public void selectDrink(String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "INSERT INTO ORDERS ";
			sql += "(ORDERID, CUSTID, DRINKID, ORDERDATE, LIST) ";
			sql += "VALUES ((SELECT NVL(MAX(ORDERID),0)+1 ";
			sql += "FROM ORDERS), ";
			sql += "?, ?, sysdate, ";
			sql += "(SELECT NVL(MAX(LIST),0)+1\r\n" + 
					"        FROM ORDERS WHERE CUSTID = ?)) ";
			pstmt = conn.prepareStatement(sql);
			
			System.out.println("**음료메뉴를 선택해주세요.");
			burger.printDataDrink();
			int selectdrk = choice.nextInt();
			
			System.out.println("수량을 선택해주세요.(0~9)");
	           
	        int num = choice.nextInt();
	        choice.nextLine();
	           
	        for(int i=1; i<=num; i++) {
				pstmt.setString(1, id);
				pstmt.setInt(2, selectdrk);
				pstmt.setString(3, id);
				pstmt.executeUpdate();
	        }
	        
	        System.out.println("음료선택이 완료되었습니다~~~~!^0^");
	        System.out.println("------------------------------------------------");
	        System.out.println("주문이 끝났으면 < 1 >,");
	        System.out.println("추가주문을 원하시면 < 2 >,");
	        System.out.println("다음화면(>디저트선택)으로 넘어가시려면 < 3 > 를 눌러주세요.");
	        System.out.println("------------------------------------------------");
	        
	        pick = choice.nextInt();
	        choice.nextLine();
	        
	        if(pick == 1) {
	        	bag();
	        } else if (pick == 2) {
	        	selectDrink(id);
	        } else if (pick == 3) {
	        	selectDs(id); 
	        } else {
	        	System.out.println("< 1~3 > 다시 선택해주세요.(T_T)");
	        	selectDrink(id);
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	//회원의 디저트주문
	public void selectDs(String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "INSERT INTO ORDERS ";
			sql += "(ORDERID, CUSTID, DESSERTID, ORDERDATE, LIST) ";
			sql += "VALUES ((SELECT NVL(MAX(ORDERID),0)+1 ";
			sql += "FROM ORDERS), ";
			sql += "?, ?, sysdate, ";
			sql += "(SELECT NVL(MAX(LIST),0)+1\r\n" + 
					"        FROM ORDERS WHERE CUSTID = ?)) ";
			pstmt = conn.prepareStatement(sql);
			
			System.out.println("**디저트 메뉴를 선택해주세요.");
			burger.printDataDessert();
			int selectds = choice.nextInt();
			
			pstmt.setString(1, id);
			pstmt.setInt(2, selectds);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
			
			System.out.println("디저트선택이 완료되었습니다.");
			System.out.println("주문이 끝났으면 < 1 >,");
	        System.out.println("추가주문을 원하시면 < 2 > 를 눌러주세요.");
	           
	        pick = choice.nextInt();
	        choice.nextLine();
	   	
	        if(pick == 1) {
	        	bag();
	        } else if (pick == 2) {
	        	addDs(id);
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	//장바구니호출후 취소
	public void bag() {
		System.out.println("*~~~~* 주문 리스트 *~~~~*");
		System.out.println("주문번호\t 고객번호\t 버거\t 음료\t 디저트\t 세트");
		burger.printDataBag();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("주문하신 정보가 맞는지 확인해주세요.");
			System.out.println("취소하시려면 < 1 >, 메인메뉴로 돌아가시려면 < 2 >"
					+ ", 결제를 원하시면 < 3 > 을 눌러주세요!");
			
			pick = choice.nextInt();
	        choice.nextLine();
			
			if (pick == 1) {
				cancel(id);
			} else if (pick == 2) {
				
			} else if (pick == 3) {
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			whereCheck();
		}
		
	}
	
	//주문수량 취소
	public void cancel(String id) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
	        sb.append("update orders set burgerid = null "
	        		+ "where burgerid = ? and custid = ? and list = ?");
			pstmt = conn.prepareStatement(sb.toString());
			
			System.out.println("취소하실 메뉴번호를 선택해주세요.");
			System.out.println("1.버거  2.음료  3.디저트  4.세트");
			
			pick = choice.nextInt();
	        choice.nextLine();
	        
			if (pick == 1) {
				burger.myorderlistbug(id);
			} else if (pick == 2) {
				burger.myorderlistdk(id);
			} else if (pick == 3) {
				burger.myorderlistdes(id);
			} else if (pick == 4) {
				burger.myorderlistset(id);
			} else {
				System.out.println("ㅠㅠ 다시 선택해주세요.");
				cancel(id);
			}
			
			Scanner choice = new Scanner(System.in);
			int cancelBuger = choice.nextInt();
			choice.nextLine();
			
			System.out.println("취소하시는 수량을 선택해주십시오.");
			int cancelNum = choice.nextInt();
	        choice.nextLine();
	        
	        for (int i=0; i<=cancelNum; i++) {                   
	            pstmt.setInt(1, cancelBuger);                     
	            pstmt.setString(2, id); //custid =3                  
	            pstmt.setInt(3, i);
	            pstmt.executeUpdate();
	        }
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}

	
	//버거합계
	public int burgersum(String id) {
		int str=0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			StringBuffer sql = new StringBuffer();
			sql.append("select sum((select price from burger where burgerid = o.burgerid)) from orders o");
			sql.append(" where custid =?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				str += rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return str;
	}
	
	//음료합계
	public int drinksum(String id) {
		int str=0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			StringBuffer sql = new StringBuffer();
			sql.append("select sum((select price from drink where drinkid = o.drinkid)) from orders o");
			sql.append(" where custid =?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				str += rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return str;
	}
	
	//디저트합계
	public int dessertsum(String id) {
		int str=0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			StringBuffer sql = new StringBuffer();
			sql.append("select sum((select price from dessert where dessertid = o.dessertid)) from orders o");
			sql.append(" where custid =?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				str += rs.getInt(1);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return str;
	}
	
	//세트합계
	public int setsum(String id) {
		int str=0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			StringBuffer sql = new StringBuffer();
			sql.append("select sum((select price from SETMENU where SETID = o.SETID)) from orders o");
			sql.append(" where custid =?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				str += rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
		return str;
	}
	
	
	//영수증
	public int bill() {
		check.date();
		int bill=0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			int i = burgersum(id);
			int j = drinksum(id);
			int k = dessertsum(id);
			
			int sum = i+j+k;			
			//double point = sum*0.05;
			System.out.println(sum);
			
			bill = sum;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		return bill;
		
	}
	
	//결제방법
	public void whereCheck() {
		
		System.out.println("결제방법을 선택하세요.");
		System.out.println("1.카드 2.현금");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) {	
			bill();
			System.out.println(bill() + " 원이 카드로 결제가 되었습니다.\n"
					+ " 감사합니다!");
			System.out.println("로그인 화면으로 돌아갑니다.");
		} else if (pick == 2) {
			bill();
			System.out.println(bill() + " 원이 현금으로 결제가 되었습니다.\n"
					+ " 감사합니다!");
		} else {
			System.out.println("다시 입력해주세요.");
			whereCheck();
		}
	}

}
