package com.cinemania.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;

import com.cinemania.activity.Base;
import com.cinemania.activity.Base.SceneType;
import com.cinemania.activity.R;
import com.cinemania.resources.ResourcesManager;

public class GameMenu extends MenuScene implements IOnMenuItemClickListener, Loader{

	private final int MENU_START = 0;
	private final int MENU_OPTION = 1;
	private final int MENU_QUIT = 2;
	
	private final int ESPACE_MENU = 20;
	
	private Base mActivity;
	private ResourcesManager mManager;

	public GameMenu(){
		super(Base.getSharedInstance().getCamera());
		mActivity = Base.getSharedInstance();
		mManager = ResourcesManager.getInstance();
		//Couleur de fond.
		setBackground(new Background(0.0f, 0.0f, 0.0f));

		setOnMenuItemClickListener(this);
	}
	
	@Override
	public void Load(){
		//Sprite qui va contenir l'image du menu
		Sprite menu = new Sprite(0,0,mManager.mMenuLogo, mActivity.getEngine().getVertexBufferObjectManager());
    	menu.setPosition((mCamera.getWidth() - menu.getWidth()) * 0.5f, 0);
    	this.attachChild(menu);
		
		//Recuperation des ressources
		Font font = mManager.mMenuFont;
		//Boutton quitter
		IMenuItem quitterButton = new TextMenuItem(MENU_QUIT, font, mActivity.getString(R.string.quit), mActivity.getVertexBufferObjectManager());
		quitterButton.setPosition(mCamera.getWidth() / 2 - quitterButton.getWidth() / 2, mCamera.getHeight() - quitterButton.getHeight() - ESPACE_MENU);
		addMenuItem(quitterButton);
		
		//Boutton options
		IMenuItem optionButton = new TextMenuItem(MENU_OPTION, font, mActivity.getString(R.string.option), mActivity.getVertexBufferObjectManager());
		optionButton.setPosition(mCamera.getWidth() / 2 - optionButton.getWidth() / 2, quitterButton.getY() - optionButton.getHeight() -  ESPACE_MENU);
		addMenuItem(optionButton);
		
		//Boutton Start
		IMenuItem startButton = new TextMenuItem(MENU_START, font, mActivity.getString(R.string.start), mActivity.getVertexBufferObjectManager());
		startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth() / 2, optionButton.getY() - startButton.getHeight() -  ESPACE_MENU);
		addMenuItem(startButton);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene menuScene, IMenuItem menuItem, float menuItemLocalX, float menuItemLocalY) {
		mManager.mSndMenuButton.stop();
		mManager.mSndMenuButton.play();
		switch (menuItem.getID()) {
	        case MENU_START:
	        	mActivity.setSceneType(SceneType.GAME);
	        	mActivity.setCurrentScene(mActivity.getGame());
	            return true;
	        case MENU_OPTION:
	        	mActivity.setSceneType(SceneType.OPTIONS);
	        	mActivity.setCurrentScene(mActivity.getOption());
	        	return true;
	        case MENU_QUIT:
	        	System.exit(0);
	        default:
	            break;
	    }
	    return false;
	}

}
