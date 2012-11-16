package com.cinemania.client;

public class Cinema extends Case {

private Room[] rooms =  new Room[MAX_ROOMS];

  int nbRoom =  0;

  public Cinema(Player owner, int value) {
		super(owner, value);
	}

  @Override
  public int totalValue() {
		return BASEVALUE_OF_CINEMA + PRICE_ROOM * nbRoom;
  }

  // TODO: maybe accordint to a int turn
  public int turnProfit() {
		return 0;//TODO: compute the profit
  }

  public void buyRoom() {
		if(nbRoom < MAX_ROOMS)
			rooms[nbRoom++] = new Room(this);
  }

}
