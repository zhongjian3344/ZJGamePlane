package com.zj.zjgameplane.scene;

import java.util.Random;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.util.Log;

import com.zj.zjgameplane.GameActivity;
import com.zj.zjgameplane.GamePlaneConfig;
import com.zj.zjgameplane.bullet.BulletSprite;
import com.zj.zjgameplane.bullettype.AbstractBulletType;
import com.zj.zjgameplane.bullettype.BombBulletType;
import com.zj.zjgameplane.bullettype.DoubleBulletType;
import com.zj.zjgameplane.entity.AbstractEnemy;
import com.zj.zjgameplane.entity.EntityOnStateChanged;
import com.zj.zjgameplane.entity.PlaySprite;
import com.zj.zjgameplane.factory.BulletFactory;
import com.zj.zjgameplane.factory.BulletFactory.BulletType;
import com.zj.zjgameplane.factory.BulletTypeFactory;
import com.zj.zjgameplane.factory.EnemyFactory;
import com.zj.zjgameplane.factory.PlayFactory;
import com.zj.zjgameplane.factory.EnemyFactory.EnemyType;
import com.zj.zjgameplane.managers.ResourceManager;
import com.zj.zjgameplane.managers.SFXManager;
import com.zj.zjgameplane.managers.SceneManager;
import com.zj.zjgameplane.util.CollideEntity;

