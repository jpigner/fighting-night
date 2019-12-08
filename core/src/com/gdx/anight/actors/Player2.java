package com.gdx.anight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.anight.assetmanager.Assets;
import com.gdx.anight.assetmanager.Textures;
import com.gdx.anight.listener.KeyListener;
import com.gdx.anight.stages.GameStage;


public class Player2 extends Actor {
	
	private KeyListener input;
	private TextureAtlas atlas;
	private Sprite standing, weapon, bullet_img;
	private BitmapFont name;
	
	private static Body corpus, arm;
	private static RevoluteJoint joint;
	private float width, height;

	// Für die Kugel
	private static Body bullet;
	private BodyDef bulletBodyDef;
	private FixtureDef bulletFixtureDef;
	private boolean shooting;
	private float timer;
	
	private ParticleEffect effect;
	
	@SuppressWarnings("static-access")
	public Player2(World world, float x, float y, float width, float heigth, KeyListener input) {
		this.width = width;
		this.height = heigth;
		
		name = new BitmapFont();
		name.getData().setScale(0.008f);
		
		atlas = new TextureAtlas();
		atlas = Assets.manager.get(Textures.ATLAS_PLAYERSHEET2, TextureAtlas.class);
		standing = new Sprite(atlas.findRegion("1"), 0, 0, Textures.BREITE_ATLASPLAYER, Textures.HOEHE_ATLASPLAYER);
		weapon = new Sprite(Assets.manager.get(Textures.TEX_WEAPON, Texture.class));
		bullet_img = new Sprite(Assets.manager.get(Textures.TEX_BULLET, Texture.class));
		
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("textures/gfx/fire.p"), Gdx.files.internal(""));
		effect.scaleEffect(0.04f);
		effect.flipY();
		effect.setFlip(true, false);
		
		effect.start();
		
		// CORPUS
		
