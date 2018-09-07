package com.mystudy.burgerproject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BurgerTest {

	public static void main(String[] args) {
		
		Burger burger = new Burger();
		ArrayList<BurgerVO> list= burger.printDataBurger();
		ArrayList<DrinkVO> listdrink= burger.printDataDrink();
		ArrayList<DessertVO> listdessert= burger.printDataDessert();
		ArrayList<SetVO> listset= burger.printDataSet();
		
		System.out.println("전체 데이터 조회");
//				
//		for (BurgerVO vo : list) {
//			vo.prindData();
//		}	
//		
//		for (DrinkVO vo : listdrink) {
//			vo.prindData();
//		}
		
//		for (DessertVO vo : listdessert) {
//			vo.prindData();
//		}
		for (SetVO vo : listset) {
			System.out.println(vo);
			
			
		//	burger.pickSet(vo);
			
		}
		
		int i =0;
//		SetVO vo = burger.pickSet(++i);
//		System.out.println(vo);
//		System.out.println(vo.getSetPrice());
		
//		BurgerVO bvo = burger.pickburger(1);
////		System.out.println(bvo);
//		System.out.println(bvo.getBgId());
		
		Date date = new Date();
		SimpleDateFormat todaydate = new SimpleDateFormat ("yyyy/MM/dd");
		System.out.println(todaydate.format(date));
		
		
		System.out.println("-------------------------------------------");
		
		
		burger.printDataBurger();
		
		
		

	}

}
