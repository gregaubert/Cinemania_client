package com.cinemania.camera;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import com.cinemania.activity.Base;
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

	@Override
	public void Load() {

		txtYear = new Text(10, 8, mResourcesManager.mYearFont, "Annee: 0123456789", mActivity.getVertexBufferObjectManager());
		txtMoney = new Text(235, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());
		txtActors = new Text(335, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());
		txtLogistics = new Text(435, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());
		txtScripts  = new Text(535, 8, mResourcesManager.mResourcesFont, "0123456789", mActivity.getVertexBufferObjectManager());
		
		setYear(1930);
		setMoney(0);
		setActors(0);
		setLogistics(0);
		setScripts(0);
		
		Sprite moneySprite =  new Sprite(200, 5, 32, 32, mResourcesManager.mMoneyLogo, mActivity.getVertexBufferObjectManager());
		Sprite actorsSprite = new Sprite(300, 5, 32, 32, mResourcesManager.mActorsLogo, mActivity.getVertexBufferObjectManager());
		Sprite logisticsSprite = new Sprite(400, 5, 32, 32, mResourcesManager.mLogisticsLogo, mActivity.getVertexBufferObjectManager());
		Sprite scriptsSprite = new Sprite(500, 5, 32, 32, mResourcesManager.mScriptsLogo, mActivity.getVertexBufferObjectManager());
		
		/* A changer / modifier / supprimer / renommer / verifier */
		float cameraHeight = ((ZoomCamera)mActivity.getCamera()).getHeight();
		Sprite diceSprite = new Sprite(10, cameraHeight - 74, mResourcesManager.mDice, mActivity.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction())
				{

	                case TouchEvent.ACTION_DOWN: {
	                	return mActivity.getGame().movePlayer();	                	
	                }
                }
                return false;
		    }
		};
		
		registerTouchArea(diceSprite);
		
		attachChild(txtYear);
		attachChild(txtActors);
		attachChild(txtMoney);
		attachChild(txtScripts);
		attachChild(txtLogistics);
		
		attachChild(actorsSprite);
		attachChild(moneySprite);
		attachChild(logisticsSprite);
		attachChild(scriptsSprite);
		attachChild(diceSprite);
	}
}
