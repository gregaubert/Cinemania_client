package com.cinemania.gamelogic;

import static com.cinemania.constants.AllConstants.*;

import org.json.JSONException;
import org.json.JSONObject;

public class BigMovie extends Movie {
	private int mLogistic;

	private int mActors;

	private int mInitPrice;

	private int mMarketing;

	private int mYear;

	private int mMaxMarketing;

	private Player mProducer;

	public BigMovie(String title, int logistic, int actors, int initPrice, int year, int maxMarketing) {
		super(title, year);
		mLogistic = logistic;
		mActors = actors;
		mInitPrice = initPrice;
		mYear = year;
		mMaxMarketing = maxMarketing;
	}

	public BigMovie(JSONObject movie) throws JSONException {
		super(movie);
		mLogistic = movie.getInt("logistic");
		mActors = movie.getInt("actors");
		mInitPrice = movie.getInt("initprice");
		mYear = movie.getInt("year");
		mMarketing = movie.getInt("marketing");
		mMaxMarketing = movie.getInt("maxmarketing");
		//FIXME Rajouter le producteur
	}

	public void produceThisMovie(Player player, int budgetMarketing) {
		mProducer = player;
		mMarketing = budgetMarketing;
		
		mProducer.looseMoney(mInitPrice);
		mProducer.looseLogistic(mLogistic);
		mProducer.looseActors(mActors);

		double rateMarketing = (1 + RATE_MARKETING / 2)
				- (mMarketing / mMaxMarketing * RATE_MARKETING);
		int peopleInit = (int) (rateMarketing
				* (double) INITIAL_SPECTATORS_BIGMOVIE * Math.pow(
				GROWING_RATE_SPECTATORS, mYear - INITIAL_YEAR));
		setPeopleInit(peopleInit);

		double decrasingRate = DECREASING_MOVIE_RATE_MIN_BM + Math.random()
				* (DECREASING_MOVIE_RATE_MAX_BM - DECREASING_MOVIE_RATE_MIN_BM);
		setDecreasingRate(1 - decrasingRate);
	}

	public Player getProducer() {
		return mProducer;
	}

	public int totalValue() {
		return mInitPrice + mMarketing + mLogistic * LOGISTIC_VALUE + mActors * ACTOR_VALUE;
	}

	@Override
	public int sellingPrice() {
		return (int) ((double) totalValue() * SELLINGPRICE_RATIO);
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject movie = super.toJson();
		movie.put("logistic", mLogistic);
		movie.put("actors", mActors);
		movie.put("initprice", mInitPrice);
		movie.put("year", mYear);
		movie.put("marketing", mMarketing);
		movie.put("maxmarketing", mMaxMarketing);
		//FIXME Ajouter une référence au joueur qui a créer le film
		//movie.put("producer", mProducer.getId());
		return movie;
	}

}
