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
			System.out.println(">> 神虞適 JDBC 球虞戚獄 稽漁 失因!");
		} catch (ClassNotFoundException e) {
			System.out.println("[森須降持]");
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
		System.out.println("嬢辞神室推. 倍獄暗脊艦陥!");
		System.out.println("噺据戚獣檎 < 1 >, 搾噺据戚獣檎 < 2 >研 喚君爽室推!");
		
		pick = choice.nextInt();
		choice.nextLine();
	
		if(pick == 1) {
			login();
		} else if (pick == 2) {
			addCust();
		} 
	}

	//噺据爽庚
	public String id;
	public String login() {
		String result =  null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
			sb.append("select * from customer where custid =? and password =?");
			pstmt = conn.prepareStatement(sb.toString());				
			
			System.out.println("焼戚巨研 脊径背爽室推");
			String custId = choice.nextLine();
			System.out.println("搾腔腰硲研 脊径背爽室推");
			String password = choice.nextLine();
				
			pstmt.setString(1, custId);
			pstmt.setString(2, password);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()==true) {
				result=custId;
				id = custId;
				System.out.println("羨紗拭 失因馬写柔艦陥.");
				whereEat();
			} else {
				System.out.println("羨紗拭 叔鳶馬写柔艦陥.");
				System.out.println("仙獣亀研 据馬獣檎 <1>, 噺据亜脊聖 据馬獣檎 <2> 研 喚君爽室推.");
				
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
	
	//搾噺据爽庚
	public void addCust() {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO CUSTOMER ");
			sb.append("		(CUSTID, PASSWORD, PHONE) ");
			sb.append("VALUES(?, ?, ?)");
			pstmt = conn.prepareStatement(sb.toString());
			
			int i=0;
			System.out.println("[噺据亜脊]" + ++i + " 腰 壱梓生稽 爽庚");
			System.out.println("焼戚巨研 竺舛背爽室推.");
			String custid = choice.nextLine();
			System.out.println("搾腔腰硲研 竺舛背爽室推.");
			String password = choice.nextLine();
			
			System.out.println("輩球肉 腰硲研 脊径背爽室推.");
			String phone = choice.nextLine();
			
			pstmt.setString(1, custid);
			pstmt.setString(2, password);
			pstmt.setString(3, phone);
			pstmt.executeUpdate();
			System.out.println("竺舛戚 鞠醸柔艦陥.");
			System.out.println("亜脊馬重 焼戚巨稽 稽益昔 獣亀背爽室推!");
			addorderlist(custid);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
	}
	
	//噺据亜脊廃 紫寓税 焼戚巨研 神希軒什闘拭 蓄亜
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
			System.out.println("稽 漁 掻 ~");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
			whereEat();
		}
		
	}
	
	
	
	
	////////////古舌 or 砺戚滴焼数?
	public void whereEat() {
		System.out.println("古舌拭辞 球獣檎 < 1 >, 砺戚滴焼数 馬獣檎 < 2 > 研 喚君爽室推!");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) { //送据拭惟 穿含
			pick();
		} else if (pick == 2) { //送据拭惟 穿含
			pick();
		} else {
			System.out.println("ばば 陥獣 識澱背爽室推.");
			whereEat();
		}
		
	}
	
	//五敢
	public void pick() {
		System.out.println("舘念 識澱精 < 1 >, 室闘 識澱精 < 2 > 研 喚君爽室推!");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) {
			selectBurger(id);
		} else if (pick == 2) {
			
		} else {
			System.out.println("ばば 陥獣 識澱背爽室推.");
		}
		
	}
	
	//噺据税 室闘爽庚
	public void selectSet(String id) {
		
		try {
	           conn = DriverManager.getConnection(URL, USER, PASSWORD);
	           
	           StringBuffer sql = new StringBuffer();
	           
	           sql.append("update ORDERS ");
	           sql.append(" set setid = ? ");
	           sql.append(" WHERE custid = ? ");
	           pstmt = conn.prepareStatement(sql.toString());
	           
	           System.out.println("**室闘五敢研 識澱背爽室推.");
	           burger.printDataSet();
	           int selectset = choice.nextInt();
	           
	           pstmt.setInt(1, selectset);
	           pstmt.setString(2, id);
	           
	           pstmt.executeUpdate();
	           System.out.println("室闘識澱戚 刃戟鞠醸柔艦陥.");
	           System.out.println("爽庚戚 魁概生檎 < 1 >,");
	           System.out.println("蓄亜爽庚聖 据馬獣檎 < 2 >,");
	           System.out.println("陥製鉢檎生稽 角嬢亜獣形檎 < 3 > 研 喚君爽室推.");
	           
	           pick = choice.nextInt();
	           choice.nextLine();
	   	
	           if(pick == 1) {
	        	   bag();
	           } else if (pick == 2) {
	        	  
	        	   addBg(id);
	           } else if (pick == 3) {
	        	   selectDrink(id); 
	           } else {
	        	   System.out.println("ばば 陥獣 識澱背爽室推.");
	        	   selectBurger(id);
	           }
	           
	        } catch (SQLException e) {
	           e.printStackTrace();
	        } finally {
	           JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
	        }
		
	}
	
	//蓄亜爽庚(室闘)
	public void addSet(String id) {
		
	}
	
	
	//噺据税 獄暗爽庚
	public void selectBurger(String id) {
        
        try {
           conn = DriverManager.getConnection(URL, USER, PASSWORD);
           
           StringBuffer sql = new StringBuffer();
           
           sql.append("update ORDERS ");
           sql.append(" set burgerid = ? ");
           sql.append(" WHERE custid = ? ");
           pstmt = conn.prepareStatement(sql.toString());
           
           System.out.println("**獄暗五敢研 識澱背爽室推.");
           burger.printDataBurger();
           int selectbur = choice.nextInt();
           
           pstmt.setInt(1, selectbur);
           pstmt.setString(2, id);
           
           pstmt.executeUpdate();
           System.out.println("獄暗識澱戚 刃戟鞠醸柔艦陥.");
           System.out.println("爽庚戚 魁概生檎 < 1 >,");
           System.out.println("蓄亜爽庚聖 据馬獣檎 < 2 >,");
           System.out.println("陥製鉢檎生稽 角嬢亜獣形檎 < 3 > 研 喚君爽室推.");
           
           pick = choice.nextInt();
           choice.nextLine();
   	
           if(pick == 1) {
        	   bag();
           } else if (pick == 2) {
        	   addBg(id);
           } else if (pick == 3) {
        	   selectDrink(id); 
           } else {
        	   System.out.println("ばば 陥獣 識澱背爽室推.");
        	   selectBurger(id);
           }
           
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
           JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
        }
     }
	
	//蓄亜爽庚(獄暗)
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
			
			System.out.println("**[蓄亜] 獄暗研 識澱背爽室推!");
			burger.printDataBurger();
			int burgerid = choice.nextInt();

			System.out.println("***蓄亜爽庚戚 刃戟亜 鞠醸柔艦陥.***");

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
	
	
	//噺据税 製戟爽庚
	public void selectDrink(String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update ORDERS ");
			sql.append(" set drinkid = ? ");
			sql.append(" WHERE custid = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			System.out.println("**製戟五敢研 識澱背爽室推.");
			burger.printDataDrink();
			int selectdrk = choice.nextInt();
			
			pstmt.setInt(1, selectdrk);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			System.out.println("製戟識澱戚 刃戟鞠醸柔艦陥.");
			System.out.println("爽庚戚 魁概生檎 < 1 >,");
	        System.out.println("蓄亜爽庚聖 据馬獣檎 < 2 >,");
	        System.out.println("陥製鉢檎生稽 角嬢亜獣形檎 < 3 > 研 喚君爽室推.");
	        
	        pick = choice.nextInt();
	        choice.nextLine();
	        
	        if(pick == 1) {
	        	bag();
	        } else if (pick == 2) {
	        	addDk(id);
	        } else if (pick == 3) {
	        	selectDs(id); 
	        } else {
	        	System.out.println("ばば 陥獣 識澱背爽室推.");
	        	selectDrink(id);
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBC_Close.closeConnStmtRs(conn, pstmt, rs);
		}
		
	}
	
	//蓄亜爽庚(製戟)
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
			
			System.out.println("**[蓄亜] 製戟研 識澱背爽室推!");
			burger.printDataDrink();
			int selectdrk = choice.nextInt();

			System.out.println("***蓄亜爽庚戚 刃戟亜 鞠醸柔艦陥.***");

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
	
	//噺据税 巨煽闘爽庚
	public void selectDs(String id) {
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update ORDERS ");
			sql.append(" set dessertid = ? ");
			sql.append(" where custid = ? ");
			pstmt = conn.prepareStatement(sql.toString());
			
			System.out.println("**巨煽闘 五敢研 識澱背爽室推.");
			burger.printDataDessert();
			int selectds = choice.nextInt();
			
			pstmt.setInt(1, selectds);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
			System.out.println("巨煽闘識澱戚 刃戟鞠醸柔艦陥.");
			System.out.println("爽庚戚 魁概生檎 < 1 >,");
	        System.out.println("蓄亜爽庚聖 据馬獣檎 < 2 > 研 喚君爽室推.");
	           
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
	
	//蓄亜爽庚(巨煽闘)
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
			
			System.out.println("**[蓄亜] 巨煽闘研 識澱背爽室推!");
			burger.printDataDessert();
			int selectds = choice.nextInt();

			System.out.println("***蓄亜爽庚戚 刃戟亜 鞠醸柔艦陥.***");

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
	
	
	//舌郊姥艦硲窒板 昼社
	public void bag() {
		System.out.println("*~~~~* 爽庚 軒什闘 *~~~~*");
		burger.printDataBag();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("爽庚馬重 舛左亜 限澗走 溌昔背爽室推.");
			System.out.println("昼社馬獣形檎 < 1 >, 五昔五敢稽 宜焼亜獣形檎 < 2 >"
					+ ", 衣薦研 据馬獣檎 < 3 > 聖 喚君爽室推!");
			
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
	
	//爽庚呪勲 昼社
	public void cancel(String id) {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			StringBuilder sb = new StringBuilder();
	        sb.append("update orders set burgerid = null "
	        		+ "where burgerid = ? and custid = ? and list = ?");
			pstmt = conn.prepareStatement(sb.toString());
			
			System.out.println("昼社馬叔 五敢腰硲研 識澱背爽室推.");
			System.out.println("1.獄暗  2.製戟  3.巨煽闘  4.室闘");
			
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
				System.out.println("ばば 陥獣 識澱背爽室推.");
				cancel(id);
			}
			
			Scanner choice = new Scanner(System.in);
			int cancelBuger = choice.nextInt();
			choice.nextLine();
			
			System.out.println("昼社馬獣澗 呪勲聖 識澱背爽淑獣神.");
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
	
	//衣薦号狛
	public void whereCheck() {
		int sum=0;
		
		System.out.println("衣薦号狛聖 識澱馬室推.");
		System.out.println("1.朝球 2.薄榎");
		
		pick = choice.nextInt();
		choice.nextLine();
		
		if(pick == 1) {			
			System.out.println(sum + "据戚 朝球稽 衣薦亜 鞠醸柔艦陥."
					+ " 姶紫杯艦陥!");
			System.out.println("稽益昔 鉢檎生稽 宜焼逢艦陥.");
			cvo.setWhereCheck("朝球");
		} else if (pick == 2) {
			System.out.println(sum + "据戚 薄榎生稽 衣薦亜 鞠醸柔艦陥."
					+ " 姶紫杯艦陥!");
			cvo.setWhereCheck("薄榎");
		} else {
			System.out.println("陥獣 脊径背爽室推");
			whereCheck();
		}
	}

}
