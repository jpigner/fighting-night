package com.gdx.anight.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.assetmanager.Assets;
import com.gdx.anight.assetmanager.Textures;
import com.gdx.anight.box2d.UserDataEnemy;
import com.gdx.anight.enums.EnemyStates;
import com.gdx.anight.listener.ContaktListener;
import com.gdx.anight.stages.GameStage;
import com.gdx.anight.utils.BatchUtils;

public class Enemy extends GameActorEnemy{
	private Sprite enemy, standing;
	private TextureRegion[][] regions_enemy;
	
	private Array<TextureRegion> lauf_bilder, hurt_bilder, attack_bilder;
	private Animation laufen, hurt, attack;
	
	private static boolean runningRight, fighting;
	private static EnemyStates currentState;
	private float timer;
	
	public Enemy(Body body) {
		super(body);
		
		body.setActive(false);
		
		enemy = new Sprite(Assets.manager.get(Textures.TEX_PLAYERSHEET, Texture.class));
		regions_enemy = BatchUtils.fillTextureRegion(regions_enemy, enemy, Textures.BPZ_PLAYERSHEET,
				Textures.BPS_PLAYERSHEET);
		
		lauf_bilder = new Array<TextureRegion>();
		lauf_bilder = BatchUtils.createFramesZeile(regions_enemy, lauf_bilder, 4, 4, 0);
		laufen = new Animation(0.1f, lauf_bilder, Animation.PlayMode.LOOP);
		
		hurt_bilder = new Array<TextureRegion>();
		hurt_bilder = BatchUtils.createFramesZeile(regions_enemy, hurt_bilder, 5, 4, 2);
		hurt = new Animation(0.1f, hurt_bilder, Animation.PlayMode.LOOP);
		
		attack_bilder = new Array<TextureRegion>();
		attack_bilder = BatchUtils.createFramesZeile(regions_enemy, attack_bilder, 6, 4, 5);
		attack = new Animation(0.1f, attack_bilder, Animation.PlayMode.LOOP);
		
		standing = new Sprite(regions_enemy[0][0]);
		
		currentState = EnemyStates.STANDING;
		runningRight = true;
				
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(currentState == EnemyStates.STANDING && runningRight) {
			standing.setFlip(false, false);
			BatchUtils.drawPlayerSprite(batch, standing, rect, body, 1.3f, 1.35f);
		}
		
		if(currentState == EnemyStates.STANDING && !runningRight) {
			standing.setFlip(true, false);
			BatchUtils.drawPlayerSprite(batch, standing, rect, body, 1.3f, 1.35f);
		}
		
		if(currentState == EnemyStates.RUNNINGLEFT ||
				currentState == EnemyStates.RUNNINGRIGHT ) 
			BatchUtils.drawPlayerTextureRegion(batch, laufen.getKeyFrame(timer), rect, body, 1.3f, 1.2f);	
		
		if(currentState == EnemyStates.COMBAT) BatchUtils.drawPlayerTextureRegion(batch, attack.getKeyFrame(timer), rect, body, 1.3f, 1.2f);
	
		if(currentState == EnemyStates.DEATH) BatchUtils.drawPlayerTextureRegion(batch, hurt.getKeyFrame(timer), rect, body, 1.3f, 1.2f);
				
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		timer += delta;
		
		if(ContaktListener.isDestroyed()) {
			currentState = EnemyStates.DEATH;
		}
		
		if(body.getPosition().x < GameStage.getPlayer().getUserData().getBody().getPosition().x +2) {
			body.setActive(true);
		}
		
		if(body.getPosition().x > GameStage.getPlayer().getUserData().getBody().getPosition().x +3) {
			body.setActive(false);
		}
			
		if(!fighting && GameStage.getPlayer().getUserData().getBody().getPosition().x > 
				body.getPosition().x) {
			runningRight = true;
			currentState = EnemyStates.RUNNINGRIGHT;
			body.setLinearDamping(5f);
			body.setLinearVelocity(2f, 0f);
			if (laufen.getKeyFrame(timer).isFlipX()) {
				laufen.getKeyFrame(timer).flip(true, false);
			}
		} else {
			runningRight = false;
			currentState = EnemyStates.RUNNINGLEFT;
			body.setLinearDamping(5f);
			body.setLinearVelocity(-2f, 0f);
			if (!(laufen.getKeyFrame(timer).isFlipX())) {
				laufen.getKeyFrame(timer).flip(true, false);
			}
		}
				
		if(fighting && GameStage.getPlayer().getUserData().getBody().getPosition().x > 
		body.getPosition().x) {
			runningRight = true;
		}
		
		if(fighting && GameStage.getPlayer().getUserData().getBody().getPosition().x < 
		body.getPosition().x) {
			runningRight = false;
		}
		
		if(fighting) {
			currentState = EnemyStates.COMBAT;
			body.setLinearVelocity(new Vector2(0f, 0f));
			body.setLinearDamping(30f);
			if (runningRight) {
				if (attack.getKeyFrame(timer).isFlipX())
					attack.getKeyFrame(timer).flip(true, false);
			}
			else if (!runningRight) {
				if (!attack.getKeyFrame(timer).isFlipX())
					attack.getKeyFrame(timer).flip(true, false);
			}
		}
		
		else if (!body.isActive()) {
			currentState = EnemyStates.STANDING;
		}

	}

	public static EnemyStates getCurrentState() {
		return currentState;
	}

	public static boolean isRunningRight() {
		return runningRight;
	}

	public static void setCurrentState(EnemyStates currentState) {
		Enemy.currentState = currentState;
	}

	public static void setFighting(boolean fighting) {
		Enemy.fighting = fighting;
	}
	
	public static boolean isFighting() {
		return fighting;
	}
	
	@Override
	public UserDataEnemy getUserData() {
		return userDataEnemy;
	}
	
}
