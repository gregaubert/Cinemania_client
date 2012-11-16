package com.cinemania.client;
public abstract class Case implements Constantes {
	
	private Player owner;

	private int baseValue;

	public Case(Player owner, int value) {
		this.owner = owner;
		this.baseValue = value;
	}

	/**
	 * Buy the case. The player become the owner and this method decrease the amount
	 * of the player.
	 * @param p : the player
	 */
	public void buy(Player p) {
		p.addProperty(this);
		p.setAmount(p.getAmount()-totalValue());
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public abstract int totalValue();
	

	public boolean hasOwner() {
		return owner != null;
	}

	public int getBaseValue() {
		return baseValue;
	}

}
