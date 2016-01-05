package com.zj.zjgameplane.util;

import java.util.ArrayList;

import org.andengine.entity.sprite.Sprite;

import com.zj.zjgameplane.bullet.BulletSprite;
import com.zj.zjgameplane.bullettype.AbstractBulletType;
import com.zj.zjgameplane.entity.AbstractEnemy;
import com.zj.zjgameplane.entity.PlaySprite;
import com.zj.zjgameplane.factory.BulletFactory;
import com.zj.zjgameplane.factory.BulletTypeFactory;
import com.zj.zjgameplane.factory.EnemyFactory;
import com.zj.zjgameplane.managers.ResourceManager;
import com.zj.zjgameplane.scene.AbstractScene;

public class CollideEntity 
{
	//子弹
	ArrayList<BulletSprite> arrBulletSprite=new  ArrayList<BulletSprite>();
	//敌机
	ArrayList<AbstractEnemy> arrEnemySprite=new  ArrayList<AbstractEnemy>();
	//掉下来的(类型)
	ArrayList<AbstractBulletType> arrBulletTypeSprite=new  ArrayList<AbstractBulletType>();
	ArrayList<AbstractBulletType> tempArrBulletTypeSprite=new  ArrayList<AbstractBulletType>();	
	
	//子弹
	ArrayList<BulletSprite> tempArrBulletSprite=new  ArrayList<BulletSprite>();
	//敌机
	ArrayList<AbstractEnemy> tempArrEnemySprite=new  ArrayList<AbstractEnemy>();
	//敌机(炸弹)
	ArrayList<AbstractEnemy> tempArrEnemySprite_Bomb=new  ArrayList<AbstractEnemy>();
		
	//玩家飞机
	PlaySprite myPlaySprite=null;
	
	
	
	//场景
	AbstractScene currentScene=null;
	
	//是否启动了轰炸
	boolean isStartBomb=false;
	
	//设置场景
	public void setScene(AbstractScene tempScene)
	{
		currentScene=tempScene;
	}
	
	//设置玩家飞机
	public void setMyPlay(PlaySprite tempSprite)
	{
		myPlaySprite=tempSprite;
	}
	
	//增加列表中的子弹
	public void addBulletSprite(BulletSprite tempSprite)
	{
		arrBulletSprite.add(tempSprite);
	}
	
	//移除列表中的子弹
	public void removeBulletSprite(Sprite tempSprite)
	{
		arrBulletSprite.remove(tempSprite);
	}
	
	//增加列表中的敌机
	public void addEnemySprite(AbstractEnemy tempSprite)
	{
		arrEnemySprite.add(tempSprite);
	}
	
	//移除列表中的敌机
	public void removeEnemySprite(Sprite tempSprite)
	{
		arrEnemySprite.remove(tempSprite);
	}
	
	//增加列表中的掉落的类型
	public void addBulletTypeSprite(AbstractBulletType tempSprite)
	{
		arrBulletTypeSprite.add(tempSprite);
	}
	

	//碰撞检测
	public void collideDetection()
	{
		tempArrBulletSprite.clear();
		tempArrEnemySprite.clear();
		tempArrBulletTypeSprite.clear();

		for(int i=0;i<arrBulletSprite.size();i++)
		{
			tempArrBulletSprite.add(arrBulletSprite.get(i));
		}
		
		for(int i=0;i<arrEnemySprite.size();i++)
		{
			tempArrEnemySprite.add(arrEnemySprite.get(i));
		}
		
		for(int i=0;i<arrBulletTypeSprite.size();i++)
		{
			tempArrBulletTypeSprite.add(arrBulletTypeSprite.get(i));
		}
		
		for(int i=0;i<tempArrBulletSprite.size();i++)
		{
			if(tempArrBulletSprite.get(i).getY()<0)
			{
				//销毁子弹
				deleteResourceBullet(tempArrBulletSprite.get(i));
				continue;
			}
			
			for(int y=0;y<tempArrEnemySprite.size();y++)
			{
				//如果点击了炸弹则不执行检测碰撞
				if(isStartBomb){continue;}
				
				if(tempArrEnemySprite.get(y).getY()>ResourceManager.getInstance().camera.getHeight())
				{
					//销毁飞机
					if(arrEnemySprite.contains(tempArrEnemySprite.get(y)))
					{
						deleteResourceEnemy(tempArrEnemySprite.get(y));
					}
					continue;
				}
				
				//如果子弹和飞机相撞
				if(tempArrBulletSprite.get(i).collidesWith(tempArrEnemySprite.get(y)))
				{
					//销毁子弹
					deleteResourceBullet(tempArrBulletSprite.get(i));
					
					if(arrEnemySprite.contains(tempArrEnemySprite.get(y)))
					{
						//增加敌机的损伤值
						tempArrEnemySprite.get(y).damage(1);
						//如果敌机已经损伤到一定值,将敌机从列表中去除
						if(tempArrEnemySprite.get(y).isDead())
						{
							arrEnemySprite.remove(tempArrEnemySprite.get(y));
						}
					}
				}
				
				//如果敌机与飞机相撞
				if(tempArrEnemySprite.get(y).collidesWith(myPlaySprite))
				{
					tempArrEnemySprite.get(y).entityOnStateChanged.onStateChanged(myPlaySprite);
				}
			}
		}
		
		//判断飞机是否与敌机相撞或与落下的类型相撞
		for(int i=0;i<tempArrBulletTypeSprite.size();i++)
		{
			if(tempArrBulletTypeSprite.get(i).getY()>ResourceManager.getInstance().camera.getHeight())
			{
				deleteResourceBulletType(tempArrBulletTypeSprite.get(i));
				continue;
			}
			
			//如果敌机和落下的类型相撞
			if(tempArrBulletTypeSprite.get(i).collidesWith(myPlaySprite))
			{
				tempArrBulletTypeSprite.get(i).entityOnStateChanged.onStateChanged(tempArrBulletTypeSprite.get(i));
			}
		}
	}
	
