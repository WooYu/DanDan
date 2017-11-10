package com.dhxgzs.goodluck.entiey;

public class RechargeNotesEntity {
	
	private String name;
	private String account;
	private String realmoney;
	private String nankname;
	private String remark;
	private String state;
	private String addtime;
	
	public RechargeNotesEntity() {
		super();
	}

	public RechargeNotesEntity(String name, String account, String realmoney, String nankname, String remark,
			String state, String addtime) {
		super();
		this.name = name;
		this.account = account;
		this.realmoney = realmoney;
		this.nankname = nankname;
		this.remark = remark;
		this.state = state;
		this.addtime = addtime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealmoney() {
		return realmoney;
	}

	public void setRealmoney(String realmoney) {
		this.realmoney = realmoney;
	}

	public String getNankname() {
		return nankname;
	}

	public void setNankname(String nankname) {
		this.nankname = nankname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return "RechargeNotesEntity [name=" + name + ", account=" + account + ", realmoney=" + realmoney + ", nankname="
				+ nankname + ", remark=" + remark + ", state=" + state + ", addtime=" + addtime + "]";
	}
	
	
}
