package com.zj.zjgameplane.managers;


import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;

import android.os.AsyncTask;








import com.zj.zjgameplane.layers.GameOverLayer;
import com.zj.zjgameplane.layers.ManagedLayer;
import com.zj.zjgameplane.layers.OptionsLayer;
import com.zj.zjgameplane.scene.AbstractScene;
import com.zj.zjgameplane.scene.GameScene;
import com.zj.zjgameplane.scene.LoadingScene;
import com.zj.zjgameplane.scene.MenuSceneWrapper;
import com.zj.zjgameplane.scene.SplashScene;


public class SceneManager 
{
	
	private static final SceneManager INSTANCE = new SceneManager();
	public static final long SPLASH_DURATION = 2000;
	
	private ResourceManager res = ResourceManager.getInstance();
	
	private AbstractScene currentScene;
	
	private LoadingScene loadingScene = null;
	
	final SplashScene splashScene = new SplashScene();
	
	private SceneManager() { }
	

	public static SceneManager getInstance() 
	{
		return INSTANCE;
	}	

	public AbstractScene getCurrentScene() 
	{
		return currentScene;
	}
	
	public void setCurrentScene(AbstractScene currentScene) 
	{
		this.currentScene = currentScene;
		res.engine.setScene(currentScene);
	}	
	
	
	
	
	//显示启动界面
	public AbstractScene showSplashAndMenuScene() 
	{
		final SplashScene splashScene = new SplashScene();
		splashScene.populate();
		setCurrentScene(splashScene);
		
		//异步方法
		new AsyncTask<Void, Void, Void>() 
		{
			@Override
			protected Void doInBackground(Void... params) 
			{
				//异步加载资源
				res.loadResource();
				//异步加载菜单
				AbstractScene menuScene = new MenuSceneWrapper();
				menuScene.populate();
				setCurrentScene(menuScene);
				splashScene.destroy();
				return null;
			}
		}.execute();	
		return splashScene;
	}

	//等待
	public void waitThreadTime(int waitTime)
	{
		try 
		{
			Thread.sleep(waitTime);
		} 
		catch (InterruptedException e) 
		{
			
		}
		
	}

	//显示菜单界面
	public void showMenuScene() 
	{
		final AbstractScene previousScene = getCurrentScene();
		setCurrentScene(loadingScene);
		new AsyncTask<Void, Void, Void>() 
		{
			@Override
			protected Void doInBackground(Void... params) 
			{
				MenuSceneWrapper menuSceneWrapper = new MenuSceneWrapper();
				menuSceneWrapper.populate();
				setCurrentScene(menuSceneWrapper);
				previousScene.destroy();
				return null;
			}
			
		}.execute();			
	}
	
	public void showGameScene() 
	{
		
		final AbstractScene previousScene = getCurrentScene();
		
		loadingScene = new LoadingScene();
		loadingScene.populate();
		setCurrentScene(loadingScene);
		
		new AsyncTask<Void, Void, Void>() 
		{
			@Override
			protected Void doInBackground(Void... params) 
			{	
				waitThreadTime(1000);
				GameScene gameScene = new GameScene();
				gameScene.populate();
				setCurrentScene(gameScene);
				
				previousScene.destroy();
				return null;
			}
			
		}.execute();			

	}

	private Engine mEngine = ResourceManager.getInstance().engine;
	private boolean mCameraHadHud = false;
	private Scene mPlaceholderModalScene;
	public ManagedLayer currentLayer;
	
	
	public void showOptionsLayer(final boolean pSuspendCurrentSceneUpdates) 
	{
		showLayer(OptionsLayer.getInstance(),false,pSuspendCurrentSceneUpdates,true);
	}
	
	public void showGameOverLayer(final boolean pSuspendCurrentSceneUpdates) 
	{
		showLayer(GameOverLayer.getInstance(),false,pSuspendCurrentSceneUpdates,true);
	}

	//在空白处显示一个图层
	public void showLayer(final ManagedLayer pLayer, final boolean pSuspendSceneDrawing, final boolean pSuspendSceneUpdates, final boolean pSuspendSceneTouchEvents) 
	{
		//如果Camera中已经有HUD,则使用它
		if(mEngine.getCamera().hasHUD())
		{
			mCameraHadHud = true;
		} 
		else 
		{
			mCameraHadHud = false;
			HUD placeholderHud = new HUD();
			mEngine.getCamera().setHUD(placeholderHud);
		}
		
		// If the managed layer needs modal properties, set them.
		if(pSuspendSceneDrawing || pSuspendSceneUpdates || pSuspendSceneTouchEvents)
		{
			// Apply the managed layer directly to the Camera's HUD
			mEngine.getCamera().getHUD().setChildScene(pLayer, pSuspendSceneDrawing, pSuspendSceneUpdates, pSuspendSceneTouchEvents);
			// Create the place-holder scene if it needs to be created.
			if(mPlaceholderModalScene==null) 
			{
				mPlaceholderModalScene = new Scene();
				mPlaceholderModalScene.setBackgroundEnabled(false);
			}
			// Apply the place-holder to the current scene.
			currentScene.setChildScene(mPlaceholderModalScene, pSuspendSceneDrawing, pSuspendSceneUpdates, pSuspendSceneTouchEvents);
		} 
		else 
		{
			// If the managed layer does not need to be modal, simply set it to the HUD.
			mEngine.getCamera().getHUD().setChildScene(pLayer);
		}
		// Set the camera for the managed layer so that it binds to the camera if the camera is moved/scaled/rotated.
		pLayer.setCamera(mEngine.getCamera());
		// Scale the layer according to screen size.
		//pLayer.setScale(ResourceManager.getInstance().cameraScaleFactorX, ResourceManager.getInstance().cameraScaleFactorY);
		// Let the layer know that it is being shown.
		pLayer.onShowManagedLayer();
		// Reflect that a layer is shown.
		isLayerShown = true;
		// Set the current layer to pLayer.
		currentLayer = pLayer;
	}
	
	
	// Boolean to reflect whether there is a layer currently shown on the screen.
	public boolean isLayerShown = false;
	
	public void hideLayer() 
	{
		if(isLayerShown) 
		{
			// Clear the HUD's child scene to remove modal properties.
			mEngine.getCamera().getHUD().clearChildScene();
			// If we had to use a place-holder scene, clear it.
			if(currentScene.hasChildScene())
			{
				if(currentScene.getChildScene()==mPlaceholderModalScene)
				{
					currentScene.clearChildScene();
				}
			}
			// If the camera did not have a HUD before we showed the layer, remove the place-holder HUD.
			if(!mCameraHadHud)
			{
				mEngine.getCamera().setHUD(null);
			}
			// Reflect that a layer is no longer shown.
			isLayerShown = false;
			// Remove the reference to the layer.
			currentLayer = null;
		}
	}

}