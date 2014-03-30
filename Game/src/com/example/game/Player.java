package com.example.game;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;


public class Player implements IUpdateHandler{
	//========== Constants =======
	private static final Player instance = new Player();
	/*TODO: Create class that represents a level. Since each level starts the player at different
	*locations, the start location could be an attribute of the level class.
	*/
	private static final float START_X = 0;
	private static final float START_Y = 0;
	private static final long animationDuration = 150;
	
	//====== Sprites =======
	private AnimatedSprite walkingTop;
	private AnimatedSprite walkingBottom;
	private ArrayList<AnimatedSprite> spriteList;

	
	public Player(){
		spriteList = new ArrayList<AnimatedSprite>();
		
		walkingTop = new AnimatedSprite(0, 0, ResourceManager.playerWalkingTop, ResourceManager.activity.getVertexBufferObjectManager());
		spriteList.add(walkingTop);
		
		walkingBottom = new AnimatedSprite(0, 0, ResourceManager.playerWalkingBottom, ResourceManager.activity.getVertexBufferObjectManager());
		spriteList.add(walkingBottom);
	}
	
	public static Player getInstance(){
		return instance;
	}

	public void attachSelf(Scene scene){
		for(AnimatedSprite sprite : spriteList){
			scene.attachChild(sprite);
		}
	}
	
	public void animateSprites(){
		walkingTop.animate(Player.animationDuration);
		walkingBottom.animate(Player.animationDuration);
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
