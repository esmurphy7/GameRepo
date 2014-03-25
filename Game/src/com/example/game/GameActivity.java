package com.example.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;


public class GameActivity extends BaseGameActivity {

	private final int CAMERA_WIDTH = 32*20;
	private final int CAMERA_HEIGHT = 32*10;
	private Scene scene;
	private BoundCamera mBoundCamera;
	private TMXTiledMap tiledMap;
	
	private BitmapTextureAtlas mBitMapTextureAtlas;
	private TiledTextureRegion playerTopHalfTextureRegion;
	private TiledTextureRegion mHelicopterTextureRegion;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		mBoundCamera = new BoundCamera(0, 0, this.CAMERA_WIDTH, this.CAMERA_HEIGHT);
		EngineOptions options = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(this.CAMERA_WIDTH, this.CAMERA_HEIGHT), mBoundCamera);
		return options;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		loadGfx();
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}


	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		this.scene = new Scene();
		//`this.scene.setBackground(new Background(0, 125, 58));
		try{
			final TMXLoader tmxLoader = new TMXLoader(this.getAssets(), this.mEngine.getTextureManager(), TextureOptions.DEFAULT, this.getVertexBufferObjectManager());
			tiledMap = tmxLoader.loadFromAsset("tmx/level1.tmx");
		}catch(final TMXLoadException e){
			Debug.e(e);
		}

		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		//Sprite sPlayer = new Sprite(this.CAMERA_WIDTH/2, this.CAMERA_HEIGHT/2, playerTextureRegion, this.mEngine.getVertexBufferObjectManager());
		//sPlayer.setRotation(45.0f);
		//this.scene.attachChild(sPlayer);
		
		//Attach each layer to the scene
		ArrayList<TMXLayer> layers = tiledMap.getTMXLayers();
		for(TMXLayer layer : layers){
			this.scene.attachChild(layer);
		}
		
		

		/* Make the camera not exceed the bounds of the TMXEntity. */
		int mapWidth = tiledMap.getTMXLayers().get(0).getWidth();
		int mapHeight = tiledMap.getTMXLayers().get(0).getHeight();
		this.mBoundCamera.setBounds(0, 0, mapHeight, mapWidth);
		this.mBoundCamera.setBoundsEnabled(true);

		/* Calculate the coordinates for the face, so its centered on the camera. */
		final float playerCenterX = (CAMERA_WIDTH - this.playerTopHalfTextureRegion.getWidth()) / 2;
		final float playerCenterY = (CAMERA_HEIGHT - this.playerTopHalfTextureRegion.getHeight()) / 2;

		final AnimatedSprite helicopter = new AnimatedSprite(32*9, 32*2, this.mHelicopterTextureRegion, this.getVertexBufferObjectManager());
		helicopter.animate(new long[] { 100, 100 }, 1, 2, true);
		scene.attachChild(helicopter);
		
		/* Create the sprite and add it to the scene. */
		final AnimatedSprite player = new AnimatedSprite(32*5, 32*2, 27, 28, this.playerTopHalfTextureRegion, this.getVertexBufferObjectManager());
		this.mBoundCamera.setChaseEntity(player);
		player.animate(100);
		this.scene.attachChild(player);
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	private void loadGfx() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//Last two params (width and height of texture) must be to the power of 2
		mBitMapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 32*20, 32*10, TextureOptions.DEFAULT);
		//Last two parameters are where the image is set to initially
		playerTopHalfTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitMapTextureAtlas, this, "topwalking.png", 0, 0, 6, 1);
		
		mHelicopterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitMapTextureAtlas, this, "helicopter_tiled.png", 0, 0, 2, 2);
		mBitMapTextureAtlas.load();
		
	}
	
}
