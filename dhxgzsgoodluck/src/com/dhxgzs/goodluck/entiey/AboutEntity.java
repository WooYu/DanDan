package com.dhxgzs.goodluck.entiey;

public class AboutEntity {

	// "version": "1.0",
	// "url": "www.yypc28.me",
	// "qq": "123456789",
	// "wechat": "pcddmm",
	// "img":"http://120.25.225.161:828/upload/imgs/img_1612301112535580161.png"

	private String version;
	private String url;
	private String qq;
	private String wechat;
	private String img;
	
	public AboutEntity() {
		super();
	}
		
	public AboutEntity(String version, String url, String qq, String wechat, String img) {
		super();
		this.version = version;
		this.url = url;
		this.qq = qq;
		this.wechat = wechat;
		this.img = img;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "AboutEntity [version=" + version + ", url=" + url + ", qq=" + qq + ", wechat=" + wechat + ", img=" + img
				+ "]";
	}
	
	

}
