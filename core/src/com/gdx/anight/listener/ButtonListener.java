package com.gdx.anight.listener;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.anight.screens.GameScreen;

public class ButtonListener extends ClickListener{
	GameScreen gameScreen;
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		super.clicked(event, x, y);
		
		if(event.getListenerActor().getName().equals("start")) {
			((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
		}
		if(event.getListenerActor().getName().equals("exit")) {
			Gdx.app.exit();
		}
	}
	
	
	
}
