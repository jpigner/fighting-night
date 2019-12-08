package com.gdx.anight.actors;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.actors.tiledmap.TiledEnemy;
import com.gdx.anight.actors.tiledmap.TiledEnemy2;
import com.gdx.anight.assetmanager.Assets;
import com.gdx.anight.assetmanager.Textures;
import com.gdx.anight.box2d.UserDataPlayer;
import com.gdx.anight.enums.PlayerStates;
import com.gdx.anight.listener.ContaktListener;
import com.gdx.anight.listener.KeyListener;
import com.gdx.anight.stages.GameStage;
import com.gdx.anight.utils.BatchUtils;
import com.gdx.anight.utils.Constants;
import com.gdx.anight.utils.GameUtils;

public class Player extends GameActorPlayer {

	private KeyListener input;
	private static boolean jump, runningRight, attack;
	private boolean att1, att2, att3;
	private static PlayerStates currentState;

	private Sprite player, standing, jumping, dead;
	private Sprite full_life, empty_life;

	private TextureRegion[][] regions_player;
	private float timer;
	
	private Array<TextureRegion> lauf_bilder, punch_bilder;
	private Animation laufen, angriff1, angriff2, angriff3, shoot;
	private static float attackCount;
	
	private TextureAtlas atlas;
	private Array<TextureRegion> frames;

	private BitmapFont font_hitPoints, font_points;
	private BitmapFont name;
	private static int points;
	private static boolean gameOver;
	
	public Player(Body body, KeyListener input) {
		super(body);
		
		atlas = new TextureAtlas();
		atlas = Assets.manager.get(Textures.ATLAS_PLAYERSHEET, TextureAtlas.class);
		
		player = new Sprite(Assets.manager.get(Textures.TEX_PLAYERSHEET, Texture.class));
		regions_player = BatchUtils.fillTextureRegion(regions_player, player, Textures.BPZ_PLAYERSHEET,
				Textures.BPS_PLAYERSHEET);
		
		lauf_bilder = new Array<TextureRegion>();
		lauf_bilder = BatchUtils.createFramesZeile(regions_player, lauf_bilder, 4, 4, 0);
		laufen = new Animation(0.1f, lauf_bilder, Animation.PlayMode.LOOP);
		
		punch_bilder = new Array<TextureRegion>();
		punch_bilder = BatchUtils.createFramesZeile(regions_player, punch_bilder, 5, 4, 2);
		angriff1 = new Animation(0.1f, punch_bilder, Animation.PlayMode.LOOP);

		standing = new Sprite(regions_player[0][0]);
		jumping = new Sprite(regions_player[2][4]);
				
		frames = new Array<TextureRegion>();		
		laufen = BatchUtils.createAtlasAni(frames, atlas, 4, 7, "1", Textures.BREITE_ATLASPLAYER, Textures.HOEHE_ATLASPLAYER, 0.1f);
		angriff1 = BatchUtils.createAtlasAni(frames, atlas, 3, 5, "15", Textures.BREITE_ATLASPLAYER+20, Textures.HOEHE_ATLASPLAYER, 0.1f);
		angriff2 = BatchUtils.createAtlasAni(frames, atlas, 0, 2, "4", Textures.BREITE_ATLASPLAYER+18, Textures.HOEHE_ATLASPLAYER, 0.2f);
		angriff3 = BatchUtils.createAtlasAni(frames, atlas, 0, 3, "7", Textures.BREITE_ATLASPLAYER+4, Textures.HOEHE_ATLASPLAYER+5, 0.2f);
		shoot = BatchUtils.createAtlasAni(frames, atlas, 0, 2, "2", Textures.BREITE_ATLASPLAYER, Textures.HOEHE_ATLASPLAYER, 0.1f);
		
		standing = new Sprite(atlas.findRegion("1"), 0, 0, Textures.BREITE_ATLASPLAYER, Textures.HOEHE_ATLASPLAYER);
		jumping = new Sprite(atlas.findRegion("3"), 0, 45, Textures.BREITE_ATLASPLAYER-5, Textures.HOEHE_ATLASPLAYER);
			
		dead = new Sprite(atlas.findRegion("13"), 0, 0, Textures.BREITE_ATLASPLAYER, Textures.HOEHE_ATLASPLAYER);
		full_life = new Sprite(Assets.manager.get(Textures.TEX_FULLLIFE, Texture.class));
		empty_life = new Sprite(Assets.manager.get(Textures.TEX_EMPTYLIFE, Texture.class));
		
		font_hitPoints = new BitmapFont();
		font_hitPoints.getData().setScale(0.02f);
		font_points = new BitmapFont();
		font_points.getData().setScale(0.05f);
		
		name = new BitmapFont();
		name.getData().setScale(0.008f);
				
		currentState = PlayerStates.STANDING;
		runningRight = true;

		this.input = input;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);

