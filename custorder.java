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
				System.out.println("��õ��� ���Ͻø� <1>, ȸ�������� ���Ͻø� <2> �� �����ּ���.");
				
				pick = choice.nextInt();
				choice.nextLine();
				
				if(pick==1) {
					login();
				} else if (pick==2) {
					addCust();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
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
			System.out.println("�����Ͻ� ���̵�� �α��� �õ����ּ���!");
			addorderlist(custid);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	//ȸ�������� ����� ���̵� ��������Ʈ�� �߰�
	public void addorderlist(String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ORDERS ");
			sb.append("(ORDERID, CUSTID, ORDERDATE, LIST) ");
			sb.append("VALUES ((SELECT NVL(MAX(ORDERID),0)+1"
					+ " FROM ORDERS), ");
			sb.append("?, SYSDATE, ");
			sb.append("(SELECT NVL(MAX(LIST),0)+1");
			sb.append(" FROM ORDERS WHERE CUSTID = ?))");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, id);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			System.out.println("�� �� �� ~");
			
			
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
			pick();
		} else if (pick == 2) { //�������� ����
			pick();
		} else {
			System.out.println("�Ф� �ٽ� �������ּ���.");
			whereEat();
		}
		
	}
	
	//�޴�
	public void pick() {
		System.out.println("��ǰ ������ < 1 >, ��Ʈ ������ < 2 > �� �����ּ���!");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) {
			selectBurger(id);
		} else if (pick == 2) {
			
		} else {
			System.out.println("�Ф� �ٽ� �������ּ���.");
		}
		
	}
	
	//ȸ���� ��Ʈ�ֹ�
	public void selectSet(String id) {
		
		try {
	           conn = DriverManager.getConnection(URL, USER, PASSWORD);
	           
	           StringBuffer sql = new StringBuffer();
	           
	           sql.append("update ORDERS ");
	           sql.append(" set setid = ? ");
	           sql.append(" WHERE custid = ? ");
	           pstmt = conn.prepareStatement(sql.toString());
	           
	           System.out.println("**��Ʈ�޴��� �������ּ���.");
	           burger.printDataSet();
	           int selectset = choice.nextInt();
	           
	           pstmt.setInt(1, selectset);
	           pstmt.setString(2, id);
	           
	           pstmt.executeUpdate();
	           System.out.println("��Ʈ������ �Ϸ�Ǿ����ϴ�.");
	           System.out.println("�ֹ��� �������� < 1 >,");
	           System.out.println("�߰��ֹ��� ���Ͻø� < 2 >,");
	           System.out.println("����ȭ������ �Ѿ�÷��� < 3 > �� �����ּ���.");
	           
	           pick = choice.nextInt();
	           choice.nextLine();
	   	
	           if(pick == 1) {
	        	   bag();
	           } else if (pick == 2) {
	        	  
	        	   addBg(id);
	           } else if (pick == 3) {
	        	   selectDrink(id); 
	           } else {
	        	   System.out.println("�Ф� �ٽ� �������ּ���.");
	        	   selectBurger(id);
	           }
	           
	        } catch (SQLException e) {
	           e.printStackTrace();
	        } finally {
	           JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
	        }
		
	}
	
	//�߰��ֹ�(��Ʈ)
	public void addSet(String id) {
		
	}
	
	
	//ȸ���� �����ֹ�
	public void selectBurger(String id) {
        
        try {
           conn = DriverManager.getConnection(URL, USER, PASSWORD);
           
           StringBuffer sql = new StringBuffer();
           
           sql.append("update ORDERS ");
           sql.append(" set burgerid = ? ");
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
           System.out.println("�߰��ֹ��� ���Ͻø� < 2 >,");
           System.out.println("����ȭ������ �Ѿ�÷��� < 3 > �� �����ּ���.");
           
           pick = choice.nextInt();
           choice.nextLine();
   	
           if(pick == 1) {
        	   bag();
           } else if (pick == 2) {
        	   addBg(id);
           } else if (pick == 3) {
        	   selectDrink(id); 
           } else {
        	   System.out.println("�Ф� �ٽ� �������ּ���.");
        	   selectBurger(id);
           }
           
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
           JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
        }
     }
	
	//�߰��ֹ�(����)
	public void addBg(String id) {
		
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
			
			//---------------------------------
			
			System.out.println("**[�߰�] ���Ÿ� �������ּ���!");
			burger.printDataBurger();
			int burgerid = choice.nextInt();

			System.out.println("***�߰��ֹ��� �Ϸᰡ �Ǿ����ϴ�.***");

			pstmt.setString(1, id);
			pstmt.setInt(2, burgerid);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			ord.title();
			bag();
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
	        System.out.println("�߰��ֹ��� ���Ͻø� < 2 >,");
	        System.out.println("����ȭ������ �Ѿ�÷��� < 3 > �� �����ּ���.");
	        
	        pick = choice.nextInt();
	        choice.nextLine();
	        
	        if(pick == 1) {
	        	bag();
	        } else if (pick == 2) {
	        	addDk(id);
	        } else if (pick == 3) {
	        	selectDs(id); 
	        } else {
	        	System.out.println("�Ф� �ٽ� �������ּ���.");
	        	selectDrink(id);
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	//�߰��ֹ�(����)
	public void addDk(String id) {
		
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
			
			//---------------------------------
			
			System.out.println("**[�߰�] ���Ḧ �������ּ���!");
			burger.printDataDrink();
			int selectdrk = choice.nextInt();

			System.out.println("***�߰��ֹ��� �Ϸᰡ �Ǿ����ϴ�.***");

			pstmt.setString(1, id);
			pstmt.setInt(2, selectdrk);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			ord.title();
			bag();
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
	        System.out.println("�߰��ֹ��� ���Ͻø� < 2 > �� �����ּ���.");
	           
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
	
	//�߰��ֹ�(����Ʈ)
	public void addDs(String id) {
		
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
			
			//---------------------------------
			
			System.out.println("**[�߰�] ����Ʈ�� �������ּ���!");
			burger.printDataDessert();
			int selectds = choice.nextInt();

			System.out.println("***�߰��ֹ��� �Ϸᰡ �Ǿ����ϴ�.***");

			pstmt.setString(1, id);
			pstmt.setInt(2, selectds);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			ord.title();
			bag();
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
	
	//�ֹ����� ���
	public void cancel(String id) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
	        sb.append("update orders set burgerid = null "
	        		+ "where burgerid = ? and custid = ? and list = ?");
			pstmt = conn.prepareStatement(sb.toString());
			
			System.out.println("����Ͻ� �޴���ȣ�� �������ּ���.");
			System.out.println("1.����  2.����  3.����Ʈ  4.��Ʈ");
			
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
				System.out.println("�Ф� �ٽ� �������ּ���.");
				cancel(id);
			}
			
			Scanner choice = new Scanner(System.in);
			int cancelBuger = choice.nextInt();
			choice.nextLine();
			
			System.out.println("����Ͻô� ������ �������ֽʽÿ�.");
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
