package com.cinemania.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Room;
import com.cinemania.network.api.API;
import com.cinemania.scenes.BoardScene;
import com.cinemania.activity.Base;
import com.cinemania.activity.R;
import com.cinemania.activity.Base.SceneType;
import com.cinemania.cells.Cell;
import com.cinemania.cells.CellGenerator;
import com.cinemania.cells.Chance;
import com.cinemania.cells.Cinema;
import com.cinemania.cells.HeadQuarters;
import com.cinemania.cells.LogisticFactory;
import com.cinemania.cells.OwnableCell;
import com.cinemania.cells.School;
import com.cinemania.cells.ScriptCell;
import com.cinemania.constants.AllConstants;

public final class GameContext {
	
	private Player[] mPlayers;
	//Nous-memes
	private Player mPlayer;
	//Joueur en train de jouer
	private Player mCurrentPlayer;
	private Cell[] mCases;
	
	private long mGameIdentifier;
	private int mCurrentTurn;
	private int mYear;
	
	//The different part of the JSON
	private JSONObject jsonGame;
	private JSONArray jsonPlayers;
	private JSONArray jsonBoard;
	
	//Singleton
    public static GameContext instance;
	
    public final int offsetYear = 1;
    
    // ===========================================================
    public static GameContext getSharedInstance() {
    	if(instance == null)
    		instance = new GameContext();
        return instance;
    }
    
	private GameContext() { }
	
	public void deserialize(String jsonString) throws JSONException {
		// Extact each JSON object from serialized data
		JSONObject json = new JSONObject(jsonString);
		jsonGame = json.getJSONObject("game");
		jsonPlayers = json.getJSONArray("players");
		jsonBoard = json.getJSONArray("board");
	}
	
	public void deserializeGame() throws JSONException {
		this.mGameIdentifier = jsonGame.getLong("id");
		this.mCurrentTurn = jsonGame.getInt("turn");
		this.mYear = jsonGame.getInt("year");
	}
	
	public void deserializeBoard(BoardScene boardScene) throws JSONException {

		mCases = new Cell[jsonBoard.length()];
		// Generate board's cells
		for (int i = 0; i < jsonBoard.length(); i++) {
			JSONObject jsonCell = jsonBoard.getJSONObject(i);
			//Recupere l'emplacement de la case
			float[] position = boardScene.calculateCasePosition(i);
			
			Cell cell = null;
			switch (jsonCell.getInt("type")) {
				case HeadQuarters.TYPE:
					cell = new HeadQuarters(jsonCell.getInt("level"),position[0], position[1]);
					break;
				case ScriptCell.TYPE:
					cell = new ScriptCell(position[0], position[1]);
					break;
				case Chance.TYPE:
					cell = new Chance(position[0], position[1]);
					break;
				case Cinema.TYPE:
					// Generate cinema's rooms
					JSONArray jsonRooms = jsonCell.getJSONArray("rooms");
					Room[] rooms = new Room[jsonRooms.length()];
					for (int j = 0; j < jsonRooms.length(); j++) {
						rooms[j] = new Room();
					}
					cell = new Cinema(rooms,position[0], position[1]);
					break;
				case School.TYPE:
					cell = new School(jsonCell.getInt("level"),position[0], position[1]);
					break;
				case LogisticFactory.TYPE:
					cell = new LogisticFactory(jsonCell.getInt("level"),position[0], position[1]);
					break;
				default:
					assert false;
			}
			mCases[i] = cell;
		}
	}
	
	public void deserializePlayers() throws JSONException {
		this.mPlayers = new Player[jsonPlayers.length()];

		for (int i = 0; i < jsonPlayers.length(); i++) {

			JSONObject jsonPlayer = jsonPlayers.getJSONObject(i);
			assert mCases[jsonPlayer.getInt("hq")] instanceof HeadQuarters;
			int indice = jsonPlayer.getInt("position");
			int HQ = jsonPlayer.getInt("hq");
			
			Player player = new Player(jsonPlayer, i, (HeadQuarters)mCases[HQ], mCases[indice]);
			
			Log.i("GAME", "Création du joueur " + player.getName() + " HQ : " + HQ + " pos : " + indice);
			
			this.mPlayers[i] = player;

			// Define local user
			if (player.getId().equals(Utilities.DEVICE_ID)) {
				this.mPlayer = player;
			}
			
			// Define who is playing
			if (player.getId().equals(jsonGame.getString("player"))) {
				this.mCurrentPlayer = player;
			}
		}
	}
	
