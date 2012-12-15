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
import com.cinemania.cases.Cinema;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.LogisticFactory;
import com.cinemania.cases.OwnableCell;
import com.cinemania.cases.School;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class Player implements JSonator{

	private final int bordure = 10;

	private ArrayList<OwnableCell> mProperties = new ArrayList<OwnableCell>();
	private ArrayList<Script> mScripts = new ArrayList<Script>();
	private ArrayList<Movie> mMovies = new ArrayList<Movie>();
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
	
	//Last time when we visite the QG
	private int mLastTurn;
	
	private int mLastProfit;
	
	private GameContext mGameContext;
	
	public Player(long identifier, int order, String name, int money, int actors, int logistics, int lastTurn, int lastProfit, HeadQuarters headQuarters, Cell currentPosition) {
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
		mLastProfit = lastProfit;
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
				player.getInt("lastProfit"),
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
	  
		IEntityModifier [] entity = new IEntityModifier[nb+1];
		
		for(int i = 0; i < nb; i++){
			
			Cell temp = mGameContext.nextCellOf(mCurrentPosition);

			//Movement from case to case. The pawn use an offset
			MoveModifier mm = new MoveModifier(0.2f, mCurrentPosition.getX()+bordure+mOrder*OFFSET, temp.getX()+bordure+mOrder*OFFSET, mCurrentPosition.getY()+bordure, temp.getY()+bordure);
		    mm.setAutoUnregisterWhenFinished(true);
		    
		    entity[i] = mm;
		    
		    Log.i("GAME","D�placement de : " +(mCurrentPosition.getX()+bordure) + " ," + (temp.getX()+bordure) + " � " + (mCurrentPosition.getY()+bordure) + " ," + (temp.getY()+bordure));
		    
			//Test if we pass in our QG.
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
		    	//The player arrive on a cell.
		    	Player.this.getPosition().onTheCell(Player.this);
		    }
		});
		
		entity[nb] = dMod;
		
		SequenceEntityModifier sem = new SequenceEntityModifier(entity);
		
		mView.registerEntityModifier(sem);
	}
	
	//When we pass our QG we can get all the profit.
	public void encaisser(){
		if(mGameContext.isCreator())
			mGameContext.completeTurn();
		
		int lvlCinema = 0;
		//Count the number of cinema.
		for(OwnableCell cell : mProperties)
			if(cell instanceof Cinema)
			{
				lvlCinema += ((Cinema)cell).getLevel();
				this.receiveMoney(((Cinema)cell).profit(this.getLastTurn(), mGameContext.getCurrentTurn()));
			}
			else if(cell instanceof School)
			{
				this.receiveActors(((School)cell).profit(this.getLastTurn(), mGameContext.getCurrentTurn()));
			}
			else if(cell instanceof LogisticFactory)
			{
				this.receiveLogistic(((LogisticFactory)cell).profit(this.getLastTurn(), mGameContext.getCurrentTurn()));
			}
		
		int nbMovie = 0;
		
		//TODO profit de tous les films * lvlCinema
		this.receiveMoney(lvlCinema*nbMovie);
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
		setAmount(this.getAmount()+amount);
	}
	
	public void looseMoney(int amount){
		setAmount(this.getAmount()-amount);
	}

	public void setAmount(int amount) {
		this.mMoney = amount;
		Base.getSharedInstance().getHUD().setMoney(this.mMoney);
	}

	public int getAmount() {
		return mMoney;
	}
	
	public int getLastProfit(){
		return this.mLastProfit;
	}

	public void setLogistic(int logistic) {
		this.mLogistics = logistic;
		Base.getSharedInstance().getHUD().setLogistics(this.mLogistics);
	}

	public int getLogistic() {
		return mLogistics;
	}

	public void receiveLogistic(int logistic) {
		setLogistic(this.getLogistic()+logistic);
	}
	
	public int getLastTurn() {
		return mLastTurn;
	}
	
	public void setLastTurn(int lastTurn){
		this.mLastTurn = lastTurn;
	}
	
	public void receiveActors(int actors) {
		setLogistic(actors);
	}
	
	public void setActors(int actors) {
		this.mActors = actors;
		Base.getSharedInstance().getHUD().setActors(this.getActors() + actors);
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
	
	public ArrayList<Movie> getMovies(){
		return mMovies;
	}
	
	public void addScript(Movie movie){
		mMovies.add(movie);
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
		player.put("lastProfit", this.getLastProfit());
		
		JSONArray scripts = new JSONArray();
		for(Script s : this.getScripts())
			scripts.put(s.toJson());
		
		player.put("scripts", scripts);
		
		return player;
	}

}
