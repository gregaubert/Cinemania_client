package com.cinemania.gamelogic;

import static com.cinemania.constants.AllConstants.*;

public abstract class Movie implements Profitable {
	
  private int id;
  private int baseId =  1;
  private String title;
  private int year;
  private int beginingTurn;


/**
   *  This factor is influenced by the context of the game :
   *  according to the city, the success (and possibly the pub)
   */
  private int peopleInit;

  /**
   *  according to the success	
   */
  private double decreasingRate;

  public Movie(String title, int year) {
		id = baseId++;
		this.title = title;
		this.year = year;
  }

  public abstract int sellingPrice() ;

  public int getPeopleInit() {
		return peopleInit;
  }

  public void setPeopleInit(int peopleInit) {
		this.peopleInit = peopleInit;
  }

  public double getDecreasingRate() {
		return decreasingRate;
  }

  public void setDecreasingRate(double decreasingRate) {
		this.decreasingRate = decreasingRate;
  }
  
  @Override
  public int profit(int startTurn, int stopTurn) {
	  int profit = 0;
	  int s = startTurn;
	  while(startTurn++ < stopTurn)
		  profit += COSTS_CINEMA_TICKET * getPeopleInit() * Math.pow(getDecreasingRate(), startTurn - getBeginingTurn());
	  
	  return profit;
  }


  public int getBeginingTurn() {
	  return beginingTurn;
  }

  /**
   * The first time the movie appear on a cinema we must to initialize the beginingTurn
   */
  public void setBeginingTurn(int beginingTurn) {
	  this.beginingTurn = beginingTurn;
  }

  

}
