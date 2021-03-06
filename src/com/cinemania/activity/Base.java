package com.cinemania.activity;

import org.andengine.audio.music.Music;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.ZoomCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;

import android.util.Log;
import android.view.KeyEvent;

import com.cinemania.camera.BoardHUD;
import com.cinemania.network.gcm.GCMConnector;
import com.cinemania.resources.ResourcesManager;
import com.cinemania.scenes.BoardScene;
import com.cinemania.scenes.GameMenu;
import com.cinemania.scenes.OptionScene;

public class Base extends BaseGameActivity
{
	
	// ===========================================================
    // Constants
    // ===========================================================
    public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;
    
    // ===========================================================
    // Fields
    // ===========================================================
    private ZoomCamera mCamera;
   
    // ===========================================================
    // Ressources
    // ===========================================================
   
    //Gestion des textes
    private Text mTitle1;
    private Text mTitle2;
    
    //Singleton
    public static Base instance;
    private ResourcesManager manager;
    
    //Scene courrante
    private Scene mCurrentScene;
    
    //Type de scene
    private SceneType mSceneType = SceneType.LOADING;
    
    //Gestion multiscene
    public enum SceneType
    {
          LOADING,
          MENU,
          OPTIONS,
          GAME,
    }

    //Liste des scenes.
    //Menu principal du jeu.
    private GameMenu mMenu;
    //Liste des options.
    private OptionScene mOption;
    //Scene du jeu
    private BoardScene mGame;
    // HUD
    private BoardHUD mHUD;

    // Musique du jeu
    private Music mMusicLoop;
    
    // ===========================================================
    // Constructors
    // ===========================================================
    public static Base getSharedInstance() {
        return instance;
    }
    
    // ===========================================================
    // Methods
    // ===========================================================
    
