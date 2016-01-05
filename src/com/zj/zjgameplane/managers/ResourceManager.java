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

	//传输对象
	public GameActivity activity;
	public Engine engine;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	
	public float cameraWidth=480;
	public float cameraHeight=800;
	
	//初始界面
	public ITextureRegion splashTextureRegion;
	//初始界面中的文字图片
	public ITextureRegion splashFontTextureRegion;
	
	//滚动背景
	public ITextureRegion singleBack0;
	//飞机(正常)
	public  ITiledTextureRegion tiledPlane;
	//飞机(爆炸)
	public  ITiledTextureRegion tiledPlaneBomb;
	//子弹(默认)
	public  ITiledTextureRegion tiledBullet;
	//掉落的(双发子弹)
	public  ITiledTextureRegion tiledBulletType_Double;
	//掉落的(炸弹)
	public  ITiledTextureRegion tiledBulletType_Bomb;
	//炸弹
	public  ITiledTextureRegion tileddefaultbomb;
	
	//敌机(小型)
	public  ITiledTextureRegion tiledSmallEnemyPlay;
	//敌机(中型)
	public  ITiledTextureRegion tiledMiddleEnemyPlay;
	//敌机(大型)
	public  ITiledTextureRegion tiledBigEnemyPlay;
	
	//按扭部分
	public ITextureRegion btnStart;
	public ITextureRegion btnAbout;
	public ITextureRegion btnVoiceOpen;
	public ITextureRegion btnVoiceClose;
	public ITiledTextureRegion tiledPause;
	public ITextureRegion btnContinue;
	public ITextureRegion btnRestart;
	
	
	// 字体
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
	
	//加载资源
	public void loadResource()
	{
		//设置目录
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//加载纹理
		loadTexture();
		//加载按扭资源
		loadBtnRes();
		//加载字体
		loadFont();
	}
	
	//加载纹理资源
	private void loadTexture()
	{
		//背景
		if(singleBack0==null) singleBack0 =getLimitableTR("bg_01.jpg",TextureOptions.BILINEAR);
		//飞机纹理(正常状态)
		if(tiledPlane==null) tiledPlane = getTiledTR("myplane.png",TextureOptions.BILINEAR,140,80,2,1);
		//飞机纹理(爆炸状态)
		if(tiledPlaneBomb==null) tiledPlaneBomb = getTiledTR("myplaneexplosion.png",TextureOptions.BILINEAR,140,80,2,1);
		//子弹(默认)
		if(tiledBullet==null) tiledBullet = getTiledTR("bullet.png",TextureOptions.BILINEAR,10,20,1,1);
		//掉落的(双发)
		if(tiledBulletType_Double==null) tiledBulletType_Double = getTiledTR("bullet_goods.png",TextureOptions.BILINEAR,40,50,1,1);
		//掉落的(炸弹)
		if(tiledBulletType_Bomb==null) tiledBulletType_Bomb = getTiledTR("missile_goods.png",TextureOptions.BILINEAR,40,50,1,1);
		//炸弹
		if(tileddefaultbomb==null) tileddefaultbomb = getTiledTR("missile_bt.png",TextureOptions.BILINEAR,40,80,1,2);
		
		//敌机纹理(小型)
		if(tiledSmallEnemyPlay==null) tiledSmallEnemyPlay = getTiledTR("small.png",TextureOptions.BILINEAR,34,84,1,3);
		//敌机纹理(中型)
		if(tiledMiddleEnemyPlay==null) tiledMiddleEnemyPlay = getTiledTR("middle.png",TextureOptions.BILINEAR,70,360,1,4);
		//敌机纹理(大型)
		if(tiledBigEnemyPlay==null) tiledBigEnemyPlay = getTiledTR("big.png",TextureOptions.BILINEAR,108,650,1,5);
	
	}
	
	//加载按扭资源
	public void loadBtnRes()
	{
		//开始游戏
		if(btnStart==null) btnStart =getLimitableTR("start.png",TextureOptions.BILINEAR);
		if(btnAbout==null) btnAbout =getLimitableTR("about.png",TextureOptions.BILINEAR);
		if(btnVoiceOpen==null) btnVoiceOpen =getLimitableTR("open.png",TextureOptions.BILINEAR);
		if(btnVoiceClose==null) btnVoiceClose =getLimitableTR("close.png",TextureOptions.BILINEAR);
		if(btnContinue==null) btnContinue =getLimitableTR("continue.png",TextureOptions.BILINEAR);
		if(btnRestart==null) btnRestart =getLimitableTR("restart.png",TextureOptions.BILINEAR);

		//按扭(暂停)
		if(tiledPause==null) tiledPause = getTiledTR("play.png",TextureOptions.BILINEAR,30,60,1,2);
	}
	
	//加载单张图片到纹理集中
	private TextureRegion getLimitableTR(String pTextureRegionPath, TextureOptions pTextureOptions) 
	{
		 IBitmapTextureAtlasSource bitmapTextureAtlasSource = AssetBitmapTextureAtlasSource.create(activity.getAssets(), BitmapTextureAtlasTextureRegionFactory.getAssetBasePath() + pTextureRegionPath);
		 BitmapTextureAtlas bitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), bitmapTextureAtlasSource.getTextureWidth(), bitmapTextureAtlasSource.getTextureHeight(), pTextureOptions);
		 TextureRegion textureRegion = new TextureRegion(bitmapTextureAtlas, 0, 0, bitmapTextureAtlasSource.getTextureWidth(), bitmapTextureAtlasSource.getTextureHeight(), false);
		 bitmapTextureAtlas.addTextureAtlasSource(bitmapTextureAtlasSource, 0, 0);
		 bitmapTextureAtlas.load();
		 return textureRegion;
	}	
	
	//加载图片到纹理集中
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
	
	//加载字体
	public void loadFont() 
	{
		fontTexture = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		int FONT_SIZE = 20;
		font = new Font(activity.getFontManager(), fontTexture, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), FONT_SIZE, true, Color.BLACK);
		font.load();
	}
	
	//加载初始资源
	public void loadSplashGraphics() 
	{
		//设置目录
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//启动背景
		if(splashTextureRegion==null) splashTextureRegion =getLimitableTR("splashbg.png",TextureOptions.REPEATING_BILINEAR);
		if(splashFontTextureRegion==null) splashFontTextureRegion =getLimitableTR("title.png",TextureOptions.REPEATING_BILINEAR);	
	}
	
	//释放初始资源
	public void unloadSplashGraphics() 
	{
		
	}
	
}
