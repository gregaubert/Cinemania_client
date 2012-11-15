package com.cinemania.client.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.TextureRegion;

import com.cinemania.client.BaseActivity;
import com.cinemania.client.ResourcesManager;

public class BoardScene extends Scene {
	
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
	
	public BoardScene() {
	    setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    mCamera = BaseActivity.getSharedInstance().getCamera();
	    mActivity = BaseActivity.getSharedInstance();
	    
	    Sprite sprit = new Sprite(5, 5, ResourcesManager.getInstance().mCaseCinema, mActivity.getVertexBufferObjectManager());
	    this.attachChild(sprit);
	    
	
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
