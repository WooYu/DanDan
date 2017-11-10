package com.dhxgzs.goodluck.entiey;

public class HuiShuiEntity {
	// "money": 200,
	// "status": "»ØË®",
	// "time": "2016-12-08"
	private String money;
	private String status;
	private String time;

	public HuiShuiEntity() {
		super();
	}

	public HuiShuiEntity(String money, String status, String time) {
		super();
		this.money = money;
		this.status = status;
		this.time = time;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "HuiShuiEntity [money=" + money + ", status=" + status + ", time=" + time + "]";
	}

	
}
