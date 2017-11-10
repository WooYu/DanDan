package com.dhxgzs.goodluck.entiey;

public class MinbettEntity {
	/**{"lotteryname":"北京28",
	 * "roomname":"初级房",
	 * "maxuser":500,
	 * "vipmaxuser":200,
	 * "backrate":1,
	 * "enter":0,
	 * "minbett":10},/
	 * 
	 */
	private String lotteryname;  //彩票类型
	private String roomname;	//房间类型
	private String maxuser;		//最大人数
	private String vipmaxuser;	//vip房间最大人数
	private String backrate;	//房间回水
	private String enter;		//进入条件
	private String minbett;		//投注最小金额
	
	
	
	
 
	public MinbettEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MinbettEntity(String lotteryname, String roomname, String maxuser,
			String vipmaxuser, String backrate, String enter, String minbett) {
		super();
		this.lotteryname = lotteryname;
		this.roomname = roomname;
		this.maxuser = maxuser;
		this.vipmaxuser = vipmaxuser;
		this.backrate = backrate;
		this.enter = enter;
		this.minbett = minbett;
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
	public String getMinbett() {
		return minbett;
	}
	public void setMinbett(String minbett) {
		this.minbett = minbett;
	}
	
	
}
