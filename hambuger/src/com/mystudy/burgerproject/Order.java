package com.mystudy.burgerproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import common_util.JDBC_Close;

public class Order {
	
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
		SetVO setvo = new SetVO();
		Burger burger;
		
		/////////////�ֹ�?
		public void start() {
			System.out.println("�ֹ�? 1");
			
			pick = choice.nextInt();
			choice.nextLine();
		
			if(pick == 1) {		
				whereEat();
			} else if (pick != 1) {  //�ٸ� ���� �Է� �� ��쿡�� ����ؼ� �ʱ�ȭ���� �����ֱ� ���� �ڱ��ڽ��� ȣ��
				start();
			}
		}
		
		////////////���� or ����ũ�ƿ�?
		public void whereEat() {
			System.out.println("��� ��ðڽ��ϱ�?");
			System.out.println("1.���� 2.����ũ�ƿ� 3.ó������");
			
			pick = choice.nextInt();
			choice.nextLine();
			
			if(pick == 1) { //�������� ����
				cvo.setWhereEat("����");
			} else if (pick == 2) { //�������� ����
				cvo.setWhereEat("����ũ�ƿ�");
			} else if (pick == 3) {
				start();
			} else {
			System.out.println("�ٽ� �������ּ���.");
			whereEat();
			}
			whatMenu();
		}
		
		////////////���� �޴�?
		public void whatMenu() {
			System.out.println("1:���Ŵ�ǰ 2:���� 3:����Ʈ 4:��Ʈ 5:�������� 6:ó������ 7:��ٱ���");
			
			pick = choice.nextInt();
			choice.nextLine();
			
			if(pick == 1) {			
				whatBg();
			} else if (pick == 2) {
				whatDk();
			} else if (pick == 3) {
				whatDs();
			} else if (pick == 4) {
				whatSet();
			} else if (pick == 5) {
				whereEat();
			} else if (pick == 6) {
				start();
			} else if (pick == 7) {
				bag();
			} else {
				System.out.println("�ٽ� �������ּ���.");
				whatMenu();
				
			}
		}
		
