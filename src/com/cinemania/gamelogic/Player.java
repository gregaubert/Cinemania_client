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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.cells.Cell;
import com.cinemania.cells.Cinema;
import com.cinemania.cells.HeadQuarters;
import com.cinemania.cells.LogisticFactory;
import com.cinemania.cells.OwnableCell;
import com.cinemania.cells.School;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.interfaces.JSonator;
import com.cinemania.network.GameContext;
import com.cinemania.resources.ResourcesManager;

public class Player implements JSonator{

	private final int bordure = 10;

	private ArrayList<OwnableCell> mProperties = new ArrayList<OwnableCell>();
	private ArrayList<Script> mScripts = new ArrayList<Script>();
	private ArrayList<Movie> mMovies = new ArrayList<Movie>();
	private HeadQuarters mHeadQuarters;
	private Cell mCurrentPosition;
	private String mIdentifier;
	private int mOrder;
	private String mName;
	private Color mColorCell;
	private Color mColorPawn;
	private int mColorAndroid;
	private int mMoney;
	private int mActors;
	private int mLogistics;
	private boolean mCanBuyAuthorFilm;
	private Sprite mView;
	
	//Last time when we visite the QG
	private int mLastTurn;
	
	private int mLastProfit;
	private int mLastActors;
	private int mLastLogistics;
	
	private GameContext mGameContext;
	
	public Player(String identifier, int order, String name, int money, int actors, int logistics, int lastTurn, int lastProfit, int lastActors, int lastLogistics, boolean canBuyAuthorFilm, HeadQuarters headQuarters, Cell currentPosition) {
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
		mLastActors = lastActors;
		mLastLogistics = lastLogistics;
		mCanBuyAuthorFilm = canBuyAuthorFilm;
		mHeadQuarters = headQuarters;
		mCurrentPosition = currentPosition;
		mView = new Sprite(mCurrentPosition.getX() + bordure + mOrder * OFFSET, 
							mCurrentPosition.getY() + bordure, 
							ResourcesManager.getInstance().mPlayer, 
							Base.getSharedInstance().getVertexBufferObjectManager());
		mView.setSize(mView.getWidth()- 2 * bordure, mView.getHeight() - 2 * bordure);
		mView.setColor(this.mColorPawn);
		
		this.mGameContext = GameContext.getSharedInstance();
	}

	public Player(JSONObject player, int order, HeadQuarters headQuarters, Cell currentPosition) throws JSONException{
		this(	player.getString("id"),
				order,
				player.getString("name"),
				player.getInt("money"),
				player.getInt("actors"),
				player.getInt("logistics"),
				player.getInt("lastTurn"),
				player.getInt("lastProfit"),
				player.getInt("lastActors"),
				player.getInt("lastLogistics"),
				player.getBoolean("canBuyAuthorFilm"),
				headQuarters,
				currentPosition);
		
		// Because the board game and all related cells are generated before without any reference to any player,		
		JSONArray jsonProperties = player.getJSONArray("properties");
		for (int j = 0; j < jsonProperties.length(); j++) {
			addProperty((OwnableCell)mGameContext.getCases()[jsonProperties.getInt(j)]);
		}
		
		JSONArray jsonScripts = player.getJSONArray("scripts");
		for (int i = 0; i < jsonScripts.length(); i++) {
			addScript(new Script(jsonScripts.getJSONObject(i)));
		}
		
		JSONArray jsonMovies = player.getJSONArray("movies");
		for (int i = 0; i < jsonMovies.length(); i++) {
			if(jsonMovies.getJSONObject(i).has("producer"))
				addMovie(new BigMovie(jsonMovies.getJSONObject(i)));
			else
				addMovie(new AuthorMovie(jsonMovies.getJSONObject(i)));
		}
		
		getHeadQuarters().setOwner(this);
	}
	
