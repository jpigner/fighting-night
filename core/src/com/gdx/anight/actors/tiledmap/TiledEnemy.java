package com.gdx.anight.actors.tiledmap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.actors.Player;
import com.gdx.anight.assetmanager.Assets;
import com.gdx.anight.assetmanager.Textures;
import com.gdx.anight.box2d.tiledmap.UserDataTiledEnemy;
import com.gdx.anight.enums.EnemyStates;
import com.gdx.anight.listener.ContaktListener;
import com.gdx.anight.stages.GameStage;
import com.gdx.anight.utils.BatchUtils;

public class TiledEnemy extends GameActorEnemyTiled{
	
	private Sprite enemy, standing, dead;
	private Sprite full_life, empty_life;
	private TextureRegion[][] regions_enemy;
	
	private Array<TextureRegion> lauf_bilder, hurt_bilder, attack_bilder;
	private Animation laufen, hurt, attack;
	
	private static boolean runningRight, fighting;
	private static EnemyStates currentState;
	private float timer;
	
	public TiledEnemy(Body body) {
		super(body);
		
		body.setActive(false);
		
		enemy = new Sprite(Assets.manager.get(Textures.TEX_PLAYERSHEET, Texture.class));
		regions_enemy = BatchUtils.fillTextureRegion(regions_enemy, enemy, Textures.BPZ_PLAYERSHEET,
				Textures.BPS_PLAYERSHEET);
		
		lauf_bilder = new Array<TextureRegion>();
		lauf_bilder = BatchUtils.createFramesZeile(regions_enemy, lauf_bilder, 4, 4, 0);
		laufen = new Animation(0.1f, lauf_bilder, Animation.PlayMode.LOOP);
		
		hurt_bilder = new Array<TextureRegion>();
		hurt_bilder = BatchUtils.createFramesZeile(regions_enemy, hurt_bilder, 0, 4, 4);
		hurt = new Animation(0.1f, hurt_bilder, Animation.PlayMode.LOOP);
		
		attack_bilder = new Array<TextureRegion>();
		attack_bilder = BatchUtils.createFramesZeile(regions_enemy, attack_bilder, 6, 4, 5);
		attack = new Animation(0.1f, attack_bilder, Animation.PlayMode.LOOP);
		
		standing = new Sprite(regions_enemy[0][0]);
		dead = new Sprite(regions_enemy[4][0]);
		full_life = new Sprite(Assets.manager.get(Textures.TEX_FULLLIFE, Texture.class));
		empty_life = new Sprite(Assets.manager.get(Textures.TEX_EMPTYLIFE, Texture.class));
		
		currentState = EnemyStates.STANDING;
		runningRight = true;

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(currentState == EnemyStates.STANDING && runningRight) {
			standing.setFlip(false, false);
			BatchUtils.drawBodyEnemy(batch, standing, rect, body, 1, 1.3f);
		}
		
		if(currentState == EnemyStates.STANDING && !runningRight) {
			standing.setFlip(true, false);
			BatchUtils.drawBodyEnemy(batch, standing, rect, body, 1, 1.3f);
		}
		
		if(currentState == EnemyStates.RUNNINGLEFT ||
				currentState == EnemyStates.RUNNINGRIGHT ) 
			BatchUtils.drawBodyEnemy(batch, laufen.getKeyFrame(timer), rect, body, 1, 1.3f);
		

		if(currentState == EnemyStates.COMBAT) BatchUtils.drawBodyEnemy(batch, attack.getKeyFrame(timer), rect, body, 1, 1.3f);
			
		if(currentState == EnemyStates.HURT) BatchUtils.drawBodyEnemy(batch, hurt.getKeyFrame(timer), rect, body, 1, 1.3f);
		
		if(ContaktListener.isCollide() && ContaktListener.isEnemy()) {
				if(GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getHealth() > 0) {
					BatchUtils.drawBodyLife(batch, empty_life, rect, GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getBody(),
							(GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getMaxHealth())/16, 10, 1, 1);
					BatchUtils.drawBodyLife(batch, full_life, rect, GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getBody(),
							(GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getHealth())/16, 10, 1, 1);
				}
		}
		
		for (int i = 0; i<GameStage.getEnemies().size; i++) {
		if(GameStage.getEnemies().get(i).getUserData().getHealth() <= 0) {
			BatchUtils.drawBodyEnemy(batch, dead, rect, GameStage.getEnemies().get(i).getUserData().getBody(), 1, 1.3f);
			GameStage.getEnemies().get(i).getUserData().getBody().setActive(false);
			GameStage.getEnemies().get(i).getUserData().setCurrentState(EnemyStates.DEATH);
			GameStage.getEnemies().get(i).getUserData().setDead(true);
		}
		}
		
	}
	 
	@Override
	public void act(float delta) {
		super.act(delta);
		timer += delta;
			
		handleEnemyAttack();
		
		if(currentState != EnemyStates.DEATH) {
		
		if(body.getPosition().x < GameStage.getPlayer().getUserData().getBody().getPosition().x +5 &&
				currentState != EnemyStates.DEATH) {
			body.setActive(true);
		}
		if(body.getPosition().x > GameStage.getPlayer().getUserData().getBody().getPosition().x +6 &&
				currentState != EnemyStates.DEATH) {
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
		} 
		if(!fighting && GameStage.getPlayer().getUserData().getBody().getPosition().x < 
			body.getPosition().x) {
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
			currentState = EnemyStates.COMBAT;
			runningRight = true;
			if (attack.getKeyFrame(timer).isFlipX())
				attack.getKeyFrame(timer).flip(true, false);
		}
			
		if(fighting && GameStage.getPlayer().getUserData().getBody().getPosition().x < 
			body.getPosition().x) {
			currentState = EnemyStates.COMBAT;
			runningRight = false;
			if (!attack.getKeyFrame(timer).isFlipX())
				attack.getKeyFrame(timer).flip(true, false);
		} 
		}		
	}
	
	private void handleEnemyAttack() {
		 if(!Player.isAttack() && TiledEnemy.isFighting() && 
				 (GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getBody().getPosition().x < 
				 GameStage.getPlayer().getUserData().getBody().getPosition().x +0.3f || 
				 GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getBody().getPosition().x > 
				 GameStage.getPlayer().getUserData().getBody().getPosition().x +0.3f )) {
			 
			 GameStage.getPlayer().getUserData().setHealth(GameStage.getPlayer().getUserData().getHealth() -
					 GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getDamage());
		 }
	}
	
	public static EnemyStates getCurrentState() {
		return currentState;
	}

	public static boolean isRunningRight() {
		return runningRight;
	}

	public static void setCurrentState(EnemyStates currentState) {
		TiledEnemy.currentState = currentState;
	}
	
	public static boolean isFighting() {
		return fighting;
	}
	
	public static void setFighting(boolean fighting) {
		TiledEnemy.fighting = fighting;
	}
	
	@Override
	public UserDataTiledEnemy getUserData() {
		return (UserDataTiledEnemy) userDataTiledEnemy;
	}

}
