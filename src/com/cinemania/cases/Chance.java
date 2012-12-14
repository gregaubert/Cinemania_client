package com.cinemania.cases;

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
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		//TODO Virer cette merde, c'est juste pour le test
		onTheCell(GameContext.getSharedInstance().getCurrentPlayer());
	}

	@Override
	public void onTheCell(Player player) {
		
		ChanceCard chanceCard = ChanceCardManager.getSharedInstance().pickACard();
		
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(false);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.chance, null);
		dialogBuilder.setView(view);
		
		
		TextView chanceTitle = (TextView) view.findViewById(R.id.chanceTitle);
		TextView chanceText = (TextView) view.findViewById(R.id.chanceText);
		TextView chancePrice = (TextView) view.findViewById(R.id.chancePrice);
		
//		chanceTitle.setText(script.getTitle());
//		chanceText.setText(script.getSummary());
//		chancePrice.setText(Integer.toString(script.getYear()));
//		
//		dialogBuilder.setPositiveButton(R.string.btn_buy, new OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				player.looseMoney(script.getPrice());
//				player.addScript(script);
//				dialog.dismiss();
//			}
//		});
//		dialogBuilder.setNegativeButton(R.string.btn_close, new OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//		 
//		Base.getSharedInstance().runOnUiThread(new Runnable() {
//			
//			@Override
//			public void run() {
//				dialogBuilder.create().show();
//			}
//		});
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", Chance.TYPE);
		
		return jsonCell;
	}
}
