package com.cinemania.client.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.*;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import com.cinemania.client.BaseActivity;
import com.cinemania.client.R;

public class MainMenuScene extends MenuScene implements IOnMenuItemClickListener {

	
	// ===========================================================
    // Constants
    // ===========================================================
	
	final int MENU_START = 0;
	
	// ===========================================================
    // Fields
    // ===========================================================

	private BaseActivity mActivity;

	// ===========================================================
    // Constructors
    // ===========================================================
	
	public MainMenuScene() {
		super(BaseActivity.getSharedInstance().getCamera());
		mActivity = BaseActivity.getSharedInstance();
		
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		IMenuItem startButton = new TextMenuItem(MENU_START, mActivity.getMenuFont(), mActivity.getString(R.string.start), mActivity.getVertexBufferObjectManager()); 
		startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth() / 2, mCamera.getHeight() / 2 - startButton.getHeight() / 2);
		addMenuItem(startButton);
		
		setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,	float pMenuItemLocalX, float pMenuItemLocalY) {
	    switch (pMenuItem.getID()) {
	        case MENU_START:
	        	mActivity.setCurrentScene(new GameScene());
	        	return true;
	        default:
	            break;
	    }
	    return false;
	}

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
	
	// ===========================================================
    // Methods
    // ===========================================================
	
	// ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
