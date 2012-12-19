package com.cinemania.cases;

import org.andengine.opengl.texture.region.ITextureRegion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Profitable;
import com.cinemania.resources.ResourcesManager;
import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.constants.AllConstants;

public abstract class Resource extends BuyableCell implements Profitable {

	private int mLevel;
	private static final int LAYER_LEVEL = 1;
	
	public Resource(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseResource, posX, posY);
		this.setLevel(0);
	}
	
	public Resource(ITextureRegion texture, int level, float posX, float posY) {
		super(texture, posX, posY);
		this.setLevel(level);
	}
	
	private void setLevel(int level){
		this.mLevel = level;
		//TODO Ajouter le Sprite avec 1,2,3 �toiles.
	}
	
	@Override
	public void upgrade() {
		//TODO prix de l'upgrade
		assert mLevel < AllConstants.LEVEL_MAX_BUILDING;
		this.setLevel(this.getLevel()+1);
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
		super.buy(p);
	}
	
	@Override
	public int totalValue() {
		int baseValue = getBaseValue();
		if (this.hasOwner())
			return (int) ((double) baseValue * AllConstants.RATE_SALE);

		return baseValue;
	}
	
	public void showBuyDialog(final Player player, int titleIcon, int titre, int type){
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
		//TODO Vérifier le profit
		txtIncome.setText(Integer.toString(profit(0, 1)));
		txtTransaction.setText(hasOwner()?R.string.txt_rachat:R.string.txt_achat);
		txtLevel.setText(Integer.toString(getLevel()));
		
		dialogBuilder.setPositiveButton(R.string.btn_buy, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Resource.this.buy(player);
				dialog.dismiss();
			}
		});
		dialogBuilder.setNegativeButton(R.string.btn_close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
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
	
	@Override
	public void strangerOnCell(Player player) {
		askToBuy(player);
	}
	
	/**
	 * Return the resource according to the kind of building. The compute is the actual depend on
	 * the level, number of extensions, the start and stop turn.
	 */
	@Override
	public int profit(int startTurn, int stopTurn) {
		// TODO fixer le calcul du profit
		return 0;
	}
	
}
