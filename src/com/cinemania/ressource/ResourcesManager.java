package com.cinemania.ressource;

import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

public class ResourcesManager {
	
	private static final ResourcesManager instance = new ResourcesManager();
	
	//**************************** TEXTURE ****************************
	public ITextureRegion mCaseCinema;
	public ITextureRegion mCaseHQ;
	public ITextureRegion mCaseResource;
	public ITextureRegion mCaseScript;
	public ITextureRegion mCaseActor;
	public ITextureRegion mCaseLogistic;
	
	public ITextureRegion mSplashLogo;
	public ITextureRegion mMenuLogo;
	//**************************** FONT ****************************
	public Font mSplashFont;
	public Font mMenuFont;
	
	private ResourcesManager(){
		
	}
	
	public static ResourcesManager getInstance(){
		return instance;
	}
	
	public void LoadSplash(Context context, Engine engine){
		//Gestion des polices d'écritures
		mSplashFont = FontFactory.create(engine.getFontManager(),engine.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, Color.WHITE);
		mSplashFont.load();
		
		//Indique le dossier contenant les textures.
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	BitmapTextureAtlas splashTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
    	//Récupère la bonne texture.
    	mSplashLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,context,"splash.png", 0, 0);
    	splashTextureAtlas.load();
	}
	
	public void LoadMenu(Context context, Engine engine){
		//Gestion des polices d'écritures
		mMenuFont = FontFactory.create(engine.getFontManager(),engine.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, Color.WHITE);
		mMenuFont.load();
		
		//Récupère la bonne texture.
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	BitmapTextureAtlas splashTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 500, 300, TextureOptions.DEFAULT);
    	//Récupère la bonne texture.
    	mMenuLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,context,"menu.jpg", 0, 0);
    	splashTextureAtlas.load();
	}
	
	public void LoadBoardGame(Context context, Engine engine) {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

        BitmapTextureAtlas boardBitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 380, 380);

        mCaseCinema = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_cinema.png", 0, 0);
        boardBitmapTextureAtlas.load();
      
	}
}
