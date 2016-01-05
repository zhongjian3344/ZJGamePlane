package com.zj.zjgameplane.bullet;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.Entity;

public class BulletShootable implements IShootable
{
	private float xVelocity;
	private float yVelocity;
	private Entity shootFrom;
	
	PhysicsHandler mPhysicsHandler;
	
	public BulletShootable(Entity shootFrom, float xVelocity, float yVelocity)
	{
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.shootFrom = shootFrom;
	}
	
	@Override
	public void shoot()
	{
		mPhysicsHandler = new PhysicsHandler(shootFrom);
		this.shootFrom.registerUpdateHandler(this.mPhysicsHandler);
		this.mPhysicsHandler.setVelocity(xVelocity,yVelocity);
	}
	
	@Override
	public void unshoot()
	{
		this.shootFrom.unregisterUpdateHandler(this.mPhysicsHandler);
	}

}