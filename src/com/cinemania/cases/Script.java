package com.cinemania.cases;

import com.cinemania.resources.ResourcesManager;

public class Script extends Case {
	
	public static final int TYPE = 2;

	public Script() {
		super(ResourcesManager.getInstance().mCaseScript);
	}

	public String getMovieStory() {
		return "";
	}

}
