package com.cinemania.client.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;

import com.cinemania.activity.Base;
import com.cinemania.client.Board;
import com.cinemania.client.Case;
import com.cinemania.client.Constantes;


public class BoardScene extends Scene implements Loader{

	// ===========================================================

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

		Sprite sprite;
		
		for (int i = 0; i < mCases.length; i++) {			
			float[] position = calculateCasePosition(i);
			
			sprite = mCases[i].getSprite();			
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
		
		// to the right (side+1)
		if (i < side){
			j = i;
			position[0] = minCoord + j * (caseSize + caseMargin);
			position[1] = minCoord;	
		}
		
		// downwards (side)
		else if (i >= side && i < 2*side){
			j = i-side;
			position[0] = maxCoord;
			position[1] = minCoord + j * (caseSize + caseMargin);
		}
		
		// to the left (side)
		else if (i >= 2*side && i < 3*side){
			j = i-2*side;
			position[0] = maxCoord - j * (caseSize + caseMargin);
			position[1] = maxCoord;
		}
		
		// upwards (side-1)
		else {
			j = i-3*side;
			position[0] = minCoord;
			position[1] = maxCoord - j * (caseSize + caseMargin);
		}
		
		return position;
	}

}
