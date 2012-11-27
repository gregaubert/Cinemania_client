package com.cinemania.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cinemania.gamelogic.Board;
import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.Room;
import com.cinemania.cases.Case;
import com.cinemania.cases.Cinema;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.LogisticFactory;
import com.cinemania.cases.LuckyCase;
import com.cinemania.cases.OwnableCell;
import com.cinemania.cases.School;
import com.cinemania.cases.Script;
import com.cinemania.constants.AllConstants;

public final class GameContext {
	
	// FIXME: These data have to be saved localy
	private static long LOCAL_IDENTIFIER = 1001;
	
	private Player[] mPlayers;
	private Player mPlayer;
	private Player mCurrentPlayer;
	private Board mBoard;
	
	private long mGameIdentifier;
	private int mCurrentTurn;
	private int mYear;
	
	
	private GameContext() { }
	
	public static GameContext deserialize(String jsonString) throws JSONException {
		// Extact each JSON object from serialized data
		JSONObject json = new JSONObject(jsonString);
		JSONObject jsonGame = json.getJSONObject("game");
		JSONArray jsonPlayers = json.getJSONArray("players");
		JSONArray jsonBoard = json.getJSONArray("board");
		// Generate a game context with all JSON informations
		GameContext c = new GameContext();
		deserializeGame(c, jsonGame);
		deserializeBoard(c, jsonBoard);
		deserializePlayers(c, jsonPlayers, jsonGame);
		return c;
	}
	
	private static void deserializeGame(GameContext c, JSONObject jsonGame) throws JSONException {
		c.mGameIdentifier = jsonGame.getLong("id");
		c.mCurrentTurn = jsonGame.getInt("turn");
	}
	
	private static void deserializeBoard(GameContext c, JSONArray jsonBoard) throws JSONException {
		// FIXME: Need to be discussed
		// Case[] boardCases = new Case[AllConstants.BOARD_SIZE];
		Case[] boardCases = new Case[jsonBoard.length()];
		// Generate board's cells
		for (int i = 0; i < jsonBoard.length(); i++) {
			JSONObject jsonCell = jsonBoard.getJSONObject(i);
			Case cell = null;
			switch (jsonCell.getInt("type")) {
				case HeadQuarters.TYPE:
					cell = new HeadQuarters(jsonCell.getInt("level"));
					break;
				case Script.TYPE:
					cell = new Script();
					break;
				case LuckyCase.TYPE:
					cell = new LuckyCase();
					break;
				case Cinema.TYPE:
					// Generate cinema's rooms
					JSONArray jsonRooms = jsonCell.getJSONArray("rooms");
					Room[] rooms = new Room[jsonRooms.length()];
					for (int j = 0; j < jsonRooms.length(); j++) {
						// TODO: Define movie from identifier
						jsonRooms.getInt(j);
						rooms[j] = new Room();
					}
					cell = new Cinema(rooms);
					break;
				case School.TYPE:
					cell = new School(jsonCell.getInt("level"));
					break;
				case LogisticFactory.TYPE:
					cell = new LogisticFactory(jsonCell.getInt("level"));
					break;
				default:
					assert false;
			}
			boardCases[i] = cell;
		}
		c.mBoard = new Board(boardCases);
	}
	
	private static void deserializePlayers(GameContext c, JSONArray jsonPlayers, JSONObject jsonGame) throws JSONException {
		Case[] board = c.mBoard.getCases();
		c.mPlayers = new Player[jsonPlayers.length()];
		for (int i = 0; i < jsonPlayers.length(); i++) {
			JSONObject jsonPlayer = jsonPlayers.getJSONObject(i);
			assert board[jsonPlayer.getInt("hq")] instanceof HeadQuarters;
			Player player = new Player(
					jsonPlayer.getLong("id"),
					i,
					jsonPlayer.getString("name"),
					jsonPlayer.getInt("money"),
					jsonPlayer.getInt("actors"),
					jsonPlayer.getInt("logistics"),
					(HeadQuarters)board[jsonPlayer.getInt("hq")],
					board[jsonPlayer.getInt("position")]);
			// Because the board game and all related cells are generated before without any reference to any player,
			// we have to link the player and his properties afterward
			JSONArray jsonProperties = jsonPlayer.getJSONArray("properties");
			for (int j = 0; j < jsonProperties.length(); j++) {
				assert board[jsonProperties.getInt(i)] instanceof OwnableCell;
				player.addProperty((OwnableCell)board[jsonProperties.getInt(i)]);
			}
			player.getHeadQuarters().setOwner(player);
			c.mPlayers[i] = player;
			// Define local user
			if (player.getId() == LOCAL_IDENTIFIER) {
				c.mPlayer = player;
			}
			// Define who is playing
			if (player.getId() == jsonGame.getLong("player")) {
				c.mCurrentPlayer = player;
			}
		}
	}
	
