package com.zj.zjgameplane.entity;

import com.zj.zjgameplane.bullettype.AbstractBulletType;

public interface EntityOnStateChanged 
{
	public void onStateChanged(AbstractEnemy tempSprite);
	public void onStateChanged(AbstractBulletType tempSprite);
	public void onStateChanged(PlaySprite mySprite);
}
