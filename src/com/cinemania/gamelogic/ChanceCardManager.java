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
		cardList.add(new ChanceCard("Oscar", "Bravo!\nVotre dernier film � remport� un Oscar! ", 300));
		cardList.add(new ChanceCard("Mauvaise pub", "Suite � un coup m�diatique de vos adversaires votre r�putation est en baisse!", -250));
		cardList.add(new ChanceCard("Acteur en fuite", "La r�siliation du contrat d'un acteur met en p�rile vos productions!", -200));
		cardList.add(new ChanceCard("Invasion d'extra-terrestre", "Vous entamez la construction d'un bunker, afin de vous prot�ger de la r�cente invasion extra-terrestre!", -500));
		cardList.add(new ChanceCard("Gr�ve des sc�naristes", "Vos sc�naristes refusent leur nouvelles conditions de travail! Ils se mettent en gr�ve!", -100));
		cardList.add(new ChanceCard("Promotion de votre secr�taire", "Vous d�couvrez des talents d'actrice chez votre secr�taire!", 200));
		cardList.add(new ChanceCard("Justice!", "Vous remportez un vieux proc�s contre un concurrent!", 250));
	}
}
