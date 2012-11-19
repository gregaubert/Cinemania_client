package com.cinemania.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;

import com.cinemania.activity.Base;
import com.cinemania.cases.Case;
import com.cinemania.constants.Constantes;
import com.cinemania.gamelogic.Board;


public class BoardScene extends Scene implements Loader{

	// ===========================================================
	// Fields
	// ===========================================================

	private Base mActivity;
	private Camera mCamera;

	private Board mBoard;

	private final int caseMargin = 5;
	private final int side = (int) Constantes.BOARD_SIZE / 4;
	private final float caseSize = (Base.CAMERA_HEIGHT-caseMargin*(side+2)) / (side+1);

	private float maxCoord = side*(caseSize + caseMargin) + caseMargin;
	private float minCoord = caseMargin;
	
	private static final int ORIENTATION_RIGHT = 0;
	private static final int ORIENTATION_DOWN = 1;
	private static final int ORIENTATION_LEFT = 2;
	private static final int ORIENTATION_UP = 3;

	// ===========================================================
	// Constructors
	// ===========================================================

	public BoardScene() {
	    setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	    mCamera = Base.getSharedInstance().getCamera();
	    mActivity = Base.getSharedInstance();	
	}

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
	@Override
	public void Load() {
		mBoard = new Board();
		displayCases();
	}
	
	private void displayCases() {
		Case[] mCases = mBoard.getCases();

		Rectangle sprite;
		
		for (int i = 0; i < mCases.length; i++) {			
			float[] position = calculateCasePosition(i);
			
			sprite = mCases[i];			
			sprite.setPosition(position[0], position[1]);
			sprite.setSize(caseSize, caseSize);
			
			this.attachChild(sprite);

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
			position[0] = minCoord + j * (caseSize + caseMargin);
			position[1] = minCoord;	
			break;
		case ORIENTATION_DOWN:
			j = i-side;
			position[0] = maxCoord;
			position[1] = minCoord + j * (caseSize + caseMargin);
			break;		
		case ORIENTATION_LEFT:
			j = i-2*side;
			position[0] = maxCoord - j * (caseSize + caseMargin);
			position[1] = maxCoord;
			break;
		case ORIENTATION_UP:
		default:
			j = i-3*side;
			position[0] = minCoord;
			position[1] = maxCoord - j * (caseSize + caseMargin);
			
		}
			
		return position;
	}
	
	private int getOrientation(int i){
		// 0 => right, 1 => down, 2 => left, 3 => up
		return (int) i / side;
	}

}
