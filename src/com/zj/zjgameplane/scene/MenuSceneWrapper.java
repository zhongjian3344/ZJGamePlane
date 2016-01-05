package com.zj.zjgameplane.scene;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.util.color.Color;

import android.content.Intent;
import android.opengl.GLES20;

import com.zj.zjgameplane.AboutActivity;
import com.zj.zjgameplane.managers.ResourceManager;
import com.zj.zjgameplane.managers.SFXManager;
import com.zj.zjgameplane.managers.SceneManager;



public class MenuSceneWrapper extends AbstractScene implements IOnMenuItemClickListener 
{
	ButtonSprite btnStart=null;
	ButtonSprite btnAbout=null;
	ButtonSprite btnVoiceOpen=null;
	ButtonSprite btnVoiceClose=null;
	
	@Override
	public void populate()
	{
		Sprite splashSprite = new Sprite(res.camera.getCenterX()-res.splashFontTextureRegion.getWidth()/2,180,res.splashFontTextureRegion,vbom);
		attachChild(splashSprite);
		
	    btnStart=new ButtonSprite(res.camera.getCenterX()-res.btnStart.getWidth()/2,450,res.btnStart,vbom);
	    btnStart.setOnClickListener(onClickListenerButton);
	    registerTouchArea(btnStart);
	    attachChild(btnStart);
	    
	    btnAbout=new ButtonSprite(res.camera.getCenterX()-res.btnStart.getWidth()/2,550,res.btnAbout,vbom);
	    btnAbout.setOnClickListener(onClickListenerButton);
	    registerTouchArea(btnAbout);
	    attachChild(btnAbout);
	    
	    
	    btnVoiceOpen=new ButtonSprite(res.camera.getCenterX()-res.btnVoiceOpen.getWidth()/2,650,res.btnVoiceOpen,vbom);
	    btnVoiceOpen.setOnClickListener(onClickListenerButton);
	    registerTouchArea(btnVoiceOpen);
	    attachChild(btnVoiceOpen);
	    
	    btnVoiceClose=new ButtonSprite(res.camera.getCenterX()-res.btnVoiceClose.getWidth()/2,650,res.btnVoiceClose,vbom);
	    btnVoiceClose.setOnClickListener(onClickListenerButton);
	    registerTouchArea(btnVoiceClose);
	    btnVoiceClose.setVisible(false);
	    attachChild(btnVoiceClose);
	    
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

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) 
		{
			case 0 : 
				SceneManager.getInstance().showGameScene();
				return true;
			case 1 :
			
			return true;
		default :
			return false;
		} 
	}

	@Override
	public void onBackKeyPressed() 
	{
		activity.finish();
	}
	
	private OnClickListener onClickListenerButton = new OnClickListener() 
	{
		
		@Override
		public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,float pTouchAreaLocalY) 
		{
			if (pButtonSprite == btnStart)
			{
				SceneManager.getInstance().showGameScene();
			}
			if (pButtonSprite == btnVoiceOpen)
			{
				btnVoiceOpen.setVisible(false);
				btnVoiceClose.setVisible(true);
				
				SFXManager.getInstance().mSoundsMuted=true;
				SFXManager.getInstance().mMusicMuted=true;
			}
			if (pButtonSprite == btnVoiceClose)
			{
				btnVoiceOpen.setVisible(true);
				btnVoiceClose.setVisible(false);
				
				SFXManager.getInstance().mSoundsMuted=false;
				SFXManager.getInstance().mMusicMuted=false;
			}
			if(pButtonSprite == btnAbout)
			{
				ResourceManager.getInstance().activity.startActivity(new Intent(ResourceManager.getInstance().activity,AboutActivity.class));
			}
		}
	};
	
}