		timer += delta;
		handleInput(delta);
		enemyDeadCollision();	
	
	}
		
	public static void setJump(boolean jump) {
		Player.jump = jump;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		name.draw(batch, "PLAYER 1", body.getPosition().x, body.getPosition().y +rect.height*4f);
		
		GameUtils.handleTextureMovement(batch, currentState, runningRight, jumping, standing, body, rect, laufen, timer,
				Constants.X_VERSATZ, Constants.Y_VERSATZ);
		
		if (currentState == PlayerStates.SHOOT) {
			BatchUtils.drawPlayerTextureRegion(batch, shoot.getKeyFrame(timer), rect, body, Constants.X_VERSATZ, Constants.Y_VERSATZ);
		}
		
		
		if (currentState == PlayerStates.ANGRIFF1) {
			BatchUtils.drawPlayerTextureRegion(batch, angriff1.getKeyFrame(timer), rect, body, Constants.X_VERSATZ, Constants.Y_VERSATZ);
		}
		
		if (currentState == PlayerStates.ANGRIFF2) {
			BatchUtils.drawPlayerTextureRegion(batch, angriff2.getKeyFrame(timer), rect, body, Constants.X_VERSATZ, Constants.Y_VERSATZ);
		}
		
		if (currentState == PlayerStates.ANGRIFF3) {
			BatchUtils.drawPlayerTextureRegion(batch, angriff3.getKeyFrame(timer), rect, body, Constants.X_VERSATZ, Constants.Y_VERSATZ);
		}

		if(GameStage.getPlayer().getUserData().getHealth() >= 0) {
			BatchUtils.drawPlayerLife(batch, empty_life, rect, GameStage.getPlayer().getUserData().getBody(),
					(GameStage.getPlayer().getUserData().getMaxHealth())/40, 10, 1, 1);
			BatchUtils.drawPlayerLife(batch, full_life, rect, GameStage.getPlayer().getUserData().getBody(),
					(GameStage.getPlayer().getUserData().getHealth())/40, 10, 1, 1);
						
		}	
	
		if(GameStage.getPlayer().getUserData().getHealth() <= 0) {
			currentState = PlayerStates.DEAD;
			BatchUtils.drawPlayerSprite(batch, dead, rect, body, Constants.X_VERSATZ+1f, Constants.Y_VERSATZ);
			GameStage.getPlayer().getUserData().getBody().setActive(false);
			
		}
		
		font_points.draw(batch, points +"", GameStage.getPlayer().getUserData().getBody().getPosition().x +2, 
				GameStage.getPlayer().getUserData().getBody().getPosition().y +4.7f);
		
		if(ContaktListener.isCollide() && ContaktListener.isEnemy() || ContaktListener.isCollide() && ContaktListener.isEnemy2()) {
			if(att1) {
				font_hitPoints.draw(batch, (GameStage.getPlayer().getUserData().getDamage_low())+" HIT", 
						GameStage.getPlayer().getUserData().getBody().getPosition().x +2f, 
						GameStage.getPlayer().getUserData().getBody().getPosition().y +4f);
				points++;
			}
			if(att2) {
				font_hitPoints.draw(batch, (GameStage.getPlayer().getUserData().getDamage_medium())+" HIT", 
						GameStage.getPlayer().getUserData().getBody().getPosition().x +2f, 
						GameStage.getPlayer().getUserData().getBody().getPosition().y +4f);
				points++;
			}
			if(att3) {
				font_hitPoints.draw(batch, (GameStage.getPlayer().getUserData().getDamage_high())+" HIT", 
						GameStage.getPlayer().getUserData().getBody().getPosition().x +2f, 
						GameStage.getPlayer().getUserData().getBody().getPosition().y +4f);	
				points++;
			}
		}
		
		if(GameStage.getPlayer().getUserData().getHealth() <= 0) {
			gameOver = true;
		}
		
		if(points > Constants.MAX_POINTS && !ContaktListener.isCollide() && !TiledEnemy.isFighting() && !TiledEnemy2.isFighting()) {
			gameOver = true;
		}
				
	}
	
	private void handleInput(float delta) {
		body.setLinearDamping(30.0f);
		
		if(currentState != PlayerStates.DEAD) {
			if (!input.getKeys()[Keys.A] && !input.getKeys()[Keys.D] && !input.getKeys()[Keys.W]) {
				currentState = PlayerStates.STANDING;
				attack = false;
				setAttacksFalse();
				attackCount = 0;
			}

			if (input.getKeys()[Keys.A]) {
				currentState = PlayerStates.RUNNINGLEFT;
				runningRight = false;
				attack = false;
				setAttacksFalse();
				attackCount = 0;
				body.applyLinearImpulse(new Vector2(-5f, 0f), body.getWorldCenter(), true);
				if (!(laufen.getKeyFrame(timer).isFlipX())) {
					laufen.getKeyFrame(timer).flip(true, false);
				}

			}

			if (input.getKeys()[Keys.D]) {
				currentState = PlayerStates.RUNNINGRIGHT;
				runningRight = true;
				attack = false;
				setAttacksFalse();
				attackCount = 0;
				body.applyLinearImpulse(new Vector2(5f, 0f), body.getWorldCenter(), true);
				if (laufen.getKeyFrame(timer).isFlipX()) {
					laufen.getKeyFrame(timer).flip(true, false);
				}
			}

			if (input.getKeys()[Keys.W]) {
				currentState = PlayerStates.JUMPING;
				attack = false;
				setAttacksFalse();
				attackCount = 0;
				if (!jump)
					body.applyLinearImpulse(0f, 45f, body.getPosition().x, body.getPosition().y, true);
			}

			if (input.getKeys()[Keys.S]) {
				currentState = PlayerStates.ANGRIFF1;
				if (runningRight) {
					attack = true;
					att1 = true;
					if(ContaktListener.isCollide()) {
						attackCount += delta *100;
					}
					handleAttack1();
					if (angriff1.getKeyFrame(timer).isFlipX())
					angriff1.getKeyFrame(timer).flip(true, false);
				}
				if (!runningRight) {
					attack = true;
					att1 = true;
					if(ContaktListener.isCollide()) {
						attackCount += delta *100;
					}
					handleAttack1();
					if (!angriff1.getKeyFrame(timer).isFlipX())
					angriff1.getKeyFrame(timer).flip(true, false);
				}
			}
			
			if (input.getKeys()[Keys.E]) {
				currentState = PlayerStates.ANGRIFF2;
				if (runningRight) {
					attack = true;
					att2 = true;
					if(ContaktListener.isCollide()) {
						attackCount += delta *100;
					}
					handleAttack2();
					if (angriff2.getKeyFrame(timer).isFlipX())
					angriff2.getKeyFrame(timer).flip(true, false);
				}
				if (!runningRight) {
					attack = true;
					att2 = true;
					if(ContaktListener.isCollide()) {
						attackCount += delta *100;
					}
					handleAttack2();
					if (!angriff2.getKeyFrame(timer).isFlipX())
					angriff2.getKeyFrame(timer).flip(true, false);
				}
			}
			
			if (input.getKeys()[Keys.Q]) {
				currentState = PlayerStates.ANGRIFF3;
				if (runningRight) {
					attack = true;
					att3 = true;
					if(ContaktListener.isCollide()) {
						attackCount += delta *100;
					}
					handleAttack3();
					if (angriff3.getKeyFrame(timer).isFlipX())
					angriff3.getKeyFrame(timer).flip(true, false);
				}
				if (!runningRight) {
					attack = true;
					att3 = true;
					if(ContaktListener.isCollide()) {
						attackCount += delta *100;
					}
					handleAttack3();
					if (!angriff3.getKeyFrame(timer).isFlipX())
					angriff3.getKeyFrame(timer).flip(true, false);
				}
			}
			
			if (input.getKeys()[Keys.SPACE]) {
				currentState = PlayerStates.SHOOT;
				if (runningRight) {
					attack = true;
					if (shoot.getKeyFrame(timer).isFlipX())
						shoot.getKeyFrame(timer).flip(true, false);
				}
				if (!runningRight) {
					attack = true;
					if (!shoot.getKeyFrame(timer).isFlipX())
						shoot.getKeyFrame(timer).flip(true, false);
				}
			}
		}
	}
	
	private void enemyDeadCollision() {
		if(ContaktListener.isCollide()) {
			body.applyLinearImpulse(new Vector2(-0.01f, 0f), body.getWorldCenter(), true);
		}
	}
	
	private void handleAttack1() {
		if(ContaktListener.isCollide() && ContaktListener.isEnemy2() && Player.isAttack()) {
			GameStage.getEnemies2().get(ContaktListener.getEnemyIndex()).getUserData().setHealth(
					GameStage.getEnemies2().get(ContaktListener.getEnemyIndex()).getUserData().getHealth() 
					-GameStage.getPlayer().getUserData().getDamage_low());
			}
		if(ContaktListener.isCollide() && ContaktListener.isEnemy() && Player.isAttack()) {
			GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().setHealth(
					GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getHealth() 
					-GameStage.getPlayer().getUserData().getDamage_low());
			}
	}
	
	private void handleAttack2() {
		if(ContaktListener.isCollide() && ContaktListener.isEnemy2() && Player.isAttack()) {
			GameStage.getEnemies2().get(ContaktListener.getEnemyIndex()).getUserData().setHealth(
					GameStage.getEnemies2().get(ContaktListener.getEnemyIndex()).getUserData().getHealth() 
					-GameStage.getPlayer().getUserData().getDamage_medium());
			}
		if(ContaktListener.isCollide() && ContaktListener.isEnemy() && Player.isAttack()) {
			GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().setHealth(
					GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getHealth() 
					-GameStage.getPlayer().getUserData().getDamage_medium());
			}
	}
	
	private void handleAttack3() {
		if(ContaktListener.isCollide() && ContaktListener.isEnemy() && Player.isAttack()) {
			GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().setHealth(
					GameStage.getEnemies().get(ContaktListener.getEnemyIndex()).getUserData().getHealth() 
					-GameStage.getPlayer().getUserData().getDamage_high());
			}
		if(ContaktListener.isCollide() && ContaktListener.isEnemy2() && Player.isAttack()) {
			GameStage.getEnemies2().get(ContaktListener.getEnemyIndex()).getUserData().setHealth(
					GameStage.getEnemies2().get(ContaktListener.getEnemyIndex()).getUserData().getHealth() 
					-GameStage.getPlayer().getUserData().getDamage_high());
			}
	}
	
	@Override
	public UserDataPlayer getUserData() {
		return userDataPlayer;
	}
	
	private void setAttacksFalse() {
		att1 = false;
		att2 = false;
		att3 = false;
	}
	
	public static boolean isAttack() {
		return attack;
	}
	
	public static float getAttackCount() {
		return attackCount;
	}
	
	public static int getPoints() {
		return points;
	}
	
	public static void setPoints(int points) {
		Player.points = points;
	}
	
	public static boolean isGameOver() {
		return gameOver;
	}
	
	public static void setGameOver(boolean gameOver) {
		Player.gameOver = gameOver;
	}
		
	public static void setAttackCount(float attackCount) {
		Player.attackCount = attackCount;
	}

}