package com.cinemania.resources;

import org.andengine.engine.Engine;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

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
	public ITextureRegion mCaseActors;
	public ITextureRegion mCaseLogistics;
	public ITextureRegion mCaseLuck;
	public ITextureRegion mCaseEmpty;
	public ITextureRegion mBoardBackground;
	public ITextureRegion mBoardCenter;
	
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
		mSplashFont = FontFactory.createFromAsset(engine.getFontManager(), new BitmapTextureAtlas(engine.getTextureManager(),256,256), context.getAssets(), "fonts/GunslingerDEMO-KCFonts.ttf", 48f, true, Color.WHITE);
		//mSplashFont = FontFactory.create(engine.getFontManager(),engine.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, Color.WHITE);
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
        BuildableBitmapTextureAtlas boardBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 2048, 1024);
        
        mCaseCinema = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_cinema.png");
        mCaseResource = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_resources.png");
        mCaseScript = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_script.png");
        mCaseActors = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_actors.png");
        mCaseLogistics = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_logistics.png");
        mCaseLuck = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_luck.png");
        mCaseEmpty = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_empty1.png");
        mCaseHQ = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_hq.png");
        
        BuildableBitmapTextureAtlas backgroundBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 1024, 1024);
        mBoardBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundBitmapTextureAtlas, context, "background.png");
        BuildableBitmapTextureAtlas boardCenterBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 2048, 2048);
        mBoardCenter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardCenterBitmapTextureAtlas, context, "boardcenter.png");
        
        try {
        	boardBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
        	backgroundBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
        	boardCenterBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
        	boardBitmapTextureAtlas.load();
        	boardCenterBitmapTextureAtlas.load();
        	backgroundBitmapTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
}
