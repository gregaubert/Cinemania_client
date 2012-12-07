package com.cinemania.cases;

import org.andengine.entity.sprite.ButtonSprite;

import com.cinemania.resources.ResourcesManager;

public class LuckyCase extends Case {
	
	public static final int TYPE = 3;

	public LuckyCase(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseLuck, posX, posY);
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTheCell() {
		// TODO Auto-generated method stub
		
	}
}
