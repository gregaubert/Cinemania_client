package com.cinemania.gamelogic;

import java.util.ArrayList;
import static com.cinemania.constants.AllConstants.*;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import android.util.Log;

import com.cinemania.activity.Base;
import com.cinemania.cases.Case;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.OwnableCell;
import com.cinemania.resources.ResourcesManager;

public class Player {

	private final int bordure = 10;

	private ArrayList<OwnableCell> mProperties = new ArrayList<OwnableCell>();
	private HeadQuarters mHeadQuarters;
	private Case mCurrentPosition;
	private long mIdentifier;
	private int mOrder;
	private String mName;
	private Color mColorCell;
	private Color mColorPawn;
	private int mMoney;
	private int mActors;
	private int mLogistics;
	private Sprite mView;
	
	
	public Player(long identifier, int order, String name, int money, int actors, int logistics, HeadQuarters headQuarters, Case currentPosition) {
		mIdentifier = identifier;
		mOrder = order;
		mName = name;
		mColorCell = PLAYER_COLOR[1][order];
		mColorPawn = PLAYER_COLOR[0][order];
		mMoney = money;
		mActors = actors;
		mLogistics = logistics;
		mHeadQuarters = headQuarters;
		mCurrentPosition = currentPosition;
		mView = new Sprite(mCurrentPosition.getX()+bordure, mCurrentPosition.getY()+bordure, ResourcesManager.getInstance().mPlayer, Base.getSharedInstance().getVertexBufferObjectManager());
		mView.setSize(mView.getWidth()-2*bordure, mView.getHeight() - 2*bordure);
		mView.setColor(this.mColorPawn);
	}

	/*
	public void Move(int nb) {
		
		while (nb != 0) {

			pos = (pos+1)%board.getSize();
			Case temp = board.getCaseAtIndex(pos);
			//TODO trouvé technique pour attendre que le mouvement soit finit.
			mView.registerEntityModifier(new MoveModifier(0.2f, mCurrentPosition.getX(), temp.getX()+bordure, mCurrentPosition.getY(), temp.getY()+bordure));
			  
			//Test si on passe au QG
			if (temp == this.mHeadQuarters) {
				this.encaisser();
			}
		  
			position = temp;
			
			nb -= 1;		
		}
	  
	}*/
	
	public void MoveTo(Case target) {
		mView.registerEntityModifier(new MoveModifier(0.2f, mCurrentPosition.getX(), target.getX()+bordure, mCurrentPosition.getY(), target.getY()+bordure));
		mCurrentPosition = target;
	}
	
	//Méthode appelée lorsque l'on passe par notre QG
	public void encaisser(){
		//TODO
		Log.i("GAME","ENCAISSER!!!");
	}

	public Sprite getView(){
		return this.mView;
	}
	
	public HeadQuarters getHeadQuarters() {
		return mHeadQuarters;
	}
	
	public long getId() {
		return mIdentifier;
	}

	public void setAmount(int amount) {
		this.mMoney = amount;
	}

	public int getAmount() {
		return mMoney;
	}

	public void setLogistic(int logistic) {
		this.mLogistics = logistic;
	}

	public int getLogistic() {
		return mLogistics;
	}

	public void setActors(int actors) {
		this.mActors = actors;
	}

	public int getActors() {
		return mActors;
	}

	public void setPosition(Case currentPosition) {
		this.mCurrentPosition = currentPosition;
	}

	public Case getPosition() {
		return mCurrentPosition;
	}

	public void addProperty(OwnableCell cell) {
		mProperties.add(cell);
		cell.setOwner(this);
	}

	public void removeProperty(OwnableCell cell) {
		mProperties.remove(cell);
	}
  
	public Color getColorCase() {
		return mColorCell;
	}
  
	public Color getColorPawn() {
		return mColorPawn;
  	}

}
