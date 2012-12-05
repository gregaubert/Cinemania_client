package com.cinemania.cases;

import com.cinemania.resources.ResourcesManager;

public class Script extends Case {
	
	public static final int TYPE = 2;

	public Script(float posX, float posY) {
		super(ResourcesManager.getInstance().mCaseScript, posX, posY);
	}

	public String getMovieStory() {
		return "";
	}

}
