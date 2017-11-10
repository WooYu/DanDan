package com.dhxgzs.goodluck.entiey;

/**
 * 用户个人信息实体类
 * 
 * @author Administrator
 *
 */
public class UserInfoEntity {
	/** 用户账号 */
	private String account;
	/** 用户昵称 */
	private String nickname;
	/** 用户个性签名 */
	private String signname;
	/** 用户头像图片地址 */
	private String avatar;
	/**用户余额*/
	private String money;
	/**分享ID*/
	private String userid;
	/**等级图片*/
	private String dengji;
	public UserInfoEntity() {
		super();
	}
	public UserInfoEntity(String account, String nickname, String signname, String avatar, String money, String userid,
			String dengji) {
		super();
		this.account = account;
		this.nickname = nickname;
		this.signname = signname;
		this.avatar = avatar;
		this.money = money;
		this.userid = userid;
		this.dengji = dengji;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSignname() {
		return signname;
	}
	public void setSignname(String signname) {
		this.signname = signname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDengji() {
		return dengji;
	}
	public void setDengji(String dengji) {
		this.dengji = dengji;
	}
	@Override
	public String toString() {
		return "UserInfoEntity [account=" + account + ", nickname=" + nickname + ", signname=" + signname + ", avatar="
				+ avatar + ", money=" + money + ", userid=" + userid + ", dengji=" + dengji + "]";
	}
	
	
	

}
