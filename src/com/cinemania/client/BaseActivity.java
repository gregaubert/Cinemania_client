package com.cinemania.client;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.cinemania.client.scenes.SplashScene;

import android.graphics.Color;
import android.graphics.Typeface;

public class BaseActivity extends BaseGameActivity {
	
	// ===========================================================
    // Constants
    // ===========================================================

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	
	// ===========================================================
    // Fields
    // ===========================================================

	private Camera mCamera;
	private Font mTitleFont;
	private Font mMenuFont;

	private static BaseActivity mInstance;
	public Scene mCurrentScene;

	
	// ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

	public Font getTitleFont() {
		return mTitleFont;
	}
	
	public Font getMenuFont() {
		return mMenuFont;
	}
	
	public Camera getCamera() {
		return mCamera;
	}
	
    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mInstance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
        return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback)	throws Exception {
		
		
		mTitleFont = FontFactory.createFromAsset(this.getFontManager(), new BitmapTextureAtlas(this.getTextureManager(),256,256), this.getAssets(), "GunslingerDEMO-KCFonts.ttf", 48f, true, Color.BLACK);
		mMenuFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
		
	    mTitleFont.load();
	    mMenuFont.load();
	    
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)	throws Exception {
		
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new SplashScene();
	    
	    pOnCreateSceneCallback.onCreateSceneFinished(mCurrentScene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	// ===========================================================
    // Methods
    // ===========================================================

	public static BaseActivity getSharedInstance() {
	    return mInstance;
	}

	// to change the current main scene
	public void setCurrentScene(Scene scene) {
	    mCurrentScene = scene;
	    getEngine().setScene(mCurrentScene);
	}
	
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
