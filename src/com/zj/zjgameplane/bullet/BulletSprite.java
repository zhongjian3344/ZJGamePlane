package com.zj.zjgameplane.bullet;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BulletSprite extends AbstractBullet 
{
	public BulletSprite(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager, int health)
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager, health);
	}
}
