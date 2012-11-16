package com.cinemania.client;

public class LogisticFactory extends Building {
  private boolean fxExtension =  false;

  /**
   *  Décors
   */
  private boolean sceneryExtension =  false;

  private boolean costumeExtension =  false;

  private boolean hairdressingExtension =  false;

  public LogisticFactory(Case theCase) {
		super(theCase);
  }

  @Override
  public int totalValue() {
		return BASEVALUE_OF_LOGISTIC * getLevel() + nbExtensions() * PRICE_LOGISTIC_EXTENSION;
  }

  @Override
  public int profit() {
		return nbExtensions()+1;
  }

  private int nbExtensions() {
		int nb = 0;
		nb = fxExtension ? 1 : 0;
		nb += sceneryExtension ? 1 : 0;
		nb += costumeExtension ? 1 : 0;
		nb += hairdressingExtension ? 1 : 0;
		return nb;
  }

  public boolean hasFxExtension() {
		return fxExtension;
  }

  public void setFxExtension(boolean fxExtension) {
		this.fxExtension = fxExtension;
  }

  public boolean hasSceneryExtension() {
		return sceneryExtension;
  }

  public void setSceneryExtension(boolean sceneryExtension) {
		this.sceneryExtension = sceneryExtension;
  }

  public boolean hasHairdressingExtension() {
		return hairdressingExtension;
  }

  public void setHairdressingExtension(boolean hairdressingExtension) {
		this.hairdressingExtension = hairdressingExtension;
  }

}
