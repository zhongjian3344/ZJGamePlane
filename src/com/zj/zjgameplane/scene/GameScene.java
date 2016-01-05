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
	//��������
	AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0,0,0,5);
	//��ҷɻ�
	PlaySprite myPlane;
	//��ͣ��ť
	private ButtonSprite pause;
	//��ť(��ݮ�ӵ�)
	private ButtonSprite strawberryBullet;
	//��ť(�����ӵ�)
	private ButtonSprite watermelonBullet;
	//��ť(ը��)
	private ButtonSprite deafultBomb;
	
	//��ǰ���ӵ�����
	private BulletType currentBulletType=BulletType.defaultbullet;
	
	//��������ײ��ʵ��
	CollideEntity collideEntity=new CollideEntity();
	
	//��ǰ�ķ�ֵ
	private Text scoreText;
	public static int score=0;
	
	//��ǰ�ļ���
	private Text gradeText;
	private int currentGrade=1;
	//��ǰ��ը������
	private Text bombText;
	private int currentBomb=0;
	
	//��ǰ�еķɻ��ٶ�
	int enemySpeed=50;
	
	//עϷ��ǰ��״̬(��ͣ��������)
	boolean currentRunState=true;
	
	//˫���ӵ�����
	int doubleCount=0;
	
	
	HUD hud = new HUD();
	
	@Override
	public void populate() 
	{
		//��������
		Sprite snowSprite0 = new Sprite(0,0,ResourceManager.getInstance().singleBack0,ResourceManager.getInstance().vbom);
		ParallaxEntity parallaxEntity=new ParallaxEntity(0,5, snowSprite0);
		autoParallaxBackground.attachParallaxEntity(parallaxEntity);
		this.setBackground(autoParallaxBackground);
		
		//����
		createScore();
		//����
		createGrade();
		//ը������
		createBomb();
		
		//���õ�ǰ�ĳ���
		collideEntity.setScene(this);

		//��ʱ���ɵл�
		this.registerUpdateHandler(createEnemyHandler);
		//������ҷɻ�
		addPlayerPlane(ResourceManager.getInstance().camera.getWidth()/2,ResourceManager.getInstance().camera.getHeight()-100);
		//��ʱ�����ӵ�
		this.registerUpdateHandler(playerShootHandler);	
		//��ײ���
		registerUpdateHandler(new collideDetection());
		//������Ť
		createButton();
		//���õ�ǰ����Ϸ�Ѷ�ϵ��
		setGameGrade();
		
		//��ʱ����ը�����ӵ�����
		this.registerUpdateHandler(playerShootType);
		
		//����ǰ��
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
	
	//����
	private void  createGrade()
	{
		gradeText = new Text(5,5, res.font, "0123456789", vbom);
		gradeText.setText("����:"+String.valueOf(currentGrade));
		hud.attachChild(gradeText);
	}
	
	//����
	private void createScore() 
	{
		scoreText = new Text(80,5, res.font, "0123456789", vbom);
		scoreText.setText("����:"+String.valueOf(score));
		hud.attachChild(scoreText);
	}	
	
	//ը��(���½�)
	private void createBomb()
	{
		bombText = new Text(45,760, res.font, "0123456789", vbom);
		bombText.setText(String.valueOf(currentBomb));
		this.attachChild(bombText);
	}
	
	//������ҷɻ�
	private void addPlayerPlane(float tx, float ty)
	{
		myPlane = PlayFactory.getInstance().createPlay(tx, ty);
		hud.attachChild(myPlane);
		camera.setHUD(hud);
		
		collideEntity.setMyPlay(myPlane);
		this.registerTouchArea(myPlane);
		this.setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	//���صл�
	private void addEnemyPlane(float tx, float ty,EnemyType enemyType)
	{
		AbstractEnemy tempEnemy=EnemyFactory.getInstance().createEnemy(tx,ty,enemyType,enemySpeed);
		attachChild(tempEnemy);
		tempEnemy.setListener(this);
		collideEntity.addEnemySprite(tempEnemy);
	}
	
	//������Ť
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
	
	//��ʱ�����ӵ�
	TimerHandler playerShootHandler = new TimerHandler(0.1f, true, new ITimerCallback()
	{
		@Override
		public void onTimePassed(TimerHandler pTimerHandler)
		{
			//������ӵ���λ��
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
			
			
			
			//��������
			SFXManager.getInstance().playSound(SFXManager.sBullet);
		}
	});
	
	//��ʱ�����ӵ�����(˫��,ը��)
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
	
	//��ʱ���ɵл�
	TimerHandler createEnemyHandler = new TimerHandler(1f, true, new ITimerCallback()
	{
		@Override
		public void onTimePassed(TimerHandler pTimerHandler)
		{
			//������ӵ���λ��
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
	
	//��ײ���
	class collideDetection implements IUpdateHandler
	{
		@Override
		public void onUpdate(float pSecondsElapsed)
		{
			//����ӵ��Ƿ���л���ײ
			collideEntity.collideDetection();
		}

		@Override
		public void reset() 
		{
			Log.v("ZJGamePlane","collideDetection:reset() ");		
		}
	}

	//״̬�����¼�
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
				//�õ���ǰ����ķ���
				scoreText.setText("����:"+String.valueOf(score));;
				scoreText.setSize(100, 20);
				//��������
				SFXManager.getInstance().playSound(SFXManager.sEnemySmall_down);
				
				if(score%100==0)
				{
					setGameGrade();
				}
			}
		});

	}
	
	//�յ����µ����ͱ���ȡ
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
			    	//��������
					SFXManager.getInstance().playSound(SFXManager.get_bomb);
			    }
			    if(tempSprite instanceof DoubleBulletType)
			    {
			    	doubleCount+=50;
			    	//��������
					SFXManager.getInstance().playSound(SFXManager.get_double_laser);
			    }
			}
		});
	}

	//�յ��ɻ������¼�
	@Override
	public void onStateChanged(PlaySprite mySprite) 
	{
		SceneManager.getInstance().showGameOverLayer(true);
		//��������
		SFXManager.getInstance().playSound(SFXManager.game_over);
	}
	
	//���ü����Ӧ���Ѷ�
	private void setGameGrade()
	{
		currentGrade=score/100;
		if(currentGrade==0)
		{
			currentGrade=1;
		}
		
		gradeText.setText("����:"+String.valueOf(currentGrade));
		
		if(0.5f-(0.1f*currentGrade)>0.1)
		{
			//�����ӵ�������ٶ�
			playerShootHandler.setTimerSeconds(0.5f-(0.1f*currentGrade));
		}
		
		if(2.0f-(0.1f*currentGrade)>0.5)
		{
			//���õл����ɵ��ٶ�
			createEnemyHandler.setTimerSeconds(2.0f-(0.1f*currentGrade));
		}
		
		//�л����е��ٶ�
		if(currentGrade*50<300)
		{
			enemySpeed=currentGrade*50;
		}
	}
	
	//����ը��
	private void launchBomb()
	{
		collideEntity.startBomb();
	}
	
	//���¿�ʼ
	public void restart()
	{
		//�ɻ���λ
		myPlane.setPosition(ResourceManager.getInstance().camera.getWidth()/2,ResourceManager.getInstance().camera.getHeight()-100);
		//������0
		score=0;
		scoreText.setText("����:"+String.valueOf(score));;
		//�ȼ���0
		currentGrade=1;
		gradeText.setText("����:"+String.valueOf(currentGrade));
		//ը������
		currentBomb=0;
		bombText.setText(String.valueOf(currentBomb));
		//ȥ�������ϵĵл�
		collideEntity.clearAll();
		//�����ٶ�
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
			
			//�����ը����Ť
			if (pButtonSprite == deafultBomb)
			{
				//��鵱ǰը��������
				if(currentBomb>0)
				{
					currentBomb-=1;
					bombText.setText(String.valueOf(currentBomb));
					//����ը��
					launchBomb();
					//��������
					SFXManager.getInstance().playSound(SFXManager.use_bomb);
				}
			}
		}
	};

	

}
