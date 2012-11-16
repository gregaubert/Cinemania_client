package com.cinemania.client;

public class Resource extends Case {
  
	public Resource() {
		super();
  }

  @Override
  public int totalValue() {
		int baseValue = getBaseValue();
		if(this.hasOwner())
			return (int)((double)baseValue*RATE_SALE);
		
		return baseValue;
  }

}
