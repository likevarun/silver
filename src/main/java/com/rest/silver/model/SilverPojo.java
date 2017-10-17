package com.rest.silver.model;

public class SilverPojo {

	private String character;

	private String amount;
	
	public SilverPojo() {
	}

	public SilverPojo(String character, String amount) {
		this.character = character;
		this.amount = amount;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}