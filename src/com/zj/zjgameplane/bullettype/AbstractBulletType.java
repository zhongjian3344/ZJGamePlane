package com.zj.zjgameplane.bullettype;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.zj.zjgameplane.bullet.IShootable;
import com.zj.zjgameplane.entity.EntityOnStateChanged;

public class AbstractBulletType  extends AnimatedSprite
{
	//状态事件
	public EntityOnStateChanged entityOnStateChanged=null;
	//移动接口
	IShootable iShootable=null;
	
	public AbstractBulletType(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	/*** 设置监听对象*/
	public void setListener(EntityOnStateChanged tempListener)
	{
		entityOnStateChanged=tempListener;
	}
	
	public void setiShootable(IShootable iShootable)
	{
		this.iShootable=iShootable;
		this.iShootable.shoot();
	}
	
	public void removeiShootable()
	{
		this.iShootable.unshoot();
	}
}
