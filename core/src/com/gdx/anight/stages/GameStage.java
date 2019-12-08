package com.gdx.anight.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.gdx.anight.actors.Player;
import com.gdx.anight.actors.Player2;
import com.gdx.anight.actors.tiledmap.TiledEnemy;
import com.gdx.anight.actors.tiledmap.TiledEnemy2;
import com.gdx.anight.actors.tiledmap.TiledEnemy3;
import com.gdx.anight.actors.tiledmap.TiledGround;
import com.gdx.anight.actors.tiledmap.TiledLine;
import com.gdx.anight.box2dlights.PointLight;
import com.gdx.anight.box2dlights.RayHandler;
import com.gdx.anight.enums.EnemyStates;
import com.gdx.anight.listener.ContaktListener;
import com.gdx.anight.listener.KeyListener;
import com.gdx.anight.utils.Box2dutils;
import com.gdx.anight.utils.CamUtils;
import com.gdx.anight.utils.Constants;
import com.gdx.anight.utils.MapUtils;

public class GameStage extends Stage{
	
	private static World world;
	private float timer;
	private Box2DDebugRenderer b2renderer;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	private static Player player;
	private static Player2 player2;
	private static TiledGround bodenBox;
	private TiledGround sideBoxes;
	private static TiledLine bodenLine;
			
	private static Array<Body> bodytoDetroy;
	private static Array<Body> bullettoDestroy;
	
	private static Array<TiledEnemy> enemies;
	private static Array<TiledEnemy2> enemies2;
	private static Array<TiledEnemy3> enemies3;
	
	private int enemies_size;
	private int enemyIndex;
	
	private KeyListener input;
	private ParticleEffect effect;
	
	private RayHandler rayHandler;
	private PointLight light, light2, light3, light4, enemies_light;
	private boolean lightson;

	public GameStage() {
		super(new ScalingViewport(Scaling.fit, Constants.WIDTH/Constants.SCALE, Constants.HEIGHT/Constants.SCALE,
				new OrthographicCamera(Constants.WIDTH, Constants.HEIGHT)));
		
		input = new KeyListener();
		Gdx.input.setInputProcessor(input);
		
		b2renderer = new Box2DDebugRenderer();
				
		world = Box2dutils.createWorld();
		world.setContactListener(new ContaktListener());
		
		map = MapUtils.mapLoader("", map);
		renderer = MapUtils.loadTiledRenderer(map);
		
		initActors();
			
		bodytoDetroy = new Array<Body>();
		bullettoDestroy = new Array<Body>();
		
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("textures/testing.p"), Gdx.files.internal(""));
		effect.scaleEffect(0.007f);
		effect.flipY();
		effect.setFlip(true, false);
		
		effect.start();
		
