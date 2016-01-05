package com.zj.zjgameplane.bullettype;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class BombBulletType extends AbstractBulletType
{
	public BombBulletType(float pX, float pY,ITiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager pVertexBufferObjectManager) 
	{
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
}
