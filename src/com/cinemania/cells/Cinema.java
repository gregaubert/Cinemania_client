package com.cinemania.cells;

import java.util.ArrayList;

import org.andengine.entity.sprite.ButtonSprite;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.TextView;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Movie;
import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Room;
import com.cinemania.gamelogic.interfaces.Profitable;
import com.cinemania.network.GameContext;
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
		assert getOwner() != null;
		getOwner().looseMoney(AllConstants.PRICE_ROOM);
		mRooms[mPurchasedRooms++] = new Room();
		addLevel(getLevel());
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
		if(GameContext.getSharedInstance().getPlayer().equals(this.getOwner())){
			ownerOnCell();
		}
	}

	@Override
	public void askToBuy(final Player player) {
		ResourcesManager.getInstance().mSndProjector.play();
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.buycinema, null);
		dialogBuilder.setView(view);

		TextView txtCost = (TextView) view.findViewById(R.id.txtCost);
		TextView txtRooms = (TextView) view.findViewById(R.id.txtRooms);
		TextView txtAmount = (TextView) view.findViewById(R.id.txtAmount);
		
		//TODO verifier si c'est bien ce qu'il faut afficher
		txtAmount.setText(Integer.toString(totalValue()));
		txtCost.setText(Integer.toString(AllConstants.COSTS_PER_CINEMA));
		txtRooms.setText(Integer.toString(getLevel()));
		
		dialogBuilder.setPositiveButton(R.string.btn_buy, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Cinema.this.buy(player);
				ResourcesManager.getInstance().mSndProjector.stop();
				ResourcesManager.getInstance().mSndCashMachine.stop();
				ResourcesManager.getInstance().mSndCashMachine.play();
				dialog.dismiss();
			}
		});
		
		dialogBuilder.setNegativeButton(R.string.btn_close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialogBuilder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				ResourcesManager.getInstance().mSndProjector.stop();
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
		Player owner = getOwner();
		ArrayList<Movie> movies = owner.getMovies();
		int montant = 0;
		for(Movie m : movies){
			 montant += m.sellingPrice();
		}
		montant /= owner.getNbCinema();
			
		player.payOpponent(getOwner(), montant);
		showPayDialog(montant, R.drawable.ic_cinema, R.string.title_cinema);
	}

	@Override
	public void ownerOnCell() {
		
		ResourcesManager.getInstance().mSndProjector.play();
		
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.cinema, null);
		dialogBuilder.setView(view);
		TextView nbFilmProduced = (TextView)view.findViewById(R.id.txtnbFilms);
		TextView benefices = (TextView)view.findViewById(R.id.txtBenefices);
		TextView level = (TextView)view.findViewById(R.id.txtLevelActuel);
		TextView levelPrice = (TextView)view.findViewById(R.id.txtLevelPrice);
		
		nbFilmProduced.setText(Integer.toString(getOwner().getMovies().size()));
		benefices.setText(Integer.toString(getOwner().getLastProfit()));
		level.setText(Integer.toString(getLevel()));
		levelPrice.setText(Integer.toString(AllConstants.PRICE_ROOM));
		
		if(updateAvailable()){
			dialogBuilder.setPositiveButton(R.string.btn_level, new android.content.DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Cinema.this.upgrade();
					ResourcesManager.getInstance().mSndProjector.stop();
					ResourcesManager.getInstance().mSndCashMachine.stop();
					ResourcesManager.getInstance().mSndCashMachine.play();
					dialog.dismiss();
				}
			});
		}
		else{
			view.findViewById(R.id.tableRowNextLevel).setVisibility(View.GONE);
		}
		
		dialogBuilder.setNegativeButton(R.string.btn_close, new android.content.DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});		
		 
		dialogBuilder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				ResourcesManager.getInstance().mSndProjector.stop();
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