	//开始轰炸
	public void startBomb()
	{
		isStartBomb=true;
		tempArrEnemySprite_Bomb.clear();
		
		for(int i=0;i<arrEnemySprite.size();i++)
		{
			tempArrEnemySprite_Bomb.add(arrEnemySprite.get(i));
		}
		
		for(int i=0;i<tempArrEnemySprite_Bomb.size();i++)
		{
			//销毁飞机
			if(arrEnemySprite.contains(tempArrEnemySprite_Bomb.get(i)) && tempArrEnemySprite_Bomb.get(i)!=null)
			{
				tempArrEnemySprite_Bomb.get(i).damage(100);
			}
		}
		isStartBomb=false;
	}
	
	//删除场景中和内存中的对象,释放资源(子弹)
	public void deleteResourceBullet(BulletSprite tempSprite)
	{
		arrBulletSprite.remove(tempSprite);
		currentScene.detachChild(tempSprite);
		BulletFactory.getInstance().destroyBullet(tempSprite);
	}
	
	//删除场景中和内存中的对象,释放资源(敌机)
	public void deleteResourceEnemy(AbstractEnemy tempSprite)
	{
		if(arrEnemySprite.contains(tempSprite))
		{
			arrEnemySprite.remove(tempSprite);
		}
		EnemyFactory.getInstance().destroyEnemy(tempSprite);
		currentScene.detachChild(tempSprite);
	}
	
	//删除场景中和内存中的对象,释放资源(落下的)
	public void deleteResourceBulletType(AbstractBulletType tempSprite)
	{
		if(arrBulletTypeSprite.contains(tempSprite))
		{
			arrBulletTypeSprite.remove(tempSprite);
		}
		BulletTypeFactory.getInstance().destroySprite(tempSprite);
		currentScene.detachChild(tempSprite);
	}
	
	//清除所有敌机
	public void clearAll()
	{
		tempArrBulletSprite.clear();
		tempArrEnemySprite.clear();
		tempArrBulletTypeSprite.clear();

		for(int i=0;i<arrBulletSprite.size();i++)
		{
			tempArrBulletSprite.add(arrBulletSprite.get(i));
		}
		
		for(int i=0;i<arrEnemySprite.size();i++)
		{
			tempArrEnemySprite.add(arrEnemySprite.get(i));
		}
		
		for(int i=0;i<arrBulletTypeSprite.size();i++)
		{
			tempArrBulletTypeSprite.add(arrBulletTypeSprite.get(i));
		}
		
		for(int i=0;i<tempArrBulletSprite.size();i++)
		{
			//销毁子弹
			deleteResourceBullet(tempArrBulletSprite.get(i));
		}
			
		for(int y=0;y<tempArrEnemySprite.size();y++)
		{
			deleteResourceEnemy(tempArrEnemySprite.get(y));
		}
			
		//判断飞机是否与敌机相撞或与落下的类型相撞
		for(int i=0;i<tempArrBulletTypeSprite.size();i++)
		{
			deleteResourceBulletType(tempArrBulletTypeSprite.get(i));
		}
		
	}
}
