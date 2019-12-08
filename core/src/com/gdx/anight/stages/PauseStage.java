package com.gdx.anight.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class PauseStage extends Stage{
		
	private BitmapFont font;
 	
	private FreeTypeFontGenerator generator;
	private FreeTypeFontParameter parameter;
	private GlyphLayout layout;
	private float font_width;
	
	public PauseStage() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SalemErgotism.ttf"));
		parameter = new FreeTypeFontParameter();
		parameter.size = 39;
		font = generator.generateFont(parameter);
		font_width = getFont_Width(font, "Leertaste druecken um weiterzuspielen");
		
	}
	
	@Override
	public void draw() {
		super.draw();
		
		getBatch().begin();
		font.draw(getBatch(), "Leertaste druecken um weiterzuspielen", Gdx.graphics.getWidth()/2 -font_width/2, Gdx.graphics.getHeight()/2);
		getBatch().end();
		
	}
	
	@Override
	public void dispose() {
		super.dispose();
		font.dispose();
		generator.dispose();
	}
	
	private float getFont_Width(BitmapFont font, String string) {
		layout = new GlyphLayout();
		layout.setText(font, string);
		
		return layout.width;
	}
}
