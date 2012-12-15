package com.cinemania.constants;

import org.andengine.util.color.Color;

import com.cinemania.activity.R;

public final class AllConstants {
	private AllConstants() {
	}

	public static final int INITIAL_YEAR = 1930;

	public static final int INITIAL_SPECTATORS_BIGMOVIE = 100;

	public static final int INITIAL_SPECTATORS_AUTHORMOVIE = 50;
	
	/**
	 * Stats rate
	 */
	public static final double GROWING_RATE_SPECTATORS = 1.02;
	public static final double RATE_MARKETING = 0.2;
	public static final double DECREASING_MOVIE_RATE_MIN_BM = 0.14;
	public static final double DECREASING_MOVIE_RATE_MAX_BM = 0.22;
	public static final double DECREASING_MOVIE_RATE_MIN_AM = 0.2;
	public static final double DECREASING_MOVIE_RATE_MAX_AM = 0.25;
	public static final double RATE_SALE = 2.0;
	
	/**
	 * Game start constant
	 */
	public static final int DEFAULT_AMOUNT = 2000;
	public static final int DEFAULT_ACTORS = 0;
	public static final int DEFAULT_LOGISTIC = 0;
	public static final int DEFAULT_LEVEL = 1;
	public static final int LEVEL_MAX_BUILDING = 3;
	public static final int BASEVALUE_OF_SCHOOL = 800;
	public static final int BASEVALUE_OF_LOGISTIC = 1000;
	public static final int BASEVALUE_OF_CINEMA = 1000;
	public static final int PRICE_SCHOOL_EXTENSION = 200;
	public static final int PRICE_LOGISTIC_EXTENSION = 300;
	public static final int PRICE_ROOM = 1000;
	public static final int MAX_ROOMS = 3;
	public static final int LOGISTIC_VALUE = 200;
	public static final int ACTOR_VALUE = 300;
	public static final double SELLINGPRICE_RATIO = 0.25;
	
	/**
	 * Costs by turn depending on the cell
	 */
	public static final int COSTS_PER_ROOM = 50;
	public static final int COSTS_PER_CINEMA = 200;
	public static final int COSTS_CINEMA_TICKET = 15;
	
	/**
	 * Costs for stepping on opponent cell
	 */
	public static final int COSTS_ON_HQ = 750;
	
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
	public static final Color[][] PLAYER_COLOR = { {new Color(136/255f, 30/255f, 30/255f), new Color(30/255f, 136/255f, 30/255f), new Color(30/255f, 30/255f, 136/255f), new Color(208/255f, 208/255f, 69/255f)},
												   {new Color(221/255f, 115/255f, 115/255f), new Color(115/255f, 221/255f, 115/255f), new Color(115/255f, 115/255f, 221/255f), new Color(223/255f, 223/255f, 131/255f)}};
	public static final int[] PLAYER_COLOR_ANDROID = {R.color.player1, R.color.player2, R.color.player3, R.color.player4};
			
}
