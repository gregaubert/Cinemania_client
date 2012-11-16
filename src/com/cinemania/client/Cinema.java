package com.cinemania.client;

public class Cinema extends Building {
  private Room[] rooms =  new Room[MAX_ROOMS];

  int nbRoom =  0;

  public Cinema(Case theCase) {
		super(theCase);
  }

  @Override
  public int totalValue() {
		return BASEVALUE_OF_CINEMA + PRICE_ROOM * nbRoom;
  }

  @Override
  public int profit() {
		return 0;
  }

  public void buyRoom() {
		if(nbRoom < MAX_ROOMS)
			rooms[nbRoom++] = new Room(this);
  }

}
