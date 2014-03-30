package com.example.game;

import java.util.ArrayList;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;


public class GameActivity extends BaseGameActivity {

	private final int CAMERA_WIDTH = 32*20;
	private final int CAMERA_HEIGHT = 32*10;
	private Scene scene;
	private BoundCamera mBoundCamera;
	private TMXTiledMap tiledMap;
	
	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion playerTopHalfTextureRegion;
	private TiledTextureRegion playerBottomHalfTextureRegion;
	private TiledTextureRegion mHelicopterTextureRegion;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mBoundCamera = new BoundCamera(0, 0, this.CAMERA_WIDTH, this.CAMERA_HEIGHT);
		EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(this.CAMERA_WIDTH, this.CAMERA_HEIGHT), mBoundCamera);
		return options;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		ResourceManager.setUp(this.mEngine, this);
		ResourceManager.getInstance().loadResources();
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		this.scene = new Scene();
		//`this.scene.setBackground(new Background(0, 125, 58));
		/*try{
			final TMXLoader tmxLoader = new TMXLoader(this.getAssets(), this.mEngine.getTextureManager(), TextureOptions.DEFAULT, this.getVertexBufferObjectManager());
			tiledMap = tmxLoader.loadFromAsset("tmx/level1.tmx");
		}catch(final TMXLoadException e){
			Debug.e(e);
		}
		 */
		
		
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		/*
		//Sprite sPlayer = new Sprite(this.CAMERA_WIDTH/2, this.CAMERA_HEIGHT/2, playerTextureRegion, this.mEngine.getVertexBufferObjectManager());
		//sPlayer.setRotation(45.0f);
		//this.scene.attachChild(sPlayer);
		
		//Attach each layer to the scene
		ArrayList<TMXLayer> layers = tiledMap.getTMXLayers();
		for(TMXLayer layer : layers){
			this.scene.attachChild(layer);
		}
		
		

		//Make the camera not exceed the bounds of the TMXEntity.
		int mapWidth = tiledMap.getTMXLayers().get(0).getWidth();
		int mapHeight = tiledMap.getTMXLayers().get(0).getHeight();
		this.mBoundCamera.setBounds(0, 0, mapHeight, mapWidth);
		this.mBoundCamera.setBoundsEnabled(true);

		// Calculate the coordinates for the face, so its centered on the camera.
		final float playerCenterX = (CAMERA_WIDTH - this.playerTopHalfTextureRegion.getWidth()) / 2;
		final float playerCenterY = (CAMERA_HEIGHT - this.playerTopHalfTextureRegion.getHeight()) / 2;

		/*
		final AnimatedSprite helicopter = new AnimatedSprite(32*9, 32*2, this.mHelicopterTextureRegion, this.getVertexBufferObjectManager());
		//each index in the long array corresponds to an index in the spritesheet, its value the animation duration in milliseconds
		//the next 2 numbers specify what part of the spritesheet to animate(first and last index)
		helicopter.animate(new long[] { 100, 100, 100, 100 }, 0, 3, true);
		scene.attachChild(helicopter);
		*/
		
		/* Create the sprite and add it to the scene. 
		final AnimatedSprite playerTopHalf = new AnimatedSprite(32*5, 32*2, this.playerTopHalfTextureRegion, this.getVertexBufferObjectManager());
		final AnimatedSprite playerBottomHalf = new AnimatedSprite(32*5, 32*3, this.playerBottomHalfTextureRegion, this.getVertexBufferObjectManager());
		this.mBoundCamera.setChaseEntity(playerTopHalf);
		int duration = 150;
		playerTopHalf.animate(duration);
		playerBottomHalf.animate(duration);
		this.scene.attachChild(playerTopHalf);
		this.scene.attachChild(playerBottomHalf);*/
		
		//Attach layers of tiledmap (must happen before sprites are attached)
		ArrayList<TMXLayer> layers = ResourceManager.getInstance().level1.getTMXLayers();
		for(TMXLayer layer : layers){
			this.scene.attachChild(layer);
		}
		
		Player.getInstance().attachSelf(this.scene);
		Player.getInstance().animateSprites();
		
		
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	
	
}
