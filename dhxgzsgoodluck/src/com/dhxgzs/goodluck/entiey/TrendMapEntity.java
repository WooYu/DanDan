package com.dhxgzs.goodluck.entiey;

public class TrendMapEntity {

	// "id": 1,
	// "expect": "802916",
	// "resultnum": "15",
	// "big": "大",
	// "small": "",
	// "single": "单",
	// "dble": "",
	// "lsingle": "大单",
	// "ssingle": "",
	// "ldble": "",
	// "sdble": ""

	private String id;
	private String expect;
	private String resultnum;
	private String big;
	private String small;
	private String single;
	private String dble;
	private String lsingle;
	private String ssingle;
	private String ldble;
	private String sdble;

	public TrendMapEntity() {
		super();
	}

	public TrendMapEntity(String id, String expect, String resultnum, String big, String small, String single,
			String dble, String lsingle, String ssingle, String ldble, String sdble) {
		super();
		this.id = id;
		this.expect = expect;
		this.resultnum = resultnum;
		this.big = big;
		this.small = small;
		this.single = single;
		this.dble = dble;
		this.lsingle = lsingle;
		this.ssingle = ssingle;
		this.ldble = ldble;
		this.sdble = sdble;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}

	public String getResultnum() {
		return resultnum;
	}

	public void setResultnum(String resultnum) {
		this.resultnum = resultnum;
	}

	public String getBig() {
		return big;
	}

	public void setBig(String big) {
		this.big = big;
	}

	public String getSmall() {
		return small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String getDble() {
		return dble;
	}

	public void setDble(String dble) {
		this.dble = dble;
	}

	public String getLsingle() {
		return lsingle;
	}

	public void setLsingle(String lsingle) {
		this.lsingle = lsingle;
	}

	public String getSsingle() {
		return ssingle;
	}

	public void setSsingle(String ssingle) {
		this.ssingle = ssingle;
	}

	public String getLdble() {
		return ldble;
	}

	public void setLdble(String ldble) {
		this.ldble = ldble;
	}

	public String getSdble() {
		return sdble;
	}

	public void setSdble(String sdble) {
		this.sdble = sdble;
	}

	@Override
	public String toString() {
		return "TrendMapEntity [id=" + id + ", expect=" + expect + ", resultnum=" + resultnum + ", big=" + big
				+ ", small=" + small + ", single=" + single + ", dble=" + dble + ", lsingle=" + lsingle + ", ssingle="
				+ ssingle + ", ldble=" + ldble + ", sdble=" + sdble + "]";
	}

	
}
