package com.dhxgzs.goodluck.entiey;

public class ZuiXinGuessingNotesEntity {
	
	
	private String expect;
	private String time;
	
	
	public ZuiXinGuessingNotesEntity() {
		super();
	}


	public ZuiXinGuessingNotesEntity(String expect, String time) {
		super();
		this.expect = expect;
		this.time = time;
	}


	public String getExpect() {
		return expect;
	}


	public void setExpect(String expect) {
		this.expect = expect;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	@Override
	public String toString() {
		return "ZuiXinGuessingNotesEntity [expect=" + expect + ", time=" + time + "]";
	}
	
	

}
