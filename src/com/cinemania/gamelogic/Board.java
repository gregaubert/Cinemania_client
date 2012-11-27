package com.cinemania.gamelogic;
import static com.cinemania.constants.AllConstants.*;

import java.util.Random;

import com.cinemania.cases.Case;
import com.cinemania.cases.Cinema;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.LuckyCase;
import com.cinemania.cases.School;
import com.cinemania.cases.LogisticFactory;
import com.cinemania.cases.Script;
import com.cinemania.resources.ResourcesManager;

public class Board {

	private Case[] mCases;
	
	public Board(Case[] cases) {
		mCases = cases;
	}
	

	public static int[] generate() {

		int[] identifiers = new int[BOARD_SIZE];
		int scriptCount = 0;
		int cinemaCount = 0;
		int actorsCount = 0;
		int logisticsCount = 0;
		int currentLineOffset = 0;
		
		for (int i = 0; i < identifiers.length; i++) {
			if (i % (identifiers.length/4) == 0){
				scriptCount = BOARD_NB_SCRIPT_LINE;
				cinemaCount = BOARD_NB_CINEMA_LINE;
				actorsCount = BOARD_NB_ACTOR_LINE;
				logisticsCount = BOARD_NB_LOGISTIC_LINE;
				currentLineOffset = BOARD_NB_TOT_LINE;
				identifiers[i] = HeadQuarters.TYPE;
			} else {
			
				int random = Math.round((float)Math.random() * (actorsCount + logisticsCount  + cinemaCount + currentLineOffset + scriptCount - 1));
				
				if (random < actorsCount){
					actorsCount--;
					identifiers[i] = School.TYPE;
				} else if (random < actorsCount + logisticsCount) {
					logisticsCount--;
					identifiers[i] = LogisticFactory.TYPE;
				} else if(random < actorsCount + logisticsCount + cinemaCount) {
					cinemaCount--;
					identifiers[i] = Cinema.TYPE;
				} else if(random < actorsCount + logisticsCount + cinemaCount + scriptCount) {
					scriptCount--;
					identifiers[i] = Script.TYPE;
				} else {
					currentLineOffset--;
					identifiers[i] = LuckyCase.TYPE;
					/*
					random = Math.round((float)Math.random() * (currentEmpty  + currentLuck - 1));
					if (random < currentEmpty){
						currentEmpty--;
						return new Case(ResourcesManager.getInstance().mCaseEmpty);
					} else{
						currentLuck--;
						return new LuckyCase();
					}
					*/
				}
			}
		}
		
		return identifiers;
	}
	
	public Case getCaseAtIndex(int index){
		try{
			return mCases[index];
		}
		catch (IndexOutOfBoundsException e){
			return null;
		}		
	}
	
	public Case[] getCases() {
		return mCases;
	}
	
	public int getSize(){
		return mCases.length;
	}

	public int findCaseIndex(Case toFind) {
		
		for (int i = 0; i < mCases.length; i++){
			if (mCases[i] == toFind)
				return i;
		}
		
		return -1;
	}
	
	private int shootOneDice() {
		return (int)(Math.random()*6)+1;
	}
	
	public int rollDice(){
		return shootOneDice() + shootOneDice();
	}
	
	public Case nextCellOf(Case source) {
		int offset = findCaseIndex(source);
		offset = (offset + 1) % mCases.length;
		return mCases[offset];
	}
}
