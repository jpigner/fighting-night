package com.gdx.anight.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gdx.anight.actors.Player;


public class GameOverStage extends Stage{
		
	private BitmapFont font, font_text;
 	
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private GlyphLayout layout;
	private float font_width, text_width, score_width;
	
	public GameOverStage() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SalemErgotism.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 70;
		font = generator.generateFont(parameter);
		font_width = getFont_Width(font, "GAME OVER");
		
		parameter.size = 30;
		font_text = generator.generateFont(parameter);
		text_width = getFont_Width(font_text, "ENTER druecken um ein neues Spiel zu beginnen");	
	}
	
	@Override
	public void draw() {
		super.draw();
		score_width = getFont_Width(font_text, "Ihre Highscore lautet" +Player.getPoints());
		
		getBatch().begin();
		font_text.draw(getBatch(), "Ihre Highscore lautet " +Player.getPoints(), Gdx.graphics.getWidth()/2 -score_width/2, Gdx.graphics.getHeight() -50);
		font.draw(getBatch(), "GAME OVER", Gdx.graphics.getWidth()/2 -font_width/2, Gdx.graphics.getHeight()/2);
		font_text.draw(getBatch(), "ENTER druecken um ein neues Spiel zu beginnen", Gdx.graphics.getWidth()/2 -text_width/2, Gdx.graphics.getHeight()/2 -50);
		getBatch().end();
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		font.dispose();
		font_text.dispose();
		generator.dispose();
	}
	
	private float getFont_Width(BitmapFont font, String string) {
		layout = new GlyphLayout();
		layout.setText(font, string);
		
		return layout.width;
	}
}
