package com.zj.zjgameplane.scene;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import com.zj.zjgameplane.GameActivity;
import com.zj.zjgameplane.managers.ResourceManager;



public abstract class AbstractScene extends Scene 
{

	protected ResourceManager res = ResourceManager.getInstance();

	protected Engine engine = res.engine;
	protected GameActivity activity = res.activity;
	protected VertexBufferObjectManager vbom = res.vbom;
	protected Camera camera = res.camera;

	public abstract void populate();

	public void destroy() 
	{

	}

	public void onBackKeyPressed() 
	{
		Debug.d("Back key pressed");
	}

	/**
	 * When the games is paused
	 */
	public abstract void onPause();

	/**
	 * When the game is resumed
	 */
	public abstract void onResume();

	//重新开始
	public void restart()
	{
		
	}
}
