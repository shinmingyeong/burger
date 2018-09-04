package com.mystudy.burgerproject;

import java.util.ArrayList;

public interface BurgerListCUD {
	//테이블에 있는 데이타를 화면 출력
	ArrayList<BurgerVO> printDataAll();
	
	//회원목록을 받아서 일괄 입력 처리
	int insertListBurger(ArrayList<BurgerVO> list);
	
	int insertListCust(ArrayList<CustomerVO> list);
	
	int insertListDrink(ArrayList<DrinkVO> list);
	
	int insertListDessert(ArrayList<DessertVO> list);
	
}
