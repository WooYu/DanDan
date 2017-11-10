package com.dhxgzs.goodluck.entiey;

public class FjZaiXianRenShuEntity {
	
//	"vip1": 2,
//    "vip2": 1,
//    "vip3": 1,
//    "vip4": 1,
//    "rmtotal": 5
	
	private String vip1;
	private String vip2;
	private String vip3;
	private String vip4;
	private String rmtotal;
	
	public FjZaiXianRenShuEntity() {
		super();
	}

	public FjZaiXianRenShuEntity(String vip1, String vip2, String vip3, String vip4, String rmtotal) {
		super();
		this.vip1 = vip1;
		this.vip2 = vip2;
		this.vip3 = vip3;
		this.vip4 = vip4;
		this.rmtotal = rmtotal;
	}

	public String getVip1() {
		return vip1;
	}

	public void setVip1(String vip1) {
		this.vip1 = vip1;
	}

	public String getVip2() {
		return vip2;
	}

	public void setVip2(String vip2) {
		this.vip2 = vip2;
	}

	public String getVip3() {
		return vip3;
	}

	public void setVip3(String vip3) {
		this.vip3 = vip3;
	}

	public String getVip4() {
		return vip4;
	}

	public void setVip4(String vip4) {
		this.vip4 = vip4;
	}

	public String getRmtotal() {
		return rmtotal;
	}

	public void setRmtotal(String rmtotal) {
		this.rmtotal = rmtotal;
	}

	@Override
	public String toString() {
		return "FjZaiXianRenShuEntity [vip1=" + vip1 + ", vip2=" + vip2 + ", vip3=" + vip3 + ", vip4=" + vip4
				+ ", rmtotal=" + rmtotal + "]";
	}
	
	

}
