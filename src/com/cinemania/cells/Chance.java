package com.cinemania.cells;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.TextView;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.gamelogic.ChanceCard;
import com.cinemania.gamelogic.ChanceCardManager;
import com.cinemania.gamelogic.Player;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class Chance extends Cell {
	
	public static final int TYPE = 3;

	public Chance(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseLuck, posX, posY);
		setOnClickListener(this);
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		//TODO Pas d'action sur le onclick, virer cette ligne
		onTheCell(GameContext.getSharedInstance().getCurrentPlayer());
	}

	@Override
	public void onTheCell(final Player player) {
		
		ResourcesManager.getInstance().mSndShufflingCards.play();
		
		final ChanceCard chanceCard = ChanceCardManager.getSharedInstance().pickACard();
		
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(false);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.chance, null);
		dialogBuilder.setView(view);
		
		
		TextView chanceTitle = (TextView) view.findViewById(R.id.chanceTitle);
		TextView chanceText = (TextView) view.findViewById(R.id.chanceText);
		TextView chancePrice = (TextView) view.findViewById(R.id.chancePrice);
		
		chanceTitle.setText(chanceCard.getTitle());
		chanceText.setText(chanceCard.getText());
		chancePrice.setText(Integer.toString(chanceCard.getAmount()));
		
		dialogBuilder.setNeutralButton(R.string.btn_close, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ResourcesManager.getInstance().mSndCashMachine.stop();
				ResourcesManager.getInstance().mSndCashMachine.play();
				player.receiveMoney(chanceCard.getAmount());
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

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", Chance.TYPE);
		
		return jsonCell;
	}
}
