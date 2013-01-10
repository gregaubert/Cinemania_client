package com.cinemania.gamelogic;

import static com.cinemania.constants.AllConstants.COSTS_PER_ROOM;

import org.json.JSONException;
import org.json.JSONObject;

import com.cinemania.gamelogic.interfaces.JSonator;
import com.cinemania.gamelogic.interfaces.Profitable;

public class Room implements Profitable, JSonator {

	  Movie movie;

	  private int turn =  0;
	  
	  public Room() {
		  
	  }

	  @Override
	  public int profit(int startTurn, int stopTurn) {
		  return movie.profit(startTurn, stopTurn) - COSTS_PER_ROOM;
	  }

	  public Movie getMovie() {
		  return movie;
	  }

	  public void setMovie(Movie movie) {
		  this.movie = movie;
	  }

	@Override
	public JSONObject toJson() throws JSONException {
		return new JSONObject();
	}
}

