package com.gdx.anight.listener;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gdx.anight.actors.Player;
import com.gdx.anight.actors.Player2;
import com.gdx.anight.actors.tiledmap.TiledEnemy;
import com.gdx.anight.actors.tiledmap.TiledEnemy2;
import com.gdx.anight.actors.tiledmap.TiledEnemy3;
import com.gdx.anight.stages.GameStage;
import com.gdx.anight.utils.Constants;

public class ContaktListener implements ContactListener{
	
	private static boolean destroyed;
	private static boolean collide;
	private static boolean enemy, enemy2;
	private static int enemyIndex;
	
	@Override
	public void beginContact(Contact contact) {
				
		Body fixA = contact.getFixtureA().getBody(); 
		Body fixB = contact.getFixtureB().getBody();
				
		// enemybullet collide testing
		if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == TiledEnemy3.getBullet())) ||
				(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == TiledEnemy3.getBullet() )) {
			 GameStage.getPlayer().getUserData().setHealth(GameStage.getPlayer().getUserData().getHealth() - Constants.SHOOTING_ENEMY_DAMAGE);
		}
		
		// playerbullet collide testing
		for(int i = 0; i < GameStage.getEnemies3().size; i++) {
		if((fixA == Player2.getBullet() && fixB == GameStage.getEnemies3().get(i).getUserData().getBody()) ||
				(fixB == Player2.getBullet() && fixA == GameStage.getEnemies3().get(i).getUserData().getBody())) {
			 System.out.println("SHOOTING ENEMY HIT");
		}
		}
		
		for(int i = 0; i < GameStage.getEnemies().size; i++) {
			if((fixA == Player2.getBullet() && fixB == GameStage.getEnemies().get(i).getUserData().getBody()) ||
					(fixB == Player2.getBullet() && fixA == GameStage.getEnemies().get(i).getUserData().getBody())) {
				 System.out.println("SHOOTING ENEMY HIT");
		}
		}
				
		
		// Jumpingcollision > mit Polygonlineboden
		for (int i = 0; i < GameStage.getBodenLine().getUserData().getBodies().size; i++) {
			if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == GameStage.getBodenLine().getUserData().getBodies().get(i))) ||
					(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == GameStage.getBodenLine().getUserData().getBodies().get(i))) {
				 Player.setJump(false);
			}	
		}
		for (int i = 0; i < GameStage.getBodenLine().getUserData().getBodies().size; i++) {
			if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == GameStage.getBodenBox().getUserData().getBodies().get(i))) ||
					(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == GameStage.getBodenBox().getUserData().getBodies().get(i))) {
				 Player.setJump(false);
			}	
		}
	
		// TILEDMAP ENEMIES
		for (int i = 0; i < GameStage.getEnemies().size; i++) {
			if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == GameStage.getEnemies().get(i).getUserData().getBody()) ||
					(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == GameStage.getEnemies().get(i).getUserData().getBody()))) {
				 TiledEnemy.setFighting(true);	 
				 				 				 
				 collide = true;
				 enemy = true;
				 enemyIndex = i;
				 				 
				 if(GameStage.getEnemies().get(i).getUserData().getHealth() <= 0) {
					 destroyed = true;
				 }
		
				 
				 if(GameStage.getEnemies().get(i).getUserData() != null && destroyed) {
					 GameStage.getBodytoDetroy().add(GameStage.getEnemies().get(i).getUserData().getBody());
					 GameStage.getEnemies().get(i).setVisible(false);
					 GameStage.getEnemies().get(i).clear();
					 GameStage.getEnemies().get(i).remove();
					 destroyed = false;
				 }
			}
		}
		
		for (int i = 0; i < GameStage.getEnemies2().size; i++) {
			if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == GameStage.getEnemies2().get(i).getUserData().getBody()) ||
					(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == GameStage.getEnemies2().get(i).getUserData().getBody()))) {
				 TiledEnemy2.setFighting(true);	 
				 				 				 
				 collide = true;
				 enemy2 = true;
				 enemyIndex = i;
				 				 
				 if(GameStage.getEnemies2().get(i).getUserData().getHealth() <= 0) {
					 destroyed = true;
				 }
				 
				 if(GameStage.getEnemies2().get(i).getUserData() != null && destroyed) {
					 GameStage.getBodytoDetroy().add(GameStage.getEnemies2().get(i).getUserData().getBody());
					 GameStage.getEnemies2().get(i).setVisible(false);
					 GameStage.getEnemies2().get(i).clear();
					 GameStage.getEnemies2().get(i).remove();
					 destroyed = false;
				 }
			}
		}
		
	}			
	
	@Override
	public void endContact(Contact contact) {	
		
		Body fixA = contact.getFixtureA().getBody(); 
		Body fixB = contact.getFixtureB().getBody();
		
		// Jumpingcollision > mit Polygonlineboden
		for (int i = 0; i < GameStage.getBodenLine().getUserData().getBodies().size; i++) {
			if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == GameStage.getBodenLine().getUserData().getBodies().get(i))) ||
					(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == GameStage.getBodenLine().getUserData().getBodies().get(i))) {
					Player.setJump(true);
			}
		}
		
		for (int i = 0; i < GameStage.getBodenLine().getUserData().getBodies().size; i++) {
			if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == GameStage.getBodenBox().getUserData().getBodies().get(i))) ||
					(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == GameStage.getBodenBox().getUserData().getBodies().get(i))) {
					Player.setJump(true);
			}
		}
		
		// TILEDMAP ENEMIES
		for (int i = 0; i < GameStage.getEnemies().size; i++) {
			if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == GameStage.getEnemies().get(i).getUserData().getBody()) ||
				(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == GameStage.getEnemies().get(i).getUserData().getBody()))) {
					 TiledEnemy.setFighting(false);
					 collide = false;
					 enemy = false;
			}		 
		}
		
		for (int i = 0; i < GameStage.getEnemies2().size; i++) {
			if(((fixA == GameStage.getPlayer().getUserData().getBody()) && (fixB == GameStage.getEnemies2().get(i).getUserData().getBody()) ||
				(fixB == GameStage.getPlayer().getUserData().getBody()) && (fixA == GameStage.getEnemies2().get(i).getUserData().getBody()))) {
					 TiledEnemy2.setFighting(false);
					 collide = false;
					 enemy2 = false;
			}		 
		}
				
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	@Override
	public void preSolve(Contact contact, Manifold manifold) {
	}

	public static boolean isDestroyed() {
		return destroyed;
	}
	
	public static boolean isCollide() {
		return collide;
	}
	
	public static void setCollide(boolean collide) {
		ContaktListener.collide = collide;
	}
	
	public static int getEnemyIndex() {
		return enemyIndex;
	}
	
	public static boolean isEnemy2() {
		return enemy2;
	}
	
	public static boolean isEnemy() {
		return enemy;
	}
	
}
