package com.zj.zjgameplane;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.IResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import android.util.Log;

import com.zj.zjgameplane.managers.ResourceManager;
import com.zj.zjgameplane.managers.SFXManager;
import com.zj.zjgameplane.managers.SceneManager;

public class GameActivity extends BaseGameActivity
{

	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 800;
	public static final String gameconfig = "gameconfig";
	public static final String max_score = "max_score";
	
	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		Log.v("ZJGamePlane", "onCreateEngineOptions");
		Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		IResolutionPolicy resolutionPolicy = new FillResolutionPolicy();
		EngineOptions engineOptions = new EngineOptions(false, ScreenOrientation.PORTRAIT_SENSOR, resolutionPolicy, camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception
	{
		this.startActivity(new Intent(this,SplashActivity.class));
		Log.v("ZJGamePlane", "onCreateResources");
		ResourceManager.getInstance().create(this, getEngine(), getEngine().getCamera(), getVertexBufferObjectManager());
		ResourceManager.getInstance().loadSplashGraphics();
		ResourceManager.getInstance().loadResource();
		//≥ı ºªØ…˘“Ù
		SFXManager.getInstance().loadSounds();
		SFXManager.getInstance().loadMusic();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception 
	{
		Log.v("ZJGamePlane", "onCreateScene");
		Scene scene = new Scene();
		//Sprite splashSprite = new Sprite(ResourceManager.getInstance().camera.getCenterX()-ResourceManager.getInstance().splashFontTextureRegion.getWidth()/2,200,ResourceManager.getInstance().splashFontTextureRegion,ResourceManager.getInstance().vbom);
		//scene.attachChild(splashSprite);
		scene.getBackground().setColor(195,201,201);
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene,OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception 
	{
		Log.v("ZJGamePlane", "onPopulateScene");
		
		SceneManager.getInstance().showSplashAndMenuScene();
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	

	@Override
	protected synchronized void onResume() 
	{
		super.onResume();
		System.gc();
	}
		
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		System.exit(0);
	}

	
	
	
}