		BodyDef bodydef = new BodyDef();
		bodydef.type = BodyDef.BodyType.DynamicBody;
		bodydef.position.set(x, y);
		bodydef.fixedRotation = false;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, heigth / 2);
		
		FixtureDef fixdef = new FixtureDef();
		fixdef.shape = shape;
		fixdef.density = 1;
		fixdef.restitution = 0;
		fixdef.friction = 0;
		
		
		corpus = world.createBody(bodydef);
		corpus.createFixture(fixdef);
		
		// ARM
		bodydef.position.set(x, y+3);
		shape.setAsBox((width/2) / 10, heigth / 8f);
		
		fixdef.density = 1f;
		
		arm = world.createBody(bodydef);
		arm.createFixture(fixdef);
		
		weapon.setSize(0.35f, 0.2f);
		weapon.scale(2f);
		weapon.setOrigin(weapon.getWidth(), weapon.getHeight());
		arm.setUserData(weapon);
		
		// JOINT
		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = corpus;
		jointDef.bodyB = arm;
		
				
		jointDef.localAnchorB.y = (heigth/2 / 2f)/5;
		jointDef.localAnchorA.y = .9f;
		jointDef.localAnchorA.x = .45f;
		jointDef.enableLimit = true; 
		jointDef.upperAngle = 10 * MathUtils.degreesToRadians; 
		jointDef.lowerAngle = -10 * MathUtils.degreesToRadians;
		jointDef.maxMotorTorque = 80;
		
		joint = (RevoluteJoint) world.createJoint(jointDef);
				
		this.input = input;
	}
		
	@Override
	public void act(float delta) {
		super.act(delta);		
		handleInput(delta);

	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		name.draw(batch, "PLAYER 2", corpus.getPosition().x, corpus.getPosition().y +height*2);
		batch.draw(standing, (corpus.getPosition().x - width), (corpus.getPosition().y -height/1.55f), width*1.75f, height*2.5f);
		
		
		if(bullet != null) {
			batch.draw(bullet_img, bullet.getPosition().x -(width / 2 / 5.2f), bullet.getPosition().y -(width / 2 / 5.2f), 0.15f, 0.15f);
			effect.update(Gdx.graphics.getDeltaTime());
			effect.setPosition(bullet.getPosition().x -(width / 2 / 5.2f), bullet.getPosition().y -(width / 2 / 5.2f));
			if(effect.isComplete()) effect.reset();	
			
		}
		
		if(bullet == null) {
			effect.update(Gdx.graphics.getDeltaTime());
			effect.reset();
		}
		
		settingsWeapon();
		weapon.draw(batch);
		effect.draw(batch);
	
	}
	
	private void settingsWeapon() {
		weapon = (Sprite) arm.getUserData();
		weapon.setPosition(arm.getPosition().x -width*0.1f, arm.getPosition().y);
		weapon.setRotation((arm.getAngle()-30) * MathUtils.radiansToDegrees);
	}
	
	@SuppressWarnings("static-access")
	private void shoot() {	
		bulletBodyDef = new BodyDef();
		bulletBodyDef.type = bulletBodyDef.type.KinematicBody;
		bulletBodyDef.position.set(arm.getPosition().x, arm.getPosition().y);
		
		bulletFixtureDef = new FixtureDef();
		CircleShape bulletShape = new CircleShape();
														   
		bulletShape.setRadius(width / 2 / 5.2f);
				
		bulletFixtureDef.shape = bulletShape;
		bulletFixtureDef.density = 5; 
		bulletFixtureDef.restitution = 0;
		bulletFixtureDef.friction = 0;
		
		bullet = GameStage.getWorld().createBody(bulletBodyDef);
		bullet.createFixture(bulletFixtureDef);
		bulletShape.dispose();
		
		float rotation = (float) (arm.getTransform().getRotation() + Math.PI /2); 
		float x = MathUtils.cos(rotation);
		float y = MathUtils.sin(rotation);
		
		bullet.setLinearVelocity(50 *x, 50 *y);
				
	}
	
	
	private void handleInput(float delta) {
		corpus.setLinearDamping(30.0f);
		arm.setLinearDamping(30f);
				
		if (input.getKeys()[Keys.LEFT]) {
			corpus.applyLinearImpulse(new Vector2(-1.6f, 0), corpus.getWorldCenter(), true);
		}
		if (input.getKeys()[Keys.RIGHT]) {
			corpus.applyLinearImpulse(new Vector2(1.6f, 0), corpus.getWorldCenter(), true);
		}
				
		// Erstmal Notlösung
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			joint.setLimits(-MathUtils.PI/2, MathUtils.PI);
			joint.enableLimit(true);
			joint.enableMotor(true);
			joint.setMotorSpeed(-5f);
			
		}
		if (Gdx.input.isButtonPressed(Buttons.RIGHT) && !shooting){
			joint.setLimits(-MathUtils.PI/2, -MathUtils.PI/4);

			joint.enableLimit(true);
			joint.enableMotor(true);
			joint.setMotorSpeed(5f);
		} 
		if (!Gdx.input.isButtonPressed(Buttons.RIGHT) && !Gdx.input.isButtonPressed(Buttons.LEFT)){
			Player2.getJoint().enableLimit(true);
			Player2.getJoint().enableMotor(false);
			Player2.getJoint().setLimits(Player2.getJoint().getJointAngle() - .0001f * MathUtils.degRad, 
					Player2.getJoint().getJointAngle() + .0001f * MathUtils.degRad); 
		}
		
		if(!shooting) {
			if(Gdx.input.isButtonPressed(Buttons.MIDDLE)) {
				
				shoot();
				shooting = true;
			}
		}
		
		if(!shooting) {
			if (input.getKeys()[Keys.END]) {
				shoot();
				shooting = true;
			}
		}
		
		shootingTimer(delta);
	
	}
	
	// Destroying bulletbodys currently deactivated
	private void shootingTimer(float delta) {
		timer += delta;
		if(timer > 1) {
			timer = 0;
			
			//if(bullet != null && shooting && timer == 0) {
			//	GameStage.getBullettoDestroy().add(bullet);
			//}

			shooting = false;
			timer += delta;
		}
	}
	
	public static Body getCorpus() {
		return corpus;
	}
	
	public static Body getArm() {
		return arm;
	}

	public static RevoluteJoint getJoint() {
		return joint;
	}

	public static Body getBullet() {
		return bullet;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
	
}
