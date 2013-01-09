package com.cinemania.scenes;

import static com.cinemania.constants.AllConstants.BOARD_SIZE;

import org.andengine.engine.camera.ZoomCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.json.JSONException;

import android.util.Log;

import com.cinemania.activity.Base;
import com.cinemania.camera.CameraManager;
import com.cinemania.gamelogic.Player;
import com.cinemania.network.GameContext;
import com.cinemania.network.api.API;
import com.cinemania.network.api.API.GameDataResult;
import com.cinemania.network.api.API.GameListResult;
import com.cinemania.resources.ResourcesManager;


public class BoardScene extends Scene implements Loader {

	// ===========================================================
	// Fields
	// ===========================================================

	private Base mActivity;
	private ZoomCamera mCamera;
	private CameraManager mCameraManager;
	private ResourcesManager mResourcesManager;

	private GameContext mGameContext;

	private final int side = (int) BOARD_SIZE / 4;
	private final float caseSize = 80f;

	private float offsetWidth = (Base.CAMERA_WIDTH * 2-(side+1)*caseSize)/2;
	private float offsetHeight = (Base.CAMERA_HEIGHT * 2-(side+1)*caseSize)/2;
	
	private float maxCoord = side*caseSize;
	private float minCoord = 0;
	
	private static final int ORIENTATION_RIGHT = 0;
	private static final int ORIENTATION_DOWN = 1;
	private static final int ORIENTATION_LEFT = 2;
	private static final int ORIENTATION_UP = 3;
	
	private static final int LAYER_COUNT = 3;

	private static final int LAYER_BACKGROUND = 0;
	private static final int LAYER_BOARD = 1;
	private static final int LAYER_PAWN = 2;
    
	private long mGameIdentifier;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public BoardScene() {
	    setBackground(new Background(0f, 0f, 0f));
	    mActivity = Base.getSharedInstance();
	    mCamera = (ZoomCamera)mActivity.getCamera();

		//TODO Modifier la constante
	    this.offsetHeight += 32;
		
	    mResourcesManager = ResourcesManager.getInstance();
	    mCameraManager = new CameraManager(mCamera);
	    setOnAreaTouchTraversalFrontToBack();
	    setOnSceneTouchListener(mCameraManager);
		setTouchAreaBindingOnActionDownEnabled(true);
	    
		// Creation des layers
	    for(int i = 0; i < LAYER_COUNT; i++)
			this.attachChild(new Entity());
	}

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
	@Override
	public void Load() {	
		
	    ;
	    
	    // Check there is already an unfull game
	    GameListResult gameList = API.availableGames();
	    if (gameList.getGames().length > 0) {
	    	mGameIdentifier = gameList.getGames()[0];
	    	Log.d(BoardScene.class.getName(), "Get game (" + mGameIdentifier + ")");
	    } else {
	    	// Create a new game
	    	mGameIdentifier = API.newGame().getGameIdentifier();
	    	Log.d(BoardScene.class.getName(), "Create new game (" + mGameIdentifier + ")");
	    }
    	API.joinGame(mGameIdentifier);
    	
    	getDataFromServer();
		
		setBackgroundEnabled(true);
		getChildByIndex(LAYER_BACKGROUND).attachChild(new Sprite(0, 0, mResourcesManager.mBoardBackground, mActivity.getVertexBufferObjectManager()));
		
		Sprite boardCenter = new Sprite(caseSize + offsetWidth, caseSize + offsetHeight, mResourcesManager.mBoardCenter, mActivity.getVertexBufferObjectManager());		
		getChildByIndex(LAYER_BACKGROUND).attachChild(boardCenter);

		regenerateGameElements();
	}

	public void getDataFromServer() {
		GameDataResult gameData = API.gameData(mGameIdentifier);
    	// Extract game data
		mGameContext = GameContext.getSharedInstance();
		try {
			mGameContext = GameContext.getSharedInstance();
			mGameContext.deserialize(gameData.getGameData());
					
			mGameContext.deserializeBoard(Base.getSharedInstance().getGame());
			mGameContext.deserializePlayers();
			mGameContext.deserializeGame();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void regenerateGameElements(){
		cleanGameElements();
		createGameElements();
	}
	
	private void cleanGameElements(){
		this.getChildByIndex(LAYER_BOARD).detachChildren();
		this.getChildByIndex(LAYER_PAWN).detachChildren();
	}
	
	private void createGameElements(){
	
		for (int i = 0; i < mGameContext.getSize(); i++) {			
			this.getChildByIndex(LAYER_BOARD).attachChild(mGameContext.getCase(i).getView());
		}
		
		// Attach player's view to 
		for (Player player : mGameContext.getPlayers()){
			getChildByIndex(LAYER_PAWN).attachChild(player.getView());
		}
	}
	
	public void movePlayer() {
		
    	int move = shootOneDice() + shootOneDice();
    	
    	Log.i("GAME","Deplacement du joueur " + move);
    	Player p = mGameContext.getPlayer();
    	p.Move(move);
	}

	/**
	 * For every index, depending on screen size, it detects the orientation to
	 * continue drawing, then calculates the x,y position
	 * 
	 * @param i
	 * @return
	 */
	public float[] calculateCasePosition(int i) {
		
		float[] position = {minCoord,minCoord};
		
		int j = 0;
		int orientation = getOrientation(i);
		
		switch(orientation){
		case ORIENTATION_RIGHT:
			j = i;
			position[0] = minCoord + j * caseSize;
			position[1] = minCoord;	
			break;
		case ORIENTATION_DOWN:
			j = i-side;
			position[0] = maxCoord;
			position[1] = minCoord + j * caseSize;
			break;		
		case ORIENTATION_LEFT:
			j = i-2*side;
			position[0] = maxCoord - j * caseSize;
			position[1] = maxCoord;
			break;
		case ORIENTATION_UP:
		default:
			j = i-3*side;
			position[0] = minCoord;
			position[1] = maxCoord - j * caseSize;
			
		}
		
		position[0] += offsetWidth;
		position[1] += offsetHeight;
			
		return position;
	}
	
	private int getOrientation(int i){
		// 0 => right, 1 => down, 2 => left, 3 => up
		return (int) i / side;
	}
	
	private int shootOneDice() {
		return (int)(Math.random()*6)+1;
	}	
}
