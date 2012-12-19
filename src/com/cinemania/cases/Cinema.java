package com.cinemania.cases;

import java.util.ArrayList;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.cinemania.activity.Base;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Profitable;
import com.cinemania.gamelogic.Room;
import com.cinemania.activity.R;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;
import com.cinemania.gamelogic.Movie;

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
		// TODO: si cette case nous appartient
		
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.cinema, null);
		
		TextView nbFilmPruiduits = (TextView)view.findViewById(R.id.txtnbFilms);
		TextView benefices = (TextView)view.findViewById(R.id.txtBenefices);
		TextView level = (TextView)view.findViewById(R.id.txtLevelActuel);
		Button btnLevel = (Button)view.findViewById(R.id.btnLevel);
		
		nbFilmPruiduits.setText("21");
		benefices.setText("5432");
		level.setText(getLevel());
		btnLevel.setEnabled(updateAvailable());

		dialogBuilder.setView(view);
		
		
		dialogBuilder.setPositiveButton("Fermer", new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
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
	public void askToBuy(final Player player) {
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.buycinema, null);
		dialogBuilder.setView(view);

		TextView txtCost = (TextView) view.findViewById(R.id.txtCost);
		TextView txtRooms = (TextView) view.findViewById(R.id.txtRooms);
		TextView txtAmount = (TextView) view.findViewById(R.id.txtAmount);
		
		txtAmount.setText(Integer.toString(totalValue()));
		txtCost.setText(Integer.toString(AllConstants.COSTS_PER_CINEMA));
		txtRooms.setText(Integer.toString(getLevel()));
		
		dialogBuilder.setPositiveButton(R.string.btn_buy, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Cinema.this.buy(player);
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
		Player proprietaire = getOwner();
		ArrayList<Movie> movies = proprietaire.getMovies(); // TODO recuperer movies du player
		int montant = 0;
		for(Movie m : movies){
			 montant += m.sellingPrice();
		}
		montant /= proprietaire.getNbCinema();
		
		player.payOpponent(getOwner(), montant);
		showPayDialog(montant, R.drawable.ic_cinema, R.string.title_cinema);
	}

	@Override
	public void ownerOnCell() {
		// TODO Auto-generated method stub
		
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
}
