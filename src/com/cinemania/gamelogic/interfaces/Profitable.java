package com.cinemania.gamelogic.interfaces;

/**
 * Interface for all case which offer resources. 
 * @author joel
 *
 */
public interface Profitable {
	
	/**
	 * Return the resource according to the kind of building. The compute is the actual depend on
	 * the level, number of extensions, the start and stop turn.
	 * We need to know the start turn and the stop turn. When a player turn all over the boardgame, 
	 * he/she could get the profit of its cases
	 */
	public int profit(int startTurn, int stopTurn);
}
