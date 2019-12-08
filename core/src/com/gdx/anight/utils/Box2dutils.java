package com.gdx.anight.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.gdx.anight.box2d.BulletUserData;
import com.gdx.anight.box2d.EnemyUserData;
import com.gdx.anight.box2d.GroundUserData;
import com.gdx.anight.box2d.PlayerUserBoxData;
import com.gdx.anight.box2d.PlayerUserCircleData;
import com.gdx.anight.enums.UserDataType;

public class Box2dutils {
	
	private static RevoluteJoint joint, joint2;
		
	public static World createWorld() {
		World world = new World(Constants.GRAVITY, true);
		return world;
	}
	
	public static Body createPlayerBoxed(World world, BodyType type, Vector2 position, float width, float height, float density, float restitution, float friction,
			float maxHealth, float health, int damage_low, int damage_medium, int damage_high) {
		Body body = createBox(world, type, position, width, height, density, restitution, friction);
		
		body.setUserData(new PlayerUserBoxData(body, width, height, maxHealth, health, damage_low, damage_medium, damage_high));
		return body;
	}
	
	public static Body createEnemy(World world, BodyType type, Vector2 position, float width, float height, float density, float restitution, float friction,
		float maxHealth, float health) {
		Body body = createBox(world, type, position, width, height, density, restitution, friction);
		
		body.setUserData(new EnemyUserData(body, width, height, maxHealth, health));
		return body;
	}
	
	public static Body createPlayerCircle(World world, BodyType type, Vector2 position, float radius, float density, float restitution, float friction) {
		Body body = createCircle(world, type, position, radius, density, restitution, friction);
		
		body.setUserData(new PlayerUserCircleData(body, radius));
		return body;
	}
	
	public static Body createPlayerJoint(World world, BodyType typeBody, BodyType typeBodyJoint, Vector2 positionBody, 
			float widthBody, float heightBody, float densityBody, float restitutionBody, float frictionBody,
			float posXJoint, float posYJoint, float widthBodyJoint, float heightBodyJoint, float densityBodyJoint, float restitutionBodyJoint, float frictionBodyJoint,
			float positionJointA_x, float positionJointB_x, float positionJointA_y, float positionJointB_y,
			float maxHealth, float health, int damage_low, int damage_medium, int damage_high) {
		
		Body body = createJoint(world, typeBody, typeBodyJoint, positionBody, widthBody, heightBody, densityBody, restitutionBody, frictionBody, posXJoint, posYJoint, widthBodyJoint, heightBodyJoint, densityBodyJoint, restitutionBodyJoint, frictionBodyJoint, positionJointA_x, positionJointB_x, positionJointA_y, positionJointB_y);
		body.setUserData(new PlayerUserBoxData(body, widthBody, heightBody, maxHealth, health, damage_low, damage_medium, damage_high));
		
		return body;
	}
	
	public static Body createPlayerJoint(World world, BodyType typeBody, BodyType typeBodyJoint, BodyType typeBodyJoint2, Vector2 positionBody, 
			float widthBody, float heightBody, float densityBody, float restitutionBody, float frictionBody,
			float posXJoint, float posYJoint, float widthBodyJoint, float heightBodyJoint, float densityBodyJoint, float restitutionBodyJoint, float frictionBodyJoint,
			float positionJointA_x, float positionJointB_x, float positionJointA_y, float positionJointB_y,
			float posXJoint2, float posYJoint2, float widthBodyJoint2, float heightBodyJoint2, float densityBodyJoint2, float restitutionBodyJoint2, float frictionBodyJoint2,
			float positionJoint2A_x, float positionJoint2B_x, float positionJoint2A_y, float positionJoint2B_y,
			float maxHealth, float health, int damage_low, int damage_medium, int damage_high) {
		
		Body body = create2Joint(world, typeBody, typeBodyJoint, typeBodyJoint2, positionBody, widthBody, heightBody, densityBody, restitutionBody, frictionBody, posXJoint, posYJoint, widthBodyJoint, heightBodyJoint, densityBodyJoint, restitutionBodyJoint, frictionBodyJoint, positionJointA_x, positionJointB_x, positionJointA_y, positionJointB_y, posXJoint2, posYJoint2, widthBodyJoint2, heightBodyJoint2, densityBodyJoint2, restitutionBodyJoint2, frictionBodyJoint2, positionJoint2A_x, positionJoint2B_x, positionJoint2A_y, positionJoint2B_y);
		body.setUserData(new PlayerUserBoxData(body, widthBody, heightBody, maxHealth, health, damage_low, damage_medium, damage_high));
		
		return body;
	}
	
	public static Body createGround(World world, BodyType type, Vector2 position, float width, float height, float density, float restitution, float friction) {
		Body body = createBox(world, type, position, width, height, density, restitution, friction);
		body.setUserData(new GroundUserData(body, width, height));
		
		return body;
	}
	
	public static Body createBullet(World world, BodyType type, Vector2 position, float width, float height, float density, float restitution, float friction) {
		Body body = createBox(world, type, position, width, height, density, restitution, friction);
		body.setUserData(new BulletUserData(body, width, height));
		
		return body;
	}
	
	public static Body createBody(World world, BodyType type, Vector2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(position);
		bodyDef.fixedRotation = true;
		Body body = world.createBody(bodyDef);
		
		return body;
	}
	
	public static Body createBody(World world, BodyType type, Vector2 position, UserDataType dataType) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(position);
		bodyDef.fixedRotation = true;
		Body body = world.createBody(bodyDef);
		body.setUserData(dataType);
		
