package com.zj.zjgameplane.scene;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zj.zjgameplane.GameActivity;


public class LoadingScene extends AbstractScene
{

	@Override
	public void populate() 
	{
		CameraScene cameraScene = new CameraScene(camera);
		Text text = new Text(GameActivity.CAMERA_WIDTH /2-50,300,res.font, "Мгдижа...", vbom);
		Sprite splashSprite = new Sprite(0,0,res.singleBack0,vbom);
		cameraScene.attachChild(splashSprite);
		cameraScene.attachChild(text);
		setChildScene(cameraScene);
	}

	
	
	@Override
	public void onPause() {
	}

	@Override
	public void onResume() {
	}

}