	public void Move(int nb){
		
		if(mGameContext.isCreator())
			mGameContext.completeTurn();
		
		IEntityModifier [] entity = new IEntityModifier[nb+1];
		
		for(int i = 0; i < nb; i++){
			
			Cell temp = mGameContext.nextCellOf(mCurrentPosition);
			//Cell temp = mGameContext.getCase((38+i)%40);
			//Movement from case to case. The pawn use an offset
			MoveModifier mm = new MoveModifier(0.2f, 
												mCurrentPosition.getX() + bordure + mOrder * OFFSET, 
												temp.getX() + bordure + mOrder * OFFSET, 
												mCurrentPosition.getY() + bordure, 
												temp.getY()+bordure);
		    mm.setAutoUnregisterWhenFinished(true);
		    
		    entity[i] = mm;
		    
		    Log.i("GAME","Deplacement de : " +(mCurrentPosition.getX()+bordure) + " ," + (temp.getX()+bordure) + " a " + (mCurrentPosition.getY()+bordure) + " ," + (temp.getY()+bordure));
		    
			//Test if we pass in our QG.
			if(temp == this.mHeadQuarters)
			{
				this.encaisser();
			}
			
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
		
		ResourcesManager.getInstance().mSndCashMachine.stop();
		ResourcesManager.getInstance().mSndCashMachine.play();
		double lvlCinema = 0.0;
		int profitCinema = 0,
			profitMovies = 0,
			profitActors = 0,
			profitLogistic = 0;

		for(OwnableCell cell : mProperties)
			if(cell instanceof Cinema)
			{
				switch(cell.getLevel()){
				case 0:
					lvlCinema += 0;
					break;
				case 1:
					lvlCinema += 1;
					break;
				case 2:
					lvlCinema += 1.15;
					break;
				case 3:
					lvlCinema += 1.4;
					break;				
				}
				
				lvlCinema += cell.getLevel();
				// FIXME: prochaines release
				// Pour l'instant, juste les charges des salles/cinémas, tant qu'il n'y a pas de salle où l'on
				// peut affecter des films
				profitCinema += ((Cinema)cell).profit(this.getLastTurn(), mGameContext.getCurrentTurn());
			}
			else if(cell instanceof School)
			{
				profitActors +=	((School)cell).profit(this.getLastTurn(), mGameContext.getCurrentTurn());
			}
			else if(cell instanceof LogisticFactory)
			{
				profitLogistic += ((LogisticFactory)cell).profit(this.getLastTurn(), mGameContext.getCurrentTurn());
			}
		
		for(Movie movie : getMovies()){
			Log.d("GAME", "(last, current,) -> profit ;;; ("+this.getLastTurn() +", " + mGameContext.getCurrentTurn() + ")->> " + movie.profit(this.getLastTurn(), mGameContext.getCurrentTurn()));
			profitMovies += movie.profit(this.getLastTurn(), mGameContext.getCurrentTurn());			
		}
		
		// maj profit
		mLastProfit = (int)(lvlCinema * (double)profitMovies) + profitCinema;
		mLastActors = profitActors;
		mLastLogistics = profitLogistic;
		
		//Affiche une boîte de dialogue avec les gains.
		final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
		dialogBuilder.setCancelable(true);
		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.profit, null);
		dialogBuilder.setView(view);

		TextView txtMovies = (TextView) view.findViewById(R.id.txtNbMovies);
		TextView txtCinema = (TextView) view.findViewById(R.id.txtNbCinema);
		TextView txtActor = (TextView) view.findViewById(R.id.txtNbActor);
		TextView txtLogistic = (TextView) view.findViewById(R.id.txtNbLogistic);
		TextView txtBonus = (TextView) view.findViewById(R.id.txtNbBonus);
		
		txtMovies.setText(Integer.toString((int)(lvlCinema * (double)profitMovies)));
		txtCinema.setText(Integer.toString(profitCinema));
		txtActor.setText(Integer.toString(mLastActors));
		txtLogistic.setText(Integer.toString(mLastLogistics));
		txtBonus.setText(Integer.toString(AllConstants.BONUS_AMOUT));
		
		dialogBuilder.setPositiveButton(R.string.btn_close, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		dialogBuilder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				Log.i("GAME", "Cancel");
				//TODO Remettre thread en marche.
			}
		});
		
		Base.getSharedInstance().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				AlertDialog dialog = dialogBuilder.create();
				dialog.show();
			}
		});
		
		
		receiveMoney(mLastProfit);
		receiveMoney(AllConstants.BONUS_AMOUT);
		receiveActors(mLastActors);
		receiveLogistic(mLastLogistics);
		setCanBuyAuthorFilm(true);
		setLastTurn(mGameContext.getCurrentTurn());
	}
	
	public void payOpponent(Player opponent, int amount){
		looseMoney(amount);
		opponent.receiveMoney(amount);
	}

	public Sprite getView(){
		return this.mView;
	}

	public int getOrder(){
		return this.mOrder;
	}
	
	public String getName(){
		return this.mName;
	}
	
	public HeadQuarters getHeadQuarters() {
		return mHeadQuarters;
	}
	
	public String getId() {
		return mIdentifier;
	}
	
	public void receiveMoney(int amount){
		setAmount(getAmount()+amount);
	}
	
	public void looseMoney(int amount){
		setAmount(getAmount()-amount);
	}

	public void setAmount(int amount) {
		mMoney = amount;
		if(GameContext.getSharedInstance().getPlayer().equals(this))
			Base.getSharedInstance().getHUD().setMoney(getAmount());
	}

	public int getAmount() {
		return mMoney;
	}
	
	public int getLastProfit(){
		return mLastProfit;
	}
	
	public int getLastActors(){
		return mLastActors;
	}
	
	public int getLastLogistics(){
		return mLastLogistics;
	}

	public void setLogistic(int logistic) {
		mLogistics = logistic;

		if(GameContext.getSharedInstance().getPlayer().equals(this))
			Base.getSharedInstance().getHUD().setLogistics(getLogistic());
	}

	public int getLogistic() {
		return mLogistics;
	}

	public void receiveLogistic(int logistic) {
		setLogistic(getLogistic() + logistic);
	}
	
	public void looseLogistic(int logistic){
		setLogistic(getLogistic() - logistic);
	}
	
	public int getLastTurn() {
		return mLastTurn;
	}
	
	public void setLastTurn(int lastTurn){
		this.mLastTurn = lastTurn;
	}
	
	public void receiveActors(int actors) {
		setActors(getActors() + actors);
	}
	
	public void looseActors(int actors) {
		setActors(getActors() - actors);
	}
	
	public void setActors(int actors) {
		mActors = actors;

		if(GameContext.getSharedInstance().getPlayer().equals(this))
			Base.getSharedInstance().getHUD().setActors(getActors());
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
	
	public void addMovie(Movie movie){
		mMovies.add(movie);
	}
	
	public ArrayList<Script> getScripts(){
		return mScripts;
	}
	
	public int getScriptCount(){
		return mScripts.size();
	}
	
	public boolean getCanBuyAuthorFilm() {
		return mCanBuyAuthorFilm;
	}
	
	public void setCanBuyAuthorFilm(boolean canBuyAuthorFilm) {
		mCanBuyAuthorFilm = canBuyAuthorFilm;
	}
	
	public void addScript(Script script){
		mScripts.add(script);

		if(GameContext.getSharedInstance().getPlayer().equals(this))
			Base.getSharedInstance().getHUD().setScripts(getScriptCount());
	}
	
	public Script removeScript(){
		assert mScripts.size()>0;

		if(GameContext.getSharedInstance().getPlayer().equals(this))
			Base.getSharedInstance().getHUD().setScripts(getScriptCount()-1);
		
		return mScripts.remove(0);
	}
	
	public boolean removeScript(Script script){
		boolean result = mScripts.remove(script); 
		if(GameContext.getSharedInstance().getPlayer().equals(this) && result)
			Base.getSharedInstance().getHUD().setScripts(getScriptCount());
		return result;
	}

	@Override
	public JSONObject toJson() throws JSONException {
		JSONObject player = new JSONObject();
		player.put("id", getId());
		player.put("name", getName());
		player.put("hq", mGameContext.findCaseIndex(getHeadQuarters()));
		player.put("position",mGameContext.findCaseIndex(getPosition()));
		
		JSONArray prop = new JSONArray();
		
		for(OwnableCell o : mProperties)
			prop.put(mGameContext.findCaseIndex(o));
		
		player.put("properties", prop);
		player.put("money", getAmount());
		player.put("actors", getActors());
		player.put("logistics", getLogistic());
		player.put("lastTurn", getLastTurn());
		player.put("lastProfit", getLastProfit());
		player.put("lastActors", getLastActors());
		player.put("lastLogistics", getLastLogistics());
		player.put("canBuyAuthorFilm", getCanBuyAuthorFilm());
		
		JSONArray scripts = new JSONArray();
		for(Script s : getScripts())
			scripts.put(s.toJson());
		
		player.put("scripts", scripts);
		
		JSONArray movies = new JSONArray();
		for(Movie m : getMovies())
			movies.put(m.toJson());
		
		player.put("movies", movies);
		
		return player;
	}
	
	public int getNbCinema(){
		int nb = 0;
		for(OwnableCell o : mProperties){
			if(o instanceof Cinema)
				nb++;
		}
		return nb;
	}

}
