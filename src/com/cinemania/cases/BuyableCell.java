package com.cinemania.cases;

import org.andengine.opengl.texture.region.ITextureRegion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.gamelogic.Player;

public abstract class BuyableCell extends OwnableCell {

	private int baseValue;
	
	public BuyableCell(ITextureRegion texture, float posX, float posY) {
		super(texture, posX, posY);
	}

	/**
	 * Buy the case. The player become the owner and this method decrease the
	 * money of the player.
	 * 
	 * @param p
	 *            : the player
	 */
	public void buy(Player p) {
		p.looseMoney(totalValue());
		p.addProperty(this);
		this.setOwner(p);
	}
	
	@Override
	public void onTheCell(Player player) {
		if(!hasOwner()){
			askToBuy(player);
		}
		else if(getOwner().equals(player)){
			ownerOnCell();
		}
		else{
			strangerOnCell(player);
		}
	}
	
	public abstract void askToBuy(Player player);

	public abstract int totalValue();

	public int getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(int value) {
		baseValue = value;
	}
}
