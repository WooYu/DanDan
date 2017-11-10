package com.dhxgzs.goodluck.entiey;

public class ZhiJieKaiHuEntity {
//	 "autoid": 1,
//     "name": "a",
//     "backfee": 28,
//     "minfee": 500,
//     "maxfee": 3000,
//     "addtime": "2017-06-02 01:13:54"
	
	
	private String autoid;
	private String name;
	private String backfee;
	private String minfee;
	private String maxfee;
	private String addtime;
	public ZhiJieKaiHuEntity() {
		super();
	}
	public ZhiJieKaiHuEntity(String autoid, String name, String backfee, String minfee, String maxfee, String addtime) {
		super();
		this.autoid = autoid;
		this.name = name;
		this.backfee = backfee;
		this.minfee = minfee;
		this.maxfee = maxfee;
		this.addtime = addtime;
	}
	public String getAutoid() {
		return autoid;
	}
	public void setAutoid(String autoid) {
		this.autoid = autoid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBackfee() {
		return backfee;
	}
	public void setBackfee(String backfee) {
		this.backfee = backfee;
	}
	public String getMinfee() {
		return minfee;
	}
	public void setMinfee(String minfee) {
		this.minfee = minfee;
	}
	public String getMaxfee() {
		return maxfee;
	}
	public void setMaxfee(String maxfee) {
		this.maxfee = maxfee;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "ZhiJieKaiHuEntity [autoid=" + autoid + ", name=" + name + ", backfee=" + backfee + ", minfee=" + minfee
				+ ", maxfee=" + maxfee + ", addtime=" + addtime + "]";
	}
	
	

}
