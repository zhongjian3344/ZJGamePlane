package com.zj.zjgameplane.entity;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class EnemyMiddleSprite extends AbstractEnemy
{
	public static final String TYPE = "EnemyMiddleSprite";
	
	public EnemyMiddleSprite(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager) 
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) 
	{
		super.onManagedUpdate(pSecondsElapsed);
		if(dead)
		{
			this.setScale(1.2f);
		}
	}

}
