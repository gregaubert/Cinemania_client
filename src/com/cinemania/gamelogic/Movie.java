package com.cinemania.gamelogic;

import static com.cinemania.constants.AllConstants.*;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Movie implements Profitable, JSonator {

	private int mId;
	private static int mBaseId = 1;
	private String mTitle;
	private int mYear;
	private int mBeginingTurn;

	/**
	 * This factor is influenced by the context of the game : according to the
	 * city, the success (and possibly the pub)
	 */
	private int mPeopleInit;

	/**
	 * according to the success
	 */
	private double decreasingRate;

	public Movie(String title, int year) {
		mId = mBaseId++;
		setTitle(title);
		setYear(year);
	}
	
	public Movie(int id, String title, int year, int beginingTurn, int peopleInit, double decreasingRate) {
		mId = id;
		mBaseId = Math.max(mBaseId, id + 1);
		setTitle(title);
		setYear(year);
		setBeginingTurn(beginingTurn);
		setDecreasingRate(decreasingRate);
		setPeopleInit(peopleInit);
	}
	
	public Movie(JSONObject movie) throws JSONException {
		this(	movie.getInt("id"),
				movie.getString("title"),
				movie.getInt("year"),
				movie.getInt("beginingturn"),
				movie.getInt("peopleinit"),
				movie.getDouble("decreasingrate"));
	}

	public abstract int sellingPrice();

	public int getPeopleInit() {
		return mPeopleInit;
	}

	public void setPeopleInit(int peopleInit) {
		mPeopleInit = peopleInit;
	}

	public double getDecreasingRate() {
		return decreasingRate;
	}

	public void setDecreasingRate(double decreasingRate) {
		this.decreasingRate = decreasingRate;
	}

	@Override
	public int profit(int startTurn, int stopTurn) {
		int profit = 0;
		while (startTurn++ < stopTurn)
			profit += COSTS_CINEMA_TICKET
					* getPeopleInit()
					* Math.pow(getDecreasingRate(), startTurn
							- getBeginingTurn());

		return profit;
	}

	public int getBeginingTurn() {
		return mBeginingTurn;
	}

	/**
	 * The first time the movie appear on a cinema we must to initialize the
	 * beginingTurn
	 */
	public void setBeginingTurn(int beginingTurn) {
		this.mBeginingTurn = beginingTurn;
	}

	public int getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public int getYear() {
		return mYear;
	}

	public void setYear(int year) {
		this.mYear = year;
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject movie = new JSONObject();
		movie.put("id", getId());
		movie.put("title", getTitle());
		movie.put("year", getYear());
		movie.put("peopleinit", getPeopleInit());
		movie.put("decreasingrate", getDecreasingRate());
		movie.put("beginingturn", getBeginingTurn());
		movie.put("sellingprice", sellingPrice());
		return movie;
	}
}
