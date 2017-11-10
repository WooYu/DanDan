package com.dhxgzs.goodluck.entiey;

public class NoticeEntity {
//	 "time": "2016-12-01",
//     "title": "Î¬»¤¹«¸æ121",
//     "body": "http://120.25.225.161:828//home/notice/2"
	private String time;
	private String title;
	private String body;
	public NoticeEntity() {
		super();
	}
	public NoticeEntity(String time, String title, String body) {
		super();
		this.time = time;
		this.title = title;
		this.body = body;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "NoticeEntity [time=" + time + ", title=" + title + ", body=" + body + "]";
	}

	
}