		rayHandler = new RayHandler(world);
		rayHandler.setAmbientLight(.1f, .1f, .1f, .01f);
		initLights();	
		
	}
	
	private void initLights() {
		light = new PointLight(rayHandler, 50, Color.BLUE, 12, (Gdx.graphics.getWidth()/2)/40,
				(Gdx.graphics.getHeight()/2)/40);
		
		light2 = new PointLight(rayHandler, 50, Color.BLUE, 12, (Gdx.graphics.getWidth())/40,
				(Gdx.graphics.getHeight()/2)/40);
		
		light3 = new PointLight(rayHandler, 50, Color.BLUE, 12, (Gdx.graphics.getWidth()*1.5f)/40,
				(Gdx.graphics.getHeight()/2)/40);
		
		light4 = new PointLight(rayHandler, 50, Color.BLUE, 12, (Gdx.graphics.getWidth()*2f)/40,
				(Gdx.graphics.getHeight()/2)/40);
	}
	
	private void initActors() {
								
		bodenLine = new TiledLine(MapUtils.createLayerPolylines(world, map, 3));
		addActor(bodenLine);
		
		bodenBox = new TiledGround(MapUtils.createLayerGround(world, map, 2, BodyType.StaticBody, 1, 0, 0));
		addActor(bodenBox);
		
		sideBoxes = new TiledGround(MapUtils.createLayerGround(world, map, 5, BodyType.StaticBody, 1, 0, 0));
		addActor(sideBoxes);
		
		createEnemies();	
		
		player = new Player(Box2dutils.createPlayerBoxed(world, BodyType.DynamicBody, new Vector2(9f, 6f), .4f, .4f, 2, 0, 0, 
				5000, 5000, 30, 20, 10), input);
				
		addActor(player);
				
		player2 = new Player2(world, 7.5f, 6, .8f, .8f, input);
		addActor(player2);
			
	}
		
	private void handleEnemyLights() {
		
		if(lightson) {
			for(int i = 0; i<enemies.size; i++) {
				if(enemies.get(i).getUserData().getBody().isActive()) {
					enemies_light = new PointLight(rayHandler, 5, Color.RED, 0.25f, enemies.get(i).getUserData().getBody().getPosition().x,
							enemies.get(i).getUserData().getBody().getPosition().y);	
				}
			}
			
			for(int i = 0; i<enemies2.size; i++) {
				if(enemies2.get(i).getUserData().getBody().isActive()) {
					enemies_light = new PointLight(rayHandler, 5, Color.RED, 0.25f, enemies2.get(i).getUserData().getBody().getPosition().x,
							enemies2.get(i).getUserData().getBody().getPosition().y);	
				}
			}
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		handleEnemyLights();
			
		renderer.render();
		rayHandler.render();
		rayHandler.update();
		
		CamUtils.playerCam(getCamera(), player, 2, 2);
		
		handleBloodEffects(delta);
		
		timer += delta;
		
		for(int i = 0; i < enemies.size; i++) {
			if(enemies.get(i).getUserData().isDead()) {
				GameStage.getEnemies().get(i).setVisible(false);
				GameStage.getEnemies().get(i).getUserData().getBody().setActive(false);
			}
			
			if(enemies.get(i).getUserData().getHealth() <= 0) {
				GameStage.getEnemies().get(i).setVisible(false);		
			}
		}
		
		for(int i = 0; i < enemies2.size; i++) {
			if(enemies2.get(i).getUserData().isDead()) {
				GameStage.getEnemies2().get(i).setVisible(false);
				GameStage.getEnemies2().get(i).getUserData().getBody().setActive(false);
			}
			
			if(enemies2.get(i).getUserData().getHealth() <= 0) {
				GameStage.getEnemies2().get(i).setVisible(false);	
			}
		}
				
		if (timer >= delta) {		
			world.step(Constants.TIMESTEP, 8, 3);
			timer -= Constants.TIMESTEP;
		}
		
		if(bullettoDestroy != null) {
			for(int i = 0; i < bullettoDestroy.size; i++) {
				world.destroyBody(bullettoDestroy.get(i));
				System.out.println("Destroyed Bullet : " +bullettoDestroy.get(i));
				bullettoDestroy.removeIndex(i);
			}
		}
		
		if(bodytoDetroy != null) {
			for(int i = 0; i < bodytoDetroy.size; i++) {
		         world.destroyBody(bodytoDetroy.get(i));
		          System.out.println("Destroyed Body : " +bodytoDetroy.get(i));
		           bodytoDetroy.removeIndex(i);
			}
		}		
		
		
	}
			
	
	private void handleBloodEffects(float delta) {
						
		if(ContaktListener.isCollide() && ContaktListener.isEnemy() && Player.isAttack()) {
				effect.update(delta);
				effect.setPosition(enemies.get(ContaktListener.getEnemyIndex()).getUserData().getBody().getPosition().x, 
						enemies.get(ContaktListener.getEnemyIndex()).getUserData().getBody().getPosition().y +
						(enemies.get(ContaktListener.getEnemyIndex()).getUserData().getHeight())/(Constants.SCALE_TILED*2));
				if(effect.isComplete()) effect.reset();
		}		
				
		if(ContaktListener.isCollide() && ContaktListener.isEnemy2() && Player.isAttack()) {
				effect.update(delta);
				effect.setPosition(enemies2.get(ContaktListener.getEnemyIndex()).getUserData().getBody().getPosition().x, 
						enemies2.get(ContaktListener.getEnemyIndex()).getUserData().getBody().getPosition().y +
						(enemies2.get(ContaktListener.getEnemyIndex()).getUserData().getHeight())/(Constants.SCALE_TILED*2));
				if(effect.isComplete()) effect.reset();
		}		
				
		if(!ContaktListener.isCollide() | !Player.isAttack()) {
			effect.update(delta);
			effect.reset();
		}
		
	}
	
	private void createEnemies() {
		enemies = new Array<TiledEnemy>();
		enemies_size = MapUtils.sizeOfLayer(world, map, 4);
		
		for(enemyIndex = 0; enemyIndex<enemies_size; enemyIndex++) {			
			enemies.add(new TiledEnemy(MapUtils.createOneBody_Layer(world, map, enemyIndex, 4, BodyType.DynamicBody, 
					1, 0, 0, 2000, 2000, 10, false, EnemyStates.STANDING)));
		}
		
		for(int i = 0; i<enemies.size; i++) {
			addActor(enemies.get(i));
		}
		
		enemies2 = new Array<TiledEnemy2>();
		enemies_size = MapUtils.sizeOfLayer(world, map, 6);
		
		for(enemyIndex = 0; enemyIndex<enemies_size; enemyIndex++) {			
			enemies2.add(new TiledEnemy2(MapUtils.createOneBody_Layer(world, map, enemyIndex, 6, BodyType.DynamicBody, 
					1, 0, 0, 2000, 2000, 10, false, EnemyStates.STANDING)));
		}
		
		for(int i = 0; i<enemies2.size; i++) {
			addActor(enemies2.get(i));
		}
		
		enemies3 = new Array<TiledEnemy3>();
		enemies_size = MapUtils.sizeOfLayer(world, map, 7);
		
		for(enemyIndex = 0; enemyIndex<enemies_size; enemyIndex++) {
			enemies3.add(new TiledEnemy3(MapUtils.createOneBodyWindow_Layer(world, map, enemyIndex, 7, BodyType.KinematicBody, 
					1, 0, 0, 1000, 1000, 25, false, EnemyStates.STANDING)));
			enemies3.get(0).getUserData().getBody().setTransform(enemies3.get(0).getUserData().getBody().getPosition().x, enemies3.get(0).getUserData().getBody().getPosition().y, 125);
		}
		
		for(int i = 0; i<enemies3.size; i++) {
			addActor(enemies3.get(i));
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
		renderer.dispose();
		world.dispose();
		map.dispose();
		b2renderer.dispose();
		effect.dispose();
		rayHandler.dispose();
		effect.dispose();
		light.dispose();
		light2.dispose();
		light3.dispose();
		light4.dispose();
		
	}

	@Override
	public void draw() {
		super.draw();
		
		rayHandler.setCombinedMatrix((OrthographicCamera) getCamera());
		renderer.setView((OrthographicCamera) getCamera());
		b2renderer.render(world, getCamera().combined);
		
		getBatch().begin();
		effect.draw(getBatch());
		getBatch().end();
		
	}
		
	public static Player getPlayer() {
		return player;
	}
	
	public static TiledGround getBodenBox() {
		return bodenBox;
	}
	
	public static TiledLine getBodenLine() {
		return bodenLine;
	}
			
	public static Array<Body> getBodytoDetroy() {
		return bodytoDetroy;
	}
	
	public static Array<Body> getBullettoDestroy() {
		return bullettoDestroy;
	}
	
	public static Array<TiledEnemy> getEnemies() {
		return enemies;
	}
	
	public static Array<TiledEnemy2> getEnemies2() {
		return enemies2;
	}
	
	public static World getWorld() {
		return world;
	}
	
	public PointLight getLight() {
		return light;
	}

	public void setLight(PointLight light) {
		this.light = light;
	}

	public PointLight getLight2() {
		return light2;
	}

	public void setLight2(PointLight light2) {
		this.light2 = light2;
	}

	public PointLight getLight3() {
		return light3;
	}

	public void setLight3(PointLight light3) {
		this.light3 = light3;
	}

	public PointLight getLight4() {
		return light4;
	}

	public void setLight4(PointLight light4) {
		this.light4 = light4;
	}

	public PointLight getEnemies_light() {
		return enemies_light;
	}
	
	public void setEnemies_light(PointLight enemies_light) {
		this.enemies_light = enemies_light;
	}

	public static Array<TiledEnemy3> getEnemies3() {
		return enemies3;
	}
	
	
}
