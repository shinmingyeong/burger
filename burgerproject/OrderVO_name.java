package com.mystudy.burgerproject;

public class OrderVO_name {
	
	int odrId;
	String custId;
	String bgname;
	String dkname;
	String dsname;
	String setname;
	
	public OrderVO_name(int odrId, String custId, String bgname, String dkname, String dsname, String setname) {
		super();
		this.odrId = odrId;
		this.custId = custId;
		this.bgname = bgname;
		this.dkname = dkname;
		this.dsname = dsname;
		this.setname = setname;
	}

	public int getOdrId() {
		return odrId;
	}

	public void setOdrId(int odrId) {
		this.odrId = odrId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBgname() {
		return bgname;
	}

	public void setBgname(String bgname) {
		this.bgname = bgname;
	}

	public String getDkname() {
		return dkname;
	}

	public void setDkname(String dkname) {
		this.dkname = dkname;
	}

	public String getDsname() {
		return dsname;
	}

	public void setDsname(String dsname) {
		this.dsname = dsname;
	}

	public String getSetname() {
		return setname;
	}

	public void setSetname(String setname) {
		this.setname = setname;
	}

	@Override
	public String toString() {
		return odrId + "\t" + custId + "\t" + bgname + "\t" + dkname + "\t"
				+ dsname + "\t" + setname;
	}
	
	public void title() {
		System.out.println("주문번호\t 아이디\t 버거\t"
				+ "음료\t 디저트\t 세트");
		}
	

}