		return body;
	}
	
	public static Body createMapBox(World world, BodyType type, Vector2 position, float width, float height, float density, float restitution, float friction) {
		Body body = createBody(world, type, position);
		
		FixtureDef fixDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(width, height);
		fixDef.shape = shape;
		
		fixDef.density = density;
		fixDef.restitution = restitution;
		fixDef.friction = friction;
		
		body.createFixture(fixDef);
		
		shape.dispose();
		return body;
	}
		 
	public static Body createBox(World world, BodyType type, Vector2 position, float width, float height, float density, float restitution, float friction) {
		Body body = createBody(world, type, position);
		
		FixtureDef fixDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(width, height);
		fixDef.shape = shape;
		
		fixDef.density = density;
		fixDef.restitution = restitution;
		fixDef.friction = friction;
				
		body.createFixture(fixDef);
		
		shape.dispose();
		return body;
	}
	
	public static Body createCircle(World world, BodyType type, Vector2 position, float radius, float density, float restitution, float friction) {
		Body body = createBody(world, type, position);
		
		FixtureDef fixDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		
		shape.setRadius(radius / Constants.SCALE);
		fixDef.shape = shape;
		
		fixDef.density = density;
		fixDef.restitution = restitution;
		fixDef.friction = friction;
		
		body.createFixture(fixDef);
		
		shape.dispose();
		return body;
	}
	
	public static Body createJoint(World world, BodyType typeBody, BodyType typeBodyJoint, Vector2 positionBody, 
			float widthBody, float heightBody, float densityBody, float restitutionBody, float frictionBody,
			float posXJoint, float posYJoint, float widthBodyJoint, float heightBodyJoint, float densityBodyJoint, float restitutionBodyJoint, float frictionBodyJoint,
			float positionJointA_x, float positionJointB_x, float positionJointA_y, float positionJointB_y) {
		
		Body body = createBox(world, typeBody, positionBody, widthBody, heightBody, densityBody, restitutionBody, frictionBody);	
		Body bodyJoint = createBox(world, typeBodyJoint, new Vector2((body.getPosition().x -widthBody/2)+posXJoint, (body.getPosition().y -heightBody/2)+posYJoint), 
				widthBodyJoint, heightBodyJoint, densityBodyJoint, restitutionBodyJoint, frictionBodyJoint);
				
		RevoluteJointDef jointDef = new RevoluteJointDef();
		
		jointDef.bodyA = body;
		jointDef.bodyB = bodyJoint;
		jointDef.localAnchorA.x = positionJointA_x;
		jointDef.localAnchorB.x = positionJointB_x;
		jointDef.localAnchorA.y = positionJointA_y;
		jointDef.localAnchorB.y = positionJointB_y;
		
		joint = (RevoluteJoint) world.createJoint(jointDef);
		return body;
	}
	
	public static Body create2Joint(World world, BodyType typeBody, BodyType typeBodyJoint, BodyType typeBodyJoint2, Vector2 positionBody, 
			float widthBody, float heightBody, float densityBody, float restitutionBody, float frictionBody,
			float posXJoint, float posYJoint, float widthBodyJoint, float heightBodyJoint, float densityBodyJoint, float restitutionBodyJoint, float frictionBodyJoint,
			float positionJointA_x, float positionJointB_x, float positionJointA_y, float positionJointB_y,
			float posXJoint2, float posYJoint2, float widthBodyJoint2, float heightBodyJoint2, float densityBodyJoint2, float restitutionBodyJoint2, float frictionBodyJoint2,
			float positionJoint2A_x, float positionJoint2B_x, float positionJoint2A_y, float positionJoint2B_y) {
		
		Body body = createBox(world, typeBody, positionBody, widthBody, heightBody, densityBody, restitutionBody, frictionBody);	
		Body bodyJoint = createBox(world, typeBodyJoint, new Vector2((body.getPosition().x -widthBody/2)+posXJoint, (body.getPosition().y -heightBody/2)+posYJoint), 
				widthBodyJoint, heightBodyJoint, densityBodyJoint, restitutionBodyJoint, frictionBodyJoint);
				
		RevoluteJointDef jointDef = new RevoluteJointDef();
		
		jointDef.bodyA = body;
		jointDef.bodyB = bodyJoint;
		jointDef.localAnchorA.x = positionJointA_x;
		jointDef.localAnchorB.x = positionJointB_x;
		jointDef.localAnchorA.y = positionJointA_y;
		jointDef.localAnchorB.y = positionJointB_y;
		
		joint = (RevoluteJoint) world.createJoint(jointDef);
		
		Body bodyJoint2 = createBox(world, typeBodyJoint2, new Vector2((body.getPosition().x -widthBody/2)+posXJoint2, (body.getPosition().y -heightBody/2)+posYJoint2), 
				widthBodyJoint2, heightBodyJoint2, densityBodyJoint2, restitutionBodyJoint2, frictionBodyJoint2);
				
		jointDef.bodyA = body;
		jointDef.bodyB = bodyJoint2;
		jointDef.localAnchorA.x = positionJoint2A_x;
		jointDef.localAnchorB.x = positionJoint2B_x;
		jointDef.localAnchorA.y = positionJoint2A_y;
		jointDef.localAnchorB.y = positionJoint2B_y;
		
		joint2 = (RevoluteJoint) world.createJoint(jointDef);
		
		return body;
	}
	
	public static RevoluteJoint getJoint() {
		return joint;
	}
	
	public static RevoluteJoint getJoint2() {
		return joint2;
	}
}
