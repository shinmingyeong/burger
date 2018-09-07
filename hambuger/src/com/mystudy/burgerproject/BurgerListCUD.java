package com.mystudy.burgerproject;

import java.util.ArrayList;

public interface BurgerListCUD {
	//테이블에 있는 데이타를 화면 출력
	ArrayList<BurgerVO> printDataBurger();
					//printDataBurger();

	ArrayList<CustomerVO> printDataCust();

	ArrayList<DrinkVO> printDataDrink();

	ArrayList<DessertVO> printDataDessert();
	
	ArrayList<SetVO> printDataSet();
	
	//회원 주문 정보
	int custOrder(CustomerVO vo);
	
	
}
