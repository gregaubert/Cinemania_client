package com.cinemania.gamelogic;
import java.util.ArrayList;
import static com.cinemania.constants.AllConstants.*;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import android.util.Log;

import com.cinemania.activity.Base;
import com.cinemania.cases.Case;
import com.cinemania.resources.ResourcesManager;

public class Player {
	
	//private final float caseSize = Base.CAMERA_HEIGHT / (((int) BOARD_SIZE / 4)+1);
	
	private final int bordure = 10;
	
	private int id;

	private int amount;

	private int logistic;

	private int actors;

	private Case position;

	private Case QG;
  
	private ArrayList<Case> properties;
  
  	private Color colorCase;
  	private Color colorPawn;

	private Sprite view;
  
	private Board board;
  
	public Player(Board board, Case initial, int anId) {
		id = anId;
		setAmount(DEFAULT_AMOUNT);
		setLogistic(DEFAULT_LOGISTIC);
		setActors(DEFAULT_ACTORS);	
		properties = new ArrayList<Case>();
		
		colorCase = PLAYER_COLOR[1][id-1];
		colorPawn = PLAYER_COLOR[0][id-1];
		
		this.board = board;
		
		//Case de départ du joueur.
		this.position = initial;
		this.QG = initial;
		
		view = new Sprite(position.getX()+bordure, position.getY()+bordure, ResourcesManager.getInstance().mPlayer, Base.getSharedInstance().getVertexBufferObjectManager());
		view.setSize(view.getWidth()-2*bordure, view.getHeight() - 2*bordure);
		view.setColor(this.colorPawn);	
		
	}
	
	public void Move(int nb){
	  
		int pos = board.findCaseIndex(position);
	  
		while(nb != 0){
			pos = (pos+1)%board.getSize();
			Case temp = board.getCaseAtIndex(pos);
			//TODO trouvé technique pour attendre que le mouvement soit finit.
			view.registerEntityModifier(new MoveModifier(0.2f, position.getX(), temp.getX()+bordure, position.getY(), temp.getY()+bordure));
			  
			//Test si on passe au QG
			if(temp == this.QG)
				this.encaisser();
		  
			position = temp;
			
			nb -= 1;		
		}
	  
	}
	  
	//Méthode appelée lorsque l'on passe par notre QG
	public void encaisser(){
		//TODO
		Log.i("GAME","ENCAISSER!!!");
	}
  
	public Sprite getView(){
		return this.view;
	}
  
  public int getId() {
		return id;
  }

  public void setAmount(int amount) {
		this.amount = amount;
  }

  public int getAmount() {
		return amount;
  }

  public void setLogistic(int logistic) {
		this.logistic = logistic;
  }

  public int getLogistic() {
		return logistic;
  }

  public void setActors(int actors) {
		this.actors = actors;
  }

  public int getActors() {
		return actors;
  }

  public void setPosition(Case position) {
		this.position = position;
  }

  public Case getPosition() {
		return position;
  }

  public void addProperty(Case c) {
		properties.add(c);
  }

  public void removeProperty(Case c) {
		properties.remove(c);
  }
  
  public Color getColorCase(){
	  return colorCase;
  }
  
  public Color getColorPawn(){
	  return colorPawn;
  }

}
