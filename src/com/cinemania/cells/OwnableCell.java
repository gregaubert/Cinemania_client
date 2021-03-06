package com.cinemania.cells;

import org.andengine.opengl.texture.region.ITextureRegion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Player;
import com.cinemania.resources.ResourcesManager;

public abstract class OwnableCell extends Cell {
	
	private Player mOwner;

	
	public OwnableCell(ITextureRegion texture, float posX, float posY) {
		super(texture, posX, posY);
	}
	
	public abstract void resetLevel();
	
	public abstract void upgrade(int price);
	
	public abstract int getLevel();
	
	public abstract boolean updateAvailable();
	
	public abstract void ownerOnCell();
	
	public abstract void strangerOnCell(Player player);
	
	public void showPayDialog(int amount, int titleIcon, int titre){
		ResourcesManager.getInstance().mSndCowboy.play();
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(false);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.payopponent, null);
		dialogBuilder.setView(view);

		TextView txtOwner = (TextView) view.findViewById(R.id.txtOwner);
		TextView txtAmount = (TextView) view.findViewById(R.id.txtAmount);
		ImageView imgView = (ImageView) view.findViewById(R.id.imgTitle);
		TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
		txtOwner.setText(getOwner().getName());
		txtOwner.setTextColor(Base.getSharedInstance().getResources().getColor(getOwner().getColorAndroid()));
		txtAmount.setText(Integer.toString(amount));
		imgView.setImageResource(titleIcon);
		txtTitle.setText(titre);
		
		dialogBuilder.setNeutralButton(R.string.btn_pay, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ResourcesManager.getInstance().mSndCashMachine.stop();
				ResourcesManager.getInstance().mSndCashMachine.play();
				dialog.dismiss();
			}
		});
		Base.getSharedInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialogBuilder.create().show();
			}
		});
	}
	
	public boolean hasOwner() {
		return mOwner != null;
	}
	
	public void setOwner(Player owner) {
		mOwner = owner;
		setColor(owner.getColorCase());
	}
	
	public void resetOwner(){
		this.mOwner = null;
		this.setColor(AllConstants.BOARD_CASE_COLOR);
		this.resetLevel();
	}
	
	public Player getOwner() {
		return mOwner;
	}
}
