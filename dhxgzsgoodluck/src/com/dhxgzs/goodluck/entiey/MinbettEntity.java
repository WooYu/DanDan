package com.dhxgzs.goodluck.entiey;

public class MinbettEntity {
	/**{"lotteryname":"����28",
	 * "roomname":"������",
	 * "maxuser":500,
	 * "vipmaxuser":200,
	 * "backrate":1,
	 * "enter":0,
	 * "minbett":10},/
	 * 
	 */
	private String lotteryname;  //��Ʊ����
	private String roomname;	//��������
	private String maxuser;		//�������
	private String vipmaxuser;	//vip�����������
	private String backrate;	//�����ˮ
	private String enter;		//��������
	private String minbett;		//Ͷע��С���
	
	
	
	
 
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
