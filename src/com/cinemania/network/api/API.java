package com.cinemania.network.api;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cinemania.network.GameContext;
import com.cinemania.network.Utilities;
import com.cinemania.network.gcm.CommonUtilities;
import com.cinemania.network.gcm.ServerUtilities;

public final class API {
	
	private static final String SERVER_LOCATION = "http://157.26.110.187/cinemania/index.php";

	private static final URL REGISTER_DEVICE_URL = createURL("/devices/register");
	private static final URL UNREGISTER_DEVICE_URL = createURL("/devices/unregister");
	private static final URL NEW_GAME_URL = createURL("/games/new");
	private static final URL AVAILABLE_GAMES_URL = createURL("/games/available");
	private static final URL JOIN_GAME_URL = createURL("/games/join");
	private static final URL PASSTURN_GAME_URL = createURL("/games/passturn");
	private static final URL DATA_GAME_URL = createURL("/games/data");
	
	// http://stackoverflow.com/questions/1866770/how-to-handle-a-static-final-field-initializer-that-throws-checked-exception
	private static URL createURL(String source) {
		try {
	        return new URL(SERVER_LOCATION + source);
	    } catch (final MalformedURLException e) {
	        throw new Error(e);
	    } 
	}
	
	/*
		/devices/register
		$device
		$key
		return: {"success":1,"message":"registered"}
	*/
	public static boolean registerDevice(String key) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("device", Utilities.DEVICE_ID);
		params.put("key", key);
		
		Utilities.Response response = Utilities.post(REGISTER_DEVICE_URL, params);
		return response.successful();
	}
	
	/*
		/devices/unregister
		$device
		return: {"success":1,"message":"unregistered"}
	 */
	public static boolean unregisterDevice() {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("device", Utilities.DEVICE_ID);
		
		Utilities.Response response = Utilities.post(UNREGISTER_DEVICE_URL, params);
		return response.successful();
	}
	
	/*
	 	/games/new
		$device
		$data
		(joins the device to the game!)
		return: {"success":1,"game":"123472"}
	 */
	public static GameIdentifierResult newGame() {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("device", Utilities.DEVICE_ID);
		params.put("data", GameContext.initialState());
		
		GameIdentifierResult result = new GameIdentifierResult();
		
		Utilities.Response response = Utilities.post(NEW_GAME_URL, params);
		// Check if any errors occurs
		if (!response.successful()) {
			result.mSuccessful = false;
		} else {
			try {
				result.mGameIdentifier = response.getJson().getInt("game");
			} catch (JSONException e) {
				result.mSuccessful = false;
			}
		}
		
		return result;
	}
	
	/*
		/games/available
		(shows a list of available games)
		return: ["12345", "89083", ...]
	 */
	public static GameListResult availableGames() {
		
		GameListResult result = new GameListResult();
		
		Utilities.Response response = Utilities.post(AVAILABLE_GAMES_URL, null);
		// Check if any errors occurs
		if (!response.successful()) {
			result.mSuccessful = false;
		} else {
			try {
				// Extract each available game's id from response
				JSONArray games = response.getJson().getJSONArray("games");
				result.mGames = new Long[games.length()];
				for (int i = 0; i < games.length(); i++) {
					result.mGames[i] = games.getLong(i);
				}
			} catch (JSONException e) {
				result.mSuccessful = false;
			}
		}
		
		return result;
	}
	
	/*
		/games/join
		$device
		$game
		(joins the device to the game)
		return: {"playerid":1,"success":1}
	 */
	public static PlayerResult joinGame(int game) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("device", Utilities.DEVICE_ID);
		params.put("game", Long.toString(game));
		
		PlayerResult result = new PlayerResult();
		
		Utilities.Response response = Utilities.post(JOIN_GAME_URL, params);
		// Check if any errors occurs
		if (!response.successful()) {
			result.mSuccessful = false;
		} else {
			try {
				result.mPlayer = response.getJson().getInt("playerid");
			} catch (JSONException e) {
				result.mSuccessful = false;
			}
		}
		
		return result;
	}
	
	/*
		/games/passturn
		$device
		$game
		$data
		(calls GCM) for all players
		return: {"currentPlayer":"1","turn":2,"success":1}
	 */
	public static boolean gamePassTurn(long gameIdentifier, String data) {
		
		// ICI on retourne quoi ?
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("device", Utilities.DEVICE_ID);
		params.put("game", Long.toString(gameIdentifier));
		params.put("data", data);
		
		Utilities.Response response = Utilities.post(PASSTURN_GAME_URL, params);
		// Check if any errors occurs
		return response.successful();
	}
	
	public static GameDataResult gameData(long gameIdentifier) {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("device", Utilities.DEVICE_ID);
		params.put("game", Long.toString(gameIdentifier));
		
		GameDataResult result = new GameDataResult();
		
		Utilities.Response response = Utilities.post(DATA_GAME_URL, params);
		// Check if any errors occurs
		if (!response.successful()) {
			result.mSuccessful = false;
		} else {
			try {
				result.mGameData = response.getJson().getString("data");
			} catch (JSONException e) {
				result.mSuccessful = false;
			}
		}
		
		return result;
	}

	public static final class GameIdentifierResult {
		private long mGameIdentifier;
		private boolean mSuccessful = true;
		private GameIdentifierResult() { }
		public long getGameIdentifier() { 
			return mGameIdentifier;
		}
		public boolean successful() {
			return mSuccessful;
		}
	}
	
	public static final class GameDataResult {
		private String mGameData;
		private boolean mSuccessful = true;
		private GameDataResult() { }
		public String getGameData() { 
			return mGameData;
		}
		public boolean successful() {
			return mSuccessful;
		}
	}
	
	public static final class GameListResult {
		private Long[] mGames;
		private boolean mSuccessful = true;
		private GameListResult() { }
		public Long[] getGames() { 
			return mGames;
		}
		public boolean successful() {
			return mSuccessful;
		}
	}
	
	public static final class PlayerResult {
		private int mPlayer;
		private boolean mSuccessful = true;
		private PlayerResult() { }
		public int getPlayer() { 
			return mPlayer;
		}
		public boolean successful() {
			return mSuccessful;
		}
	}
}
