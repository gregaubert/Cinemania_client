package com.cinemania.gamelogic;

import java.util.ArrayList;

public class ChanceCardManager {

	private static ChanceCardManager instance;
	private ArrayList<ChanceCard> cardList = new ArrayList<ChanceCard>();
	
	private ChanceCardManager() {
		loadCard();
	}
	
	public static ChanceCardManager getSharedInstance(){
		if(instance == null)
			instance = new ChanceCardManager();
		return instance;
	}
	
	public ChanceCard pickACard(){
		return cardList.get((int)(Math.random() * (cardList.size()-0)));
	}
	
	private void loadCard(){
		//TODO Changer cette merde et charger depuis un fichier genre json ou autre. Mais osef pour le proto
		cardList.add(new ChanceCard("Oscar", "Bravo!\nVotre dernier film à remporté un Oscar! ", 300));
		cardList.add(new ChanceCard("Mauvaise pub", "Suite à un coup médiatique de vos adversaires votre réputation est en baisse!", -250));
		cardList.add(new ChanceCard("Acteur en fuite", "La résiliation du contrat d'un acteur met en périle vos productions!", -200));
		cardList.add(new ChanceCard("Invasion d'extra-terrestre", "Vous entamez la construction d'un bunker, afin de vous protéger de la récente invasion extra-terrestre!", -500));
		cardList.add(new ChanceCard("Grève des scénaristes", "Vos scénaristes refusent leur nouvelles conditions de travail! Ils se mettent en grève!", -100));
		cardList.add(new ChanceCard("Promotion de votre secrétaire", "Vous découvrez des talents d'actrice chez votre secrétaire!", 200));
		cardList.add(new ChanceCard("Justice!", "Vous remportez un vieux procès contre un concurrent!", 250));
	}
}
