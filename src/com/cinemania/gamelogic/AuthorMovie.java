package com.cinemania.gamelogic;
public class AuthorMovie extends Movie {
  private int sellingPrice;

  public AuthorMovie(String title, int year, int sellingPrice) {
		super(title, year);
		setDecreasingRate(0.8); // TODO: according to the context of the game (city and random)
		setPeopleInit(50); // TODO: according to the context of the game (city and random)
		this.sellingPrice = sellingPrice;
		
		int peopleInit = (int)((double)INITIAL_SPECTATORS_AUTHORMOVIE * Math.pow(GROWING_RATE_SPECTATORS, year - INITIAL_YEAR));
		setPeopleInit(peopleInit);
		
		double decrasingRate = DECREASING_MOVIE_RATE_MIN_AM + Math.random() * (DECREASING_MOVIE_RATE_MAX_AM - DECREASING_MOVIE_RATE_MIN_AM);
		setDecreasingRate(1 - decrasingRate); 
		
  }

  @Override
  public int sellingPrice() {
		return sellingPrice;
  }

}
