package com.zj.zjgameplane.managers;



import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.util.debug.Debug;

public class SFXManager 
{
	private static final SFXManager INSTANCE = new SFXManager();
	public boolean mSoundsMuted;
	public boolean mMusicMuted;
	
	//背景音乐
	public static Music mMusic;
	
	//子弹
	public static Sound sBullet;
	//敌机爆炸
	public static Sound sEnemySmall_down;
	//获取炸弹
	public static Sound get_bomb;
	//获取双发子弹
	public static Sound get_double_laser;
	
	//使用炸弹
	public static Sound use_bomb;
	//游戏结束
	public static Sound game_over;
	
	
	public static SFXManager getInstance()
	{
		return INSTANCE;
	}
	
	public static boolean isSoundMuted() 
	{
		return getInstance().mSoundsMuted;
	}
	
	public static boolean isMusicMuted()
	{
		return getInstance().mMusicMuted;
	}
	
	public void loadSounds()
	{
		SoundFactory.setAssetBasePath("mfx/");
		//子弹的声音
		sBullet = getSound("bullet.mp3");
		//敌机爆炸的声音
		sEnemySmall_down = getSound("enemySmall_down.mp3");
		//获取炸弹
		get_bomb= getSound("get_bomb.mp3");
		//获取双发子弹
		get_double_laser= getSound("get_double_laser.mp3");
		//使用炸弹
		use_bomb= getSound("use_bomb.mp3");
		//游戏结束
		game_over= getSound("game_over.mp3");
	}
	
	public void loadMusic()
	{
		MusicFactory.setAssetBasePath("mfx/");
		mMusic =getMusic("game_music.mp3");
	}
	
	//得到音频
	private Sound getSound(String tempValue)
	{
		try 
		{
			return SoundFactory.createSoundFromAsset(ResourceManager.getInstance().activity.getSoundManager(),ResourceManager.getInstance().activity,tempValue);
		} 
		catch (final IOException e) 
		{ 
			Debug.e(e); 
		}
		return null;
	}
	
	//得到音乐
	private Music getMusic(String tempValue)
	{
		try 
		{
			return MusicFactory.createMusicFromAsset(ResourceManager.getInstance().activity.getMusicManager(),ResourceManager.getInstance().activity,tempValue);
		} 
		catch (final IOException e) 
		{ 
			Debug.e(e); 
		}
		return null;
	}
	
	/**播放声音(子弹)*/
	public void playShoot(final float pRate, final float pVolume) 
	{
		playSound(sBullet,pRate,pVolume);
	}

	/**播放声音方法*/
	private static void playSound(final Sound pSound, final float pRate, final float pVolume) 
	{
		if(SFXManager.isSoundMuted()) 
		{
			return;
		}
		
		pSound.setRate(pRate);
		pSound.setVolume(pVolume);
		pSound.play();
	}

	/**通用方法播放声音*/
	public void playSound(final Sound pSound)
	{
		if(SFXManager.isSoundMuted()) 
		{
			return;
		}
		
		pSound.play();
	}
	
	/**通用方法播放音乐*/
	public void playMusic(final Music pMusic)
	{
		if(SFXManager.isMusicMuted()) 
		{
			return;
		}
		
		pMusic.play();
	}

}

