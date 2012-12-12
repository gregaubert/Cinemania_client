package com.cinemania.gamelogic;

import org.json.JSONException;
import org.json.JSONObject;

import com.cinemania.activity.R;
import com.cinemania.cases.Cinema;
import static com.cinemania.constants.AllConstants.*;

public class Room implements Profitable, JSonator {
	  Cinema cinema;

	  Movie movie;

	  private int turn =  0;
	  
	  public Room() {
		  
	  }

	  // FIXME: � supprimer, une salle n'a pas besoin d'avoir une r�f�rence de son cin�ma
	  /*
	  public Room(Cinema cinema) {
			this.cinema = cinema;
	  }
	  */

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
		// TODO Auto-generated method stub
		return null;
	}
}

