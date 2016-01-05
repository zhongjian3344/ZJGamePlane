package com.zj.zjgameplane;

import java.util.Locale;

import com.zj.zjadplatform.ADVType;
import com.zj.zjadplatform.ZJAdvManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends Activity
{
	
	//ʱ��
	Handler handler=new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ViewGroup container = (ViewGroup) this.findViewById(R.id.splash_container);
		
		if(getResources().getString(R.string.app_area).endsWith("cn"))
		{
			ZJAdvManager.setAdvPlatform(ADVType.gdt);
			ZJAdvManager.showSplash(this, container);
		}
		else
		{
			ZJAdvManager.setAdvPlatform(ADVType.inmobi);
			ZJAdvManager.showInterst(this, container);
			handler.postDelayed(runnable,3000);
		}
		
		initConfig();
		
		ImageView rocketImage = (ImageView) findViewById(R.id.splash_anim);
		AnimationDrawable rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
		rocketAnimation.start();
		
	}

	//��ʼ�������ļ�
	private void initConfig()
	{
		//��ʼ�������ļ�
        int MODE=Context.MODE_WORLD_READABLE+Context.MODE_WORLD_WRITEABLE;
    	final String PREFERENCE_NAME="gameplaneconfig";
    	GamePlaneConfig.getInstance().initSynConfig(getSharedPreferences(PREFERENCE_NAME,MODE));
	}
	
	//����ʱ�� ��ʱ��������
	protected Runnable runnable=new Runnable()
	{
	    public void run() 
	    {
	    	SplashActivity.this.finish();
	    }
	};
}
