package com.dhxgzs.goodluck.entiey;

/**
 * ¬÷≤•Õº µÃÂ¿‡
 * 
 * @author Administrator
 *
 */
public class LunBoEntity {

	private String title;
	private String image;
	private String url;

	public LunBoEntity() {
		super();
	}

	public LunBoEntity(String title, String image, String url) {
		super();
		this.title = title;
		this.image = image;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "LunBoEntity [title=" + title + ", image=" + image + ", url=" + url + "]";
	}

}
