package com.cinemania.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.opengl.font.Font;

import com.cinemania.activity.Base;
import com.cinemania.activity.Base.SceneType;
import com.cinemania.activity.R;
import com.cinemania.resources.ResourcesManager;

import android.util.Log;

public class OptionScene extends MenuScene implements IOnMenuItemClickListener, Loader{
	
	private final int MENU_OPTION1 = 0;
	private final int MENU_OPTION2 = 1;
	private final int MENU_QUIT = 2;
	
	private final int ESPACE_MENU = 20;
	
	private Base mActivity;
	private ResourcesManager mManager;
	
	public OptionScene(){
		super(Base.getSharedInstance().getCamera());
		mActivity = Base.getSharedInstance();
		mManager = ResourcesManager.getInstance();
		//Couleur de fond.
		setBackground(new Background(0.0f, 0.0f, 0.0f));
				
		setOnMenuItemClickListener(this);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene menuScene, IMenuItem menuItem, float menuItemLocalX, float menuItemLocalY) {
		mManager.mSndMenuButton.stop();
		mManager.mSndMenuButton.play();
	    switch (menuItem.getID()) {
	        case MENU_OPTION1:
	        	Log.i("GAME","OPTION1");
	            return true;
	        case MENU_OPTION2:
	        	Log.i("GAME","OPTION2");
	        	return true;
	        case MENU_QUIT:
	        	mActivity.setSceneType(SceneType.MENU);
	        	mActivity.setCurrentScene(mActivity.getGameMenu());
	        default:
	            break;
	    }
	    return false;
	}

	@Override
	public void Load() {
		//Recuperation des ressources
		Font font = mManager.mMenuFont;
		//Boutton option1
		IMenuItem option1 = new TextMenuItem(MENU_OPTION1, font, mActivity.getString(R.string.option1), mActivity.getVertexBufferObjectManager());
		option1.setPosition(mCamera.getWidth() / 2 - option1.getWidth() / 2, mCamera.getHeight() / 2 - option1.getHeight() / 2);
		addMenuItem(option1);
		
		//Boutton option2
		IMenuItem option2 = new TextMenuItem(MENU_OPTION2, font, mActivity.getString(R.string.option2), mActivity.getVertexBufferObjectManager());
		option2.setPosition(mCamera.getWidth() / 2 - option2.getWidth() / 2, option1.getY() + option1.getHeight() + ESPACE_MENU);
		addMenuItem(option2);
		
		//Boutton quitter
		IMenuItem quitter = new TextMenuItem(MENU_QUIT, font, mActivity.getString(R.string.retour), mActivity.getVertexBufferObjectManager());
		quitter.setPosition(mCamera.getWidth() / 2 - quitter.getWidth() / 2, option2.getY() + option2.getHeight() + ESPACE_MENU);
		addMenuItem(quitter);
	}
}
