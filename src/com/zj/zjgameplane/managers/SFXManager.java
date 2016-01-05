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
	
	//��������
	public static Music mMusic;
	
	//�ӵ�
	public static Sound sBullet;
	//�л���ը
	public static Sound sEnemySmall_down;
	//��ȡը��
	public static Sound get_bomb;
	//��ȡ˫���ӵ�
	public static Sound get_double_laser;
	
	//ʹ��ը��
	public static Sound use_bomb;
	//��Ϸ����
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
		//�ӵ�������
		sBullet = getSound("bullet.mp3");
		//�л���ը������
		sEnemySmall_down = getSound("enemySmall_down.mp3");
		//��ȡը��
		get_bomb= getSound("get_bomb.mp3");
		//��ȡ˫���ӵ�
		get_double_laser= getSound("get_double_laser.mp3");
		//ʹ��ը��
		use_bomb= getSound("use_bomb.mp3");
		//��Ϸ����
		game_over= getSound("game_over.mp3");
	}
	
	public void loadMusic()
	{
		MusicFactory.setAssetBasePath("mfx/");
		mMusic =getMusic("game_music.mp3");
	}
	
	//�õ���Ƶ
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
	
	//�õ�����
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
	
	/**��������(�ӵ�)*/
	public void playShoot(final float pRate, final float pVolume) 
	{
		playSound(sBullet,pRate,pVolume);
	}

	/**������������*/
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

	/**ͨ�÷�����������*/
	public void playSound(final Sound pSound)
	{
		if(SFXManager.isSoundMuted()) 
		{
			return;
		}
		
		pSound.play();
	}
	
	/**ͨ�÷�����������*/
	public void playMusic(final Music pMusic)
	{
		if(SFXManager.isMusicMuted()) 
		{
			return;
		}
		
		pMusic.play();
	}

}

