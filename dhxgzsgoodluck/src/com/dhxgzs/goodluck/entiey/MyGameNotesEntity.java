package com.dhxgzs.goodluck.entiey;

public class MyGameNotesEntity {

	// "id": 2,
	// "type": "北京28 -- 798824期",
	// "winmoney": 100,
	// "result": "9+4+2=15",
	// "items": "大,大单,单,15,红",
	// "bttype": "大小单双",
	// "userbttype": "大单",
	// "money": 100,
	// "lkmoney": 200,
	// "addtime": "2016-12-17t09:11:38.463",
	// "totalcount": 2

	private String id;
	private String type;
	private String winmoney;
	private String result;
	private String items;
	private String bttype;
	private String userbttype;
	private String money;
	private String lkmoney;
	private String addtime;
	private String totalcount;

	public MyGameNotesEntity() {
		super();
	}

	public MyGameNotesEntity(String id, String type, String winmoney, String result, String items, String bttype,
			String userbttype, String money, String lkmoney, String addtime, String totalcount) {
		super();
		this.id = id;
		this.type = type;
		this.winmoney = winmoney;
		this.result = result;
		this.items = items;
		this.bttype = bttype;
		this.userbttype = userbttype;
		this.money = money;
		this.lkmoney = lkmoney;
		this.addtime = addtime;
		this.totalcount = totalcount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWinmoney() {
		return winmoney;
	}

	public void setWinmoney(String winmoney) {
		this.winmoney = winmoney;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getBttype() {
		return bttype;
	}

	public void setBttype(String bttype) {
		this.bttype = bttype;
	}

	public String getUserbttype() {
		return userbttype;
	}

	public void setUserbttype(String userbttype) {
		this.userbttype = userbttype;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getLkmoney() {
		return lkmoney;
	}

	public void setLkmoney(String lkmoney) {
		this.lkmoney = lkmoney;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}

	@Override
	public String toString() {
		return "MyGameNotesEntity [id=" + id + ", type=" + type + ", winmoney=" + winmoney + ", result=" + result
				+ ", items=" + items + ", bttype=" + bttype + ", userbttype=" + userbttype + ", money=" + money
				+ ", lkmoney=" + lkmoney + ", addtime=" + addtime + ", totalcount=" + totalcount + "]";
	}

}
