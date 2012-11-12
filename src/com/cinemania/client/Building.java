
public abstract class Building implements Constantes {
  private int level =  DEFAULT_LEVEL;

  private Case theCase;

  public Building(Case theCase) {
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

  public void setTheCase(Case theCase) {
		this.theCase = theCase;
  }

  public Case getTheCase() {
		return theCase;
  }

}
@SuppressWarnings("serial")
class IncreaseLevelImpossible extends Exception implements Constantes {
  public IncreaseLevelImpossible(String string) {
		super(string);
  }

  public int getLevelMax() {
		return LEVEL_MAX_BUILDING;
  }

}
