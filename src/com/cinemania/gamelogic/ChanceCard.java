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

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getText() {
		return mText;
	}

	public void setText(String mText) {
		this.mText = mText;
	}

	public int getAmount() {
		return mAmount;
	}

	public void setAmount(int mAmount) {
		this.mAmount = mAmount;
	}
}
