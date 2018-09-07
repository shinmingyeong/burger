package com.mystudy.burgerproject;

public class SetVO {
	
	private int setId;
	private String setName;
	private int setPrice;
	
	public SetVO(int setId, String setName, int setPrice) {
		
		this.setId = setId;
		this.setName = setName;
		this.setPrice = setPrice;
	}
	
	public SetVO() {
		
		this.setId = setId;
		this.setName = setName;
		this.setPrice = setPrice;
	}

	public int getSetId() {
		return setId;
	}

	public void setSetId(int setId) {
		this.setId = setId;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public int getSetPrice() {
		return setPrice;
	}

	public void setSetPrice(int setPrice) {
		this.setPrice = setPrice;
	}

	@Override
	public String toString() {
		return setId + "\t" + setName + "\t" + setPrice;
	}
	
}
