package com.cinemania.camera;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.gamelogic.Player;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;
import com.cinemania.scenes.Loader;

public class BoardHUD extends HUD implements Loader {

	private ResourcesManager mResourcesManager;
	private Base mActivity;
	
	private Text txtYear;
	private Text txtActors; 
	private Text txtMoney;
	private Text txtLogistics;
	private Text txtScripts;
	private Text txtCurrentPlayer;

	private Sprite mBackground;
	
	private ButtonSprite mDiceSprite;
	
	private ButtonSprite mNextSprite;
	private Sprite mWaiting;
	
	private GameContext mGameContext;
	
	public BoardHUD() {
		mResourcesManager = ResourcesManager.getInstance();
		mActivity = Base.getSharedInstance();
		setVisible(false);
	}
	
	public void setYear(int year){
		txtYear.setText("Annee: " + Integer.toString(year));
	}
	
	public void setActors(int actors){
		txtActors.setText(Integer.toString(actors));
	}
	
	public void setMoney(int money){
		txtMoney.setText(Integer.toString(money));
	}
	
	public void setLogistics(int logistics){
		txtLogistics.setText(Integer.toString(logistics));
	}
	
	public void setScripts(int scripts){
		txtScripts.setText(Integer.toString(scripts));
	}

	public void setCurrentPlayer(Player p){		
		
		if(txtCurrentPlayer==null){
			txtCurrentPlayer = new Text(600, 8, mResourcesManager.mResourcesFontTab[p.getOrder()], "abcdefghijklmnopqrstuvwxyz0123456789", mActivity.getVertexBufferObjectManager());
			this.attachChild(txtCurrentPlayer);
		}
		
		txtCurrentPlayer.setText("Id : " + p.getName());
	}
	
	public void hideWaitingForPlayers(){
		mDiceSprite.setVisible(true);
		mNextSprite.setVisible(false);
		mWaiting.setVisible(false);
	}
	
	public void showWaitingForPlayer(){
		mDiceSprite.setVisible(false);
		mNextSprite.setVisible(false);
		mWaiting.setVisible(true);
	}
	
	public void update(){
		//Si c'est à nous de jouer.
		if(mGameContext.getCurrentPlayer() == mGameContext.getPlayer())
			hideWaitingForPlayers();
		else
			showWaitingForPlayer();
		
		setYear(mGameContext.getYear());
		setMoney(mGameContext.getPlayer().getAmount());
		setActors(mGameContext.getPlayer().getActors());
		setLogistics(mGameContext.getPlayer().getLogistic());
		setScripts(mGameContext.getPlayer().getScriptCount());
		setCurrentPlayer(mGameContext.getPlayer());
	}
	
	@Override
	public void Load() {
		
		//On efface tout avant de load.
		this.detachChildren();
		
		mGameContext = GameContext.getSharedInstance();
		
		txtYear 		= new Text(10, 8, mResourcesManager.mYearFont, "Annee: 0123456789", mActivity.getVertexBufferObjectManager());
		txtMoney 		= new Text(205, 8, mResourcesManager.mResourcesFont, "-0123456789", mActivity.getVertexBufferObjectManager());
		txtActors 		= new Text(335, 8, mResourcesManager.mResourcesFont, "-0123456789", mActivity.getVertexBufferObjectManager());
		txtLogistics 	= new Text(435, 8, mResourcesManager.mResourcesFont, "-0123456789", mActivity.getVertexBufferObjectManager());
		txtScripts  	= new Text(535, 8, mResourcesManager.mResourcesFont, "-0123456789", mActivity.getVertexBufferObjectManager());
		
		txtCurrentPlayer = null;
		
		Sprite moneySprite =  new Sprite(170, 5, 32, 32, mResourcesManager.mMoneyLogo, mActivity.getVertexBufferObjectManager());
		Sprite actorsSprite = new Sprite(300, 5, 32, 32, mResourcesManager.mActorsLogo, mActivity.getVertexBufferObjectManager());
		Sprite logisticsSprite = new Sprite(400, 5, 32, 32, mResourcesManager.mLogisticsLogo, mActivity.getVertexBufferObjectManager());
		Sprite scriptsSprite = new Sprite(500, 5, 32, 32, mResourcesManager.mScriptsLogo, mActivity.getVertexBufferObjectManager());
		
		mBackground = new Sprite(0,0,Base.CAMERA_WIDTH, 40,mResourcesManager.mBackgroundHUD, mActivity.getVertexBufferObjectManager());
		//mBackground.setColor(Color.WHITE);
		mBackground.setAlpha(0.8f);
		
		mDiceSprite = new ButtonSprite(10, Base.CAMERA_HEIGHT - 74, mResourcesManager.mDice, mActivity.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction())
				{

	                case TouchEvent.ACTION_DOWN: {
	                	
	                	ResourcesManager.getInstance().mSndDiceWood.play();
	                	setVisible(false);
	                	mNextSprite.setVisible(true);
	                	mActivity.getGame().movePlayer();

	            		//L'action a été gérée.
	            		return true;
	                }
                }
                return false;
		    }
		};
		mNextSprite = new ButtonSprite(10, Base.CAMERA_HEIGHT - 120, mResourcesManager.mNextTurn, mActivity.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction())
				{

	                case TouchEvent.ACTION_DOWN: {
	                	
	                	final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
	            		dialogBuilder.setCancelable(true);
	            		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.confirmturn, null);
	            		dialogBuilder.setView(view);
	            		
	            		//On confirme le passage de tour.
	            		dialogBuilder.setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mNextSprite.setVisible(false);
								
								new Thread("Connect to Serveur" ) {
							        public void run() { mGameContext.nextTurn(); }
							     }.start();
							     
			                	mWaiting.setVisible(true);
			                	dialog.dismiss();
							}
						});
	            		
	            		//On annule le passage de tour.
	            		dialogBuilder.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						}); 
	            		
	            		Base.getSharedInstance().runOnUiThread(new Runnable() {
	            			@Override
	            			public void run() {
	            				AlertDialog dialog = dialogBuilder.create();
	            				dialog.show();
	            			}
	            		});
	            		
	            		//L'action a été gérée.
	                	return true;	                	
	                }
                }
                return false;
		    }
		};
		mNextSprite.setVisible(false);
		
		mWaiting = new Sprite((Base.CAMERA_WIDTH - mResourcesManager.mWaiting.getWidth())/2, (Base.CAMERA_HEIGHT - mResourcesManager.mWaiting.getHeight())/2, mResourcesManager.mWaiting, mActivity.getVertexBufferObjectManager());
		mWaiting.setVisible(false);
		
		registerTouchArea(mDiceSprite);
		registerTouchArea(mNextSprite);
		
		attachChild(mBackground);
		
		attachChild(txtYear);
		attachChild(txtActors);
		attachChild(txtMoney);
		attachChild(txtScripts);
		attachChild(txtLogistics);

		attachChild(actorsSprite);
		attachChild(moneySprite);
		attachChild(logisticsSprite);
		attachChild(scriptsSprite);
		attachChild(mDiceSprite);
		attachChild(mNextSprite);
		attachChild(mWaiting);
		
		update();
	}
}
