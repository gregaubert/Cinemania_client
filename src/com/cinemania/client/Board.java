package com.cinemania.client;

public class Board implements Constantes {

	private Case[] mCases = new Case[Constantes.BOARD_SIZE];

	private int size = Constantes.BOARD_SIZE;

	public Board() {
		populate();
	}

	private void populate() {
		for (int i = 0; i < size; i++) {
			mCases[i] = new Cinema();
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
