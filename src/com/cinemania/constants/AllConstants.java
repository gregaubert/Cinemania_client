package com.cinemania.constants;

import org.andengine.util.color.Color;

import com.cinemania.activity.R;

public final class AllConstants {
	private AllConstants() {
	}

	public static final int INITIAL_YEAR = 1930;

	public static final int INITIAL_SPECTATORS_BIGMOVIE = 60;

	public static final int INITIAL_SPECTATORS_AUTHORMOVIE = 40;
	
	public static final int NUMBER_PLAYER = 4;
	public static final long VIBRATE_TIME_LOCAL = 400;
	public static final long VIBRATE_TIME_OTHER = 200;

	/**
	 * Stats rate
	 */
	public static final double GROWING_RATE_SPECTATORS = 1.02;
	public static final double INFLATION = 1.0055; // Inflation -> Croissance des prix et ressources
	public static final double RATE_MARKETING = 0.2;
	public static final double DECREASING_MOVIE_RATE_MIN_BM = 0.05;
	public static final double DECREASING_MOVIE_RATE_MAX_BM = 0.07;
	public static final double DECREASING_MOVIE_RATE_MIN_AM = 0.07;
	public static final double DECREASING_MOVIE_RATE_MAX_AM = 0.09;
	public static final double RATE_SALE = 2.0;
	public static final int FACTOR_DIVIDE_FACTORY = 2; // Division des revenus ressources
	public static final int FACTOR_DIVIDE_SCHOOL = 2; // Division des revenus écoles

	public static final int BONUS_AMOUT = 2000;
	
	/**
	 * Game start constant
	 */
	public static final int DEFAULT_AMOUNT = 15000;
	public static final int DEFAULT_ACTORS = 8;
	public static final int DEFAULT_LOGISTIC = 8;
	public static final int DEFAULT_HQ_LEVEL = 1;
	public static final int DEFAULT_RESOURCES_LEVEL_BF_BUY = 0;
	public static final int DEFAULT_RESOURCES_LEVEL_AF_BUY = 1;
	public static final int DEFAULT_BUILDING_LEVEL = 1;
	public static final int LEVEL_MAX_BUILDING = 3;
	public static final int BASEVALUE_OF_SCHOOL = 1200;
	public static final int BASEVALUE_OF_LOGISTIC = 1200;
	public static final int BASEVALUE_OF_CINEMA = 1000;
	public static final int PRICE_SCHOOL_EXTENSION = 2800;
	public static final int PRICE_LOGISTIC_EXTENSION = 2800;
	public static final int PRICE_ROOM = 2500;
	public static final int MAX_ROOMS = 3;
	public static final int LOGISTIC_VALUE = PRICE_LOGISTIC_EXTENSION / 10; // Ce que vaut un niveau logistique
	public static final int ACTOR_VALUE = PRICE_SCHOOL_EXTENSION / 10; // Ce que vaut un niveau acteur
	public static final double SELLINGPRICE_RATIO = 0.25;
	public static final int BASE_SCHOOL_INCOME = 1;
	public static final int BASE_LOGISTIC_INCOME = 1;



	/**
	 * Costs by turn depending on the cell
	 */
	public static final int COSTS_PER_ROOM = 50;
	public static final int COSTS_PER_CINEMA = 200;
	public static final int COSTS_CINEMA_TICKET = 10;

	/**
	 * General cost
	 */
	public static final int COSTS_ON_HQ = 750;
	public static final int COSTS_ON_CINEMA = 200;
	public static final int COSTS_SCRIPT_MIN = 1000;
	public static final int COSTS_SCRIPT_MAX = 2000;
	public static final int COSTS_ACTOR_MIN = 0;
	public static final int COSTS_ACTOR_MAX = 12;
	public static final int COSTS_LOGISTIC_MIN = 4;
	public static final int COSTS_LOGISTIC_MAX = 8;
	public static final int COSTS_MOVIE_MIN = 6000; // Rapporte entre 10000 et 20000
	public static final int COSTS_MOVIE_MAX = 16000;
	public static final int COSTS_AUTHOR_MIN = 4000; // Rapporte entre 5000 et 10000
	public static final int COSTS_AUTHOR_MAX = 8000;

	/** Max/Min price for a movie
	 * 
	 */
	public static final int MAX_RELATIVE_PRICE_MOVIE = COSTS_LOGISTIC_MAX * LOGISTIC_VALUE +
	COSTS_ACTOR_MAX * ACTOR_VALUE + COSTS_MOVIE_MAX;		


	/**
	 * Board generation
	 */
	public static final int BOARD_SIZE = 40;
	public static final int BOARD_NB_CINEMA_LINE = 3;
	public static final int BOARD_NB_ACTOR_LINE = 2;
	public static final int BOARD_NB_LOGISTIC_LINE = 2;
	public static final int BOARD_NB_SCRIPT_LINE = 1;
	public static final int BOARD_NB_TOT_LINE = 1;
	public static final int BOARD_NB_EMPTY_TOT = 0;
	public static final int BOARD_NB_LUCK_TOT = 4;
	public static final float BOARD_ZOOM_MIN = 0.5f;
	public static final float BOARD_ZOOM_MAX = 2f;
	public static int OFFSET = 10;

	public static final Color  BOARD_CASE_COLOR = new Color(250/255f, 252/255f, 124/255f);
	public static final Color[][] PLAYER_COLOR = { {new Color(136/255f, 30/255f, 30/255f), new Color(30/255f, 136/255f, 30/255f), new Color(30/255f, 30/255f, 136/255f), new Color(168/255f, 118/255f, 60/255f)},
		{new Color(221/255f, 115/255f, 115/255f), new Color(115/255f, 221/255f, 115/255f), new Color(115/255f, 115/255f, 221/255f), new Color(253/255f, 177/255f, 91/255f)}};
	public static final int[] PLAYER_COLOR_ANDROID = {R.color.player1, R.color.player2, R.color.player3, R.color.player4};
	
	public static int aleatory(int from, int to){
		return (int)(Math.random() * (to-from) + from);
	}
	
	public static int aleatoryAccordingInflation(int from, int to, int turn){
		from *= (int) Math.pow(INFLATION, turn);
		to *= (int) Math.pow(INFLATION, turn);
		return aleatory(from, to);
	}
	

}
