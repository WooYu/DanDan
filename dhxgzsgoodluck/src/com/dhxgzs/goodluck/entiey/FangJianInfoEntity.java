package com.dhxgzs.goodluck.entiey;

public class FangJianInfoEntity {

	// "lotteryname": "北京28",
	// "roomname": "中级房",
	// "maxuser": 500,
	// "vipmaxuser": 200,
	// "backrate": 18,
	// "enter": 0
	
	private String lotteryname;
	private String roomname;
	private String maxuser;
	private String vipmaxuser;
	private String backrate;
	private String enter;
	
	public FangJianInfoEntity() {
		super();
	}

	public FangJianInfoEntity(String lotteryname, String roomname, String maxuser, String vipmaxuser, String backrate,
			String enter) {
		super();
		this.lotteryname = lotteryname;
		this.roomname = roomname;
		this.maxuser = maxuser;
		this.vipmaxuser = vipmaxuser;
		this.backrate = backrate;
		this.enter = enter;
	}

	public String getLotteryname() {
		return lotteryname;
	}

	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getMaxuser() {
		return maxuser;
	}

	public void setMaxuser(String maxuser) {
		this.maxuser = maxuser;
	}

	public String getVipmaxuser() {
		return vipmaxuser;
	}

	public void setVipmaxuser(String vipmaxuser) {
		this.vipmaxuser = vipmaxuser;
	}

	public String getBackrate() {
		return backrate;
	}

	public void setBackrate(String backrate) {
		this.backrate = backrate;
	}

	public String getEnter() {
		return enter;
	}

	public void setEnter(String enter) {
		this.enter = enter;
	}

	@Override
	public String toString() {
		return "FangJianInfoEntity [lotteryname=" + lotteryname + ", roomname=" + roomname + ", maxuser=" + maxuser
				+ ", vipmaxuser=" + vipmaxuser + ", backrate=" + backrate + ", enter=" + enter + "]";
	}
	
	

}
