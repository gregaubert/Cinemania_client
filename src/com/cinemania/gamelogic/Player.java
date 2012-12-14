package com.cinemania.gamelogic;

import static com.cinemania.constants.AllConstants.OFFSET;
import static com.cinemania.constants.AllConstants.PLAYER_COLOR;
import static com.cinemania.constants.AllConstants.PLAYER_COLOR_ANDROID;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.cinemania.activity.Base;
import com.cinemania.cases.Cell;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.OwnableCell;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class Player implements JSonator{

	private final int bordure = 10;

	private ArrayList<OwnableCell> mProperties = new ArrayList<OwnableCell>();
	private ArrayList<Script> mScripts = new ArrayList<Script>();
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
	
	//Dernier fois où l'on a visité le QG
	private int mLastTurn;
	
	private GameContext mGameContext;
	
	public Player(long identifier, int order, String name, int money, int actors, int logistics, int lastTurn, HeadQuarters headQuarters, Cell currentPosition) {
		mIdentifier = identifier;
		mOrder = order;
		mName = name;
		mColorCell = PLAYER_COLOR[1][order];
		mColorPawn = PLAYER_COLOR[0][order];
		mColorAndroid = PLAYER_COLOR_ANDROID[order];
		mMoney = money;
		mActors = actors;
		mLogistics = logistics;
		mLastTurn = lastTurn;
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
				player.getInt("lastTurn"),
				headQuarters,
				currentPosition);
		
		// Because the board game and all related cells are generated before without any reference to any player,		
		JSONArray jsonProperties = player.getJSONArray("properties");
		for (int j = 0; j < jsonProperties.length(); j++) {
			this.addProperty((OwnableCell)mGameContext.getCases()[jsonProperties.getInt(j)]);
		}
		
		JSONArray jsonScripts = player.getJSONArray("scripts");
		for (int i = 0; i < jsonScripts.length(); i++) {
			this.addScript(new Script(jsonScripts.getJSONObject(i)));
		}
		
		this.getHeadQuarters().setOwner(this);
	}
	
	public void Move(int nb){
	  
		//int pos = mBoard.findCaseIndex(mCurrentPosition);
	
		IEntityModifier [] entity = new IEntityModifier[nb+1];
		
		for(int i = 0; i < nb; i++){
			
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
		
		}
		
		DelayModifier dMod = new DelayModifier(0.5f);
		dMod.addModifierListener(new IModifierListener<IEntity>() {
		    @Override
		    public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
		    }
		 
		    @Override
		    public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
		    	//Le joueur arrive sur une position.
		    	Player.this.getPosition().onTheCell(Player.this);
		    }
		});
		
		entity[nb] = dMod;
		
		SequenceEntityModifier sem = new SequenceEntityModifier(entity);
		
		mView.registerEntityModifier(sem);
	}
	
	//Methode appelee lorsque l'on passe par notre QG
	public void encaisser(){
		if(mGameContext.isCreator())
			mGameContext.addYear();
		
		//TODO calcul profit.
		
		this.setLastTurn(mGameContext.getCurrentTurn());
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

	public int getLastTurn() {
		return mLastTurn;
	}
	
	public void setLastTurn(int lastTurn){
		this.mLastTurn = lastTurn;
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
	
	public ArrayList<Script> getScripts(){
		return mScripts;
	}
	
	public int getScriptCount(){
		return mScripts.size();
	}
	
	public void addScript(Script script){
		mScripts.add(script);
	}
	
	public Script removeScript(){
		assert mScripts.size()>0;
		return mScripts.remove(0);
	}
	
	public boolean removeScript(Script script){
		return mScripts.remove(script);
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
		player.put("lastTurn", this.getLastTurn());
		
		JSONArray scripts = new JSONArray();
		for(Script s : this.getScripts())
			scripts.put(s.toJson());
		
		player.put("scripts", scripts);
		
		return player;
	}

}
