package com.gdx.anight.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gdx.anight.listener.ButtonListener;

public class MenuStage extends Stage{
	
	private Skin skin;
	private Table root, uiElements;
	private Label lblMenu;
	private TextButton txtBtnStart, txtBtnExit;
	
	private ButtonListener buttonListener;
	
	public MenuStage() {
	
		buttonListener = new ButtonListener();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		root = new Table();
		root.setFillParent(true);
		
		uiElements = new Table();
		
		txtBtnStart = new TextButton("Starten", skin);
		txtBtnStart.setName("start");
		txtBtnStart.addListener(buttonListener);
		uiElements.add(txtBtnStart).width(getViewport().getWorldWidth() / 3).padTop(getViewport().getWorldHeight() / 60).padBottom(getViewport().getWorldHeight() / 60).row();
		
		txtBtnExit = new TextButton("Beenden", skin);
		txtBtnExit.setName("exit");
		txtBtnExit.addListener(buttonListener);
		uiElements.add(txtBtnExit).width(getViewport().getWorldWidth() / 3).padTop(getViewport().getWorldHeight() / 60).padBottom(getViewport().getWorldHeight() / 60).row();
		
		root.add(lblMenu).padBottom(getViewport().getWorldHeight() / 20).row();
		root.add(uiElements);
		
		addActor(root);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		super.dispose();
		skin.dispose();
	}
	
	
}
