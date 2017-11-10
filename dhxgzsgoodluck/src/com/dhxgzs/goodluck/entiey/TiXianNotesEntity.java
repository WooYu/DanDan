package com.dhxgzs.goodluck.entiey;

public class TiXianNotesEntity {
//	"money": 100,
//    "state": "提现成功",
//    "addtime": "2016-12-14"
	
	private String money;
	private String state;
	private String addtime;
	
	public TiXianNotesEntity() {
		super();
	}

	public TiXianNotesEntity(String money, String state, String addtime) {
		super();
		this.money = money;
		this.state = state;
		this.addtime = addtime;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	@Override
	public String toString() {
		return "TiXianNotesEntity [money=" + money + ", state=" + state + ", addtime=" + addtime + "]";
	}
	
	

}
