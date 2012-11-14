package com.cinemania.client.scenes;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.IModifier;

import com.cinemania.client.BaseActivity;
import com.cinemania.client.R;

public class SplashScene extends Scene {
	
	// ===========================================================
    // Constants
    // ===========================================================
	
	// ===========================================================
    // Fields
    // ===========================================================

	private BaseActivity mActivity;

	// ===========================================================
    // Constructors
    // ===========================================================
	
	public SplashScene() {
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		mActivity = BaseActivity.getSharedInstance();
		Text title1 = new Text(0, 0, mActivity.getTitleFont(), mActivity.getString(R.string.title1), mActivity.getVertexBufferObjectManager());
		Text title2 = new Text(0, 0, mActivity.getTitleFont(), mActivity.getString(R.string.title2), mActivity.getVertexBufferObjectManager());
		title1.setPosition(-title1.getWidth(), mActivity.getCamera().getHeight() / 2);
		title2.setPosition(mActivity.getCamera().getWidth(), mActivity.getCamera().getHeight() / 2);
		attachChild(title1);
		attachChild(title2);
		title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), mActivity.getCamera().getWidth() / 2 - title1.getWidth()));
		title2.registerEntityModifier(new MoveXModifier(1, title2.getX(), mActivity.getCamera().getWidth() / 2));
		
		loadResources();
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
	
	private void loadResources(){
		
		IEntityModifierListener pEntityModifierListener = new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
				
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
				mActivity.setCurrentScene(new MainMenuScene());
			}
		};
		
		// Attend 2 secondes
		DelayModifier dMod = new DelayModifier(2f, pEntityModifierListener);
		registerEntityModifier(dMod);
	}

	
	// ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
