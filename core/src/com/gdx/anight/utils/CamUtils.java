package com.gdx.anight.utils;

import com.badlogic.gdx.graphics.Camera;
import com.gdx.anight.actors.Player;

public class CamUtils {
	
	public static void playerCam(Camera camera, Player player, float x_versatz, float y_versatz) {
		camera.position.x = player.getUserData().getBody().getPosition().x +x_versatz;
		camera.position.y = player.getUserData().getBody().getPosition().y +y_versatz;
		camera.update();
	}

	
	public static void screenCam(Camera camera) {
		camera.position.set((camera.viewportWidth / 2), camera.viewportHeight /2, 0f);
		camera.update();
	}
	
}
