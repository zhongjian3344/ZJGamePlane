package com.zj.zjgameplane;


import java.util.Locale;

import com.zj.zjadplatform.ADVType;
import com.zj.zjadplatform.ZJAdvManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AboutActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		ViewGroup container = (ViewGroup) this.findViewById(R.id.container);
		
		if(getResources().getString(R.string.app_area).endsWith("cn"))
		{
			ZJAdvManager.setAdvPlatform(ADVType.gdt);
			ZJAdvManager.showBanner(this, container);
		}
		else
		{
			ZJAdvManager.setAdvPlatform(ADVType.inmobi);
			ZJAdvManager.showBanner(this, container);
		}
	}

	public void eventBtnBack(View view)
	{
		this.finish();
	}
	
}
