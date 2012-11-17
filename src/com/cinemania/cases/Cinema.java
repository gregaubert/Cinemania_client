package com.cinemania.cases;

import org.andengine.opengl.texture.region.ITextureRegion;

import com.cinemania.gamelogic.Profitable;
import com.cinemania.gamelogic.Room;
import com.cinemania.resources.ResourcesManager;

public class Cinema extends Case implements Profitable {

	private Room[] rooms =  new Room[MAX_ROOMS];

	  int nbRoom =  0;

	  public Cinema() {
		super(ResourcesManager.getInstance().mCaseCinema);	
	  }

	  @Override
	  public int totalValue() {
			return BASEVALUE_OF_CINEMA + PRICE_ROOM * nbRoom;
	  }


	  public void buyRoom() {
			if(nbRoom < MAX_ROOMS)
				rooms[nbRoom++] = new Room(this);
	  }

	  @Override
	  public int profit(int startTurn, int stopTurn) {
		  int profit = 0;
		  for(int i = 0; i < nbRoom; i++){
			  profit += rooms [i].profit(startTurn, stopTurn);			  
		  }
		  
		  

		  return profit - COSTS_PER_CINEMA;
	  }
	  
	  public Room[] getRooms(){
		  return rooms;
	  }
	  
	  public int getNbRoom(){
		  return nbRoom;
	  }
	    
	}
