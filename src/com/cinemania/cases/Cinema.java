package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import android.app.AlertDialog;
import android.util.Log;

import com.cinemania.activity.Base;
import com.cinemania.client.R;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Profitable;
import com.cinemania.gamelogic.Room;
import com.cinemania.resources.ResourcesManager;

public class Cinema extends BuyableCase implements Profitable, OnClickListener  {
	
	public static final int TYPE = 4;

	private Room[] mRooms = new Room[AllConstants.MAX_ROOMS];
	private int mPurchasedRooms = 0;

	public Cinema(Room[] rooms) {
		super(ResourcesManager.getInstance().mCaseCinema);
		for (int i = 0; i < rooms.length; i++) {
			mRooms[i] = rooms[i];
		}
		setOnClickListener(this);
	}

	public Cinema() {
		super(ResourcesManager.getInstance().mCaseCinema);
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
		dialogBuilder.setCancelable(true);
		dialogBuilder.setView(Base.getSharedInstance().getLayoutInflater().inflate(R.layout.cinema, null));
		/*.setPositiveButton("Connect", new OnClickListener() {
		@Override
		public void onClick(final DialogInterface pDialog, final int pWhich) {
		PongGameActivity.this.mServerIP = ipEditText.getText().toString();
		PongGameActivity.this.initClient();
		}
		})
		.setNegativeButton(android.R.string.cancel, new OnClickListener() {
		@Override
		public void onClick(final DialogInterface pDialog, final int pWhich) {
		PongGameActivity.this.finish();
		}
		})
		*/
		 
		Base.getSharedInstance().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				dialogBuilder.create().show();
			}
		});
	}
}