		//���� �޴�
		public void whatBg() {
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
				pickBurger();
			}
			
		}
		
			
		//���� ���̵�� �˻�
		public void pickBurger () {
				System.out.println("��������?");
				BurgerVO bvo = null;			
			try {			
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
				String sql = "";
				sql += "SELECT BURGERID, BURGERNAME, PRICE ";
				sql += "  FROM BURGER ";
				sql += " WHERE BURGERID = ?";
				pstmt = conn.prepareStatement(sql);
				
				pick = choice.nextInt();
    			choice.nextLine();
				
				pstmt.setInt(1, pick);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					bvo = new BurgerVO(
					rs.getInt(1)
				   ,rs.getString(2)
				   ,rs.getInt(3)
					);
					cvo.setWhatBg(bvo.getBgName());
					
				} else {
					System.out.println("������ ����!");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				JDBC_Close.closeConnStmtRs(conn, pstmt, rs);	
				whatMenu(); 
				
			}  
			//whatBg2(); 
		}
			
		
		
		//��Ʈ���ý�
		public void whatSet() {
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
				whatMenu();
			}
		}
		
		//��Ʈ�̸����� �˻�
		public void pickSet () {
			System.out.println("������Ʈ?");
			SetVO setvo = null;			
		try {			
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "";
			sql += "SELECT SETID, SETNAME, PRICE ";
			sql += "  FROM SETMENU ";
			sql += " WHERE SETID = ?";
			pstmt = conn.prepareStatement(sql);
			
			pick = choice.nextInt();
			choice.nextLine();
			
			pstmt.setInt(1, pick);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				setvo = new SetVO(
				rs.getInt(1)
			   ,rs.getString(2)
			   ,rs.getInt(3)
				);
				cvo.setWhatSet(setvo.getSetName());
			} else {
				System.out.println("������ ����!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}  whatMenu();			
	}
		
		//���ἱ�ý�
		public void whatDk() {
			ArrayList<DrinkVO> list = null;
			
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
				StringBuilder sb = new StringBuilder();
				sb.append("SELECT drinkid, drinkname, PRICE ");
				sb.append("  FROM drink ");
				sb.append(" ORDER BY drinkID ");
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
				pickDrink();
			}
		}
		//���� ���̵�� �˻�
				public void pickDrink () {
						System.out.println("��������?");
						DrinkVO dvo = null;			
					try {			
						conn = DriverManager.getConnection(URL, USER, PASSWORD);
						
						String sql = "";
						sql += "SELECT drinkid, drinkname, PRICE ";
						sql += "  FROM drink ";
						sql += " WHERE drinkid = ?";
						pstmt = conn.prepareStatement(sql);
						
						pick = choice.nextInt();
		    			choice.nextLine();
						
						pstmt.setInt(1, pick);
						
						rs = pstmt.executeQuery();
						
						if (rs.next()) {
							dvo = new DrinkVO(
							rs.getInt(1)
						   ,rs.getString(2)
						   ,rs.getInt(3)
							);
							cvo.setWhatDk(dvo.getDkName());
						} else {
							System.out.println("������ ����!");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
					}  whatMenu();			
				}
				
		//����Ʈ���ý�
		public void whatDs() {
			ArrayList<DessertVO> list = null;
			
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
				StringBuilder sb = new StringBuilder();
				sb.append("SELECT dessertid, dessertname, PRICE ");
				sb.append("  FROM dessert ");
				sb.append(" ORDER BY dessertid ");
				pstmt = conn.prepareStatement(sb.toString());
				
				rs = pstmt.executeQuery();
				
				list = new ArrayList<DessertVO>();
				while (rs.next()) {
					list.add(new DessertVO(rs.getInt("dessertid"),
										  rs.getString("dessertname"),
										  rs.getInt("PRICE")));
					
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
				pickDessert ();
			}
		}
		//����Ʈ ���̵�� �˻�
				public void pickDessert () {
						System.out.println("��������Ʈ?");
						DessertVO dvs = null;			
					try {			
						conn = DriverManager.getConnection(URL, USER, PASSWORD);
						
						String sql = "";
						sql += "SELECT dessertid, dessertname, PRICE ";
						sql += "  FROM dessert ";
						sql += " WHERE dessertid = ?";
						pstmt = conn.prepareStatement(sql);
						
						pick = choice.nextInt();
		    			choice.nextLine();
						
						pstmt.setInt(1, pick);
						
						rs = pstmt.executeQuery();
						
						if (rs.next()) {
							dvs = new DessertVO(
							rs.getInt(1)
						   ,rs.getString(2)
						   ,rs.getInt(3)
							);
							cvo.setWhatDs(dvs.getDsName());
						} else {
							System.out.println("������ ����!");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
					}  whatMenu();			
				}
		
		//��ٱ���?
		public void bag() {
			System.out.println("1.��� 2.���θ޴� 3.����");
			
			System.out.println(cvo);
	
			pick = choice.nextInt();
			choice.nextLine();
			
			if(pick==1) {
				System.out.println("��Ҹ� ���Ͻô� �޴��� �������ּ���");
				System.out.println("1.����, 2.����, 3.����Ʈ 4.����ȭ��");
				
				pick = choice.nextInt();
				choice.nextLine();
				
				
				if(pick==1) {
					for (int i=0; i<cvo.bgList.size(); i++) {
						System.out.println(i+1 + "." + cvo.bgList.get(i));
					}
					
					System.out.println("��Ҹ� ���Ͻô� ���Ź�ȣ�� �������ּ���");
					
					pick = choice.nextInt();
					choice.nextLine();
					
					cvo.bgList.remove(pick-1);
							
					System.out.println(cvo.bgList);
					bag();
					
					
				} else if (pick==2) {
					for (int i=0; i<cvo.dkList.size(); i++) {
						System.out.println(i+1 + "." + cvo.dkList.get(i));
					}
					
					System.out.println("��Ҹ� ���Ͻô� �����ȣ�� �������ּ���");
					
					pick = choice.nextInt();
					choice.nextLine();
					
					cvo.dkList.remove(pick-1);
							
					System.out.println(cvo.dkList);
					bag();
					
				} else if (pick==3) {
					for (int i=0; i<cvo.dsList.size(); i++) {
						System.out.println(i+1 + "." + cvo.dsList.get(i));
					}
					
					System.out.println("��Ҹ� ���Ͻô� ����Ʈ��ȣ�� �������ּ���");
					
					pick = choice.nextInt();
					choice.nextLine();
					
					cvo.dsList.remove(pick-1);
							
					System.out.println(cvo.dsList);
					bag();
				} else if (pick==4) {
					bag();
				} else {
					System.out.println("�ٽ� �������ּ���.");
					System.out.println("1.����, 2.����, 3.����Ʈ 4.����ȭ��");
					
					pick = choice.nextInt();
					choice.nextLine();
					}
			}
			
			if (pick==2) {
				whatMenu();
			} 
			
			if(pick==3) {
				whereCheck();
			} else {
				System.out.println("�ٽ� �Է����ּ���");
				bag();
			}
		} 
			
				
	
		public void whereCheck() {
			System.out.println("��� �����Ͻðڽ��ϱ�?");
			System.out.println("1ī����,2����");
			pick = choice.nextInt();
			choice.nextLine();
			
			if(pick == 1) {			
				System.out.println("ī����");
				cvo.setWhereCheck("ī����");
			} else if (pick == 2) {
				System.out.println("���");
				cvo.setWhereCheck("����");
			} else {
				System.out.println("�ٽ� �Է����ּ���");
				whereCheck();
			}
			pirntBill();
		}

		
		public void pirntBill() {
			System.out.println("������");
			System.out.println(cvo);
//			try {
//				conn = DriverManager.getConnection(URL, USER, PASSWORD);
//				StringBuffer sql = new StringBuffer();
//				sql.append("select * from customer where id = ?");
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			System.out.println("�����մϴ�.");
			start();
		}
	
		
}