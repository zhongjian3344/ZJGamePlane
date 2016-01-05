/**
 * 备注:炸弹工厂
 * 说明:此类只负责建造炸弹
 */

package com.zj.zjgameplane.factory;


import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.zj.zjgameplane.bullet.BulletShootable;
import com.zj.zjgameplane.bullet.BulletSprite;
import com.zj.zjgameplane.managers.ResourceManager;

public class BulletFactory 
{
	private static BulletFactory INSTANCE = new BulletFactory();
	
	public static final FixtureDef ENEMY_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f, true);
	

	public static BulletFactory getInstance() 
	{
		return INSTANCE;
	}
	
	
	//创建默认的炸弹
	public BulletSprite createBullet(float x, float y,float yVelocity,BulletType type) 
	{
		BulletSprite bulletSprite = null;
		if(type==BulletType.defaultbullet)
		{
			bulletSprite=new BulletSprite(x,y+30,ResourceManager.getInstance().tiledBullet,ResourceManager.getInstance().vbom,1);
		}
			
		bulletSprite.setiShootable(new BulletShootable(bulletSprite, 0, -300));
		return bulletSprite;
	}
	
	//销毁子弹
	public void destroyBullet(BulletSprite tempSprite)
	{
		if(!tempSprite.isDisposed())
		{
			tempSprite.removeiShootable();
			tempSprite.detachSelf();
			tempSprite.dispose();
			tempSprite=null;
		}
	}

	//子弹的类型
	public enum BulletType
	{
		defaultbullet,
		strawberry,
		watermelon
	}
}
