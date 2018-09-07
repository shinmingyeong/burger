package com.mystudy.burgerproject;

public class DrinkVO {
	
	private int dkId;
	private String dkName;
	private int dkPrice;
	
	DrinkVO(int dkId, String dkName, int dkPrice) {
		this.dkId = dkId;
		this.dkName = dkName;
		this.dkPrice = dkPrice;
	}
	
	DrinkVO() {
		this.dkId = dkId;
		this.dkName = dkName;
		this.dkPrice = dkPrice;
	}

	public int getDkId() {
		return dkId;
	}

	public void setDkId(int dkId) {
		this.dkId = dkId;
	}

	public String getDkName() {
		return dkName;
	}

	public void setDkName(String dkName) {
		this.dkName = dkName;
	}

	public int getDkPrice() {
		return dkPrice;
	}

	public void setDkPrice(int dkPrice) {
		this.dkPrice = dkPrice;
	}

	@Override
	public String toString() {
		return dkId + "\t" + dkName + "\t" + dkPrice;
	}
	

}
