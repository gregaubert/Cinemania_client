package com.cinemania.client.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;

import com.cinemania.client.BaseActivity;
import com.cinemania.client.ResourcesManager;
import com.cinemania.client.Board;
import com.cinemania.client.Case;

public class BoardScene extends Scene {
	
	// ===========================================================
    // Constants
    // ===========================================================
	
	// ===========================================================
    // Fields
    // ===========================================================

	private BaseActivity mActivity;
	private Camera mCamera;
	
	private Board mBoard;

	// ===========================================================
    // Constructors
    // ===========================================================
	
	public BoardScene() {
	    setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    mCamera = BaseActivity.getSharedInstance().getCamera();
	    mActivity = BaseActivity.getSharedInstance();
	    mBoard = new Board();
	    
	    displayCases();
	}
	
	private void displayCases(){
		Case[] mCases = mBoard.getCases();
		
		ITextureRegion texture;
		
		for (int i = 0; i < mCases.length; i++){
			texture = mCases[i].getTextureRegion();
			
			float x = 5 + i*texture.getWidth();
			float y = 5;
			Sprite sprit = new Sprite(x, y, texture, mActivity.getVertexBufferObjectManager());
		    this.attachChild(sprit);
		    mCases[i].setSprite(sprit);
		}
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
