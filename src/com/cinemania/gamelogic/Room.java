package com.cinemania.gamelogic;

import com.cinemania.cases.Cinema;
import static com.cinemania.constants.AllConstants.*;

public class Room implements Profitable {
	  Cinema cinema;

	  Movie movie;

	  private int turn =  0;

	  public Room(Cinema cinema) {
			this.cinema = cinema;
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

	}

