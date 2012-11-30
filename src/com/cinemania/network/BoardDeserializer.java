package com.cinemania.network;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cinemania.cases.Case;
import com.cinemania.cases.Cinema;
import com.cinemania.cases.HeadQuarters;
import com.cinemania.cases.LogisticFactory;
import com.cinemania.cases.LuckyCase;
import com.cinemania.cases.School;
import com.cinemania.cases.Script;
import com.cinemania.gamelogic.Board;
import com.cinemania.gamelogic.Room;

public class BoardDeserializer {

	public static Board deserialize(JSONArray jsonBoard) {
		// FIXME: Need to be discussed
		// Case[] boardCases = new Case[AllConstants.BOARD_SIZE];
		Case[] boardCases = new Case[jsonBoard.length()];
		// Generate board's cells
		try{
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
		}
		catch(Exception e){};
			
		return new Board(boardCases);
	}
}
