package com.cinemania.client;
public abstract class Movie implements Constantes {
	
  private int id;
  private int baseId =  1;
  private String title;
  private int year;
  private int turn =  0;

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

  public void incrementsTurn() {
		turn += 1;
  }

}
