package com.mystudy.burgerproject;

import java.util.ArrayList;


public class BurgerTest {

	public static void main(String[] args) {
		
		Burger burger = new Burger();
		ArrayList<BurgerVO> list= burger.printDataAll();
		
		System.out.println("��ü ������ ��ȸ");
		for (BurgerVO vo : list) {
			System.out.println(vo);
		}
		

	}

}
