/**
 * ��ע:�ɻ�����
 * ˵��:����ֻ��������ҷɻ�
 */


package com.zj.zjgameplane.factory;

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.zj.zjgameplane.entity.PlaySprite;
import com.zj.zjgameplane.managers.ResourceManager;

public class PlayFactory 
{
	private static PlayFactory INSTANCE = new PlayFactory();
	
	public static final FixtureDef ENEMY_FIXTURE = PhysicsFactory.createFixtureDef(1f, 0f, 1f, true);

	public static PlayFactory getInstance() 
	{
		return INSTANCE;
	}
	

	
	//������ҷɻ�
	public PlaySprite createPlay(float x, float y) 
	{
		PlaySprite myPlane = new PlaySprite(x,y,ResourceManager.getInstance().tiledPlane,ResourceManager.getInstance().vbom,1);
		myPlane.animate(200);
		return myPlane;
	}

}
