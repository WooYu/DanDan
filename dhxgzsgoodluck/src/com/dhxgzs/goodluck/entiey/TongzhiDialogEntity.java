package com.dhxgzs.goodluck.entiey;

public class TongzhiDialogEntity {

	
	private String title;
	private String time;
	private String body;
	public TongzhiDialogEntity() {
		super();
	}
	public TongzhiDialogEntity(String title, String time, String body) {
		super();
		this.title = title;
		this.time = time;
		this.body = body;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "TongzhiDialogEntity [title=" + title + ", time=" + time + ", body=" + body + "]";
	}
	
	

}
