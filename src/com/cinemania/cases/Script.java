package com.cinemania.cases;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.entity.sprite.ButtonSprite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.gamelogic.Player;
import com.cinemania.resources.ResourcesManager;

public class Script extends Case {
	
	public static final int TYPE = 2;

	public Script(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseScript, posX, posY);
		setOnClickListener(this);
	}

	public String getMovieStory() {
		return "";
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
	}

	@Override
	public void onTheCell(Player player) {
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.script, null);
		dialogBuilder.setView(view);
		dialogBuilder.setPositiveButton(R.string.btn_buy, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialogBuilder.setNegativeButton(R.string.btn_close, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		ListView scriptListView = (ListView) view.findViewById(R.id.scriptListView);
		ArrayList<HashMap<String, String>> scriptListItem = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", "Star Wars 18");
        map.put("year", "2033");
        map.put("price", "38757");
        map.put("id", "1");
        scriptListItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Picsou");
        map.put("year", "2003");
        map.put("price", "8757");
        map.put("id", "2");
        scriptListItem.add(map);
        map = new HashMap<String, String>();
        map.put("title", "Demain ne meurt jamais");
        map.put("year", "2000");
        map.put("price", "18757");
        map.put("id", "3");
        scriptListItem.add(map);
        
        SimpleAdapter mSchedule = new SimpleAdapter (Base.getSharedInstance().getBaseContext(), scriptListItem, R.layout.scriptitem,
                new String[] {"title", "price", "year"}, new int[] {R.id.scriptTitle, R.id.scriptPrice, R.id.scriptYear});
        scriptListView.setAdapter(mSchedule);
		 
		Base.getSharedInstance().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				dialogBuilder.create().show();
			}
		});
	}
}
