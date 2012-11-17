package com.cinemania.cases;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

import com.cinemania.activity.Base;
import com.cinemania.constants.Constantes;

public class Case extends Rectangle implements Constantes {
	
	private Sprite textureSprite;
	private final int TEXTURELAYER = 0;
	private final int LEVELLAYER = 1;

	public Case(ITextureRegion texture) {
		super(0, 0, texture.getWidth(), texture.getHeight(), Base.getSharedInstance().getVertexBufferObjectManager());
		setBackgroundColor(Color.WHITE);
		textureSprite = new Sprite(0, 0, texture, Base.getSharedInstance().getVertexBufferObjectManager());
		attachChild(textureSprite);
	}
	
	public void setBackgroundColor(Color color){
		setColor(color);
	}
	
	@Override
	public void setSize(float pWidth, float pHeight) {
		for(IEntity ie : mChildren)
			if(ie.getClass() == Sprite.class)
				((Sprite)ie).setSize(pWidth, pHeight);
		
		super.setSize(pWidth, pHeight);
	}
}
