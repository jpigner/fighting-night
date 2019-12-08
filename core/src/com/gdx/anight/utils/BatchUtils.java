package com.gdx.anight.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class BatchUtils {
	
	public static void drawPlayerSprite(Batch batch, Sprite sprite, Rectangle rect, Body body, float x_versatz, float y_versatz) {
		batch.draw(sprite, body.getPosition().x - rect.width*y_versatz, body.getPosition().y -rect.height*x_versatz, rect.width*3.5f, rect.height*5f);
	}
	
	public static void drawPlayerTextureRegion(Batch batch, TextureRegion tex, Rectangle rect, Body body, float x_versatz, float y_versatz) {
		batch.draw(tex, body.getPosition().x - rect.width*y_versatz, body.getPosition().y -rect.height*x_versatz, rect.width*3.5f, rect.height*5f);
	}
	
	public static void drawBodyEnemy(Batch batch, TextureRegion tex, Rectangle rect, Body body, float x_versatz, float y_versatz) {
		batch.draw(tex, body.getPosition().x -(rect.width/Constants.SCALE) *x_versatz, body.getPosition().y -(rect.height/Constants.SCALE) *y_versatz, 
				((rect.width*3.55f)/Constants.SCALE), ((rect.height*3.55f)/Constants.SCALE));
	}
	
	public static void drawBodyLife(Batch batch, TextureRegion tex, Rectangle rect, Body body, float width, float height, float x_versatz, float y_versatz) {
		batch.draw(tex, body.getPosition().x -((rect.width*1.7f)/Constants.SCALE), body.getPosition().y +((rect.height*2.4f)/Constants.SCALE), 
				((width)/Constants.SCALE), ((height)/Constants.SCALE));
	}
	
	public static void drawPlayerLife(Batch batch, TextureRegion tex, Rectangle rect, Body body, float width, float height, float x_versatz, float y_versatz) {
		batch.draw(tex, body.getPosition().x -((rect.width*10.5f)), body.getPosition().y +((rect.height*12.5f)), 
				((width*10f)/Constants.SCALE), ((height*5)/Constants.SCALE));
	}
	
	public static void drawBodiesTextureRegion(Batch batch, TextureRegion tex, Rectangle rect, Array<Body> bodies, float x_versatz, float y_versatz) {
		
		for(int i = 0; i < bodies.size; i++) {
			batch.draw(tex, bodies.get(i).getPosition().x -(rect.width/Constants.SCALE_TILED)/2, bodies.get(i).getPosition().y -(rect.height/Constants.SCALE_TILED)/2, 
					((rect.width)/Constants.SCALE_TILED), ((rect.height)/Constants.SCALE_TILED));
					
		}
		
	}
	
	public static void drawPlayerTexture(Batch batch, Texture tex, Rectangle rect, Body body) {
		batch.draw(tex, body.getPosition().x - rect.width, body.getPosition().y -rect.height, rect.width*2.5f, rect.height*2.5f);
	}
		
	public static TextureRegion[][] fillTextureRegion(TextureRegion[][] region, Sprite sprite, int breite_pro_bild, int hoehe_pro_bild) {
		region = TextureRegion.split(sprite.getTexture(), sprite.getRegionWidth()/ breite_pro_bild, sprite.getRegionHeight()/ hoehe_pro_bild);
		
		return region;
	}
	
	public static Array<TextureRegion> createFramesSpalte(TextureRegion[][] region, Array<TextureRegion> frames, int startbildpos_vonOben, int endBildpos_vonUnten, int spalte) {
		for (int i = startbildpos_vonOben; i < region.length -endBildpos_vonUnten; i++) {
			frames.add(region[i][spalte]);
		}
		
		return frames;
	}
	
	public static Array<TextureRegion> createFramesZeile(TextureRegion[][] region, Array<TextureRegion> frames, int startbildpos_vonLinks, int endBildpos_vonRechts, int zeile) {
		for (int i = startbildpos_vonLinks; i < region[zeile].length -endBildpos_vonRechts; i++) {
			frames.add(region[zeile][i]);
		}
		
		return frames;

	}
	
	public static Animation createAtlasAni(Array<TextureRegion> frames, TextureAtlas atlas,
			int startBild, int endBild, String atlasRegion, int bildBreite, int bildHoehe, float framerate) {
		for(int i = startBild; i < endBild; i++) {
			frames.add(new TextureRegion(atlas.findRegion(atlasRegion), i * bildBreite, 0, bildBreite, bildHoehe));
		}
		Animation animation = new Animation(framerate, frames, Animation.PlayMode.LOOP);
		frames.clear();
		
		return animation;
	}
	

}
