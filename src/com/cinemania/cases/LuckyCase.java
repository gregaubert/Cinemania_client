package com.cinemania.cases;

import com.cinemania.resources.ResourcesManager;

public class LuckyCase extends Case {

	public LuckyCase() {
		super(ResourcesManager.getInstance().mCaseLuck);
	}
}
