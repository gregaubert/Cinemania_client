package com.cinemania.gamelogic;
public class BigMovie extends Movie {
  private int logistic;

  private int actors;

  private int initPrice;

  private int marketing;
  
  private int year;
  
  private int maxMarketing;

  /**
   *  if producer is null then the movie is a author's film 
   */
  private Player producer;

  public BigMovie(String title, int logistic, int actors, int initPrice, int year, int maxMarketing) {
		super(title, year);
		this.logistic = logistic;
		this.actors = actors;
		this.initPrice = initPrice;
		this.year = year;
		this.maxMarketing = maxMarketing;
  }

  public void produceThisMovie(Player player, int budgetMarketing) {
		this.producer = player;
		this.marketing = budgetMarketing;
		
		double rateMarketing = (1 + RATE_MARKETING / 2 )- (marketing / maxMarketing * RATE_MARKETING);
		int peopleInit = (int)(rateMarketing * (double) INITIAL_SPECTATORS_BIGMOVIE * Math.pow(GROWING_RATE_SPECTATORS, year - INITIAL_YEAR));
		setPeopleInit(peopleInit);
		
		double decrasingRate = DECREASING_MOVIE_RATE_MIN_BM + Math.random() * (DECREASING_MOVIE_RATE_MAX_BM - DECREASING_MOVIE_RATE_MIN_BM);
		setDecreasingRate(1 - decrasingRate); 
  }

  public Player getProducer() {
		return producer;
  }

  public int totalValue() {
		return initPrice + marketing + logistic * LOGISTIC_VALUE + actors * ACTOR_VALUE;
  }

  @Override
  public int sellingPrice() {
		return (int)((double)totalValue() * SELLINGPRICE_RATIO);
  }

}
