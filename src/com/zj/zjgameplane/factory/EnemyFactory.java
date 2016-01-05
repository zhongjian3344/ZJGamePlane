/**
 * 备注:敌机工厂
 * 说明:此类只负责建造敌机
 */

package com.zj.zjgameplane.factory;


import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.zj.zjgameplane.bullet.BulletShootable;
import com.zj.zjgameplane.entity.AbstractEnemy;
import com.zj.zjgameplane.entity.EnemyBigSprite;
import com.zj.zjgameplane.entity.EnemyMiddleSprite;
import com.zj.zjgameplane.entity.EnemySmallSprite;
import com.zj.zjgameplane.managers.ResourceManager;

public class EnemyFactory 
{
	private static EnemyFactory INSTANCE = new EnemyFactory();
	
	public static final FixtureDef ENEMY_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f, true);

	
	public static EnemyFactory getInstance() 
	{
		return INSTANCE;
	}
	

	public AbstractEnemy createEnemy(float x, float y,EnemyType tempType,float yVelocity) 
	{
		//随机生成不同类型的敌机
		AbstractEnemy enemy = null;
		if(tempType==EnemyType.smallEnemy)
		{
			enemy = new EnemySmallSprite(x, y, ResourceManager.getInstance().tiledSmallEnemyPlay,ResourceManager.getInstance().vbom);
			enemy.setHealth(1);
			enemy.setScore(10);
		}
		else if(tempType==EnemyType.middleEnemy)
		{
			enemy = new EnemyMiddleSprite(x, y, ResourceManager.getInstance().tiledMiddleEnemyPlay,ResourceManager.getInstance().vbom);
			enemy.setHealth(2);
			enemy.setScore(20);
		}
		else 
		{
			enemy = new EnemyBigSprite(x, y, ResourceManager.getInstance().tiledBigEnemyPlay,ResourceManager.getInstance().vbom);
			enemy.setHealth(3);
			enemy.setScore(30);
		}
		
		enemy.setiShootable(new BulletShootable(enemy, 0,yVelocity));
			
		return enemy;
	}
	
	//销毁敌机
	public void destroyEnemy(AbstractEnemy tempSprite)
	{
		if(!tempSprite.isDisposed())
		{
			tempSprite.removeiShootable();
			tempSprite.detachSelf();
			tempSprite.dispose();
			tempSprite=null;
		}
	}

	//敌机的类型
	public enum EnemyType
	{
		smallEnemy,
		middleEnemy,
		bigEnemy
	}
}
