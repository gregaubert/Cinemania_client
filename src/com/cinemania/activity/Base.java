package com.cinemania.activity;

import org.andengine.engine.camera.Camera;
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

import com.cinemania.client.R;
import com.cinemania.client.scenes.BoardScene;
import com.cinemania.client.scenes.GameMenu;
import com.cinemania.client.scenes.OptionScene;
import com.cinemania.ressource.ResourcesManager;

import android.util.Log;
import android.view.KeyEvent;

public class Base extends BaseGameActivity
{
	
	// ===========================================================
    // Constants
    // ===========================================================
    private final int CAMERA_WIDTH = 720;
    private final int CAMERA_HEIGHT = 480;
    
    // ===========================================================
    // Fields
    // ===========================================================
    private Camera mCamera;
   
    // ===========================================================
    // Ressources
    // ===========================================================
   
    //Gestion des textes
    private Text mTitle1;
    private Text mTitle2;
    
    //Singleton
    public static Base instance;
    private ResourcesManager manager;
    
    //Scène courrante
    private Scene mCurrentScene;
    //Type de scène
    private SceneType mSceneType = SceneType.LOADING;
    
    //Gestion multiscène
    public enum SceneType
    {
          LOADING,
          MENU,
          OPTIONS,
          GAME,
    }

    //Liste des scènes.
    //Menu principal du jeu.
    private GameMenu mMenu;
    //Liste des options.
    private OptionScene mOption;
    //Scene du jeu
    private BoardScene mGame;
    
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
    	//Récupère instance manager ressources
    	manager = ResourcesManager.getInstance();
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), mCamera);
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
		
		mTitle1.setPosition(-mTitle1.getWidth(), mCamera.getHeight() - 60);
		mTitle2.setPosition(mCamera.getWidth(), mCamera.getHeight() - 60);
		
		//Ajout de la modification du titre
		mTitle1.registerEntityModifier(new MoveXModifier(1, mTitle1.getX(), mCamera.getWidth() / 2 - mTitle1.getWidth()));
		mTitle2.registerEntityModifier(new MoveXModifier(1, mTitle2.getX(), mCamera.getWidth() / 2));
    	
    	pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception
    {
    	 initSplashScene();
         pOnCreateSceneCallback.onCreateSceneFinished(this.mCurrentScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception
    {
    	//Après 3 secondes, on appel la méthode onTimePassed
    	mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() 
    	{
    		public void onTimePassed(final TimerHandler pTimerHandler) 
    	    {
    			//Désincription du handler
    			mEngine.unregisterUpdateHandler(pTimerHandler);
    	        //Chargement des ressources et scènes.
    			loadScenes();
    			loadResources();
    	        mCurrentScene.detachChildren();
    	        setSceneType(SceneType.MENU);
    	        setCurrentScene(mMenu);
    	        
    	    }
    	}));
    	  
    	pOnPopulateSceneCallback.onPopulateSceneFinished();

    }
    
    //Créer la scène affiché à l'ecran.
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
            
            //Ajoute le sprite splash à la scène.
            splash.setPosition((CAMERA_WIDTH - splash.getWidth()) * 0.5f, (CAMERA_HEIGHT-splash.getHeight()) * 0.5f);
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
		//Initialisation des ressources
		mMenu.Load();
		mOption.Load();
		mGame.Load();
	}
	
	private void loadScenes()
	{
		// load your game here, you scenes
		mMenu = new GameMenu(); 
    	mOption = new OptionScene();
    	mGame = new BoardScene();
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
                    mEngine.setScene(mMenu);
                    mSceneType = SceneType.MENU;
                    break;
                case GAME:
                    mEngine.setScene(mMenu);
                    mSceneType = SceneType.MENU;
                    break; 
              }
         }
         return false;
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
    
    public void setSceneType(SceneType aType){
    	this.mSceneType = aType;
    }
    
    // to change the current main scene
    public void setCurrentScene(Scene scene) {
    	Log.i("GAME","Scene : " + scene.getClass());
    	mCurrentScene = scene;
        getEngine().setScene(mCurrentScene);
    }
}