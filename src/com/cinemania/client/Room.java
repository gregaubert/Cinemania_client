package com.cinemania.client;

public class Room implements Profitable, Constantes{
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
