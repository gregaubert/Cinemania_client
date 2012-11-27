package com.cinemania.cases;

import com.cinemania.resources.ResourcesManager;

public class LuckyCase extends Case {
	
	public static final int TYPE = 3;

	public LuckyCase() {
		super(ResourcesManager.getInstance().mCaseLuck);
	}
}
