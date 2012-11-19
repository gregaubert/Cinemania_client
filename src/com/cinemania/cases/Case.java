package com.cinemania.cases;

import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

import com.cinemania.activity.Base;
import com.cinemania.constants.Constants;

public class Case extends Rectangle implements Constants {
	
	private Sprite textureSprite;
	private final int TEXTURELAYER = 0;
	private final int LEVELLAYER = 1;

	public Case(ITextureRegion texture) {
		super(0, 0, texture.getWidth(), texture.getHeight(), Base.getSharedInstance().getVertexBufferObjectManager());
		setColor(1f / 255f * 250f, 1f / 255f * 252f, 1f / 255f * 124f);
		textureSprite = new Sprite(0, 0, texture, Base.getSharedInstance().getVertexBufferObjectManager());
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
