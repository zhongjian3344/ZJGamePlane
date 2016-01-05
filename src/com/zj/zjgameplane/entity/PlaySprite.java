package com.zj.zjgameplane.entity;


import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;







import com.badlogic.gdx.physics.box2d.Body;
import com.zj.zjgameplane.bullet.IShootable;

public class PlaySprite extends AnimatedSprite
{
	
	public static final String TYPE = "PlaySprite";
	boolean dead = false;
	
	private int health=1;

	public PlaySprite(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager,int health)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.health = health;
	}

	public void setiShootable(IShootable iShootable)
	{
		iShootable.shoot();
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) 
	{
		if (pSceneTouchEvent.isActionMove() || pSceneTouchEvent.isActionDown()) 
		{
			this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
			return true;
		}
		return false;
	}	
	

}
