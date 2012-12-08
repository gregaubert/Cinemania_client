package com.cinemania.gamelogic;

import static com.cinemania.constants.AllConstants.OFFSET;
import static com.cinemania.constants.AllConstants.PLAYER_COLOR;
import static com.cinemania.constants.AllConstants.PLAYER_COLOR_ANDROID;

import java.util.ArrayList;

import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.cases.Cell;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.OwnableCell;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class Player implements JSonator{

	private final int bordure = 10;

	private ArrayList<OwnableCell> mProperties = new ArrayList<OwnableCell>();
	private HeadQuarters mHeadQuarters;
	private Cell mCurrentPosition;
	private long mIdentifier;
	private int mOrder;
	private String mName;
	private Color mColorCell;
	private Color mColorPawn;
	private int mColorAndroid;
	private int mMoney;
	private int mActors;
	private int mLogistics;
	private Sprite mView;	
	
	private GameContext mGameContext;
	
	public Player(long identifier, int order, String name, int money, int actors, int logistics, HeadQuarters headQuarters, Cell currentPosition) {
		mIdentifier = identifier;
		mOrder = order;
		mName = name;
		mColorCell = PLAYER_COLOR[1][order];
		mColorPawn = PLAYER_COLOR[0][order];
		mColorAndroid = PLAYER_COLOR_ANDROID[order];
		mMoney = money;
		mActors = actors;
		mLogistics = logistics;
		mHeadQuarters = headQuarters;
		mCurrentPosition = currentPosition;
		mView = new Sprite(mCurrentPosition.getX()+bordure, mCurrentPosition.getY()+bordure, ResourcesManager.getInstance().mPlayer, Base.getSharedInstance().getVertexBufferObjectManager());
		mView.setSize(mView.getWidth()-2*bordure, mView.getHeight() - 2*bordure);
		mView.setColor(this.mColorPawn);
		
		this.mGameContext = GameContext.getSharedInstance();
	}

	public Player(JSONObject player, int order, HeadQuarters headQuarters, Cell currentPosition) throws JSONException{
		this(
				player.getLong("id"),
				order,
				player.getString("name"),
				player.getInt("money"),
				player.getInt("actors"),
				player.getInt("logistics"),
				headQuarters,
				currentPosition);
		
		// Because the board game and all related cells are generated before without any reference to any player,		
		JSONArray jsonProperties = player.getJSONArray("properties");
		for (int j = 0; j < jsonProperties.length(); j++) {
			this.addProperty((OwnableCell)mGameContext.getCases()[jsonProperties.getInt(j)]);
		}
		this.getHeadQuarters().setOwner(this);
		
	}
	
	public void Move(int nb){
	  
		//int pos = mBoard.findCaseIndex(mCurrentPosition);
	
		IEntityModifier [] entity = new IEntityModifier[nb];
		
		int i = 0;
		
		while(i < nb){
			//pos = (pos+1)%mBoard.getSize();
			Cell temp = mGameContext.nextCellOf(mCurrentPosition);

			//Mouvement de la case courante, a la case suivant. Le pion est decalle
			MoveModifier mm = new MoveModifier(0.2f, mCurrentPosition.getX()+bordure+mOrder*OFFSET, temp.getX()+bordure+mOrder*OFFSET, mCurrentPosition.getY()+bordure, temp.getY()+bordure);
		    mm.setAutoUnregisterWhenFinished(true);
		    
		    entity[i] = mm;
		    
		    Log.i("GAME","Déplacement de : " +(mCurrentPosition.getX()+bordure) + " ," + (temp.getX()+bordure) + " à " + (mCurrentPosition.getY()+bordure) + " ," + (temp.getY()+bordure));
		    
			//Test si on passe au QG
			if(temp == this.mHeadQuarters)
				this.encaisser();
		  
			mCurrentPosition = temp;
			
			i++;		
		}
		
		SequenceEntityModifier sem = new SequenceEntityModifier(entity);
		
		mView.registerEntityModifier(sem);
	  
	}
	
	//Methode appelee lorsque l'on passe par notre QG
	public void encaisser(){
		//TODO
		Log.i("GAME","ENCAISSER!!!");
	}
	
	public void payOpponent(Player opponent, int amount){
		looseMoney(amount);
		opponent.receiveMoney(amount);
	}

	public Sprite getView(){
		return this.mView;
	}

	public String getName(){
		return this.mName;
	}
	
	public HeadQuarters getHeadQuarters() {
		return mHeadQuarters;
	}
	
	public long getId() {
		return mIdentifier;
	}
	
	public void receiveMoney(int amount){
		this.mMoney += amount;
	}
	
	public void looseMoney(int amount){
		this.mMoney -= amount;
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

	public void setPosition(Cell currentPosition) {
		this.mCurrentPosition = currentPosition;
	}

	public Cell getPosition() {
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

	public int getColorAndroid() {
		return mColorAndroid;
  	}


	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject player = new JSONObject();
		player.put("id", this.getId());
		player.put("name", this.getName());
		player.put("hq", mGameContext.findCaseIndex(this.getHeadQuarters()));
		player.put("position",mGameContext.findCaseIndex(this.getPosition()));
		
		JSONArray prop = new JSONArray();
		
		for(OwnableCell o : this.mProperties)
			prop.put(o.toJson());
		
		player.put("properties", prop);
		player.put("money", this.getAmount());
		player.put("actors", this.getActors());
		player.put("logistics", this.getLogistic());
		return player;
	}

}
