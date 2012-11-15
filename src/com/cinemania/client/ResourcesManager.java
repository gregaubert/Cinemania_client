package com.cinemania.client;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.content.Context;

public class ResourcesManager {
	
	private static final ResourcesManager instance = new ResourcesManager();
	
	public ITextureRegion mCaseCinema;
	private ITextureRegion mCaseHQ;
	private ITextureRegion mCaseResource;
	private ITextureRegion mCaseScript;
	private ITextureRegion mCaseActor;
	private ITextureRegion mCaseLogistic;

	private ResourcesManager(){
		
	}
	
	public static ResourcesManager getInstance(){
		return instance;
	}
	
	public void LoadBoardGame(Context context, Engine engine) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        BitmapTextureAtlas boardBitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 380, 380);

        mCaseCinema = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_cinema.png", 0, 0);
        boardBitmapTextureAtlas.load();
      
	}
}
