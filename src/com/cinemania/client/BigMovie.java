package com.cinemania.client;

public class BigMovie extends Movie {
  private int logistic;

  private int actors;

  private int initPrice;

  private int marketing;

  /**
   *  if producer is null then the movie is a author's film 
   */
  private Player producer;

  public BigMovie(String title, int logistic, int actors, int initPrice, int year) {
		super(title, year);
		this.logistic = logistic;
		this.actors = actors;
		this.initPrice = initPrice;
  }

  public void produceThisMovie(Player player, int budgetMarketing) {
		this.producer = player;
		setPeopleInit(100); // TODO: according to the context : city, random, marketing, ...
		setDecreasingRate(0.6); // TODO: IDEM
		this.marketing = budgetMarketing;
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
