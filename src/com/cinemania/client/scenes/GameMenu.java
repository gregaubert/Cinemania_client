package com.cinemania.client.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;

import com.cinemania.activity.Base;
import com.cinemania.activity.Base.SceneType;
import com.cinemania.client.R;
import com.cinemania.ressource.ResourcesManager;

public class GameMenu extends MenuScene implements IOnMenuItemClickListener, Loader{

	private final int MENU_START = 0;
	private final int MENU_OPTION = 1;
	private final int MENU_QUIT = 2;
	
	private final int ESPACE_MENU = 20;
	
	private Base activity;
	private ResourcesManager manager;

	public GameMenu(){
		super(Base.getSharedInstance().getCamera());
		activity = Base.getSharedInstance();
		manager = ResourcesManager.getInstance();
		//Couleur de fond.
		setBackground(new Background(0.0f, 0.0f, 0.0f));

		setOnMenuItemClickListener(this);
	}
	
	@Override
	public void Load(){
		//Sprite qui va contenir l'image du menu
		Sprite menu = new Sprite(0,0,manager.mMenuLogo, activity.getEngine().getVertexBufferObjectManager());
    	menu.setPosition((mCamera.getWidth() - menu.getWidth()) * 0.5f, 0);
    	this.attachChild(menu);
		
		//Récupération des ressources
		Font font = manager.mMenuFont;
		//Boutton quitter
		IMenuItem quitterButton = new TextMenuItem(MENU_QUIT, font, activity.getString(R.string.quit), activity.getVertexBufferObjectManager());
		quitterButton.setPosition(mCamera.getWidth() / 2 - quitterButton.getWidth() / 2, mCamera.getHeight() - quitterButton.getHeight() - ESPACE_MENU);
		addMenuItem(quitterButton);
		
		//Boutton options
		IMenuItem optionButton = new TextMenuItem(MENU_OPTION, font, activity.getString(R.string.option), activity.getVertexBufferObjectManager());
		optionButton.setPosition(mCamera.getWidth() / 2 - optionButton.getWidth() / 2, quitterButton.getY() - optionButton.getHeight() -  ESPACE_MENU);
		addMenuItem(optionButton);
		
		//Boutton Start
		IMenuItem startButton = new TextMenuItem(MENU_START, font, activity.getString(R.string.start), activity.getVertexBufferObjectManager());
		startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth() / 2, optionButton.getY() - startButton.getHeight() -  ESPACE_MENU);
		addMenuItem(startButton);
	}
	
	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
	    switch (arg1.getID()) {
	        case MENU_START:
	        	activity.setSceneType(SceneType.GAME);
	        	activity.setCurrentScene(activity.getGame());
	            return true;
	        case MENU_OPTION:
	        	activity.setSceneType(SceneType.OPTIONS);
	        	activity.setCurrentScene(activity.getOption());
	        	return true;
	        case MENU_QUIT:
	        	System.exit(0);
	        default:
	            break;
	    }
	    return false;
	}

}
