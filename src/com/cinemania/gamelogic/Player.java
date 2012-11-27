package com.cinemania.gamelogic;

import java.util.ArrayList;
import static com.cinemania.constants.AllConstants.*;

import org.andengine.util.color.Color;

import com.cinemania.cases.Case;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.OwnableCell;

public class Player {

	private ArrayList<OwnableCell> mProperties = new ArrayList<OwnableCell>();
	private HeadQuarters mHeadQuarters;
	private Case mCurrentPosition;
	private long mIdentifier;
	private int mOrder;
	private String mName;
	private Color mColor;
	private int mMoney;
	private int mActors;
	private int mLogistics;
	
	
	public Player(long identifier, int order, String name, int money, int actors, int logistics, HeadQuarters headQuarters, Case currentPosition) {
		mIdentifier = identifier;
		mOrder = order;
		mName = name;
		mColor = PLAYER_COLOR[order];
		mMoney = money;
		mActors = actors;
		mLogistics = logistics;
		mHeadQuarters = headQuarters;
		mCurrentPosition = currentPosition;
	}

	// FIXME: A supprimer
	/*
	public Player() {
		mIdentifier = generalId++;
		setAmount(DEFAULT_AMOUNT);
		setLogistic(DEFAULT_LOGISTIC);
		setActors(DEFAULT_ACTORS);
		properties = new ArrayList<Case>();
		mColor = PLAYER_COLOR[(int) (mIdentifier - 1)];
	}
	*/
	
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

	public int shootOneDice() {
		return (int) (Math.random() * 6) + 1;
	}

	public Color getColor() {
		return mColor;
	}

}
