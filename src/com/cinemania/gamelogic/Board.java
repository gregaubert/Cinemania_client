package com.cinemania.gamelogic;

import com.cinemania.cases.Case;
import com.cinemania.cases.Cinema;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.LuckyCase;
import com.cinemania.cases.Resource;
import com.cinemania.cases.Script;
import com.cinemania.constants.Constantes;
import com.cinemania.resources.ResourcesManager;

public class Board implements Constantes {

	private Case[] mCases = new Case[Constantes.BOARD_SIZE];

	private int size = Constantes.BOARD_SIZE;
	
	private int currentHQ = 0;
	private int currentScript;
	private int currentCinema;
	private int currentResource;
	private int currentEmpty = Constantes.BOARD_NB_EMPTY_TOT;
	private int currentLuck = Constantes.BOARD_NB_LUCK_TOT;
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
			currentTotLine = Constantes.BOARD_NB_TOT_LINE;
			return new HeadQuarters(currentHQ++);
		}
		
		int random = Math.round((float)Math.random() * (currentResource  + currentCinema + currentTotLine + currentScript - 1));
		
		if(random < currentResource){
			currentResource--;
			return new Resource();
		}
		else if(random < currentResource + currentCinema){
			currentCinema--;
			return new Cinema();
		}
		else if(random < currentResource + currentCinema + currentScript){
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
