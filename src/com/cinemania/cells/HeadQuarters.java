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
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.BigMovie;
import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Script;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class HeadQuarters extends OwnableCell {
	
	public static final int TYPE = 1;

	private int mLevel;
	
	
	public HeadQuarters(int level, float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseHQ, posX, posY);
		setLevel(level);
		setOnClickListener(this);
	}
	
	@Override
	public void upgrade() {
		assert mLevel < AllConstants.LEVEL_MAX_BUILDING;
		mLevel++;
		addLevel(getLevel());
	}
	
	@Override
	public int getLevel() {
		return mLevel;
	}
	
	protected void setLevel(int level){
		mLevel = level;
		addLevel(mLevel);
	}

	@Override
	public boolean updateAvailable() {
		return mLevel < AllConstants.LEVEL_MAX_BUILDING;
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", HeadQuarters.TYPE);
		jsonCell.put("level", this.getLevel());
		
		return jsonCell;
	}
	
	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		if(GameContext.getSharedInstance().getPlayer().equals(getOwner())){
			
			final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
			dialogBuilder.setCancelable(true);
			View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.hq, null);
			dialogBuilder.setView(view);
			
			Script fetchedScript = null;
			if(getOwner().getScriptCount() > 0)
				fetchedScript = getOwner().getScripts().get(0);
			
			final Script script = fetchedScript;
			
			if(script != null){
				
				TextView hqScriptTitle = (TextView) view.findViewById(R.id.scriptTitle);
				TextView hqScriptYear = (TextView) view.findViewById(R.id.scriptYear);
				TextView hqScriptPriceProd = (TextView) view.findViewById(R.id.scriptPriceProd);
				TextView hqScriptReaActors = (TextView) view.findViewById(R.id.scriptReaActors);
				TextView hqScriptReaLogistics = (TextView) view.findViewById(R.id.scriptReaLogistics);
				TextView hqScriptReaPrice = (TextView) view.findViewById(R.id.scriptReaPrice);
				
				hqScriptTitle.setText(script.getTitle());
				hqScriptYear.setText(Integer.toString(script.getYear()));
				hqScriptPriceProd.setText(Integer.toString(script.getPriceProd()));
				hqScriptReaActors.setText(script.getActors() + (script.getActors() <= 1 ?" acteur, ":" acteurs"));
				hqScriptReaLogistics.setText(script.getLogistics() + (script.getLogistics() <= 1 ?" logistique":" logistiques"));
				hqScriptReaPrice.setText(script.getPriceProd() + ".-");

				if(getOwner().getActors() < script.getActors())
					hqScriptReaActors.setTextColor(Base.getSharedInstance().getResources().getColor(R.color.red));
				if(getOwner().getLogistic() < script.getLogistics())
					hqScriptReaLogistics.setTextColor(Base.getSharedInstance().getResources().getColor(R.color.red));
				if(getOwner().getAmount() < script.getPriceProd())
					hqScriptReaPrice.setTextColor(Base.getSharedInstance().getResources().getColor(R.color.red));
				
				dialogBuilder.setPositiveButton(R.string.btn_prod, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(getOwner().removeScript(script)){
							// TODO Marketing
							BigMovie movie = new BigMovie(script.getTitle(), script.getLogistics(), script.getActors(), script.getPriceProd(), GameContext.getSharedInstance().getYear(), 10);
							movie.produceThisMovie(getOwner(), 5);
							getOwner().addMovie(movie);
						}
						dialog.dismiss();
					}
				});	
			}
			else{
				TextView txtResTitle = (TextView) view.findViewById(R.id.txtHQProd);
				txtResTitle.setText(R.string.txt_no_script_available);
				view.findViewById(R.id.layoutScriptView).setVisibility(View.GONE);
			}
			
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
					if(dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null 
							&& script != null
							&& (getOwner().getAmount() < script.getPriceProd()
								|| getOwner().getActors() < script.getActors()
								|| getOwner().getLogistic() < script.getLogistics()))
						dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				}
			});
		}
	}
	
	@Override
	public void onTheCell(Player player) {
		assert hasOwner();
		if(getOwner().equals(player))
			ownerOnCell();
		else
			strangerOnCell(player);
	}

	@Override
	public void ownerOnCell() {
		// Encaisse le double si on tombe pile dessus
		getOwner().encaisser();
	}

	@Override
	public void strangerOnCell(Player player) {
		showPayDialog(AllConstants.COSTS_ON_HQ, R.drawable.ic_hq, R.string.title_hq);
		player.payOpponent(getOwner(), AllConstants.COSTS_ON_HQ);
	}
}
