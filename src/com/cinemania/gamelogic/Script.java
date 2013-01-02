package com.cinemania.gamelogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.SparseArray;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.interfaces.JSonator;
import com.cinemania.network.GameContext;

public class Script implements JSonator{
	
	private String mTitle;
	private int mYear;
	private String mSummary;
	private int mPrice;
	private int mActors;
	private int mLogistics;
	private int mPriceProd;
	
	private static SparseArray<ArrayList<MovieName>> mMovieNameDB = new SparseArray<ArrayList<MovieName>>();
	
	public Script(String title, int year, int price, int priceProd, int actors, int logistics) {
		mTitle = title;
		mYear = year;
		mPrice = price;
		mActors = actors;
		mLogistics = logistics;
		mPriceProd = priceProd;
	}
	
	public Script(JSONObject script) throws JSONException{
		this(script.getString("title"), script.getInt("year"), script.getInt("price"), script.getInt("priceprod"), script.getInt("actors"), script.getInt("logistics"));
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
			return "Pas de description.";
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
	
	public int getPriceProd() {
		return mPriceProd;
	}

	public void setPriceProd(int mPriceProd) {
		this.mPriceProd = mPriceProd;
	}

	public int getActors() {
		return mActors;
	}

	public void setActors(int mActors) {
		this.mActors = mActors;
	}

	public int getLogistics() {
		return mLogistics;
	}

	public void setLogistics(int mLogistics) {
		this.mLogistics = mLogistics;
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject script = new JSONObject();
		script.put("title", this.getTitle());
		script.put("summary", this.getSummary());
		script.put("year", this.getYear());
		script.put("price", this.getPrice());
		script.put("priceprod", this.getPriceProd());
		script.put("actors", this.getActors());
		script.put("logistics", this.getLogistics());
		return script;
	}
	
	public static Script pickAScript(){
		ArrayList<MovieName> moviesList;
		int year = GameContext.getSharedInstance().getYear(); 
		while(mMovieNameDB.indexOfKey(year) < 0 && year >= AllConstants.INITIAL_YEAR)
			year--;
		
		MovieName movieName;
		if(year >= AllConstants.INITIAL_YEAR){
			moviesList = mMovieNameDB.get(year);
			movieName = moviesList.get((int)(Math.random() * (moviesList.size()-0)));
		}
		else
			movieName = new MovieName(GameContext.getSharedInstance().getYear(), "Film d'exemple");
		
		Script script = new Script(movieName.mName, movieName.mYear, 
				(int)(Math.random() * (AllConstants.COSTS_SCRIPT_MAX-AllConstants.COSTS_SCRIPT_MIN) + AllConstants.COSTS_SCRIPT_MIN),
				(int)(Math.random() * (AllConstants.COSTS_MOVIE_MAX-AllConstants.COSTS_MOVIE_MIN) + AllConstants.COSTS_MOVIE_MIN),
				(int)(Math.random() * (AllConstants.COSTS_ACTOR_MAX-AllConstants.COSTS_ACTOR_MIN) + AllConstants.COSTS_ACTOR_MIN),
				(int)(Math.random() * (AllConstants.COSTS_LOGISTIC_MAX-AllConstants.COSTS_LOGISTIC_MIN) + AllConstants.COSTS_LOGISTIC_MIN));
		script.setSummary(movieName.mSummary);
		return script;
	}
	
	public static void loadMovieDB() {

		InputStream inputStream = Base.getSharedInstance().getResources()
				.openRawResource(R.raw.movie_db);
		if (inputStream != null) {
			BufferedReader buffreader = new BufferedReader(
					new InputStreamReader(inputStream));
			try {
				String line;
				while ((line = buffreader.readLine()) != null) {
					MovieName movieName = new MovieName(line.split(";"));
					if (mMovieNameDB.indexOfKey(movieName.mYear) < 0)
						mMovieNameDB.put(movieName.mYear, new ArrayList<MovieName>());
					mMovieNameDB.get(movieName.mYear).add(movieName);
				}
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class MovieName{
		int mYear;
		String mName;
		String mSummary;
		
		public MovieName(String[] splitLine) {
			mYear = Integer.parseInt(splitLine[0]);
			mName = splitLine[1];
			if(splitLine.length >= 3)
				mSummary = splitLine[2];
			else
				mSummary = "Pas encore de description.";
		}

		public MovieName(int year, String name) {
			mYear = year;
			mName = name;
			mSummary = "Pas encore de description.";
		}
	}
}
