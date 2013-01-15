package com.cinemania.gamelogic;

import static com.cinemania.constants.AllConstants.*;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthorMovie extends Movie {
	private int mSellingPrice;

	public AuthorMovie(String title, int year, int sellingPrice, int currentTurn) {
		super(title, year);

		mSellingPrice = sellingPrice;

		double rapport = mSellingPrice / (COSTS_AUTHOR_MAX * Math.pow(INFLATION, currentTurn));
		
		setBeginingTurn(currentTurn);
		
		int peopleInit = (int) ((double) INITIAL_SPECTATORS_AUTHORMOVIE * Math
				.pow(GROWING_RATE_SPECTATORS, year - INITIAL_YEAR));
		
		// People init en fonction des ressources demandées
		peopleInit = (int)((double)peopleInit*(1+rapport));
		
		setPeopleInit(peopleInit);

		double decrasingRate = DECREASING_MOVIE_RATE_MIN_AM + Math.random()
				* (DECREASING_MOVIE_RATE_MAX_AM - DECREASING_MOVIE_RATE_MIN_AM);
		setDecreasingRate(1 - decrasingRate);

	}

	public AuthorMovie(JSONObject movie) throws JSONException {
		super(movie);
		mSellingPrice = movie.getInt("sellingprice");
	}

	@Override
	public int sellingPrice() {
		return mSellingPrice;
	}
	
	@Override
	public JSONObject toJson() throws JSONException {
		return super.toJson();
	}
}