	@Override
    public EngineOptions onCreateEngineOptions()
    {
    	//Singleton
    	instance = this;
    	
    	//Recupere instance manager ressources
    	manager = ResourcesManager.getInstance();
    	mCamera = new ZoomCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
        engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        return engineOptions;
    }
    
    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception
    {    	
    	manager.LoadSplash(this, this.mEngine);
    	
    	Font font = manager.mSplashFont;
    	
    	//Ajout du titre
		mTitle1 = new Text(0, 0, font, this.getString(R.string.titre1), this.getVertexBufferObjectManager());
		mTitle2 = new Text(0, 0, font, this.getString(R.string.titre2), this.getVertexBufferObjectManager());
		
		mTitle1.setPosition(-mTitle1.getWidth(), mCamera.getHeight() - 125);
		mTitle2.setPosition(mCamera.getWidth(), mCamera.getHeight() - 125);
		
		//Ajout de la modification du titre
		mTitle1.registerEntityModifier(new MoveXModifier(1f, mTitle1.getX(), mCamera.getWidth() / 2 - mTitle1.getWidth() - 18));
		mTitle2.registerEntityModifier(new MoveXModifier(1f, mTitle2.getX(), mCamera.getWidth() / 2 - 18));
    	
    	pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
    	// Active GCM message reception
    	Log.d("GAME", "PUTAIN DE MERDE ..............................................");
    	GCMConnector.connect();
    	enableVibrator();
    	initSplashScene();
        pOnCreateSceneCallback.onCreateSceneFinished(this.mCurrentScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
    	// Apres 1 secondes, on appel la methode onTimePassed
    	mEngine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
    		public void onTimePassed(final TimerHandler pTimerHandler) {
    			mEngine.unregisterUpdateHandler(pTimerHandler);
    			
    			loadScenes();
    			loadResources();
    			
    			mCamera.setHUD(mHUD);
    	        mCurrentScene.detachChildren();
    	        setSceneType(SceneType.MENU);
    	        setCurrentScene(mMenu);
    	    }
    	}));
    	  
    	pOnPopulateSceneCallback.onPopulateSceneFinished();
    }
    
    // TODO cannot make this actually execute when the game closes!!!
    @Override
    public synchronized void onGameDestroyed(){
    	/*try{
    		GameContext.getSharedInstance().leaveGame();
    	} catch(Exception e){}*/
    	GCMConnector.destroy();
    	super.onGameDestroyed();
    }
    
    @Override
    public synchronized void onResumeGame() {
    	if(this.mEngine != null)
    		super.onResumeGame();
    }
        
    //Creer la scene affiche e l'ecran.
    private void initSplashScene()
    {
            mCurrentScene = new Scene();
            //Sprite qui va contenir le logo
            Sprite splash = new Sprite(0, 0, manager.mSplashLogo, mEngine.getVertexBufferObjectManager())
            {
            	@Override
			    protected void preDraw(GLState pGLState, Camera pCamera)
			    {
            		super.preDraw(pGLState, pCamera);
			        pGLState.enableDither();
			    }
            };
            splash.setScale(0.8f);
            //Ajoute le sprite splash e la scene.
            splash.setPosition((mCamera.getWidth() - splash.getWidth()) * 0.5f, (mCamera.getHeight()-splash.getHeight() - 150f) * 0.5f);
            mCurrentScene.attachChild(splash);
    
            //Attache le titre
            mCurrentScene.attachChild(mTitle1);
            mCurrentScene.attachChild(mTitle2);
    }
    
    public void loadResources() 
	{
    	//Chargement des ressources
		manager.LoadMenu(this, this.mEngine);
		manager.LoadBoardGame(this,this.mEngine);
		manager.LoadPlayer(this, this.mEngine);
		manager.LoadHUD(this, this.mEngine);
		
		mMenu.Load();		
		
		// Charge la musique
		mMusicLoop = ResourcesManager.getInstance().mMusicLoop;
	}
	
	private void loadScenes()
	{
		Log.i("Game","loadScenes");
		
		// load your game here, you scenes
		mMenu = new GameMenu(); 
    	mOption = new OptionScene();
    	mGame = new BoardScene();
    	mHUD = new BoardHUD();
	}
	
	@Override
    //Gestion du retour
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {  
         if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() ==KeyEvent.ACTION_DOWN)
         {       
        	 switch (mSceneType)
             {
             	case LOADING:
             		System.exit(0);
             		break;
                case MENU:
                	System.exit(0);
                    break;
                case OPTIONS:
                    this.setSceneType(SceneType.MENU);
                	this.setCurrentScene(mMenu);
                    break;
                case GAME:
                	this.setSceneType(SceneType.MENU);
                	this.setCurrentScene(mMenu);
                	if(mMusicLoop.isPlaying())
                		mMusicLoop.pause();
                    break; 
              }
         }
         return false;
    }

	public void vibrate(long time){
		mEngine.vibrate(time);
	}
	
	//************GETTER************
    public Camera getCamera(){
    	return mCamera;
    }

    public BoardScene getGame(){
    	return this.mGame;
    }
    
    public GameMenu getGameMenu(){
    	return this.mMenu;
    }
    
    public OptionScene getOption(){
    	return this.mOption;
    }
    
    public BoardHUD getHUD(){
    	return this.mHUD;
    }
    
    public void setSceneType(SceneType aType){
    	this.mSceneType = aType;
    }
    
    // to change the current main scene
    public void setCurrentScene(Scene scene) {
    	Log.i("GAME","Scene : " + scene.getClass());
    	
    	if(mSceneType == SceneType.GAME){
    		mHUD.setVisible(true);
    		mCamera.setZoomFactor(0.5f);
    		if(!mMusicLoop.isPlaying())
    			mMusicLoop.play();
    	}
    	else{
    		mHUD.setVisible(false);
    		mCamera.setZoomFactor(1f);
    	}
    	
    	mCurrentScene = scene;
        getEngine().setScene(mCurrentScene);
    }
}
