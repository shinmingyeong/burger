package com.mystudy.burgerproject;

public class BurgerVO {
	
	private int bgId;
	private String bgName;
	private int bgPrice;
	
	BurgerVO(int bgId, String bgName, int bgPrice) {
		this.bgId = bgId;
		this.bgName = bgName;
		this.bgPrice = bgPrice;		
	}
	
	BurgerVO() {
		this.bgId = bgId;
		this.bgName = bgName;
		this.bgPrice = bgPrice;		
	}
	
	public int getBgId() {
		return bgId;
	}
	
	public void setBgId(int bgId) {
		this.bgId = bgId;
	}
	
	public String getBgName() {
		return bgName;
	}
	
	public void setBgName(String bgName) {
		this.bgName = bgName;
	}
	
	public int getBgPrice() {
		return bgPrice;
	}
	
	public void setBgPrice(int bgPrice) {
		this.bgPrice = bgPrice;
	}
	
	@Override
	public String toString() {
		return bgId + "\t" + bgName
								+ "\t" + bgPrice;
	}
	
	public void prindData() {
		//System.out.println(bgId + "\t" + bgName + "\t" + bgPrice);
		System.out.printf("%d \t%s \t%1d \n", bgId, bgName, bgPrice);
		
		
	}
	
	public void title() {
		System.out.println("ID\t BURGER\t PRICE");
		}
	
}