	public String serialize() {
		return "";
	}
	
	
	/**
	 * Détermine si le tour est au joueur local
	 */
	public boolean isLocalTurn() {
		return mPlayer == mCurrentPlayer;
	}
	
	
	public long getLocalIdentifier() {
		return GameContext.LOCAL_IDENTIFIER;
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
	
	public Board getBoard() {
		return mBoard;
	}
	
	public long getGameIdentifier() {
		return mGameIdentifier;
	}

	public int getCurrentTurn() {
		return mCurrentTurn;
	}

	public int getYear() {
		return mYear;
	}
	
	
	// FIXME: Only for tests
	public static String test1() {
		return "{ \"version\": 1, \"game\": { \"id\": 123456, \"turn\": 1, \"player\": 0 }, \"board\": [ { \"type\": 1, \"level\": 2 }, { \"type\": 1, \"level\": 3 }, { \"type\": 2 }, { \"type\": 5, \"level\": 0 } ], \"players\": [ { \"id\": 12345, \"name\": \"Mok\",  \"hq\": 0, \"position\": 0, \"properties\": [ 3 ], \"money\": 1000, \"actors\": 2, \"logistics\": 10 }, { \"id\": 4321, \"name\": \"Bof\", \"hq\": 1, \"position\": 1, \"properties\": [], \"money\": 223, \"actors\": 3, \"logistics\": 4 } ] }";
	}
	
	// FIXME: Only for tests
	public static String test2() {
		try {
			int[] cellIdentifiers = Board.generate();
			int offset = 0;
			JSONArray jsonPlayers = new JSONArray();
			// Player 1
			offset = next(cellIdentifiers, offset);
			JSONObject player1 = new JSONObject();
			player1.put("id", 1001);
			player1.put("name", "player1");
			player1.put("hq", offset);
			player1.put("position", offset);
			player1.put("properties", new JSONArray());
			player1.put("money", 1000);
			player1.put("actors", 12);
			player1.put("logistics", 10);
			offset += 1;
			// Player 2
			offset = next(cellIdentifiers, offset);
			JSONObject player2 = new JSONObject();
			player2.put("id", 2001);
			player2.put("name", "player2");
			player2.put("hq", offset);
			player2.put("position", offset);
			player2.put("properties", new JSONArray());
			player2.put("money", 1000);
			player2.put("actors", 12);
			player2.put("logistics", 10);
			offset += 1;
			// Player 3
			offset = next(cellIdentifiers, offset);
			JSONObject player3 = new JSONObject();
			player3.put("id", 3001);
			player3.put("name", "player3");
			player3.put("hq", offset);
			player3.put("position", offset);
			player3.put("properties", new JSONArray());
			player3.put("money", 1000);
			player3.put("actors", 12);
			player3.put("logistics", 10);
			offset += 1;
			// Player 4
			offset = next(cellIdentifiers, offset);
			JSONObject player4 = new JSONObject();
			player4.put("id", 4001);
			player4.put("name", "player4");
			player4.put("hq", offset);
			player4.put("position", offset);
			player4.put("properties", new JSONArray());
			player4.put("money", 1000);
			player4.put("actors", 12);
			player4.put("logistics", 10);
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
						jsonCell.put("level", 1);
						break;
					case Script.TYPE:
						jsonCell.put("type", Script.TYPE);
						break;
					case LuckyCase.TYPE:
						jsonCell.put("type", LuckyCase.TYPE);
						break;
					case Cinema.TYPE:
						jsonCell.put("type", Cinema.TYPE);
						jsonCell.put("rooms", new JSONArray());
						break;
					case School.TYPE:
						jsonCell.put("type", School.TYPE);
						jsonCell.put("level", 1);
						break;
					case LogisticFactory.TYPE:
						jsonCell.put("type", LogisticFactory.TYPE);
						jsonCell.put("level", 1);
						break;
				}
				jsonBoard.put(jsonCell);
			}
			// Game
			JSONObject jsonGame = new JSONObject();
			jsonGame.put("player", 0);
			jsonGame.put("turn", 1);
			jsonGame.put("id", 1234567);
			JSONObject json = new JSONObject();
			json.put("version", 1);
			json.put("game", jsonGame);
			json.put("players", jsonPlayers);
			json.put("board", jsonBoard);
			return json.toString();
		} catch (JSONException e) {
			
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
}
