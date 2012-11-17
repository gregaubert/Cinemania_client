package com.cinemania.gamelogic;

import com.cinemania.cases.Case;
import com.cinemania.cases.Cinema;
import com.cinemania.constants.Constantes;

public class Board implements Constantes {

	private Case[] mCases = new Case[Constantes.BOARD_SIZE];

	private int size = Constantes.BOARD_SIZE;
	
	private int currentHQ = 0;
	private int currentScript;
	private int currentCinema;
	private int currentResource;
	private int currentEmpty;
	private int currentLuck;
	private int currentTotLine;

	public Board() {
		populate();
	}

	private void populate() {
		for (int i = 0; i < size; i++) {
			mCases[i] = randomCase(i);
		}
	}
	
	private Case randomCase(int i){
		if(i % (size/4) == 0){
			currentScript = Constantes.BOARD_NB_SCRIPT_LINE;
			currentCinema = Constantes.BOARD_NB_CINEMA_LINE;
			currentResource = Constantes.BOARD_NB_RESOURCE_LINE;
			currentEmpty = Constantes.BOARD_NB_EMPTY_TOT;
			currentLuck = Constantes.BOARD_NB_LUCK_TOT;
			currentTotLine = Constantes.BOARD_NB_TOT_LINE;
			return new Cinema();
		}
		Math.round(Math.random() * 10 );

		return new Cinema();
	}
	
	
	public Case getCaseAtIndex(int index){
		try{
			return mCases[index];
		}
		catch (IndexOutOfBoundsException e){
			return null;
		}		
	}
	
	public Case[] getCases(){
		return mCases;
	}
}
