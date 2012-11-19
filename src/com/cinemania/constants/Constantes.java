package com.cinemania.constants;

import org.andengine.util.color.Color;


public interface Constantes {
	
  final int INITIAL_YEAR = 1930;
  
  final int INITIAL_SPECTATORS_BIGMOVIE = 100;
  
  final int INITIAL_SPECTATORS_AUTHORMOVIE = 50;
  
  final double GROWING_RATE_SPECTATORS = 1.02;
  
  final double RATE_MARKETING = 0.2;
  
  final double DECREASING_MOVIE_RATE_MIN_BM = 0.14;
  final double DECREASING_MOVIE_RATE_MAX_BM = 0.22;
  
  final double DECREASING_MOVIE_RATE_MIN_AM = 0.2;
  final double DECREASING_MOVIE_RATE_MAX_AM = 0.25;

  final int DEFAULT_AMOUNT =  2000;

  final int DEFAULT_ACTORS =  0;

  final int DEFAULT_LOGISTIC =  0;

  final int DEFAULT_LEVEL =  1;

  final double RATE_SALE =  2.0;

  final int LEVEL_MAX_BUILDING =  3;

  final int BASEVALUE_OF_SCHOOL =  800;

  final int BASEVALUE_OF_LOGISTIC =  1000;

  final int BASEVALUE_OF_CINEMA =  1000;

  final int PRICE_SCHOOL_EXTENSION =  200;

  final int PRICE_LOGISTIC_EXTENSION =  300;

  final int PRICE_ROOM =  1000;

  final int MAX_ROOMS =  3;

  final int LOGISTIC_VALUE =  200;

  final int ACTOR_VALUE =  300;

  final double SELLINGPRICE_RATIO =  0.25;
  
  final int BOARD_SIZE = 40;
  
  final int BOARD_NB_CINEMA_LINE = 3;
  final int BOARD_NB_ACTOR_LINE = 2;
  final int BOARD_NB_LOGISTIC_LINE = 2;
  final int BOARD_NB_SCRIPT_LINE = 1;
  final int BOARD_NB_TOT_LINE = 1; 
  final int BOARD_NB_EMPTY_TOT = 0;
  final int BOARD_NB_LUCK_TOT = 4;
  
  /**
   * Costs by turn depending on the case
   */
  final int COSTS_PER_ROOM = 50;
  final int COSTS_PER_CINEMA = 200;
  
  final int COSTS_CINEMA_TICKET = 15;
  
  final Color[] PLAYER_COLOR = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};

}
