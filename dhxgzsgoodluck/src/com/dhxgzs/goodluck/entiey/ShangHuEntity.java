package com.dhxgzs.goodluck.entiey;

public class ShangHuEntity {

	// "key": "н╒пе",
	// "name": "",
	// "account":
	// "http://120.25.225.161:828/upload/imgs/img_1612300958212800118.png",
	// "bank": "",
	// "bankaddress": ""

	private String key;
	private String name;
	private String account;
	private String bank;
	private String bankaddress;

	public ShangHuEntity() {
		super();
	}
 
	public ShangHuEntity(String key, String name, String account, String bank, String bankaddress) {
		super();
		this.key = key;
		this.name = name;
		this.account = account;
		this.bank = bank;
		this.bankaddress = bankaddress;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankaddress() {
		return bankaddress;
	}

	public void setBankaddress(String bankaddress) {
		this.bankaddress = bankaddress;
	}

	@Override
	public String toString() {
		return "ShangHuEntity [key=" + key + ", name=" + name + ", account=" + account + ", bank=" + bank
				+ ", bankaddress=" + bankaddress + "]";
	}

}
