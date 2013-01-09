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
import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Script;
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
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,	float pTouchAreaLocalY) {
	}

	@Override
	public void onTheCell(final Player player) {
		
		ResourcesManager.getInstance().mSndTaDa.play();
		
		final Script script = Script.pickAScript();
		
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		final View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.script, null);
		dialogBuilder.setView(view);
		
		
		final TextView scriptTitle = (TextView) view.findViewById(R.id.scriptTitle);
		final TextView scriptSummary = (TextView) view.findViewById(R.id.scriptSummary);
		final TextView scriptYear = (TextView) view.findViewById(R.id.scriptYear);
		final TextView scriptPrice = (TextView) view.findViewById(R.id.scriptPrice);
		final TextView scriptRealisation = (TextView) view.findViewById(R.id.scriptRealisation);
		
		scriptTitle.setText(script.getTitle());
		scriptSummary.setText(script.getSummary());
		scriptYear.setText(Integer.toString(script.getYear()));
		scriptPrice.setText(Integer.toString(script.getPrice()));
		scriptRealisation.setText(script.getActors() + (script.getActors() <= 1 ?" acteur, ":" acteurs, ") 
				+ script.getLogistics() + (script.getLogistics() <= 1 ?" logistique et ":" logistiques et ")
				+ script.getPriceProd() + ".-");
		
		dialogBuilder.setPositiveButton(R.string.btn_buy, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				ResourcesManager.getInstance().mSndCashMachine.stop();
				ResourcesManager.getInstance().mSndCashMachine.play();
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
				AlertDialog dialog = dialogBuilder.create();
				dialog.show();
				if(player.getAmount() < script.getPrice())
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
			}
		});
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject jsonCell = new JSONObject();
		
		jsonCell.put("type", ScriptCell.TYPE);		
		return jsonCell;
	}
}
