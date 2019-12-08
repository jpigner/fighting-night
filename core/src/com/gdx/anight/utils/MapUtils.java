package com.gdx.anight.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdx.anight.actors.tiledmap.MapObjekte;
import com.gdx.anight.box2d.tiledmap.MapLineUserData;
import com.gdx.anight.box2d.tiledmap.MapObjekteUserData;
import com.gdx.anight.box2d.tiledmap.TiledEnemyUserData;
import com.gdx.anight.box2d.tiledmap.TiledGroundUserData;
import com.gdx.anight.enums.EnemyStates;
import com.gdx.anight.enums.UserDataType;

public class MapUtils {
	
	public static TiledMap mapLoader(String level, TiledMap map) {
		TmxMapLoader maploader = new TmxMapLoader();	
		map = maploader.load(("maps/bloodymap2" +level +".tmx"));
		
		return map;
	}
	
	public static OrthogonalTiledMapRenderer loadTiledRenderer(TiledMap map) {
		OrthogonalTiledMapRenderer renderer;
		renderer = new OrthogonalTiledMapRenderer(mapLoader("", map), 1f / Constants.SCALE);
		
		return renderer;
	}
		
	
	public static void renameUserDataType(MapObjekte array, UserDataType dataType) {		
		for (int i = 0; i < array.getUserData().getBodies().size; i++) {
			array.getUserData().getBodies().get(i).setUserData(dataType);
		}		
	}
	
	public static Array<Body> createLayerGround(World world, TiledMap map, int layerIndex, BodyType bodyType, float density, float restitution, float friction) {
		Array<Body> bodies = new Array<Body>();
		
		for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			Body body = Box2dutils.createMapBox(world, bodyType, new Vector2((rect.getX()/Constants.SCALE + (rect.getWidth()/2/Constants.SCALE)), 
					(rect.getY()/Constants.SCALE + (rect.getHeight()/2)/Constants.SCALE))
					, (rect.getWidth()/Constants.SCALE / 2), (rect.getHeight()/Constants.SCALE / 2), density, restitution, friction);
			
			bodies.add(body);
			body.setUserData(new TiledGroundUserData(bodies, rect.width, rect.height));
		}
				
