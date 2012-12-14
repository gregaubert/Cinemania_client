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
import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Script;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class ScriptCell extends Cell {
	
	public static final int TYPE = 2;

	public ScriptCell(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseScript, posX, posY);
		setOnClickListener(this);
	}

	public String getMovieStory() {
		return "";
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		//TODO désactiver le onclick ou changer l'action dessus, la c'est juste pour pouvoir tester
		onTheCell(GameContext.getSharedInstance().getCurrentPlayer());
	}

	@Override
	public void onTheCell(final Player player) {
		
		//TODO Récupérer des scripts aléatoirement d'un bd
		final Script script = new Script("Demain ne meurt jamais", 1589, 5569);
		script.setSummary("bla bla bla blabla bla bla blabla bla bla blabla bla bla blabla bla bla blabla bla bla bla bla bla bla blabla bla bla blabla bla bla blabla bla bla blbla bla blabla bla bla blabla bla bla blbla bla blabla bla bla blabla bla bla blbla bla blabla bla bla blabla bla bla blbla bla.");
		
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.script, null);
		dialogBuilder.setView(view);
		
		
		TextView scriptTitle = (TextView) view.findViewById(R.id.scriptTitle);
		TextView scriptSummary = (TextView) view.findViewById(R.id.scriptSummary);
		TextView scriptYear = (TextView) view.findViewById(R.id.scriptYear);
		TextView scriptPrice = (TextView) view.findViewById(R.id.scriptPrice);
		
		scriptTitle.setText(script.getTitle());
		scriptSummary.setText(script.getSummary());
		scriptYear.setText(Integer.toString(script.getYear()));
		scriptPrice.setText(Integer.toString(script.getPrice()));
		
		dialogBuilder.setPositiveButton(R.string.btn_buy, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				player.looseMoney(script.getPrice());
				player.addScript(script);
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
				dialogBuilder.create().show();
			}
		});
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", HeadQuarters.TYPE);		
		return jsonCell;
	}
}
