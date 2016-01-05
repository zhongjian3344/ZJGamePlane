package com.zj.zjgameplane.entity;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class EnemyBigSprite extends AbstractEnemy
{
	
	public static final String TYPE = "EnemyBigSprite";

	public EnemyBigSprite(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager) 
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
			if(getCurrentTileIndex()>=4)
			{
				entityOnStateChanged.onStateChanged(this);
			}
		}
	}

}
