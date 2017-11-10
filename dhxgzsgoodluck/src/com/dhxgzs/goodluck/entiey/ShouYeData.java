package com.dhxgzs.goodluck.entiey;

public class ShouYeData {

	/**用户人数*/
	private String users;
	/**赚取的元宝数*/
	private String money;
	/**赚钱率百分比*/
	private String percent;

	public ShouYeData() {
		super();
	}

	public ShouYeData(String users, String money, String percent) {
		super();
		this.users = users;
		this.money = money;
		this.percent = percent;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return "ShouYeData [users=" + users + ", money=" + money + ", percent=" + percent + "]";
	}

}
