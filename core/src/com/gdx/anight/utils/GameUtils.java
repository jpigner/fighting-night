package com.gdx.anight.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gdx.anight.enums.PlayerStates;
import com.gdx.anight.listener.KeyListener;

public class GameUtils {
		
	public static void handleMovement(Body body, KeyListener input, PlayerStates currentState, boolean runningRight,
			Animation laufen, float timer, boolean jump, float impulsX, float impulsY) {
		body.setLinearDamping(30.0f);
		
		if (!input.getKeys()[Keys.A] && !input.getKeys()[Keys.D] && !input.getKeys()[Keys.W]) {
			currentState = PlayerStates.STANDING;
		}
		
		if (input.getKeys()[Keys.A]) {
			currentState = PlayerStates.RUNNINGLEFT;
			runningRight = false;
			body.applyLinearImpulse(new Vector2(-impulsX, 0f), body.getWorldCenter(), true);
			if (!(laufen.getKeyFrame(timer).isFlipX())) {
				laufen.getKeyFrame(timer).flip(true, false);
			}

		}

		if (input.getKeys()[Keys.D]) {
			currentState = PlayerStates.RUNNINGRIGHT;
			runningRight = true;
			body.applyLinearImpulse(new Vector2(impulsX, 0f), body.getWorldCenter(), true);
			if (laufen.getKeyFrame(timer).isFlipX()) {
				laufen.getKeyFrame(timer).flip(true, false);
			}
		}

		if (input.getKeys()[Keys.W]) {
			if (!jump)
				currentState = PlayerStates.JUMPING;
				body.applyLinearImpulse(0f, impulsY, body.getPosition().x, body.getPosition().y, true);
		}

	}
	
	public static void handleMovementPlus(Body body, KeyListener input, PlayerStates currentState, boolean runningRight,
			Animation laufen, float timer, boolean jump, Animation aufgabe, float impulsX, float impulsY) {
		body.setLinearDamping(30.0f);

		if (!input.getKeys()[Keys.A] && !input.getKeys()[Keys.D] && !input.getKeys()[Keys.W]) {
			currentState = PlayerStates.STANDING;
		}

		if (input.getKeys()[Keys.A]) {
			currentState = PlayerStates.RUNNINGLEFT;
			runningRight = false;
			body.applyLinearImpulse(new Vector2(-10f, 0f), body.getWorldCenter(), true);
			if (!(laufen.getKeyFrame(timer).isFlipX())) {
				laufen.getKeyFrame(timer).flip(true, false);
			}

		}

		if (input.getKeys()[Keys.D]) {
			currentState = PlayerStates.RUNNINGRIGHT;
			runningRight = true;
			body.applyLinearImpulse(new Vector2(10f, 0f), body.getWorldCenter(), true);
			if (laufen.getKeyFrame(timer).isFlipX()) {
				laufen.getKeyFrame(timer).flip(true, false);
			}
		}

		if (input.getKeys()[Keys.W]) {
			currentState = PlayerStates.JUMPING;
			if (!jump)
				body.applyLinearImpulse(0f, 50f, body.getPosition().x, body.getPosition().y, true);
		}

		if (input.getKeys()[Keys.S]) {
			currentState = PlayerStates.ANGRIFF1;
			if (runningRight) {
				if (aufgabe.getKeyFrame(timer).isFlipX())
				aufgabe.getKeyFrame(timer).flip(true, false);
			}
			if (!runningRight) {
				if (!aufgabe.getKeyFrame(timer).isFlipX())
				aufgabe.getKeyFrame(timer).flip(true, false);
			}
		}

	}
	
	public static void handleTextureMovement(Batch batch, PlayerStates currentState, boolean runningRight, Sprite jumping, Sprite standing,
			Body body, Rectangle rect, Animation laufen, float timer, float posXVersatz, float posYVersatz) {
		
		if(currentState == PlayerStates.RUNNINGLEFT || currentState == PlayerStates.RUNNINGRIGHT) BatchUtils.drawPlayerTextureRegion(batch, laufen.getKeyFrame(timer), rect, body, 1.3f, 1.2f);	
		
		if(currentState == PlayerStates.JUMPING && runningRight) {
			jumping.setFlip(false, false);
			BatchUtils.drawPlayerSprite(batch, jumping, rect, body, posXVersatz, posYVersatz);
		}
		if(currentState == PlayerStates.JUMPING && !runningRight) {
			jumping.setFlip(true, false);
			BatchUtils.drawPlayerSprite(batch, jumping, rect, body, posXVersatz, posYVersatz);
		}	
		
		if(currentState == PlayerStates.STANDING && runningRight) {
			standing.setFlip(false, false);
			BatchUtils.drawPlayerSprite(batch, standing, rect, body, posXVersatz, posYVersatz);
		}  
		if(currentState == PlayerStates.STANDING && !runningRight) {
			standing.setFlip(true, false);
			BatchUtils.drawPlayerSprite(batch, standing, rect, body, posXVersatz, posYVersatz);
		}
	}
	
}
