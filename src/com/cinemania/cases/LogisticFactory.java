package com.cinemania.cases;

import com.cinemania.resources.ResourcesManager;

public class LogisticFactory extends Resource {

	private boolean sceneryExtension =  false;
	private boolean costumeExtension =  false;
	private boolean hairdressingExtension =  false;
	private boolean fxExtension =  false;

	public LogisticFactory() {
		super(ResourcesManager.getInstance().mCaseLogistics);
	}

	@Override
	public int totalValue() {
		return BASEVALUE_OF_LOGISTIC * getLevel() + nbExtensions() * PRICE_LOGISTIC_EXTENSION;
	}

	@Override
	public int profit(int startTurn, int stopTurn){
		return getLevel() + nbExtensions();
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
