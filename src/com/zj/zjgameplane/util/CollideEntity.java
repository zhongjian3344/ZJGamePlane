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
	//�ӵ�
	ArrayList<BulletSprite> arrBulletSprite=new  ArrayList<BulletSprite>();
	//�л�
	ArrayList<AbstractEnemy> arrEnemySprite=new  ArrayList<AbstractEnemy>();
	//��������(����)
	ArrayList<AbstractBulletType> arrBulletTypeSprite=new  ArrayList<AbstractBulletType>();
	ArrayList<AbstractBulletType> tempArrBulletTypeSprite=new  ArrayList<AbstractBulletType>();	
	
	//�ӵ�
	ArrayList<BulletSprite> tempArrBulletSprite=new  ArrayList<BulletSprite>();
	//�л�
	ArrayList<AbstractEnemy> tempArrEnemySprite=new  ArrayList<AbstractEnemy>();
	//�л�(ը��)
	ArrayList<AbstractEnemy> tempArrEnemySprite_Bomb=new  ArrayList<AbstractEnemy>();
		
	//��ҷɻ�
	PlaySprite myPlaySprite=null;
	
	
	
	//����
	AbstractScene currentScene=null;
	
	//�Ƿ������˺�ը
	boolean isStartBomb=false;
	
	//���ó���
	public void setScene(AbstractScene tempScene)
	{
		currentScene=tempScene;
	}
	
	//������ҷɻ�
	public void setMyPlay(PlaySprite tempSprite)
	{
		myPlaySprite=tempSprite;
	}
	
	//�����б��е��ӵ�
	public void addBulletSprite(BulletSprite tempSprite)
	{
		arrBulletSprite.add(tempSprite);
	}
	
	//�Ƴ��б��е��ӵ�
	public void removeBulletSprite(Sprite tempSprite)
	{
		arrBulletSprite.remove(tempSprite);
	}
	
	//�����б��еĵл�
	public void addEnemySprite(AbstractEnemy tempSprite)
	{
		arrEnemySprite.add(tempSprite);
	}
	
	//�Ƴ��б��еĵл�
	public void removeEnemySprite(Sprite tempSprite)
	{
		arrEnemySprite.remove(tempSprite);
	}
	
	//�����б��еĵ��������
	public void addBulletTypeSprite(AbstractBulletType tempSprite)
	{
		arrBulletTypeSprite.add(tempSprite);
	}
	

	//��ײ���
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
				//�����ӵ�
				deleteResourceBullet(tempArrBulletSprite.get(i));
				continue;
			}
			
			for(int y=0;y<tempArrEnemySprite.size();y++)
			{
				//��������ը����ִ�м����ײ
				if(isStartBomb){continue;}
				
				if(tempArrEnemySprite.get(y).getY()>ResourceManager.getInstance().camera.getHeight())
				{
					//���ٷɻ�
					if(arrEnemySprite.contains(tempArrEnemySprite.get(y)))
					{
						deleteResourceEnemy(tempArrEnemySprite.get(y));
					}
					continue;
				}
				
				//����ӵ��ͷɻ���ײ
				if(tempArrBulletSprite.get(i).collidesWith(tempArrEnemySprite.get(y)))
				{
					//�����ӵ�
					deleteResourceBullet(tempArrBulletSprite.get(i));
					
					if(arrEnemySprite.contains(tempArrEnemySprite.get(y)))
					{
						//���ӵл�������ֵ
						tempArrEnemySprite.get(y).damage(1);
						//����л��Ѿ����˵�һ��ֵ,���л����б���ȥ��
						if(tempArrEnemySprite.get(y).isDead())
						{
							arrEnemySprite.remove(tempArrEnemySprite.get(y));
						}
					}
				}
				
				//����л���ɻ���ײ
				if(tempArrEnemySprite.get(y).collidesWith(myPlaySprite))
				{
					tempArrEnemySprite.get(y).entityOnStateChanged.onStateChanged(myPlaySprite);
				}
			}
		}
		
		//�жϷɻ��Ƿ���л���ײ�������µ�������ײ
		for(int i=0;i<tempArrBulletTypeSprite.size();i++)
		{
			if(tempArrBulletTypeSprite.get(i).getY()>ResourceManager.getInstance().camera.getHeight())
			{
				deleteResourceBulletType(tempArrBulletTypeSprite.get(i));
				continue;
			}
			
			//����л������µ�������ײ
			if(tempArrBulletTypeSprite.get(i).collidesWith(myPlaySprite))
			{
				tempArrBulletTypeSprite.get(i).entityOnStateChanged.onStateChanged(tempArrBulletTypeSprite.get(i));
			}
		}
	}
	
	//��ʼ��ը
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
			//���ٷɻ�
			if(arrEnemySprite.contains(tempArrEnemySprite_Bomb.get(i)) && tempArrEnemySprite_Bomb.get(i)!=null)
			{
				tempArrEnemySprite_Bomb.get(i).damage(100);
			}
		}
		isStartBomb=false;
	}
	
	//ɾ�������к��ڴ��еĶ���,�ͷ���Դ(�ӵ�)
	public void deleteResourceBullet(BulletSprite tempSprite)
	{
		arrBulletSprite.remove(tempSprite);
		currentScene.detachChild(tempSprite);
		BulletFactory.getInstance().destroyBullet(tempSprite);
	}
	
	//ɾ�������к��ڴ��еĶ���,�ͷ���Դ(�л�)
	public void deleteResourceEnemy(AbstractEnemy tempSprite)
	{
		if(arrEnemySprite.contains(tempSprite))
		{
			arrEnemySprite.remove(tempSprite);
		}
		EnemyFactory.getInstance().destroyEnemy(tempSprite);
		currentScene.detachChild(tempSprite);
	}
	
	//ɾ�������к��ڴ��еĶ���,�ͷ���Դ(���µ�)
	public void deleteResourceBulletType(AbstractBulletType tempSprite)
	{
		if(arrBulletTypeSprite.contains(tempSprite))
		{
			arrBulletTypeSprite.remove(tempSprite);
		}
		BulletTypeFactory.getInstance().destroySprite(tempSprite);
		currentScene.detachChild(tempSprite);
	}
	
	//������ел�
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
			//�����ӵ�
			deleteResourceBullet(tempArrBulletSprite.get(i));
		}
			
		for(int y=0;y<tempArrEnemySprite.size();y++)
		{
			deleteResourceEnemy(tempArrEnemySprite.get(y));
		}
			
		//�жϷɻ��Ƿ���л���ײ�������µ�������ײ
		for(int i=0;i<tempArrBulletTypeSprite.size();i++)
		{
			deleteResourceBulletType(tempArrBulletTypeSprite.get(i));
		}
		
	}
}
