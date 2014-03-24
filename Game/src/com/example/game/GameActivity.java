package com.example.game;

import java.util.ArrayList;
import java.util.HashMap;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
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
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;


public class GameActivity extends BaseGameActivity {

	private final int CAMERA_WIDTH = 32*20;
	private final int CAMERA_HEIGHT = 32*10;
	private Scene scene;
	private BoundCamera mBoundCamera;
	private TMXTiledMap tiledMap;
	
	private BitmapTextureAtlas playerTexture;
	private ITextureRegion playerTextureRegion;
	
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
		this.mBoundCamera.setBounds(0, 0, tiledMap.getTMXLayers().get(0).getHeight(), tiledMap.getTMXLayers().get(0).getWidth());
		this.mBoundCamera.setBoundsEnabled(true);

		/* Calculate the coordinates for the face, so its centered on the camera. */
		//final float playerCenterX = (CAMERA_WIDTH - this.playerTextureRegion.getWidth()) / 2;
		//final float playerCenterY = (CAMERA_HEIGHT - this.playerTextureRegion.getHeight()) / 2;

			
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	private void loadGfx() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//Last two params (width and height of texture) must be to the power of 2
		//playerTexture = new BitmapTextureAtlas(getTextureManager(), 64, 64);
		//Last two parameters are where the image is set to initially
		//playerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(playerTexture, this, "player.png", 0, 0);
		//playerTexture.load();
		
	}
	
}
