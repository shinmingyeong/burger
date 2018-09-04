package com.mystudy.burgerproject;

public class DrinkVO {
	
	private String dkName;
	private String dkPrice;
	
	DrinkVO(String dkName, String dkPrice) {
		this.dkName = dkName;
		this.dkPrice = dkPrice;
	}

	public String getDkName() {
		return dkName;
	}

	public void setDkName(String dkName) {
		this.dkName = dkName;
	}

	public String getDkPrice() {
		return dkPrice;
	}

	public void setDkPrice(String dkPrice) {
		this.dkPrice = dkPrice;
	}

	@Override
	public String toString() {
		return "DrinkVO [dkName=" + dkName + ", dkPrice=" + dkPrice + "]";
	}

}
