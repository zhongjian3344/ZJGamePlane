package com.zj.zjgameplane.layers;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;


import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

import com.zj.zjgameplane.managers.ResourceManager;
import com.zj.zjgameplane.managers.SceneManager;

public class OptionsLayer extends ManagedLayer
{
	private static final OptionsLayer INSTANCE = new OptionsLayer();
	float BackgroundWidth = 350f; 
	float BackgroundHeight = 200f;
	
	
	public static OptionsLayer getInstance()
	{
		return INSTANCE;
	}
	
	// Animates the layer to slide in from the top.
	IUpdateHandler SlideIn = new IUpdateHandler() 
	{
		@Override
		public void onUpdate(float pSecondsElapsed) 
		{
			
			if(OptionsLayer.getInstance().getY()>ResourceManager.getInstance().cameraHeight/2f) 
			{
				OptionsLayer.getInstance().setPosition(ResourceManager.getInstance().cameraWidth/2-BackgroundWidth/2,ResourceManager.getInstance().cameraHeight/2f-BackgroundHeight/2);
			} 
			else 
			{
				OptionsLayer.getInstance().unregisterUpdateHandler(this);
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
			if(OptionsLayer.getInstance().getY()<ResourceManager.getInstance().cameraHeight/2f+480f)
			{
				OptionsLayer.getInstance().setPosition(OptionsLayer.getInstance().getX(), Math.min(OptionsLayer.getInstance().getY()+(3600*(pSecondsElapsed)),ResourceManager.getInstance().cameraHeight/2f+480f));
			}
			else 
			{
				OptionsLayer.getInstance().unregisterUpdateHandler(this);
				SceneManager.getInstance().hideLayer();
			}
		}
		@Override public void reset() {}
	};
	
	ButtonSprite btnContinue;
	
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
		
		
		btnContinue=new ButtonSprite(BackgroundWidth/2f-ResourceManager.getInstance().btnContinue.getWidth()/2f,BackgroundHeight/2f,ResourceManager.getInstance().btnContinue,ResourceManager.getInstance().vbom);
		btnContinue.setOnClickListener(onClickListenerButton);
		registerTouchArea(btnContinue);
		attachChild(btnContinue);
		
		this.setPosition(ResourceManager.getInstance().cameraWidth/2f, ResourceManager.getInstance().cameraHeight/2f+480f);
	}

	@Override
	public void onShowLayer()
	{
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
	
	//°´Å¤ÊÂ¼þ
	private OnClickListener onClickListenerButton = new OnClickListener() 
	{
		@Override
		public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,float pTouchAreaLocalY) 
		{
			if (pButtonSprite == btnContinue)
			{
				onHideLayer();
			}
		}
	};
}