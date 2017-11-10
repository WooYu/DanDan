package com.dhxgzs.goodluck.entiey;

public class BankCardInfoEntity {
//	  "account": "18237777903",
//      "username": "张三",
//      "card": "农业银行",
//      "cardnum": "6666666666666666666",
//      "cardaddress": "工业路支行"
	private String account;
	private String username;
	private String card;
	private String cardnum;
	private String cardaddress;
	
	public BankCardInfoEntity() {
		super();
	}

	public BankCardInfoEntity(String account, String username, String card, String cardnum, String cardaddress) {
		super();
		this.account = account;
		this.username = username;
		this.card = card;
		this.cardnum = cardnum;
		this.cardaddress = cardaddress;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public String getCardaddress() {
		return cardaddress;
	}

	public void setCardaddress(String cardaddress) {
		this.cardaddress = cardaddress;
	}

	@Override
	public String toString() {
		return "BankCardInfoEntity [account=" + account + ", username=" + username + ", card=" + card + ", cardnum="
				+ cardnum + ", cardaddress=" + cardaddress + "]";
	}
	
	

}