public class GameScene extends AbstractScene implements EntityOnStateChanged
{
	//滚动背景
	AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0,0,0,5);
	//玩家飞机
	PlaySprite myPlane;
	//暂停按钮
	private ButtonSprite pause;
	//按钮(草莓子弹)
	private ButtonSprite strawberryBullet;
	//按钮(西瓜子弹)
	private ButtonSprite watermelonBullet;
	//按钮(炸弹)
	private ButtonSprite deafultBomb;
	
	//当前的子弹类型
	private BulletType currentBulletType=BulletType.defaultbullet;
	
	//所有需碰撞的实例
	CollideEntity collideEntity=new CollideEntity();
	
	//当前的分值
	private Text scoreText;
	public static int score=0;
	
	//当前的级别
	private Text gradeText;
	private int currentGrade=1;
	//当前的炸弹数量
	private Text bombText;
	private int currentBomb=0;
	
	//当前敌的飞机速度
	int enemySpeed=50;
	
	//注戏当前的状态(暂停还是运行)
	boolean currentRunState=true;
	
	//双发子弹数量
	int doubleCount=0;
	
	
	HUD hud = new HUD();
	
	@Override
	public void populate() 
	{
		//滚动背景
		Sprite snowSprite0 = new Sprite(0,0,ResourceManager.getInstance().singleBack0,ResourceManager.getInstance().vbom);
		ParallaxEntity parallaxEntity=new ParallaxEntity(0,5, snowSprite0);
		autoParallaxBackground.attachParallaxEntity(parallaxEntity);
		this.setBackground(autoParallaxBackground);
		
		//分数
		createScore();
		//级别
		createGrade();
		//炸弹数量
		createBomb();
		
		//设置当前的场景
		collideEntity.setScene(this);

		//定时生成敌机
		this.registerUpdateHandler(createEnemyHandler);
		//加载玩家飞机
		addPlayerPlane(ResourceManager.getInstance().camera.getWidth()/2,ResourceManager.getInstance().camera.getHeight()-100);
		//定时发射子弹
		this.registerUpdateHandler(playerShootHandler);	
		//碰撞检测
		registerUpdateHandler(new collideDetection());
		//创建按扭
		createButton();
		//设置当前的游戏难度系数
		setGameGrade();
		
		//定时出现炸弹和子弹类型
		this.registerUpdateHandler(playerShootType);
		
		//设置前景
		camera.setHUD(hud);
		
		SFXManager.getInstance().playMusic(SFXManager.mMusic);
	}

	@Override
	public void onPause() 
	{
		
	}

	@Override
	public void onResume() 
	{
		
	}
	
	//级别
	private void  createGrade()
	{
		gradeText = new Text(5,5, res.font, "0123456789", vbom);
		gradeText.setText("级别:"+String.valueOf(currentGrade));
		hud.attachChild(gradeText);
	}
	
	//分数
	private void createScore() 
	{
		scoreText = new Text(80,5, res.font, "0123456789", vbom);
		scoreText.setText("分数:"+String.valueOf(score));
		hud.attachChild(scoreText);
	}	
	
	//炸弹(左下角)
	private void createBomb()
	{
		bombText = new Text(45,760, res.font, "0123456789", vbom);
		bombText.setText(String.valueOf(currentBomb));
		this.attachChild(bombText);
	}
	
	//加载玩家飞机
	private void addPlayerPlane(float tx, float ty)
	{
		myPlane = PlayFactory.getInstance().createPlay(tx, ty);
		hud.attachChild(myPlane);
		camera.setHUD(hud);
		
		collideEntity.setMyPlay(myPlane);
		this.registerTouchArea(myPlane);
		this.setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	//加载敌机
	private void addEnemyPlane(float tx, float ty,EnemyType enemyType)
	{
		AbstractEnemy tempEnemy=EnemyFactory.getInstance().createEnemy(tx,ty,enemyType,enemySpeed);
		attachChild(tempEnemy);
		tempEnemy.setListener(this);
		collideEntity.addEnemySprite(tempEnemy);
	}
	
	//创建按扭
	private void createButton()
	{
		pause=new ButtonSprite(450,5,res.tiledPause,vbom);
		registerTouchArea(pause);
		pause.setOnClickListener(onClickListenerButton);
		hud.attachChild(pause);
		
		deafultBomb=new ButtonSprite(5,750,res.tileddefaultbomb,vbom);
		registerTouchArea(deafultBomb);
		deafultBomb.setOnClickListener(onClickListenerButton);
		hud.attachChild(deafultBomb);	
	}
	
	//定时发射子弹
	TimerHandler playerShootHandler = new TimerHandler(0.1f, true, new ITimerCallback()
	{
		@Override
		public void onTimePassed(TimerHandler pTimerHandler)
		{
			//计算出子弹的位置
			float pX = myPlane.getX() + myPlane.getWidth() / 2.0f;
			float pY = myPlane.getY() - myPlane.getHeight() / 2.0f;
			
			
			if(doubleCount>0)
			{
				BulletSprite tempBullet=BulletFactory.getInstance().createBullet(pX,pY,-10,currentBulletType);
				tempBullet.setPosition(pX-tempBullet.getWidth()/2-20, pY);
				GameScene.this.attachChild(tempBullet);
				collideEntity.addBulletSprite(tempBullet);
				
				tempBullet=BulletFactory.getInstance().createBullet(pX,pY,-10,currentBulletType);
				tempBullet.setPosition(pX-tempBullet.getWidth()/2+20, pY);
				GameScene.this.attachChild(tempBullet);
				collideEntity.addBulletSprite(tempBullet);
				
				doubleCount--;
			}
			else
			{
				BulletSprite tempBullet=BulletFactory.getInstance().createBullet(pX,pY,-10,currentBulletType);
				tempBullet.setPosition(pX-tempBullet.getWidth()/2, pY);
				GameScene.this.attachChild(tempBullet);
				collideEntity.addBulletSprite(tempBullet);
			}
			
			
			
			//播放声音
			SFXManager.getInstance().playSound(SFXManager.sBullet);
		}
	});
	
	//定时生成子弹类型(双发,炸弹)
	TimerHandler playerShootType = new TimerHandler(20f, true, new ITimerCallback()
	{
		@Override
		public void onTimePassed(TimerHandler pTimerHandler)
		{
			float pX = random.nextFloat()*450;
			AbstractBulletType bulletType_double=BulletTypeFactory.getInstance().createBulletType_Double(pX, 0);
			bulletType_double.setListener(GameScene.this);
			collideEntity.addBulletTypeSprite(bulletType_double);
			attachChild(bulletType_double);
			
			pX = random.nextFloat()*450;
			AbstractBulletType bulletType_bomb=BulletTypeFactory.getInstance().createBulletType_Bomb(pX, 0);
			attachChild(bulletType_bomb);
			bulletType_bomb.setListener(GameScene.this);
			collideEntity.addBulletTypeSprite(bulletType_bomb);
		}
	});
	
	private Random random = new  Random();
	
	//定时生成敌机
	TimerHandler createEnemyHandler = new TimerHandler(1f, true, new ITimerCallback()
	{
		@Override
		public void onTimePassed(TimerHandler pTimerHandler)
		{
			//计算出子弹的位置
			float pX = random.nextFloat()*450;
			float pY = -100;
			float tempValue = random.nextFloat()*10;
			EnemyType currentEnemyType=EnemyType.smallEnemy;
			if(tempValue>=0 && tempValue<=5)
			{
				currentEnemyType=EnemyType.smallEnemy;
			}
			else if(tempValue>5 && tempValue<=9)
			{
				currentEnemyType=EnemyType.middleEnemy;
			}
			else
			{
				currentEnemyType=EnemyType.bigEnemy;
			}
			addEnemyPlane(pX,pY,currentEnemyType);
		}
	});
	
	//碰撞检测
	class collideDetection implements IUpdateHandler
	{
		@Override
		public void onUpdate(float pSecondsElapsed)
		{
			//检测子弹是否与敌机碰撞
			collideEntity.collideDetection();
		}

		@Override
		public void reset() 
		{
			Log.v("ZJGamePlane","collideDetection:reset() ");		
		}
	}

	//状态接收事件
	@Override
	public void onStateChanged(final AbstractEnemy tempSprite) 
	{
		score+=tempSprite.getScore();
		
		ResourceManager.getInstance().activity.runOnUpdateThread(new Runnable() 
		{
			@Override
			public void run()
			{
				collideEntity.deleteResourceEnemy(tempSprite);
				//得到当前精灵的分数
				scoreText.setText("分数:"+String.valueOf(score));;
				scoreText.setSize(100, 20);
				//播放声音
				SFXManager.getInstance().playSound(SFXManager.sEnemySmall_down);
				
				if(score%100==0)
				{
					setGameGrade();
				}
			}
		});

	}
	
	//收到落下的类型被获取
	@Override
	public void onStateChanged(final AbstractBulletType tempSprite) 
	{
		ResourceManager.getInstance().activity.runOnUpdateThread(new Runnable() 
		{
			@Override
			public void run()
			{
				collideEntity.deleteResourceBulletType(tempSprite);
			
			    if(tempSprite instanceof BombBulletType)
			    {
			    	currentBomb+=1;
			    	bombText.setText(String.valueOf(currentBomb));
			    	//播放声音
					SFXManager.getInstance().playSound(SFXManager.get_bomb);
			    }
			    if(tempSprite instanceof DoubleBulletType)
			    {
			    	doubleCount+=50;
			    	//播放声音
					SFXManager.getInstance().playSound(SFXManager.get_double_laser);
			    }
			}
		});
	}

	//收到飞机损伤事件
	@Override
	public void onStateChanged(PlaySprite mySprite) 
	{
		SceneManager.getInstance().showGameOverLayer(true);
		//播放声音
		SFXManager.getInstance().playSound(SFXManager.game_over);
	}
	
	//设置级别对应的难度
	private void setGameGrade()
	{
		currentGrade=score/100;
		if(currentGrade==0)
		{
			currentGrade=1;
		}
		
		gradeText.setText("级别:"+String.valueOf(currentGrade));
		
		if(0.5f-(0.1f*currentGrade)>0.1)
		{
			//设置子弹发射的速度
			playerShootHandler.setTimerSeconds(0.5f-(0.1f*currentGrade));
		}
		
		if(2.0f-(0.1f*currentGrade)>0.5)
		{
			//设置敌机生成的速度
			createEnemyHandler.setTimerSeconds(2.0f-(0.1f*currentGrade));
		}
		
		//敌机飞行的速度
		if(currentGrade*50<300)
		{
			enemySpeed=currentGrade*50;
		}
	}
	
	//发射炸弹
	private void launchBomb()
	{
		collideEntity.startBomb();
	}
	
	//重新开始
	public void restart()
	{
		//飞机置位
		myPlane.setPosition(ResourceManager.getInstance().camera.getWidth()/2,ResourceManager.getInstance().camera.getHeight()-100);
		//分数置0
		score=0;
		scoreText.setText("分数:"+String.valueOf(score));;
		//等级置0
		currentGrade=1;
		gradeText.setText("级别:"+String.valueOf(currentGrade));
		//炸弹数量
		currentBomb=0;
		bombText.setText(String.valueOf(currentBomb));
		//去除界面上的敌机
		collideEntity.clearAll();
		//设置速度
		setGameGrade();			
	}
	
	private OnClickListener onClickListenerButton = new OnClickListener() 
	{
		@Override
		public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,float pTouchAreaLocalY) 
		{
			if (pButtonSprite == pause)
			{
				currentRunState=!currentRunState;
				SceneManager.getInstance().showOptionsLayer(true);
			}
			
			//点击了炸弹按扭
			if (pButtonSprite == deafultBomb)
			{
				//检查当前炸弹的数量
				if(currentBomb>0)
				{
					currentBomb-=1;
					bombText.setText(String.valueOf(currentBomb));
					//发射炸弹
					launchBomb();
					//播放声音
					SFXManager.getInstance().playSound(SFXManager.use_bomb);
				}
			}
		}
	};

	

}
