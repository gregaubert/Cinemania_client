package com.cinemania.client.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

import com.cinemania.client.BaseActivity;

public class GameScene extends Scene {
	
	// ===========================================================
    // Constants
    // ===========================================================
	
	// ===========================================================
    // Fields
    // ===========================================================

	private BaseActivity mActivity;
	private Camera mCamera;

	// ===========================================================
    // Constructors
    // ===========================================================
	
	public GameScene() {
	    setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    mCamera = BaseActivity.getSharedInstance().getCamera();
	
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
