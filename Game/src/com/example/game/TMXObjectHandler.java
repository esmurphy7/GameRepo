package com.example.game;

import java.util.HashMap;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.Shape;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;

public class TMXObjectHandler {

	/*Simple value to check if the incoming entity is a wall type and should trigger collisions
	 * Could add various other checks such as items, enemies, projectiles etc.
	 * Data type is Object so that the check could compare values other than simply a string
	 */
	private static final Object TYPE_WALL = "wall";

	public static void addTMXObject(Activity pParent, Scene scene, int pX, int pY, int pWidth, int pHeight, String pType, HashMap<String, String> propertiesMap, VertexBufferObjectManager vertexBufferObjectManager){
		if(pType.equals(TMXObjectHandler.TYPE_WALL)){
			TMXObjectHandler.addWall(pParent, scene, pX, pY, pWidth, pHeight, propertiesMap, vertexBufferObjectManager);
	    } 


	  }

	  private static void addWall(Activity pParent, Scene pScene, int pX, int pY, int pWidth, int pHeight, HashMap<String,String> propertiesMap, VertexBufferObjectManager vertexBufferObjectManager){
	    final Shape wall = new Rectangle((float)pX, (float)pY, (float)pWidth, (float)pHeight, vertexBufferObjectManager);
	    wall.setVisible(false);
	    pScene.attachChild(wall);
	  }
	
}
