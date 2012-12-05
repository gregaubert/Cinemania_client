package com.cinemania.cases;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import com.cinemania.activity.Base;
import com.cinemania.constants.AllConstants;

public class Case extends Rectangle {
	
	private Sprite textureSprite;
	private final int TEXTURELAYER = 0;
	private final int LEVELLAYER = 1;

	public Case(ITextureRegion texture, float posX, float posY) {
		super(posX, posY, texture.getWidth(), texture.getHeight(), Base.getSharedInstance().getVertexBufferObjectManager());
		setColor(AllConstants.BOARD_CASE_COLOR);
		
		// Creation des layers
	    this.attachChild(new Entity());
	    this.attachChild(new Entity());
	   
		textureSprite = new Sprite(0, 0, texture, Base.getSharedInstance().getVertexBufferObjectManager());
		this.getChildByIndex(TEXTURELAYER).attachChild(textureSprite);
	}
	
	public Case(ITextureRegion texture, OnClickListener listener) {
		super(0, 0, texture.getWidth(), texture.getHeight(), Base.getSharedInstance().getVertexBufferObjectManager());
		setColor(AllConstants.BOARD_CASE_COLOR);
		textureSprite = new ButtonSprite(0, 0, texture, Base.getSharedInstance().getVertexBufferObjectManager());
		((ButtonSprite)textureSprite).setOnClickListener(listener);
		attachChild(textureSprite);
	}
	
	@Override
	public void setSize(float pWidth, float pHeight) {
		for(IEntity ie : mChildren)
			if(ie.getClass() == Sprite.class)
				((Sprite)ie).setSize(pWidth, pHeight);
		super.setSize(pWidth, pHeight);
	}
}
