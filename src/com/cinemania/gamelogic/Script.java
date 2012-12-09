package com.cinemania.gamelogic;

import org.json.JSONException;
import org.json.JSONObject;

public class Script implements JSonator{
	
	private String mTitle;
	private int mYear;
	private String mSummary;
	private int mPrice;
	
	public Script(String title, int year, int price) {
		mTitle = title;
		mYear = year;
		mPrice = price;
	}
	
	public Script(JSONObject script) throws JSONException{
		this(script.getString("title"), script.getInt("year"), script.getInt("price"));
		this.setSummary(script.getString("summary"));
	}
	
	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public int getYear() {
		return mYear;
	}

	public void setYear(int mYear) {
		this.mYear = mYear;
	}

	public String getSummary() {
		if(mSummary == null)
			return "";
		return mSummary;
	}

	public void setSummary(String mSummary) {
		this.mSummary = mSummary;
	}

	public int getPrice() {
		return mPrice;
	}

	public void setPrice(int mPrice) {
		this.mPrice = mPrice;
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject script = new JSONObject();
		script.put("title", this.getTitle());
		script.put("summary", this.getSummary());
		script.put("year", this.getYear());
		script.put("price", this.getPrice());
		return script;
	}

}
