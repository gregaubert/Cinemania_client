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
		//FIXME Rajouter le producteur, pour lorsqu'il y aura une collaboration pour la production de film seulement
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
		
		double rapport = totalValue() / (MAX_PRICE_MOVIE + mMaxMarketing);
		
		// People init en fonction des ressources demandées
		peopleInit = (int)((double)peopleInit*(1+rapport));
		
		setPeopleInit(peopleInit);

		// Tx de décroissance aléatoire
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
		//movie.put("producer", mProducer.getId());		// TODO Rajouter le producteur lorsqu'il y aura la collaboration pour les films
		return movie;
	}

}
