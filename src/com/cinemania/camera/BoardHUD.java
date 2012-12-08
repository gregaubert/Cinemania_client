package com.cinemania.camera;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

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
		txtCurrentPlayer.setText("Id : " + p.getName());
		txtCurrentPlayer.setColor(p.getColorPawn());
	}
	
	public void setCurrentTurn(){
		mDiceSprite.setVisible(true);
		mWaiting.setVisible(false);
	}
	
	@Override
	public void Load() {
		
		mGameContext = GameContext.getSharedInstance();
		
		txtYear = new Text(10, 8, mResourcesManager.mYearFont, "Annee: 0123456789", mActivity.getVertexBufferObjectManager());
		txtMoney = new Text(235, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());
		txtActors = new Text(335, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());
		txtLogistics = new Text(435, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());
		txtScripts  = new Text(535, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());
		txtCurrentPlayer = new Text(600, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());

		setYear(mGameContext.getYear());
		setMoney(mGameContext.getPlayer().getAmount());
		setActors(mGameContext.getPlayer().getActors());
		setLogistics(mGameContext.getPlayer().getLogistic());
		//TODO récupérer le nombre de script du joueur
		setScripts(0);
		setCurrentPlayer(mGameContext.getPlayer());
		
		Sprite moneySprite =  new Sprite(200, 5, 32, 32, mResourcesManager.mMoneyLogo, mActivity.getVertexBufferObjectManager());
		Sprite actorsSprite = new Sprite(300, 5, 32, 32, mResourcesManager.mActorsLogo, mActivity.getVertexBufferObjectManager());
		Sprite logisticsSprite = new Sprite(400, 5, 32, 32, mResourcesManager.mLogisticsLogo, mActivity.getVertexBufferObjectManager());
		Sprite scriptsSprite = new Sprite(500, 5, 32, 32, mResourcesManager.mScriptsLogo, mActivity.getVertexBufferObjectManager());
		
		/*TODO Vérifier emplacement */
		float cameraHeight = ((ZoomCamera)mActivity.getCamera()).getHeight();
		float cameraWidth = ((ZoomCamera)mActivity.getCamera()).getWidth();
		
		mDiceSprite = new ButtonSprite(10, cameraHeight - 74, mResourcesManager.mDice, mActivity.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction())
				{

	                case TouchEvent.ACTION_DOWN: {
	                	this.setVisible(false);
	                	mNextSprite.setVisible(true);
	                	return mActivity.getGame().movePlayer();	                	
	                }
                }
                return false;
		    }
		};
		
		mNextSprite = new ButtonSprite(10, cameraHeight - 74, mResourcesManager.mNextTurn, mActivity.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction())
				{

	                case TouchEvent.ACTION_DOWN: {
	                	this.setVisible(false);
	                	mGameContext.nextTurn();
	                	mWaiting.setVisible(true);
	                	
	                	return true;	                	
	                }
                }
                return false;
		    }
		};
		mNextSprite.setVisible(false);
		
		mWaiting = new Sprite((cameraWidth - mResourcesManager.mWaiting.getWidth())/2, (cameraHeight - mResourcesManager.mWaiting.getHeight())/2, mResourcesManager.mWaiting, mActivity.getVertexBufferObjectManager());
		mWaiting.setVisible(false);
		
		registerTouchArea(mDiceSprite);
		registerTouchArea(mNextSprite);
		
		attachChild(txtYear);
		attachChild(txtActors);
		attachChild(txtMoney);
		attachChild(txtScripts);
		attachChild(txtLogistics);
		attachChild(txtCurrentPlayer);
		
		attachChild(actorsSprite);
		attachChild(moneySprite);
		attachChild(logisticsSprite);
		attachChild(scriptsSprite);
		attachChild(mDiceSprite);
		attachChild(mNextSprite);
		attachChild(mWaiting);
	}
}
