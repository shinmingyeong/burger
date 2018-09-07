package com.mystudy.burgerproject;

public class logintest {
	
	public static void main(String[] args) {
		login login = new login();
		
//		login.createAccount();
		
		String id = login.login();
		
		System.out.println(id);
		
	}

}
