package com.gdx.anight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.gdx.anight.stages.MenuStage;

public class MenuScreen extends ScreenAdapter{
	private MenuStage menuStage;
	
	@Override
	public void show() {
		menuStage = new MenuStage();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		menuStage.act();
		menuStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		menuStage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose() {
		menuStage.dispose();
	}

	@Override
	public void hide() {
		dispose();
	}

}
