package com.dhxgzs.goodluck.entiey;

public class ZhangBianEntity {

//	 "money": 200,
//     "remark": "»ØË®",
//     "time": "2016-12-08"
	
	private String money;
	private String remark;
	private String time;
	
	public ZhangBianEntity() {
		super();
	}

	public ZhangBianEntity(String money, String remark, String time) {
		super();
		this.money = money;
		this.remark = remark;
		this.time = time;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ZhangBianEntity [money=" + money + ", remark=" + remark + ", time=" + time + "]";
	}
	
	
}
