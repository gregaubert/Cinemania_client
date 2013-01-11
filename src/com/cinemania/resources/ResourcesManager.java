package com.cinemania.resources;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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

import com.cinemania.activity.Base;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Script;

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
	public ITextureRegion[] mCaseLevel = new ITextureRegion[AllConstants.LEVEL_MAX_BUILDING];
	public ITextureRegion mBoardBackground;
	public ITextureRegion mBoardCenter;
	
	public ITextureRegion mMoneyLogo;
	public ITextureRegion mScriptsLogo;
	public ITextureRegion mActorsLogo;
	public ITextureRegion mLogisticsLogo;
	
	//Gestion du dé.
	public ITextureRegion mDice;	
	public ITextureRegion mNextTurn;
	public ITextureRegion mWaiting;
	
	public ITextureRegion mPlayer;
	
	public ITextureRegion mSplashLogo;
	public ITextureRegion mMenuLogo;
	
	//**************************** FONT ****************************
	public Font mSplashFont;
	public Font mMenuFont;
	public Font mYearFont;
	public Font mResourcesFont;
	public Font[] mResourcesFontTab = new Font[AllConstants.NUMBER_PLAYER];
	public Font mWindowsFont;
	
	//**************************** SOUND ****************************
	public Sound mSndCashMachine;
	public Sound mSndMenuButton;
	public Sound mSndCowboy;
	public Sound mSndDiceWood;
	public Sound mSndProjector;
	public Sound mSndShufflingCards;
	public Sound mSndTaDa;
	public Music mMusicLoop;
	
	private ResourcesManager(){
		
	}
	
	public static ResourcesManager getInstance(){
		return instance;
	}
	
	public void LoadSplash(Context context, Engine engine){
		//Gestion des polices d'ecritures
		mSplashFont = FontFactory.createFromAsset(engine.getFontManager(), new BitmapTextureAtlas(engine.getTextureManager(),256,256), context.getAssets(), "fonts/GunslingerDEMO-KCFonts.ttf", 48f, true, Color.WHITE);
		//mSplashFont = FontFactory.create(engine.getFontManager(),engine.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, Color.WHITE);
		mSplashFont.load();
		
		//Indique le dossier contenant les textures.
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	BitmapTextureAtlas splashTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
    	//Recupere la bonne texture.
    	mSplashLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,context,"splash.png", 0, 0);
    	splashTextureAtlas.load();
	}
	
	public void LoadMenu(Context context, Engine engine){
		//Gestion des polices d'ecritures
		mMenuFont = FontFactory.create(engine.getFontManager(),engine.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, Color.WHITE);
		mMenuFont.load();
		

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		SoundFactory.setAssetBasePath("mfx/");
		
		//Recupere la bonne texture
    	BitmapTextureAtlas splashTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 500, 300, TextureOptions.DEFAULT);
    	//Recupere la bonne texture.
    	mMenuLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,context,"menu.jpg", 0, 0);
    	splashTextureAtlas.load();
    	
    	// Sound
    	try {
        	mSndMenuButton = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "button.ogg");
        	mSndMenuButton.setVolume(0.3f);
        } catch (final IOException e) {
        	Debug.e(e);
        }
	}
	
	public void LoadHUD(Context context, Engine engine){
		// Gestion des polices d'�critures
		mYearFont = FontFactory.createFromAsset(engine.getFontManager(), new BitmapTextureAtlas(engine.getTextureManager(),256,256), context.getAssets(), "fonts/veteran typewriter.ttf", 24, true, Color.BLACK);
		//mYearFont = FontFactory.create(engine.getFontManager(),engine.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24, Color.BLACK);
		mYearFont.load();
		mResourcesFont = FontFactory.createFromAsset(engine.getFontManager(), new BitmapTextureAtlas(engine.getTextureManager(),256,256), context.getAssets(), "fonts/veteran typewriter.ttf", 24, true, Color.BLACK);
		//mResourcesFont = FontFactory.create(engine.getFontManager(),engine.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 24, Color.BLACK);
		mResourcesFont.load();
		
		for(int i = 0; i < AllConstants.NUMBER_PLAYER; i++)
		{
			mResourcesFontTab[i] = FontFactory.createFromAsset(engine.getFontManager(), new BitmapTextureAtlas(engine.getTextureManager(),256,256), context.getAssets(), "fonts/veteran typewriter.ttf", 24, true, Base.getSharedInstance().getResources().getColor(AllConstants.PLAYER_COLOR_ANDROID[i]));
			mResourcesFontTab[i].load();
		}
		
		// Gestion des textures
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BuildableBitmapTextureAtlas HUDBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 512, 512);
		
		mMoneyLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDBitmapTextureAtlas, context, "logo_money.png");
		mScriptsLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDBitmapTextureAtlas, context, "logo_scripts.png");
		mLogisticsLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDBitmapTextureAtlas, context, "logo_logistics.png");
		mActorsLogo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDBitmapTextureAtlas, context, "logo_actors.png");
	    mNextTurn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDBitmapTextureAtlas, context, "next_turn.png");
	    mWaiting = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDBitmapTextureAtlas, context, "waiting.png");
	    
	    mDice = BitmapTextureAtlasTextureRegionFactory.createFromAsset(HUDBitmapTextureAtlas, context, "dice2.png");
	    
		try {
			HUDBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
			HUDBitmapTextureAtlas.load();
			
		} catch (final TextureAtlasBuilderException e) {
			Debug.e("GAME", "Chargement texture erreur : " + e);
		}
	}
	
	public void LoadPlayer(Context context, Engine engine){
		//Recupere la bonne texture.
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	BitmapTextureAtlas PlayerTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 80, 80, TextureOptions.DEFAULT);
    	//Recupere la bonne texture.
    	mPlayer = BitmapTextureAtlasTextureRegionFactory.createFromAsset(PlayerTextureAtlas,context,"player.png", 0, 0);
    	PlayerTextureAtlas.load();
	}
	
	public void LoadWindow(Context context, Engine engine) {
		
	}
	
	public void LoadBoardGame(Context context, Engine engine) {
		
		Script.loadMovieDB();
		
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        SoundFactory.setAssetBasePath("mfx/");
        MusicFactory.setAssetBasePath("mfx/");
        
        BuildableBitmapTextureAtlas boardBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 2048, 1024);
        
        mCaseCinema = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_cinema.png");
        mCaseResource = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_resources.png");
        mCaseScript = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_script.png");
        mCaseActors = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_actors.png");
        mCaseLogistics = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_logistics.png");
        mCaseLuck = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_luck.png");
        mCaseEmpty = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_empty1.png");
        mCaseHQ = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "case_hq.png");
        mCaseLevel[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "level1.png");
        mCaseLevel[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "level2.png");
        mCaseLevel[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(boardBitmapTextureAtlas, context, "level3.png");
        
        BuildableBitmapTextureAtlas backgroundBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 2048, 1024);
        mBoardBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backgroundBitmapTextureAtlas, context, "background.png");
        BuildableBitmapTextureAtlas boardCenterBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 1024, 1024);
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
        
        try {
        	mSndCashMachine = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "cha_ching.ogg");
        	mSndCashMachine.setVolume(0.8f);
        	mSndCowboy = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "cowboy.ogg");
        	mSndDiceWood = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "dice_wood.ogg");
        	mSndProjector = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "16mm_film_projector.ogg");
        	mSndShufflingCards = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "shuffling_cards.ogg");
        	mSndTaDa = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "ta_da.ogg");
        	mSndTaDa.setVolume(0.25f);
//        	mMusicLoop = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "western_spaghetti.ogg");
//    		mMusicLoop.setVolume(0.6f);
        	mMusicLoop = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "le_jeu.ogg");
    		mMusicLoop.setVolume(0.5f);
        	mMusicLoop.setLooping(true);
        } catch (final IOException e) {
        	Debug.e(e);
        }
	}
}
