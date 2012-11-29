package com.cinemania.scenes;

import org.andengine.engine.camera.Camera;
import static com.cinemania.constants.AllConstants.*;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.cinemania.activity.Base;
import com.cinemania.cases.Case;
import com.cinemania.gamelogic.Board;
import com.cinemania.gamelogic.Player;
import com.cinemania.resources.ResourcesManager;


public class BoardScene extends Scene implements Loader{

	// ===========================================================
	// Fields
	// ===========================================================

	private Base mActivity;
	private Camera mCamera;

	private Board mBoard;

	private final int side = (int) BOARD_SIZE / 4;
	private final float caseSize = Base.CAMERA_HEIGHT / (side+1);
	
	private float offset = (Base.CAMERA_WIDTH-(side+1)*caseSize)/2; 
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
	
	//FIXME
    public static final int NB_PLAYER = 4;
    private int ID_PLAYER = 0;
    
    //Liste des joueurs.
    private Player mPlayer[] = new Player[NB_PLAYER];
    
	// ===========================================================
	// Constructors
	// ===========================================================

	public BoardScene() {
	    setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    mCamera = Base.getSharedInstance().getCamera();
	    mActivity = Base.getSharedInstance();
	    
	    for(int i = 0; i < LAYER_COUNT; i++)
			this.attachChild(new Entity());
	}

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
	@Override
	public void Load() {
		mBoard = new Board();
		
		this.setBackgroundEnabled(false);
		this.getChildByIndex(LAYER_BACKGROUND).attachChild(new Sprite(0, 0, ResourcesManager.getInstance().mBoardBackground, mActivity.getVertexBufferObjectManager()));
		
		Sprite boardCenter = new Sprite(0, 0, ResourcesManager.getInstance().mBoardCenter, mActivity.getVertexBufferObjectManager());
		boardCenter.setSize(caseSize * (side - 1), caseSize * (side - 1));
		boardCenter.setPosition(caseSize + offset, caseSize);
		this.getChildByIndex(LAYER_PAWN).attachChild(boardCenter);
		
		displayCases();
		
		//Instancie les players.
		for(int i = 0; i < NB_PLAYER; i++){
			mPlayer[i] = new Player(mBoard, mBoard.getQG(i),i+1);
			this.getChildByIndex(LAYER_PAWN).attachChild(mPlayer[i].getView());
		}
		
		//TODO Modifié le -50
		Sprite dice = new Sprite(0,mCamera.getHeight()-50,ResourcesManager.getInstance().mDice, mActivity.getVertexBufferObjectManager()){
			@Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction())
				{

	                case TouchEvent.ACTION_DOWN: {	                	
	                	int move = mBoard.rollDice();
	                	Log.i("GAME","Déplacement du joueur : " + ID_PLAYER + " de " + move);
	    				mPlayer[ID_PLAYER].Move(move);
	    				ID_PLAYER = (ID_PLAYER + 1) % NB_PLAYER;
	    		        return true;
	                }
                }
                return false;
		    }
		};
		dice.setSize(caseSize, caseSize);
		this.registerTouchArea(dice);
		this.getChildByIndex(LAYER_BOARD).attachChild(dice);
	}
	
	private void displayCases() {
		Case[] mCases = mBoard.getCases();

		Rectangle sprite;
		
		for (int i = 0; i < mCases.length; i++) {			
			float[] position = calculateCasePosition(i);
			
			sprite = mCases[i];			
			sprite.setPosition(position[0], position[1]);
			sprite.setSize(caseSize, caseSize);
			
			this.getChildByIndex(LAYER_BOARD).attachChild(sprite);
		}
	}

	/**
	 * For every index, depending on screen size, it detects the orientation to
	 * continue drawing, then calculates the x,y position
	 * 
	 * @param i
	 * @return
	 */
	private float[] calculateCasePosition(int i) {
		
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
		
		position[0] += offset;
			
		return position;
	}
	
	private int getOrientation(int i){
		// 0 => right, 1 => down, 2 => left, 3 => up
		return (int) i / side;
	}

}
