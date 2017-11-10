package com.dhxgzs.goodluck.entiey;

public class BankListEntity {

	private String type;

	public BankListEntity() {
		super();
	}

	public BankListEntity(String type) {
		super();
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BankListEntity [type=" + type + "]";
	}
	
	
	
}
