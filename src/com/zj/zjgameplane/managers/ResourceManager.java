package com.zj.zjgameplane.managers;

import org.andengine.audio.music.Music;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.graphics.Typeface;

import com.zj.zjgameplane.GameActivity;

public class ResourceManager 
{
	private static final ResourceManager INSTANCE = new ResourceManager();

	//�������
	public GameActivity activity;
	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	
	public float cameraWidth=480;
	public float cameraHeight=800;
	
	//��ʼ����
	public ITextureRegion splashTextureRegion;
	//��ʼ�����е�����ͼƬ
	public ITextureRegion splashFontTextureRegion;
	
	//��������
	public ITextureRegion singleBack0;
	//�ɻ�(����)
	public  ITiledTextureRegion tiledPlane;
	//�ɻ�(��ը)
	public  ITiledTextureRegion tiledPlaneBomb;
	//�ӵ�(Ĭ��)
	public  ITiledTextureRegion tiledBullet;
	//�����(˫���ӵ�)
	public  ITiledTextureRegion tiledBulletType_Double;
	//�����(ը��)
	public  ITiledTextureRegion tiledBulletType_Bomb;
	//ը��
	public  ITiledTextureRegion tileddefaultbomb;
	
	//�л�(С��)
	public  ITiledTextureRegion tiledSmallEnemyPlay;
	//�л�(����)
	public  ITiledTextureRegion tiledMiddleEnemyPlay;
	//�л�(����)
	public  ITiledTextureRegion tiledBigEnemyPlay;
	
	//��Ť����
	public ITextureRegion btnStart;
	public ITextureRegion btnAbout;
	public ITextureRegion btnVoiceOpen;
	public ITextureRegion btnVoiceClose;
	public ITiledTextureRegion tiledPause;
	public ITextureRegion btnContinue;
	public ITextureRegion btnRestart;
	
	
	// ����
	public Font font;
	public ITextureRegion itr;
	public ITexture fontTexture;
	
	//music
	public Music music;
	
	public static ResourceManager getInstance() 
	{
		return INSTANCE;
	}
	
	public void create(GameActivity activity, Engine engine, Camera camera, VertexBufferObjectManager vbom) 
	{
		this.activity = activity;
		this.engine = engine;
		this.camera = camera;
		this.vbom = vbom;
	}
	
	//������Դ
	public void loadResource()
	{
		//����Ŀ¼
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//��������
		loadTexture();
		//���ذ�Ť��Դ
		loadBtnRes();
		//��������
		loadFont();
	}
	
	//����������Դ
	private void loadTexture()
	{
		//����
		if(singleBack0==null) singleBack0 =getLimitableTR("bg_01.jpg",TextureOptions.BILINEAR);
		//�ɻ�����(����״̬)
		if(tiledPlane==null) tiledPlane = getTiledTR("myplane.png",TextureOptions.BILINEAR,140,80,2,1);
		//�ɻ�����(��ը״̬)
		if(tiledPlaneBomb==null) tiledPlaneBomb = getTiledTR("myplaneexplosion.png",TextureOptions.BILINEAR,140,80,2,1);
		//�ӵ�(Ĭ��)
		if(tiledBullet==null) tiledBullet = getTiledTR("bullet.png",TextureOptions.BILINEAR,10,20,1,1);
		//�����(˫��)
		if(tiledBulletType_Double==null) tiledBulletType_Double = getTiledTR("bullet_goods.png",TextureOptions.BILINEAR,40,50,1,1);
		//�����(ը��)
		if(tiledBulletType_Bomb==null) tiledBulletType_Bomb = getTiledTR("missile_goods.png",TextureOptions.BILINEAR,40,50,1,1);
		//ը��
		if(tileddefaultbomb==null) tileddefaultbomb = getTiledTR("missile_bt.png",TextureOptions.BILINEAR,40,80,1,2);
		
		//�л�����(С��)
		if(tiledSmallEnemyPlay==null) tiledSmallEnemyPlay = getTiledTR("small.png",TextureOptions.BILINEAR,34,84,1,3);
		//�л�����(����)
		if(tiledMiddleEnemyPlay==null) tiledMiddleEnemyPlay = getTiledTR("middle.png",TextureOptions.BILINEAR,70,360,1,4);
		//�л�����(����)
		if(tiledBigEnemyPlay==null) tiledBigEnemyPlay = getTiledTR("big.png",TextureOptions.BILINEAR,108,650,1,5);
	
	}
	
	//���ذ�Ť��Դ
	public void loadBtnRes()
	{
		//��ʼ��Ϸ
		if(btnStart==null) btnStart =getLimitableTR("start.png",TextureOptions.BILINEAR);
		if(btnAbout==null) btnAbout =getLimitableTR("about.png",TextureOptions.BILINEAR);
		if(btnVoiceOpen==null) btnVoiceOpen =getLimitableTR("open.png",TextureOptions.BILINEAR);
		if(btnVoiceClose==null) btnVoiceClose =getLimitableTR("close.png",TextureOptions.BILINEAR);
		if(btnContinue==null) btnContinue =getLimitableTR("continue.png",TextureOptions.BILINEAR);
		if(btnRestart==null) btnRestart =getLimitableTR("restart.png",TextureOptions.BILINEAR);

		//��Ť(��ͣ)
		if(tiledPause==null) tiledPause = getTiledTR("play.png",TextureOptions.BILINEAR,30,60,1,2);
	}
	
	//���ص���ͼƬ��������
	private TextureRegion getLimitableTR(String pTextureRegionPath, TextureOptions pTextureOptions) 
	{
		 IBitmapTextureAtlasSource bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource.create(activity.getAssets(), BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + pTextureRegionPath);
		 BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), bitmapTextureAtlasSource.getTextureWidth(), bitmapTextureAtlasSource.getTextureHeight(), pTextureOptions);
		 TextureRegion textureRegion = new TextureRegion(bitmapTextureAtlas, 0, 0, bitmapTextureAtlasSource.getTextureWidth(), bitmapTextureAtlasSource.getTextureHeight(), false);
		 bitmapTextureAtlas.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		 bitmapTextureAtlas.load();
		 return textureRegion;
	}	
	
	//����ͼƬ��������
	private TiledTextureRegion getTiledTR(String pTextureRegionPath, TextureOptions pTextureOptions,int width,int height,int column,int row) 
	{
		BuildableBitmapTextureAtlas mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(activity.getTextureManager(),width,height, TextureOptions.BILINEAR);
		TiledTextureRegion mTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlas,activity,pTextureRegionPath,column,row);
		try
		{
			mBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
			mBitmapTextureAtlas.load();
		} 
		catch (TextureAtlasBuilderException e) 
		{
			e.printStackTrace();
		}

		return mTiledTextureRegion;
	}	
	
	//��������
	public void loadFont() 
	{
		fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		int FONT_SIZE = 20;
		font = new Font(activity.getFontManager(), fontTexture, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), FONT_SIZE, true, Color.BLACK);
		font.load();
	}
	
	//���س�ʼ��Դ
	public void loadSplashGraphics() 
	{
		//����Ŀ¼
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//��������
		if(splashTextureRegion==null) splashTextureRegion =getLimitableTR("splashbg.png",TextureOptions.REPEATING_BILINEAR);
		if(splashFontTextureRegion==null) splashFontTextureRegion =getLimitableTR("title.png",TextureOptions.REPEATING_BILINEAR);	
	}
	
	//�ͷų�ʼ��Դ
	public void unloadSplashGraphics() 
	{
		
	}
	
}