		return bodies;	
	}
	
	public static Array<Body> createLayerObject(World world, TiledMap map, int layerIndex, BodyType bodyType, float density, float restitution, float friction) {
		Array<Body> bodies = new Array<Body>();
		
		for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			Body body = Box2dutils.createMapBox(world, bodyType, new Vector2((rect.getX()/Constants.SCALE + (rect.getWidth()/2/Constants.SCALE)), 
					(rect.getY()/Constants.SCALE + (rect.getHeight()/2)/Constants.SCALE))
					, (rect.getWidth()/Constants.SCALE / 2), (rect.getHeight()/Constants.SCALE / 2), density, restitution, friction);
			
			bodies.add(body);
			body.setUserData(new MapObjekteUserData(bodies, rect.width, rect.height));
		}
				
		return bodies;	
	}
	
		
	public static Array<Body> createLayerEnemy(World world, TiledMap map, int layerIndex, BodyType bodyType, float density, float restitution, float friction, 
			float maxHealth, float health, float damage, boolean dead, EnemyStates currentState) {
		Array<Body> bodies = new Array<Body>();
		
		for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			Body body = Box2dutils.createMapBox(world, bodyType, new Vector2((rect.getX()/Constants.SCALE + (rect.getWidth()/Constants.SCALE)), 
					(rect.getY()/Constants.SCALE + (rect.getHeight())/Constants.SCALE))
					, (rect.getWidth()/Constants.SCALE), (rect.getHeight()/Constants.SCALE), density, restitution, friction);
			
			bodies.add(body);
			body.setUserData(new TiledEnemyUserData(body, rect.width, rect.height, maxHealth, health, damage, dead, currentState));
		}
				
		return bodies;	
	}
		
	public static Body createOneBody_Layer(World world, TiledMap map, int enemyIndex, int layerIndex, BodyType bodyType, float density, float restitution, float friction,
			float maxHealth, float health, float damage, boolean dead, EnemyStates currentState) {
		
		Rectangle rect = map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class).get(enemyIndex).getRectangle();
		
		Body body = Box2dutils.createMapBox(world, bodyType, new Vector2((rect.getX()/Constants.SCALE + (rect.getWidth()/Constants.SCALE)), 
				(rect.getY()/Constants.SCALE + (rect.getHeight())/Constants.SCALE))
				, ((rect.getWidth()/2)/Constants.SCALE), (rect.getHeight()/Constants.SCALE), density, restitution, friction);
			body.setUserData(new TiledEnemyUserData(body, rect.width, rect.height, maxHealth, health, damage, dead, currentState));
		return body;
		
	}
	
	public static Body createOneBodyWindow_Layer(World world, TiledMap map, int enemyIndex, int layerIndex, BodyType bodyType, float density, float restitution, float friction,
			float maxHealth, float health, float damage, boolean dead, EnemyStates currentState) {
		
		Rectangle rect = map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class).get(enemyIndex).getRectangle();
		
		Body body = Box2dutils.createMapBox(world, bodyType, new Vector2((rect.getX()/Constants.SCALE + (rect.getWidth()/2/Constants.SCALE)), 
				(rect.getY()/Constants.SCALE + (rect.getHeight()/2)/Constants.SCALE))
				, ((rect.getWidth()/2)/Constants.SCALE), ((rect.getHeight()/2)/Constants.SCALE), density, restitution, friction);
			body.setUserData(new TiledEnemyUserData(body, rect.width, rect.height, maxHealth, health, damage, dead, currentState));
		return body;
		
	}
	
	public static int sizeOfLayer(World world, TiledMap map, int layerIndex) {
		Array<Body> bodies = new Array<Body>();
		int size;
		
		for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			Body body = Box2dutils.createMapBox(world, BodyType.DynamicBody, new Vector2((rect.getX()/Constants.SCALE + (rect.getWidth()/Constants.SCALE)), 
					(rect.getY()/Constants.SCALE + (rect.getHeight())/Constants.SCALE))
					, (rect.getWidth()/Constants.SCALE), (rect.getHeight()/Constants.SCALE), 1, 0, 0);
			
			bodies.add(body);
		}
		
		size = bodies.size;
		
		for(int i = 0; i<bodies.size; i++) {
			world.destroyBody(bodies.get(i));
		}
		
		return size;
	}
	
	public static Array<Body> createLayerTriangles(World world, TiledMap map, int layerIndex) {
		Array<Body> bodies = new Array<Body>();
		
		for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(PolygonMapObject.class)) {
			Polygon polygon = ((PolygonMapObject) object).getPolygon();
			
			PolygonShape shape = new PolygonShape();
			float[] vertices = polygon.getTransformedVertices();
			Vector2[] worldVertices = new Vector2[vertices.length / 2];
			
			for(int i = 0; i < worldVertices.length; i++) {
				worldVertices[i] = new Vector2(vertices[i * 2] / Constants.SCALE, vertices[i * 2 + 1] / Constants.SCALE);
			}
			
			shape.set(worldVertices);
			Body body;
			BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(shape, 1.0f);
            body.setUserData(new MapLineUserData(bodies));
            bodies.add(body);
			shape.dispose();
		}
		
		return bodies;
	}
		
	public static Array<Body> createLayerPolylines(World world, TiledMap map, int layerIndex) {
		Array<Body> bodies = new Array<Body>();
		
		for(MapObject object : map.getLayers().get(layerIndex).getObjects().getByType(PolylineMapObject.class)) {
			Polyline line = ((PolylineMapObject) object).getPolyline();
			
			float[] vertices = line.getTransformedVertices();
			Vector2[] worldVertices = new Vector2[vertices.length / 2];
			
			for(int i = 0; i < worldVertices.length; i++) {
				worldVertices[i] = new Vector2(vertices[i * 2] / Constants.SCALE, vertices[i * 2 + 1] / Constants.SCALE);
			}
		
			ChainShape shape = new ChainShape();	
			shape.createChain(worldVertices);
			Body body;
			BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(shape, 1.0f);
            body.setUserData(new MapLineUserData(bodies));
            bodies.add(body);
			shape.dispose();
           
		}	
		return bodies;
	}
	
}
