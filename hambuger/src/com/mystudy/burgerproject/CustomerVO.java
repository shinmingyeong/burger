package com.mystudy.burgerproject;

import java.util.ArrayList;

public class CustomerVO {
	
	ArrayList<String> bgList = new ArrayList();
	ArrayList<String> dkList = new ArrayList();
	ArrayList<String> dsList = new ArrayList();
	ArrayList<String> setList = new ArrayList();
	
	private String whereEat;
	private String whatMenu;
	private ArrayList whatBg;
	private ArrayList whatDk;
	private ArrayList whatDs;
	private ArrayList whatSet;
	private String whereCheck;
	
	
	public String getWhereEat() {
		return whereEat;
	}
	public void setWhereEat(String whereEat) {
		this.whereEat = whereEat;
	}
	
	public String getWhatMenu() {
		return whatMenu;
	}
	
	public void setWhatMenu(String whatMenu) {
		this.whatMenu = whatMenu;
	}
	
	
	public ArrayList getWhatBg() {
		return bgList;
	}
	public void setWhatBg(String whatBg) {
		bgList.add(whatBg);
	}
	
	public ArrayList getWhatDk() {
		return dkList;
	}
	
	public void setWhatDk(String whatDk) {
		dkList.add(whatDk);
	}
	
	public ArrayList getWhatDs() {
		return dsList;
	}
	
	public void setWhatDs(String whatDs) {
		dsList.add(whatDs);
	}
	
	public ArrayList getWhatSet() {
		return setList;
	}
	public void setWhatSet(String whatSet) {
		setList.add(whatSet);
	}
	
	public String getWhereCheck() {
		return whereCheck;
	}
	public void setWhereCheck(String whereCheck) {
		this.whereCheck = whereCheck;
	}
	
	@Override
	public String toString() {
		return "CustomerVO [bgList=" + bgList + ", dkList=" + dkList + ", dsList=" + dsList + ", setList=" + setList
				+ ", whereEat=" + whereEat + ", whereCheck=" + whereCheck + "]";
	}
	
}
