package com.cinemania.client;

import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.ressource.ResourcesManager;

public class Cinema extends Case {

private Room[] rooms =  new Room[MAX_ROOMS];

  int nbRoom =  0;

  public Cinema() {
	super(ResourcesManager.getInstance().mCaseCinema);	
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
