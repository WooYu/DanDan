package com.dhxgzs.goodluck.entiey;

public class ZuiXinLotteryNotesEntity {

//	 "expect": "��803283��",
//     "result": "5+2+9=16(��,˫)"
	
	private String expect;
	private String result;
	
	
	public ZuiXinLotteryNotesEntity() {
		super();
	}


	public ZuiXinLotteryNotesEntity(String expect, String result) {
		super();
		this.expect = expect;
		this.result = result;
	}


	public String getExpect() {
		return expect;
	}


	public void setExpect(String expect) {
		this.expect = expect;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	@Override
	public String toString() {
		return "ZuiXinLotteryNotesEntity [expect=" + expect + ", result=" + result + "]";
	}
	
	
}
