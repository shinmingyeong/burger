package com.mystudy.burgerproject;

public class DessertVO {
	
	private String dsName;
	private String dsPrice;
	
	DessertVO(String dsName, String dsPrice) {
		this.dsName = dsName;
		this.dsPrice = dsPrice;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public String getDsPrice() {
		return dsPrice;
	}

	public void setDsPrice(String dsPrice) {
		this.dsPrice = dsPrice;
	}

	@Override
	public String toString() {
		return "Dessert [dsName=" + dsName + ", dsPrice=" + dsPrice + "]";
	}

}
