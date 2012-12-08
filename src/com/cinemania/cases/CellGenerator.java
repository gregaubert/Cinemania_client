package com.cinemania.cases;

import static com.cinemania.constants.AllConstants.BOARD_NB_ACTOR_LINE;
import static com.cinemania.constants.AllConstants.BOARD_NB_CINEMA_LINE;
import static com.cinemania.constants.AllConstants.BOARD_NB_LOGISTIC_LINE;
import static com.cinemania.constants.AllConstants.BOARD_NB_SCRIPT_LINE;
import static com.cinemania.constants.AllConstants.BOARD_NB_TOT_LINE;
import static com.cinemania.constants.AllConstants.BOARD_SIZE;
import com.cinemania.activity.R;

public class CellGenerator {
	public static int[] generate() {

		int[] identifiers = new int[BOARD_SIZE];
		int scriptCount = 0;
		int cinemaCount = 0;
		int actorsCount = 0;
		int logisticsCount = 0;
		int currentLineOffset = 0;
		
		for (int i = 0; i < identifiers.length; i++) {
			if (i % (identifiers.length/4) == 0){
				scriptCount = BOARD_NB_SCRIPT_LINE;
				cinemaCount = BOARD_NB_CINEMA_LINE;
				actorsCount = BOARD_NB_ACTOR_LINE;
				logisticsCount = BOARD_NB_LOGISTIC_LINE;
				currentLineOffset = BOARD_NB_TOT_LINE;
				identifiers[i] = HeadQuarters.TYPE;
			} else {
			
				int random = Math.round((float)Math.random() * (actorsCount + logisticsCount  + cinemaCount + currentLineOffset + scriptCount - 1));
				
				if (random < actorsCount){
					actorsCount--;
					identifiers[i] = School.TYPE;
				} else if (random < actorsCount + logisticsCount) {
					logisticsCount--;
					identifiers[i] = LogisticFactory.TYPE;
				} else if(random < actorsCount + logisticsCount + cinemaCount) {
					cinemaCount--;
					identifiers[i] = Cinema.TYPE;
				} else if(random < actorsCount + logisticsCount + cinemaCount + scriptCount) {
					scriptCount--;
					identifiers[i] = Script.TYPE;
				} else {
					currentLineOffset--;
					identifiers[i] = Chance.TYPE;
				}
			}
		}
		
		return identifiers;
	}
}
