package com.cinemania.client;

import java.util.ArrayList;
public class Player implements Constantes {
  private static int generalId =  1;

  private int id;

  private int amount;

  private int logistic;

  private int actors;

  private Case position;

  private ArrayList<Case> properties;

  public Player() {
		id = generalId++;
		setAmount(DEFAULT_AMOUNT);
		setLogistic(DEFAULT_LOGISTIC);
		setActors(DEFAULT_ACTORS);	
		properties = new ArrayList<Case>();
  }

  public int getId() {
		return id;
  }

  public void setAmount(int amount) {
		this.amount = amount;
  }

  public int getAmount() {
		return amount;
  }

  public void setLogistic(int logistic) {
		this.logistic = logistic;
  }

  public int getLogistic() {
		return logistic;
  }

  public void setActors(int actors) {
		this.actors = actors;
  }

  public int getActors() {
		return actors;
  }

  public void setPosition(Case position) {
		this.position = position;
  }

  public Case getPosition() {
		return position;
  }

  public void addProperty(Case c) {
		properties.add(c);
  }

  public void removeProperty(Case c) {
		properties.remove(c);
  }

  public int shootOneDice() {
		return (int)(Math.random()*6)+1;
  }

}
