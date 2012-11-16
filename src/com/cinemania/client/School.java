
public class School extends Building {
  private boolean danceExtension =  false;

  private boolean theatreExtension =  false;

  private boolean musicExtension =  false;

  private boolean makeupExtension =  false;

  private boolean disguiseExtension =  false;

  public School(Case theCase) {
		super(theCase);
  }

  @Override
  public int totalValue() {
		
		return BASEVALUE_OF_SCHOOL * getLevel() + nbExtensions() * PRICE_SCHOOL_EXTENSION;
  }

  @Override
  public int profit() {
		return nbExtensions()+1;
  }

  private int nbExtensions() {
		int nb = 0;
		nb = danceExtension ? 1 : 0;
		nb += theatreExtension ? 1 : 0;
		nb += musicExtension ? 1 : 0;
		nb += makeupExtension ? 1 : 0;
		nb += disguiseExtension ? 1 : 0;
		return nb;
  }

  public boolean hasDanceExtension() {
		return danceExtension;
  }

  public void setDanceExtension(boolean danceExtension) {
		this.danceExtension = danceExtension;
  }

  public boolean hasTheatreExtension() {
		return theatreExtension;
  }

  public void setTheatreExtension(boolean theatreExtension) {
		this.theatreExtension = theatreExtension;
  }

  public boolean hasMusicExtension() {
		return musicExtension;
  }

  public void setMusicExtension(boolean musicExtension) {
		this.musicExtension = musicExtension;
  }

  public boolean hasMakeupExtension() {
		return makeupExtension;
  }

  public void setMakeupExtension(boolean makeupExtension) {
		this.makeupExtension = makeupExtension;
  }

  public boolean hasDisguiseExtension() {
		return disguiseExtension;
  }

  public void setDisguiseExtension(boolean disguiseExtension) {
		this.disguiseExtension = disguiseExtension;
  }

}
