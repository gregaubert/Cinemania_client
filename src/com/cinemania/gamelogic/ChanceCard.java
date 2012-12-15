package com.cinemania.gamelogic;

public class ChanceCard {
	
	private String mTitle;
	private String mText;
	private int mAmount;

	public ChanceCard(String title, String text, int amount) {
		mTitle = title;
		mText = text;
		mAmount = amount;
	}
	
	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public String getText() {
		return mText;
	}

	public void setText(String text) {
		mText = text;
	}

	public int getAmount() {
		return mAmount;
	}

	public void setAmount(int amount) {
		mAmount = amount;
	}
}
