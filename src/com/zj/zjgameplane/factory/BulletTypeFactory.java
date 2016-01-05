/**
 * 备注:炸弹工厂
 * 说明:此类只负责建造炸弹
 */

package com.zj.zjgameplane.factory;


import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.zj.zjgameplane.bullet.BulletShootable;
import com.zj.zjgameplane.bullettype.AbstractBulletType;
import com.zj.zjgameplane.bullettype.BombBulletType;
import com.zj.zjgameplane.bullettype.DoubleBulletType;
import com.zj.zjgameplane.managers.ResourceManager;

public class BulletTypeFactory 
{
	private static BulletTypeFactory INSTANCE = new BulletTypeFactory();
	
	public static final FixtureDef ENEMY_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f, true);
	

	public static BulletTypeFactory getInstance() 
	{
		return INSTANCE;
	}
	
	//创建(掉下的)双发的子弹
	public AbstractBulletType createBulletType_Double(float x, float y) 
	{
		DoubleBulletType bulletTypeSprite =new DoubleBulletType(x,y,ResourceManager.getInstance().tiledBulletType_Double,ResourceManager.getInstance().vbom);
		bulletTypeSprite.setiShootable(new BulletShootable(bulletTypeSprite, 0,300));
		return bulletTypeSprite;
	}
	
	//创建(掉下的)炸弹
	public AbstractBulletType createBulletType_Bomb(float x, float y) 
	{
		BombBulletType bulletTypeSprite =new BombBulletType(x,y,ResourceManager.getInstance().tiledBulletType_Bomb,ResourceManager.getInstance().vbom);
		bulletTypeSprite.setiShootable(new BulletShootable(bulletTypeSprite, 0,200));
		return bulletTypeSprite;
	}
	
	//销毁子弹类型
	public void destroySprite(AbstractBulletType tempSprite)
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
