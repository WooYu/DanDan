package com.dhxgzs.goodluck.entiey;

import java.util.List;

public class PeiLvEntity {

	/**
	 * bttypeid : 43 item : ´ó odds : 1:2.0 nums :
	 * 14,15,16,17,18,19,20,21,22,23,24,25,26,27
	 */

	private String bttypeid;
	private String item;
	private String odds;
	private String nums;
	public PeiLvEntity() {
		super();
	}
	public PeiLvEntity(String bttypeid, String item, String odds, String nums) {
		super();
		this.bttypeid = bttypeid;
		this.item = item;
		this.odds = odds;
		this.nums = nums;
	}
	public String getBttypeid() {
		return bttypeid;
	}
	public void setBttypeid(String bttypeid) {
		this.bttypeid = bttypeid;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getOdds() {
		return odds;
	}
	public void setOdds(String odds) {
		this.odds = odds;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	@Override
	public String toString() {
		return "PeiLvEntity [bttypeid=" + bttypeid + ", item=" + item + ", odds=" + odds + ", nums=" + nums + "]";
	}
	
	
	
}
