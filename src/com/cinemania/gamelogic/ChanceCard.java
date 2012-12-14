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
	
	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getmText() {
		return mText;
	}

	public void setmText(String mText) {
		this.mText = mText;
	}

	public int getmAmount() {
		return mAmount;
	}

	public void setmAmount(int mAmount) {
		this.mAmount = mAmount;
	}
}
