package com.zj.zjgameplane.scene;


import org.andengine.entity.sprite.Sprite;



public class SplashScene extends AbstractScene 
{

	@Override
	public void populate() 
	{
		Sprite splashSprite = new Sprite(res.camera.getCenterX()-res.splashFontTextureRegion.getWidth()/2,200,res.splashFontTextureRegion,vbom);
		attachChild(splashSprite);
		this.getBackground().setColor(195,201,201);
		
	}
	
	

	



	@Override
	public void onPause()
	{
		
	}

	@Override
	public void onResume() 
	{
		
	}

}