	public String serialize() {
		try {
			
			JSONArray jsonPlayers = new JSONArray();
			//Ajout des differents players.
			for(Player p : this.mPlayers)
				jsonPlayers.put(p.toJson());
			
			// Board
			JSONArray jsonBoard = new JSONArray(); 
			
			for(Cell c : mCases)
				jsonBoard.put(c.toJson());
			
			// Game
			JSONObject jsonGame = new JSONObject();
			jsonGame.put("player",mPlayer.getId());
			jsonGame.put("turn", mCurrentTurn);
			jsonGame.put("id", mGameIdentifier);
			jsonGame.put("year", mYear);
			JSONObject json = new JSONObject();
			json.put("version", 1);
			json.put("game", jsonGame);
			json.put("players", jsonPlayers);
			json.put("board", jsonBoard);
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * On passe le tour, on envoit les infos au serveur.
	 */
	public void nextTurn() {	
		
		// Check if the player could perform this action
		// These checks are both done on the client and the server
		if (mCurrentPlayer == mPlayer) {
			API.gamePassTurn(mGameIdentifier, serialize());
		}
		
		
		//Base.getSharedInstance().getHUD().setCurrentPlayer(mCurrentPlayer);
	}
	
	/**
	 * Permet d'augmenter le tour d'un.
	 * 
	 * Tout les 4 tour, on augment l'année.
	 */
	public void completeTurn(){
		this.mCurrentTurn++;
		//TODO Je suis pas sur que ce soit bien de faire tout les 4 tours, ça fait un peu bizarre si y a que 1 ou 3 joueurs
		//if(mCurrentTurn%4==0)
		{
			this.mYear += offsetYear;
			Base.getSharedInstance().getHUD().setYear(getYear());
		}
	}
	
	/**
	 * Déermine si le tour est au joueur local
	 */
	public boolean isLocalTurn() {
		return mPlayer == mCurrentPlayer;
	}
	
	
	public String getLocalIdentifier() {
		return Utilities.DEVICE_ID;
	}
	
	public boolean isCreator(){
		return this.mPlayer == this.mPlayers[0];
	}
	
	public Player[] getPlayers() {
		return mPlayers;
	}
	
	public Player getPlayer() {
		return mPlayer;
	}
	
	public Player getCurrentPlayer() {
		return mCurrentPlayer;
	}
	
	public long getGameIdentifier() {
		return mGameIdentifier;
	}

	public int getCurrentTurn() {
		return mCurrentTurn;
	}

	public int getYear() {
		return mYear + AllConstants.INITIAL_YEAR;
	}
	
	public int getNumberOfYearSinceStart(){
		return mYear;
	}
	
	//Etat initial lors d'une nouvelle partie.
	public static String initialState() {
		try {
			int[] cellIdentifiers = CellGenerator.generate();
			int offset = 0;
			JSONArray jsonPlayers = new JSONArray();
			// Player 1
			offset = next(cellIdentifiers, offset);
			JSONObject player1 = new JSONObject();
			player1.put("id", -1);
			player1.put("name", "Player 1");
			player1.put("hq", offset);
			player1.put("position", offset);
			player1.put("properties", new JSONArray());
			player1.put("money", AllConstants.DEFAULT_AMOUNT);
			player1.put("actors", AllConstants.DEFAULT_ACTORS);
			player1.put("logistics", AllConstants.DEFAULT_LOGISTIC);
			player1.put("lastTurn", 0);
			player1.put("lastProfit",0);
			player1.put("lastActors",0);
			player1.put("lastLogistics",0);
			player1.put("canBuyAuthorFilm", true);
			player1.put("scripts", new JSONArray());
			player1.put("movies", new JSONArray());
			offset += 1;
			// Player 2
			offset = next(cellIdentifiers, offset);
			JSONObject player2 = new JSONObject();
			player2.put("id", -1);
			player2.put("name", "Player 2");
			player2.put("hq", offset);
			player2.put("position", offset);
			player2.put("properties", new JSONArray());
			player2.put("money", AllConstants.DEFAULT_AMOUNT);
			player2.put("actors", AllConstants.DEFAULT_ACTORS);
			player2.put("logistics", AllConstants.DEFAULT_LOGISTIC);
			player2.put("lastTurn", 0);
			player2.put("lastProfit",0);
			player2.put("lastActors",0);
			player2.put("lastLogistics",0);
			player2.put("canBuyAuthorFilm", true);
			player2.put("scripts", new JSONArray());
			player2.put("movies", new JSONArray());
			offset += 1;
			// Player 3
			offset = next(cellIdentifiers, offset);
			JSONObject player3 = new JSONObject();
			player3.put("id", -1);
			player3.put("name", "Player 3");
			player3.put("hq", offset);
			player3.put("position", offset);
			player3.put("properties", new JSONArray());
			player3.put("money", AllConstants.DEFAULT_AMOUNT);
			player3.put("actors", AllConstants.DEFAULT_ACTORS);
			player3.put("logistics", AllConstants.DEFAULT_LOGISTIC);
			player3.put("lastTurn", 0);
			player3.put("lastProfit",0);
			player3.put("lastActors",0);
			player3.put("lastLogistics",0);
			player3.put("canBuyAuthorFilm", true);
			player3.put("scripts", new JSONArray());
			player3.put("movies", new JSONArray());
			offset += 1;
			// Player 4
			offset = next(cellIdentifiers, offset);
			JSONObject player4 = new JSONObject();
			player4.put("id", -1);
			player4.put("name", "Player 4");
			player4.put("hq", offset);
			player4.put("position", offset);
			player4.put("properties", new JSONArray());
			player4.put("money", AllConstants.DEFAULT_AMOUNT);
			player4.put("actors", AllConstants.DEFAULT_ACTORS);
			player4.put("logistics", AllConstants.DEFAULT_LOGISTIC);
			player4.put("lastTurn", 0);
			player4.put("lastProfit",0);
			player4.put("lastActors",0);
			player4.put("lastLogistics",0);
			player4.put("canBuyAuthorFilm", true);
			player4.put("scripts", new JSONArray());
			player4.put("movies", new JSONArray());
			offset += 1;
			jsonPlayers.put(player1);
			jsonPlayers.put(player2);
			jsonPlayers.put(player3);
			jsonPlayers.put(player4);
			// Board
			JSONArray jsonBoard = new JSONArray(); 
			for (int i = 0; i < cellIdentifiers.length; i++) {
				JSONObject jsonCell = new JSONObject();
				switch (cellIdentifiers[i]) {
					case HeadQuarters.TYPE:
						jsonCell.put("type", HeadQuarters.TYPE);
						jsonCell.put("level", AllConstants.DEFAULT_HQ_LEVEL);
						break;
					case ScriptCell.TYPE:
						jsonCell.put("type", ScriptCell.TYPE);
						break;
					case Chance.TYPE:
						jsonCell.put("type", Chance.TYPE);
						break;
					case Cinema.TYPE:
						jsonCell.put("type", Cinema.TYPE);
						jsonCell.put("rooms", new JSONArray());
						break;
					case School.TYPE:
						jsonCell.put("type", School.TYPE);
						jsonCell.put("level", AllConstants.DEFAULT_RESOURCES_LEVEL_BF_BUY);
						break;
					case LogisticFactory.TYPE:
						jsonCell.put("type", LogisticFactory.TYPE);
						jsonCell.put("level", AllConstants.DEFAULT_RESOURCES_LEVEL_BF_BUY);
						break;
				}
				jsonBoard.put(jsonCell);
			}
			// Game
			JSONObject jsonGame = new JSONObject();
			jsonGame.put("player", -1);
			jsonGame.put("turn", 1);
			jsonGame.put("id", -1);
			jsonGame.put("year", 0);
			JSONObject json = new JSONObject();
			json.put("version", 1);
			json.put("game", jsonGame);
			json.put("players", jsonPlayers);
			json.put("board", jsonBoard);
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static int next(int[] identifiers, int offset) {
		for (int i = offset; i < identifiers.length; i++) {
			if (identifiers[i] == HeadQuarters.TYPE) {
				return i;
			}
		}
		return -1;
	}
	
	public Cell[] getCases() {
		return mCases;
	}
	
	public Cell getCase(int i){
		return mCases[i];
	}
	
	public int getSize(){
		return mCases.length;
	}

	public int findCaseIndex(Cell toFind) {
		
		for (int i = 0; i < mCases.length; i++){
			if (mCases[i] == toFind)
				return i;
		}
		
		return -1;
	}
	
	public Cell nextCellOf(Cell source) {
		int offset = findCaseIndex(source);
		offset = (offset + 1) % mCases.length;
		return mCases[offset];
	}
	
	public void setGameIdentifier(long gameIdentifier) {
		mGameIdentifier = gameIdentifier;
	}
	
	public void leaveGame(){
		API.gameLeave(mGameIdentifier);
	}
	
	public void checkLooseGame(){
		if (mCurrentPlayer.getAmount() < 0)
		{
			
			for(OwnableCell o :  mCurrentPlayer.getOwnableCell()){
				o.resetOwner();
			}
			
			API.gamePassTurn(mGameIdentifier, serialize());
			API.gameLeave(mGameIdentifier);
			
			final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Base.getSharedInstance());
    		dialogBuilder.setCancelable(true);
    		View view = Base.getSharedInstance().getLayoutInflater().inflate(R.layout.perdu, null);
    		dialogBuilder.setView(view);
    		
    		//On confirme le passage de tour.
    		dialogBuilder.setPositiveButton(R.string.btn_perdu, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
                	dialog.dismiss();
                		
                	Base a = Base.getSharedInstance();
                	a.setSceneType(SceneType.MENU);
                	a.setCurrentScene(a.getGameMenu());
				}
			});
    		
    		Base.getSharedInstance().runOnUiThread(new Runnable() {
    			@Override
    			public void run() {
    				AlertDialog dialog = dialogBuilder.create();
    				dialog.show();
    			}
    		});
		}
	}
}
