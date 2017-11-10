package com.dhxgzs.goodluck.entiey;

public class PeiLvExplainEntity {
	
//	 "odds": "1:13.0",
//     "item": "6,7,8,9,10,11,16,17,18,19,20,21"
	
	private String odds;
	private String item;
	
	
	public PeiLvExplainEntity() {
		super();
	}
	
	public PeiLvExplainEntity(String odds, String item) {
		super();
		this.odds = odds;
		this.item = item;
	}
	
	public String getOdds() {
		return odds;
	}
	public void setOdds(String odds) {
		this.odds = odds;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	
	@Override
	public String toString() {
		return "PeiLvExplainEntity [odds=" + odds + ", item=" + item + "]";
	}

	
	
}
