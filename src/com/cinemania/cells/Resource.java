package com.cinemania.cells;

import org.andengine.opengl.texture.region.ITextureRegion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.interfaces.Profitable;
import com.cinemania.resources.ResourcesManager;
import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.constants.AllConstants;

public abstract class Resource extends BuyableCell implements Profitable {

	private int mLevel = AllConstants.DEFAULT_RESOURCES_LEVEL_BF_BUY;
	
	public Resource(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseResource, posX, posY);
		setBaseValue(AllConstants.BASEVALUE_OF_CINEMA);
	}
	
	public Resource(ITextureRegion texture, int level, float posX, float posY) {
		super(texture, posX, posY);
		setLevel(level);
	}
	
	protected void setLevel(int level){
		mLevel = level;
		for(int i = 0; i < mLevel; i++)
			addLevel(i+1);
	}
	
	protected void upgradeLevel(){
		assert getLevel() < AllConstants.LEVEL_MAX_BUILDING;
		assert getOwner() != null;
		addLevel(++mLevel);
	}

	@Override
	public int getLevel() {
		return mLevel;
	}
	
	@Override
	public boolean updateAvailable() {
		return mLevel < AllConstants.LEVEL_MAX_BUILDING;
	}

	@Override
	public void buy(Player p) {
		if(hasOwner()){
			getOwner().removeProperty(this);
			getOwner().receiveMoney(totalValue());
		}
		else{
			setLevel(AllConstants.DEFAULT_RESOURCES_LEVEL_AF_BUY);
		}
		super.buy(p);
	}
	
	public abstract int totalValue();
	
	public void showBuyDialog(final Player player, int titleIcon, int titre, int type, int income){
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.buyresource, null);
		dialogBuilder.setView(view);

		if(hasOwner()){
			TextView txtOwner = (TextView) view.findViewById(R.id.txtOwner);
			txtOwner.setText(getOwner().getName());
			txtOwner.setTextColor(Base.getSharedInstance().getResources().getColor(getOwner().getColorAndroid()));
		}

		ImageView imgView = (ImageView) view.findViewById(R.id.imgTitle);
		TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
		TextView txtIncome = (TextView) view.findViewById(R.id.txtIncome);
		TextView txtTransaction = (TextView) view.findViewById(R.id.txtTransaction);
		TextView txtType = (TextView) view.findViewById(R.id.txtType);
		TextView txtAmount = (TextView) view.findViewById(R.id.txtAmount);
		TextView txtLevel = (TextView) view.findViewById(R.id.txtLevel);
		
		imgView.setImageResource(titleIcon);
		txtTitle.setText(titre);
		txtAmount.setText(Integer.toString(totalValue()));
		txtType.setText(type);
		txtIncome.setText(Integer.toString(income));
		txtTransaction.setText(hasOwner()?R.string.txt_rachat:R.string.txt_achat);
		txtLevel.setText(Integer.toString(getLevel()));
		
		dialogBuilder.setPositiveButton(R.string.btn_buy, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ResourcesManager.getInstance().mSndCashMachine.stop();
				ResourcesManager.getInstance().mSndCashMachine.play();
				Resource.this.buy(player);
				dialog.dismiss();
			}
		});
		dialogBuilder.setNegativeButton(R.string.btn_close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		Base.getSharedInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AlertDialog dialog = dialogBuilder.create();
				dialog.show();
				if(player.getAmount() < totalValue())
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
			}
		});
	}
	
	public void showOwnerDialog(int titleIcon, int titre, int lastIncome, int priceExtension){
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.resource, null);
		dialogBuilder.setView(view);

		ImageView imgView = (ImageView) view.findViewById(R.id.imgResTitle);
		TextView txtTitle = (TextView) view.findViewById(R.id.txtResTitle);
		TextView txtIncome = (TextView)view.findViewById(R.id.txtIncome);
		TextView txtLevel = (TextView)view.findViewById(R.id.txtLevel);
		TextView txtLevelPrice = (TextView)view.findViewById(R.id.txtLevelPrice);

		imgView.setImageResource(titleIcon);
		txtTitle.setText(titre);
		txtIncome.setText(Integer.toString(lastIncome));
		txtLevel.setText(Integer.toString(getLevel()));
		txtLevelPrice.setText(Integer.toString(priceExtension));
		
		final int price = priceExtension;
		
		if(updateAvailable()){
			dialogBuilder.setPositiveButton(R.string.btn_level, new android.content.DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					ResourcesManager.getInstance().mSndCashMachine.stop();
					ResourcesManager.getInstance().mSndCashMachine.play();
					Resource.this.upgrade(price);
					dialog.dismiss();
				}
			});
		}
		else{
			view.findViewById(R.id.tableRowNextLevel).setVisibility(View.INVISIBLE);
		}
		
		dialogBuilder.setNegativeButton(R.string.btn_close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		Base.getSharedInstance().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				AlertDialog dialog = dialogBuilder.create();
				dialog.show();
				if(dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null && getOwner().getAmount() < AllConstants.PRICE_ROOM)
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
			}
		});
	}
	
	@Override
	public void strangerOnCell(Player player) {
		askToBuy(player);
	}	
}
