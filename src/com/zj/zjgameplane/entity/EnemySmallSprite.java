package com.zj.zjgameplane.entity;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class EnemySmallSprite extends AbstractEnemy
{
	
	public static final String TYPE = "EnemySmallSprite";

	
	public EnemySmallSprite(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager) 
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	@Override
	public void damage(int amount)
	{
		super.damage(amount);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) 
	{
		super.onManagedUpdate(pSecondsElapsed);
		if(dead)
		{
			this.setScale(1.2f);
			if(getCurrentTileIndex()>=2)
			{
				entityOnStateChanged.onStateChanged(this);
			}
		}
	}
	

}
