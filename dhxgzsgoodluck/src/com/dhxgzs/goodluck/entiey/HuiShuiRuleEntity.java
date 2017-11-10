 package com.dhxgzs.goodluck.entiey;

public class HuiShuiRuleEntity {
//			
//	"room": "³õ¼¶·¿",
//    "minloss": 301,
//    "maxloss": 30000,
//    "backrate": 1
	
	private String room;
	private String minloss;
	private String maxloss;
	private String backrate;
	
	public HuiShuiRuleEntity() {
		super();
	}

	public HuiShuiRuleEntity(String room, String minloss, String maxloss, String backrate) {
		super();
		this.room = room;
		this.minloss = minloss;
		this.maxloss = maxloss;
		this.backrate = backrate;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getMinloss() {
		return minloss;
	}

	public void setMinloss(String minloss) {
		this.minloss = minloss;
	}

	public String getMaxloss() {
		return maxloss;
	}

	public void setMaxloss(String maxloss) {
		this.maxloss = maxloss;
	}

	public String getBackrate() {
		return backrate;
	}

	public void setBackrate(String backrate) {
		this.backrate = backrate;
	}

	@Override
	public String toString() {
		return "HuiShuiRuleEntity [room=" + room + ", minloss=" + minloss + ", maxloss=" + maxloss + ", backrate="
				+ backrate + "]";
	}
	
	
}
