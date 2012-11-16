package com.cinemania.client;

public abstract class Case implements Constantes {
  private Player owner;

  Building building;

  private int baseValue;

  public Case(){}
  
  public Case(Player owner, int value) {
	  this();
	  this.setOwner(owner);
	  this.setBaseValue(value);		
  }

  /**
   * Buy the case. The player become the owner and this method decrease the amount
   * of the player.
   * @param p : the player
   */
  public void buy(Player p) {
	  p.addProperty(this);
	  this.setOwner(p);
	  p.setAmount(p.getAmount()-totalValue());
  }

  public void buyBuilding(Building b) {
	  this.building = b;
  }

  /**
   * Return the profit. Depending on the building or the case. By example
   * a logistic factory returns number of their own resource while a cinema returns
   * an amount of cash
   * @return
   */
  public int turnProfit() {
	  return building.profit();
  }

  public void setOwner(Player owner) {
	  this.owner = owner;
  }

  public Player getOwner() {
	  return owner;
  }

  public abstract int totalValue() ;

  public boolean hasOwner() {
	  return owner != null;
  }

  public int getBaseValue() {
	  return baseValue;
  }
  
  public void setBaseValue(int value){
	  this.baseValue = value;
  }

}
