package com.cinemania.network;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cinemania.cells.Cell;
import com.cinemania.cells.HeadQuarters;
import com.cinemania.cells.OwnableCell;
import com.cinemania.gamelogic.Player;

public class PlayerDeserializer {
	// FIXME: Need to be discussed this shit, not used
	public static Player deserialize(JSONObject jsonPlayer,Cell[] board,int order){
		
		try{
			assert board[jsonPlayer.getInt("hq")] instanceof HeadQuarters;
			
			Player player = new Player(
				jsonPlayer.getString("id"),
				order,
				jsonPlayer.getString("name"),
				jsonPlayer.getInt("money"),
				jsonPlayer.getInt("actors"),
				jsonPlayer.getInt("logistics"),
				jsonPlayer.getInt("lastTurn"),
				jsonPlayer.getInt("lastProfit"),
				jsonPlayer.getInt("lastActors"),
				jsonPlayer.getInt("lastLogistics"),
				jsonPlayer.getBoolean("canBuyAuthorFilm"),
				(HeadQuarters)board[jsonPlayer.getInt("hq")],
				board[jsonPlayer.getInt("position")]
			);
			
			// Because the board game and all related cells are generated before without any reference to any player,
			// we have to link the player and his properties afterward
			JSONArray jsonProperties = jsonPlayer.getJSONArray("properties");
			for (int j = 0; j < jsonProperties.length(); j++) {
				assert board[jsonProperties.getInt(order)] instanceof OwnableCell;
				player.addProperty((OwnableCell)board[jsonProperties.getInt(order)]);
			}
			player.getHeadQuarters().setOwner(player);
			
			return player;
		}
		catch (Exception e){};
		
		return null;
			
	}
}
