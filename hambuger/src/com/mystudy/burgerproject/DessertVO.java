package com.mystudy.burgerproject;

public class DessertVO {
	
	private int dsId;
	private String dsName;
	private int dsPrice;
	
	DessertVO(int dsId, String dsName, int dsPrice) {
		this.dsId = dsId;
		this.dsName = dsName;
		this.dsPrice = dsPrice;
	}
	
	DessertVO() {
		this.dsId = dsId;
		this.dsName = dsName;
		this.dsPrice = dsPrice;
	}
	
	public int getDsId() {
		return dsId;
	}
	
	public void setDsId(int dsId) {
		this.dsId = dsId;
	}

	public String getDsName() {
		return dsName;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}
	
	public int getDsPrice() {
		return dsPrice;
	}
	
	public void setDsPrice(int dsPrice) {
		this.dsPrice = dsPrice;
	}
	
	@Override
	public String toString() {
		return dsId + "\t" + dsName + "\t" + dsPrice;
	}
	
}
