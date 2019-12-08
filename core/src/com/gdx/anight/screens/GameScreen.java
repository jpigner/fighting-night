package com.gdx.anight.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.gdx.anight.actors.Player;
import com.gdx.anight.stages.GameOverStage;
import com.gdx.anight.stages.GameStage;
import com.gdx.anight.stages.PauseStage;

public class GameScreen extends ScreenAdapter{
	private GameStage gameStage;
	private PauseStage pauseStage;
	private GameOverStage gameOverStage;
	
	private boolean pause;
	
	@Override
	public void show() {
		gameStage = new GameStage();
		pauseStage = new PauseStage();
		gameOverStage = new GameOverStage();		
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
		if(!pause && !Player.isGameOver()) {
			gameStage.act();
			gameStage.draw();		
		}
		 
		if(pause) {
			pauseStage.act();
			pauseStage.draw();
		}
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			pause = true;
		}
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			pause = false;
		}
		
		if(Player.isGameOver()) {
			gameOverStage.draw();
			gameStage.clear();
			
			if(Gdx.input.isKeyPressed(Keys.ENTER)) {		
				((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
				Player.setGameOver(false);
				Player.setPoints(0);

			}	
		}

	}
	
	@Override
	public void dispose() {
		gameStage.dispose();
		pauseStage.dispose();
		gameOverStage.dispose();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void resize(int width, int height) {	
		gameStage.getViewport().update(width, height, true);
		pauseStage.getViewport().update(width, height, true);
		gameOverStage.getViewport().update(width, height, true);
	}
		
}
