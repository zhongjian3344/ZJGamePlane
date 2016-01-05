/**
 * 抽象类
 */


package com.zj.zjgameplane.entity;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.zj.zjgameplane.bullet.IShootable;

public abstract class AbstractEnemy extends AnimatedSprite
{
	//状态事件
	public EntityOnStateChanged entityOnStateChanged=null;
	//移动接口
	IShootable iShootable=null;
	//是否销毁
	boolean dead = false;
	//健康指数
	private int health=1;
	//分数
	private int score=1;
	
	public AbstractEnemy(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	/*** 设置监听对象*/
	public void setListener(EntityOnStateChanged tempListener)
	{
		entityOnStateChanged=tempListener;
	}
	
	/**设置健康指数*/
	public void setHealth(int tempHealth)
	{
		health=tempHealth;
	}
	
	/**损伤减血*/
	public synchronized void  damage(int amount)
	{
		health = health - amount;
		
		//判断是否已经损坏,如果损坏则播放动画
		if(isDead() && !dead)
		{
			dead=true;
			this.animate(100,0);
		}
		
	}
	
	/**补充加血*/
	public void supply(int amount)
	{
		health = health + amount;
	}
	
	/**得到健康指数*/
	public int getHealth()
	{
		return health;
	}

	/**设置分数*/
	public void setScore(int tempScore)
	{
		score=tempScore;
	}
	
	/**得到分数*/
	public int getScore()
	{
		return score;
	}
	
	
	/**判断是否损坏 true:损坏 false:末损坏*/
	public boolean isDead()
	{
		if(health <= 0)
		{
			return true;
		}
		return false;
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

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) 
	{
		super.onManagedUpdate(pSecondsElapsed);
		if(dead)
		{
			if(getCurrentTileIndex()>=3)
			{
				entityOnStateChanged.onStateChanged(this);
			}
		}
	}
	
}
