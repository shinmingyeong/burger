package com.mystudy.burgerproject;

import java.util.ArrayList;

public interface BurgerListCUD {
	//���̺� �ִ� ����Ÿ�� ȭ�� ���
	ArrayList<BurgerVO> printDataBurger();
					//printDataBurger();

	ArrayList<CustomerVO> printDataCust();

	ArrayList<DrinkVO> printDataDrink();

	ArrayList<DessertVO> printDataDessert();
	
	ArrayList<SetVO> printDataSet();
	
	//ȸ�� �ֹ� ����
	int custOrder(CustomerVO vo);
	
	
}
