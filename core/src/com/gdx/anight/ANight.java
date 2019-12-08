package com.gdx.anight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.anight.assetmanager.Assets;
import com.gdx.anight.screens.MenuScreen;

public class ANight extends Game {
	private SpriteBatch batch;
				
	@Override
	public void create () {		
		Assets.load();
		
		//while (!Assets.manager.update()); // Für Progressbar
		Assets.manager.finishLoading(); // Wenn es sich nicht lohnt
		
		batch = new SpriteBatch();
		setScreen(new MenuScreen());	
	}
	
	
	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		Assets.dispose();
	}



	public SpriteBatch getBatch() {
		return batch;
	}
	

}
