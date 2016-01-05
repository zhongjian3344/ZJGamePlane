/**
 * ������
 */


package com.zj.zjgameplane.entity;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.zj.zjgameplane.bullet.IShootable;

public abstract class AbstractEnemy extends AnimatedSprite
{
	//״̬�¼�
	public EntityOnStateChanged entityOnStateChanged=null;
	//�ƶ��ӿ�
	IShootable iShootable=null;
	//�Ƿ�����
	boolean dead = false;
	//����ָ��
	private int health=1;
	//����
	private int score=1;
	
	public AbstractEnemy(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	/*** ���ü�������*/
	public void setListener(EntityOnStateChanged tempListener)
	{
		entityOnStateChanged=tempListener;
	}
	
	/**���ý���ָ��*/
	public void setHealth(int tempHealth)
	{
		health=tempHealth;
	}
	
	/**���˼�Ѫ*/
	public synchronized void  damage(int amount)
	{
		health = health - amount;
		
		//�ж��Ƿ��Ѿ���,������򲥷Ŷ���
		if(isDead() && !dead)
		{
			dead=true;
			this.animate(100,0);
		}
		
	}
	
	/**�����Ѫ*/
	public void supply(int amount)
	{
		health = health + amount;
	}
	
	/**�õ�����ָ��*/
	public int getHealth()
	{
		return health;
	}

	/**���÷���*/
	public void setScore(int tempScore)
	{
		score=tempScore;
	}
	
	/**�õ�����*/
	public int getScore()
	{
		return score;
	}
	
	
	/**�ж��Ƿ��� true:�� false:ĩ��*/
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
