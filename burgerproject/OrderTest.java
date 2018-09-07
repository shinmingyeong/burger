package com.mystudy.burgerproject;

public class OrderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		custorder cut = new custorder();
		Burger bur = new Burger();
		
		//bur.myorderlist("2");

		cut.start();
		
		//cut.bag();
		
		checkbill ch = new checkbill();
		//ch.bill();
	}

}
