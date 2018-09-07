package com.mystudy.burgerproject;


public class OrderVO {
	
	int odrId;
	String custId;
	int bgId;
	int dkId;
	int dsId;
	int setId;

	public OrderVO(int odrId, String custId, int bgId, int dkId,
					int dsId, int setId) {
		this.odrId = odrId;
		this.custId = custId;
		this.bgId = bgId;
		this.dkId = dkId;
		this.dsId = dsId;
		this.setId = setId;

	}

	public OrderVO() {
		this.odrId = odrId;
		this.custId = custId;
		this.bgId = bgId;
		this.dkId = dkId;
		this.dsId = dsId;
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

	public int getBgId() {
		return bgId;
	}

	public void setBgId(int bgId) {
		this.bgId = bgId;
	}

	public int getDkId() {
		return dkId;
	}

	public void setDkId(int dkId) {
		this.dkId = dkId;
	}

	public int getDsId() {
		return dsId;
	}

	public void setDsId(int dsId) {
		this.dsId = dsId;
	}

	public int getSetId() {
		return setId;
	}

	public void setSetId(int setId) {
		this.setId = setId;
	}


	@Override
	public String toString() {
		return 	odrId + "\t" + custId + "\t" + bgId +
				"\t" + dkId + "\t" + dsId + "\t" + setId;
	}
	
	public void title() {
		System.out.println("주문번호\t 아이디\t 버거\t"
				+ "음료\t 디저트\t 세트");
		}

}
