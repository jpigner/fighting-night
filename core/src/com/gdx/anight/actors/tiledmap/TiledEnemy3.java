package com.gdx.anight.actors.tiledmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.gdx.anight.assetmanager.Assets;
import com.gdx.anight.assetmanager.Textures;
import com.gdx.anight.box2d.tiledmap.UserDataTiledEnemy;
import com.gdx.anight.stages.GameStage;
import com.gdx.anight.utils.Constants;

public class TiledEnemy3 extends GameActorEnemyTiled{
	
	private Sprite img_enemy;
	private ParticleEffect effect;
	
	private static Body bullet;
	private BodyDef bulletBodyDef;
	private FixtureDef bulletFixtureDef;
	private float timer;
	private boolean shooting;
	
	public TiledEnemy3(Body body) {
		super(body);
		
		body.setActive(true);
		img_enemy = new Sprite(Assets.manager.get(Textures.TEX_ENEMY, Texture.class));
		
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("textures/gfx/fire.p"), Gdx.files.internal(""));
		effect.scaleEffect(0.004f);
		effect.flipY();
		effect.setFlip(true, false);
		
		effect.start();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		batch.draw(img_enemy, body.getPosition().x -(rect.width*2/Constants.SCALE), body.getPosition().y -(rect.height/Constants.SCALE), (img_enemy.getWidth()/Constants.SCALE)/2, 
				(img_enemy.getHeight()/Constants.SCALE)/2);
		effect.update(Gdx.graphics.getDeltaTime());
		effect.setPosition(bullet.getPosition().x, bullet.getPosition().y);
		if(effect.isComplete()) effect.reset();	
		
		effect.draw(batch);
		
	}
	 
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if(GameStage.getPlayer().getUserData().getBody().getPosition().x > body.getPosition().x) {
			img_enemy.setFlip(true, false);
		} else {
			img_enemy.setFlip(false, false);
		}
		
		timer += delta;
		
		if(!shooting) {
			shoot();
			shooting = true;			
		}
		
		if(timer > 1) {
			shooting = false;
			timer = 0;
			
			timer += delta;
		}
		
	}
	
	// Bullets need to be destroyed - currently not implemented
	@SuppressWarnings("static-access")
	private void shoot() {	
		bulletBodyDef = new BodyDef();
		bulletBodyDef.type = bulletBodyDef.type.KinematicBody;
		bulletBodyDef.position.set(body.getPosition().x, body.getPosition().y);
		bulletBodyDef.angularVelocity = 2f;
		
		
		bulletFixtureDef = new FixtureDef();
		CircleShape bulletShape = new CircleShape();
														   
		bulletShape.setRadius(rect.width / 750);
		
		
		bulletFixtureDef.shape = bulletShape;
		bulletFixtureDef.density = 5; 
		bulletFixtureDef.restitution = 0;
		bulletFixtureDef.friction = 0;
		bulletFixtureDef.isSensor = true;
		
		bullet = GameStage.getWorld().createBody(bulletBodyDef);
		bullet.setAngularVelocity(1f);
		bullet.createFixture(bulletFixtureDef);
		bullet.setBullet(true);
		bulletShape.dispose();
		
		for (int i = 0; i < GameStage.getEnemies3().size; i++) {
			if(!GameStage.getEnemies3().get(i).getUserData().isDead() && GameStage.getPlayer().getUserData().getBody().getPosition().x <
					body.getPosition().x)
			bullet.setLinearVelocity(-20.25f, -10.25f);
			else {
				bullet.setLinearVelocity(20.25f, -10.25f);	
			}
		}				
	}
	
	@Override
	public UserDataTiledEnemy getUserData() {
		return (UserDataTiledEnemy) userDataTiledEnemy;
	}

	public static Body getBullet() {
		return bullet;
	}
	
}
