package com.zj.zjgameplane.layers;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;


import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import android.widget.Toast;

import com.zj.zjgameplane.GameActivity;
import com.zj.zjgameplane.GamePlaneConfig;
import com.zj.zjgameplane.managers.ResourceManager;
import com.zj.zjgameplane.managers.SceneManager;
import com.zj.zjgameplane.scene.GameScene;

public class GameOverLayer extends ManagedLayer
{
	private static final GameOverLayer INSTANCE = new GameOverLayer();
	float BackgroundWidth = 350f; 
	float BackgroundHeight = 200f;
	Text scoreText=null;

	public static GameOverLayer getInstance()
	{
		return INSTANCE;
	}
	
	// Animates the layer to slide in from the top.
	IUpdateHandler SlideIn = new IUpdateHandler() 
	{
		@Override
		public void onUpdate(float pSecondsElapsed) 
		{
			
			if(GameOverLayer.getInstance().getY()>ResourceManager.getInstance().cameraHeight/2f) 
			{
				GameOverLayer.getInstance().setPosition(ResourceManager.getInstance().cameraWidth/2-BackgroundWidth/2,ResourceManager.getInstance().cameraHeight/2f-BackgroundHeight/2);
			} 
			else 
			{
				GameOverLayer.getInstance().unregisterUpdateHandler(this);
			}
			
		}
		@Override public void reset() {}
	};
	
	// Animates the layer to slide out through the top and tell the SceneManager to hide it when it is off-screen;
	IUpdateHandler SlideOut = new IUpdateHandler() 
	{
		@Override
		public void onUpdate(float pSecondsElapsed) 
		{
			if(GameOverLayer.getInstance().getY()<ResourceManager.getInstance().cameraHeight/2f+480f)
			{
				GameOverLayer.getInstance().setPosition(GameOverLayer.getInstance().getX(), Math.min(GameOverLayer.getInstance().getY()+(3600*(pSecondsElapsed)),ResourceManager.getInstance().cameraHeight/2f+480f));
			}
			else 
			{
				GameOverLayer.getInstance().unregisterUpdateHandler(this);
				SceneManager.getInstance().hideLayer();
			}
		}
		@Override public void reset() {}
	};
	
	ButtonSprite btnRestart;
	
	@Override
	public void onLoadLayer()
	{
		// Create and attach a background that hides the Layer when touched.
		final float BackgroundX = 0f, BackgroundY = 0f;
		
		Rectangle smth = new Rectangle(BackgroundX,BackgroundY,BackgroundWidth,BackgroundHeight,ResourceManager.getInstance().engine.getVertexBufferObjectManager());
		smth.setColor(Color.TRANSPARENT);
		smth.setAlpha(0.1f);
		this.attachChild(smth);
		this.registerTouchArea(smth);
		
		
		btnRestart=new ButtonSprite(BackgroundWidth/2f-ResourceManager.getInstance().btnRestart.getWidth()/2f,BackgroundHeight/2f,ResourceManager.getInstance().btnRestart,ResourceManager.getInstance().vbom);
		btnRestart.setOnClickListener(onClickListenerButton);
		registerTouchArea(btnRestart);
		attachChild(btnRestart);
		
		//分数
		scoreText = new Text(0,5,ResourceManager.getInstance().font, "0123456789",ResourceManager.getInstance().vbom);
		attachChild(scoreText);

		this.setPosition(ResourceManager.getInstance().cameraWidth/2f, ResourceManager.getInstance().cameraHeight/2f+480f);
	
		
		
	}

	@Override
	public void onShowLayer()
	{
		String historyMaxScroe=GamePlaneConfig.getInstance().getConfigValue(GamePlaneConfig.maxscore,"0");
		if(GameScene.score>Integer.parseInt(historyMaxScroe))
		{
			//写入分值
			GamePlaneConfig.getInstance().setConfigValue(GamePlaneConfig.maxscore,GameScene.score+"");
		}

		//写入分值
		String maxScroe=GamePlaneConfig.getInstance().getConfigValue(GamePlaneConfig.maxscore,"0");
		scoreText.setText("最高分:"+maxScroe);
				
		this.registerUpdateHandler(SlideIn);
	}

	@Override
	public void onHideLayer() 
	{
		this.registerUpdateHandler(SlideOut);
	}
	@Override
	public void onUnloadLayer()
	{
		
	}
	
	//按扭事件
	private OnClickListener onClickListenerButton = new OnClickListener() 
	{
		@Override
		public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,float pTouchAreaLocalY) 
		{
			if (pButtonSprite == btnRestart)
			{
				onHideLayer();
				
				SceneManager.getInstance().getCurrentScene().restart();
			}
		}
	};
}