package com.mystudy.burgerproject;


public class OrderVO {
	
	int odrId;
	int custId;
	int bgId;
	int dkId;
	int dsId;
	int setId;
	int pricetot;
	
	
	
	public OrderVO(int odrId, int custId, int bgId, int dkId,
					int dsId, int setId, int pricetot) {
		this.odrId = odrId;
		this.custId = custId;
		this.bgId = bgId;
		this.dkId = dkId;
		this.dsId = dsId;
		this.setId = setId;
		this.pricetot = pricetot;

	}

	public OrderVO() {
		this.odrId = odrId;
		this.custId = custId;
		this.bgId = bgId;
		this.dkId = dkId;
		this.dsId = dsId;
		this.pricetot = pricetot;
	}

	public int getOdrId() {
		return odrId;
	}

	public void setOdrId(int odrId) {
		this.odrId = odrId;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
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

	public int getPricetot() {
		return pricetot;
	}

	public void setPricetot(int pricetot) {
		this.pricetot = pricetot;
	}


	@Override
	public String toString() {
		return 	odrId + "\t" + custId + "\t" + bgId +
				"\t" + dkId + "\t" + dsId + "\t" + setId + "\t" + pricetot;
	}
	
	public void title() {
		System.out.println("주문번호\t 아이디\t 버거\t"
				+ "음료\t 디저트\t 세트\t 총가격");
		}

}
