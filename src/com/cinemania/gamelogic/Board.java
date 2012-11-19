package com.cinemania.gamelogic;

import com.cinemania.cases.Case;
import com.cinemania.cases.Cinema;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.LuckyCase;
import com.cinemania.cases.School;
import com.cinemania.cases.LogisticFactory;
import com.cinemania.cases.Script;
import com.cinemania.constants.Constants;
import com.cinemania.resources.ResourcesManager;

public class Board implements Constants {

	private Case[] mCases = new Case[Constants.BOARD_SIZE];

	private int size = Constants.BOARD_SIZE;
	
	private int currentHQ = 0;
	private int currentScript;
	private int currentCinema;
	private int currentActor;
	private int currentLogistic;
	private int currentEmpty = Constants.BOARD_NB_EMPTY_TOT;
	private int currentLuck = Constants.BOARD_NB_LUCK_TOT;
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
			currentScript = Constants.BOARD_NB_SCRIPT_LINE;
			currentCinema = Constants.BOARD_NB_CINEMA_LINE;
			currentActor = Constants.BOARD_NB_ACTOR_LINE;
			currentLogistic = Constants.BOARD_NB_LOGISTIC_LINE;
			currentTotLine = Constants.BOARD_NB_TOT_LINE;
			return new HeadQuarters(currentHQ++);
		}
		
		int random = Math.round((float)Math.random() * (currentActor + currentLogistic  + currentCinema + currentTotLine + currentScript - 1));
		
		if(random < currentActor){
			currentActor--;
			return new School();
		}
		else if (random < currentActor + currentLogistic){
			currentLogistic--;
			return new LogisticFactory();
		}
		else if(random < currentActor + currentLogistic + currentCinema){
			currentCinema--;
			return new Cinema();
		}
		else if(random < currentActor + currentLogistic + currentCinema + currentScript){
			currentScript--;
			return new Script();
		}
		else {
			currentTotLine--;
			random = Math.round((float)Math.random() * (currentEmpty  + currentLuck - 1));
			if(random < currentEmpty){
				currentEmpty--;
				return new Case(ResourcesManager.getInstance().mCaseEmpty);
			}
			else{
				currentLuck--;
				return new LuckyCase();
			}
		}
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
