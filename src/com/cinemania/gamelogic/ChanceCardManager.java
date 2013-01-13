package com.cinemania.gamelogic;

import java.util.ArrayList;

import com.cinemania.network.GameContext;

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
		
		//TODO Changer ça et charger depuis un fichier genre json ou autre. Mais pas pour le proto.
		cardList.add(new ChanceCard("Oscar", "Bravo!\nVotre dernier film à remporté un Oscar! ", 300));
		cardList.add(new ChanceCard("Promotion de votre secrétaire", "Vous découvrez des talents d'actrice chez votre secrétaire!", 200));
		cardList.add(new ChanceCard("Justice!", "Vous remportez un vieux procès contre un concurrent!", 250));
		cardList.add(new ChanceCard("Adaptation en jeu vidéo!", "Vous avez attiré la curiosité d'un grand producteur de jeux vidéos.\nIl veut votre coopération sur l'adaptation vidéoludique d'un de vos films!", 275));
		cardList.add(new ChanceCard("Mauvaise pub", "Suite à un coup médiatique de vos adversaires votre réputation est en baisse!", -250));
		cardList.add(new ChanceCard("Acteur en fuite", "La résiliation du contrat d'un acteur met en périle vos productions!", -200));
		cardList.add(new ChanceCard("Invasion d'extra-terrestre", "Vous entamez la construction d'un bunker afin de vous protéger de la récente invasion extra-terrestre!", -300));
		cardList.add(new ChanceCard("Grève des scénaristes", "Vos scénaristes refusent leur nouvelles conditions de travail! Ils se mettent en grève!", -50));
		cardList.add(new ChanceCard("Caprice d'actrice!", "Votre actrice star exige un jacuzzi dans sa loge!", -75));
		cardList.add(new ChanceCard("Des vols dans l'équipe", "Un de vos collaborateurs pique dans la caisse", -(int)(GameContext.getSharedInstance().getCurrentPlayer().getAmount()*0.05)));
		cardList.add(new ChanceCard("Vous flambez au poker", "Vous gagner un tournois très prestigieux", 1000));
	}
}
