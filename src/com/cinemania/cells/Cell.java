package com.cinemania.cells;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

import com.cinemania.activity.Base;
import com.cinemania.constants.AllConstants;
import com.cinemania.gamelogic.Player;
import com.cinemania.gamelogic.interfaces.JSonator;
import com.cinemania.resources.ResourcesManager;

public abstract class Cell implements JSonator, org.andengine.entity.sprite.ButtonSprite.OnClickListener{
	
	private ButtonSprite mImage;
	private Rectangle mView;
	
	private static final int LAYER_IMAGE = 0;
	private static final int LAYER_LEVEL = 1;
	
	public Cell(ITextureRegion texture, float posX, float posY) {
		mView = new Rectangle(posX, posY, texture.getWidth(), texture.getHeight(), Base.getSharedInstance().getVertexBufferObjectManager());
		mImage = new ButtonSprite(0, 0, texture, Base.getSharedInstance().getVertexBufferObjectManager());
		
		mView.attachChild(new Entity());
		mView.getChildByIndex(LAYER_IMAGE).attachChild(mImage);
		mView.attachChild(new Entity());
		
		this.setColor(AllConstants.BOARD_CASE_COLOR);
	}
	
	public void setOnClickListener(OnClickListener listener){
		mImage.setOnClickListener(listener);
		Base.getSharedInstance().getGame().registerTouchArea(mImage);
	}
	
	public void setSize(float pWidth, float pHeight) {		
		mView.setSize(pWidth, pHeight);
		
	}

	public void setPosition(float posX, float posY){
		mView.setPosition(posX, posY);
	}
	
	public float getX(){
		return this.mView.getX();
	}
	
	public float getY(){
		return this.mView.getY();
	}
	
	protected void setScale(float pScale){
		mView.setScale(pScale);
	}
	
	protected void setColor(Color pColor){
		mView.setColor(pColor);
	}
	
	protected void addLevel(int level){
		mView.getChildByIndex(LAYER_LEVEL).attachChild(new Sprite(0, 0, ResourcesManager.getInstance().mCaseLevel[level-1], Base.getSharedInstance().getVertexBufferObjectManager()));
	}
	
	public Rectangle getView(){
		return this.mView;
	}
	
	@Override
	public abstract void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY);

	public abstract void onTheCell(Player player);
}
