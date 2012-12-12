package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.cinemania.activity.Base;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Profitable;
import com.cinemania.gamelogic.Room;
import com.cinemania.activity.R;
import com.cinemania.resources.ResourcesManager;

public class Cinema extends BuyableCell implements Profitable  {
	
	public static final int TYPE = 4;

	private Room[] mRooms = new Room[AllConstants.MAX_ROOMS];
	private int mPurchasedRooms = 0;

	public Cinema(Room[] rooms, float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseCinema, posX, posY);
		for (int i = 0; i < rooms.length; i++) {
			mRooms[i] = rooms[i];
		}
		setOnClickListener(this);
	}

	public Cinema(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseCinema, posX, posY);
		setOnClickListener(this);
	}
	
	@Override
	public void upgrade() {
		assert mPurchasedRooms < AllConstants.MAX_ROOMS;
		mRooms[mPurchasedRooms++] = new Room();
	}

	@Override
	public int getLevel() {
		return mPurchasedRooms;
	}

	@Override
	public boolean updateAvailable() {
		return mPurchasedRooms < AllConstants.MAX_ROOMS;
	}
	
	
	public Room[] getRooms() {
		return mRooms;
	}
	

	@Override
	public int totalValue() {
		return AllConstants.BASEVALUE_OF_CINEMA + AllConstants.PRICE_ROOM * mPurchasedRooms;
	}

	@Override
	public int profit(int startTurn, int stopTurn) {
		int profit = 0;
		for (int i = 0; i < mPurchasedRooms; i++) {
			profit += mRooms[i].profit(startTurn, stopTurn);
		}

		return profit - AllConstants.COSTS_PER_CINEMA;
	}
	
	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		
		
		
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());

		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.cinema, null);



		
		
		
//		dialogBuilder.setView(view);
		
//		TextView benefCinema = (TextView)view.findViewById(R.id.txtBeneficeCinema);
		
		
		
		
		
//		dialogBuilder.setPositiveButton("oui", new android.content.DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//
//		});
//		dialogBuilder.setNegativeButton("non", new android.content.DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//		
//		
//		dialogBuilder.setView(view);
//		
//		
//		
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
		
		jsonCell.put("type", Cinema.TYPE);
		
		JSONArray jsonRoom = new JSONArray();
		
		for(Room r : this.mRooms)
			if(r!=null)
				jsonRoom.put(r.toJson());
		
		jsonCell.put("rooms", jsonRoom);
		
		return jsonCell;
	}

	@Override
	public void askToBuy(Player player) {
		//TODO
	}

	@Override
	public void strangerOnCell(Player player) {
		// TODO montant correct
		player.payOpponent(getOwner(), AllConstants.COSTS_ON_HQ);
		showPayDialog(AllConstants.COSTS_ON_HQ, R.drawable.ic_cinema, R.string.title_cinema);
	}

	@Override
	public void ownerOnCell() {
		// TODO Auto-generated method stub
		
	}
	
}
