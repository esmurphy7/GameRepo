package com.example.game;

import org.andengine.engine.Engine;
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

import android.app.Activity;

public class ResourceManager {

	//====================================================
	// CONSTANTS
	//====================================================
	private static final ResourceManager INSTANCE = new ResourceManager();
	//====================================================
	// VARIABLES
	//====================================================
	public static Engine mEngine;
	public static BaseGameActivity activity;
	//=============== Game Resources ====================
	public TMXTiledMap level1;
	
	public static TiledTextureRegion playerWalkingTop;
	public static TiledTextureRegion playerWalkingBottom;
	public static TiledTextureRegion mHelicopterTextureRegion;
	
	public ResourceManager(){
		
	}
	
	public static void setUp(Engine mEngine, BaseGameActivity act){
		getInstance().activity=act;
		getInstance().mEngine=mEngine;
	}
	
	public static ResourceManager getInstance(){
		return INSTANCE;
	}
	
	public void loadResources(){
		getInstance().loadTiledMaps();
		getInstance().loadGameTextures();
		getInstance().loadMenuTextures();
		getInstance().loadFonts();
		getInstance().loadSounds();
	}
	
	public void loadTiledMaps(){
		try{
			final TMXLoader tmxLoader = new TMXLoader(activity.getAssets(), this.mEngine.getTextureManager(), TextureOptions.DEFAULT, activity.getVertexBufferObjectManager());
			level1 = tmxLoader.loadFromAsset("tmx/level1.tmx");
		}catch(final TMXLoadException e){
			Debug.e(e);
		}
		
	}
	
	private void loadGameTextures(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	
		//Load player textures
		BuildableBitmapTextureAtlas playerTopAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 32*20, 32*10, TextureOptions.NEAREST);
		playerWalkingTop = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(playerTopAtlas, ResourceManager.activity, "topwalking.png", 6, 1);
		
		BuildableBitmapTextureAtlas playerBottomAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(), 32*20, 32*10, TextureOptions.NEAREST);
		playerWalkingBottom = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(playerBottomAtlas, ResourceManager.activity, "bottomwalking.png", 6, 1);
		
		try {
			playerTopAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			playerTopAtlas.load();
			playerBottomAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
			playerBottomAtlas.load();
		} catch (TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
	
	private void loadMenuTextures(){
		
	}
	
	private void loadFonts(){
		
	}
	
	private void loadSounds(){
		
	}
	
}
