package com.zj.zjgameplane.bullet;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class AbstractBullet extends AnimatedSprite
{
	
	public static final String TYPE = "BulletSprite";
	boolean dead = false;
	private int health=1;
	//ÒÆ¶¯½Ó¿Ú
	IShootable iShootable=null;
	
	public AbstractBullet(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager,int health)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.health = health;
	}

	/**ËðÉË¼õÑª*/
	public void damage(int amount)
	{
		health = health - amount;
	}
	
	/**²¹³ä¼ÓÑª*/
	public void supply(int amount)
	{
		health = health + amount;
	}
	
	public int getHealth()
	{
		return health;
	}

	public boolean isDead()
	{
		return health <= 0;
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
