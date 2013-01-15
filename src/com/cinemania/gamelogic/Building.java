package com.cinemania.gamelogic;

import com.cinemania.cells.Cell;

import static com.cinemania.constants.AllConstants.*;

public abstract class Building {
  private int level =  DEFAULT_BUILDING_LEVEL;

  private Cell theCase;

  public Building(Cell theCase) {
		this.setTheCase(theCase);
  }

  public abstract int totalValue() ;

  /**
   * Increase the level and modify the value according to it
   * @param level
   */
  public void increaseLevel() throws IncreaseLevelImpossible {
		if(!isLevelCanBeIncrease())
			throw new IncreaseLevelImpossible("Level max already reached");
		this.level += 1;
  }

  public int getLevel() {
		return level;
  }

  public boolean isLevelCanBeIncrease() {
		return level < LEVEL_MAX_BUILDING;
  }

  public abstract int profit() ;

  public void setTheCase(Cell theCase) {
		this.theCase = theCase;
  }

  public Cell getTheCase() {
		return theCase;
  }

}
@SuppressWarnings("serial")
class IncreaseLevelImpossible extends Exception {
  public IncreaseLevelImpossible(String string) {
		super(string);
  }

  public int getLevelMax() {
		return LEVEL_MAX_BUILDING;
  }

}
