package com.zj.zjgameplane;

import android.content.SharedPreferences;

public class GamePlaneConfig
{
	public static String maxscore="maxscore";
	
	
	private SharedPreferences sharedPreferences=null;//����SharedPreferences����
	private SharedPreferences.Editor editor=null;
	private static GamePlaneConfig synConfig=null;
	
	/**
	 * ��ȡ�����ļ���������
	 * @return
	 */
	public static GamePlaneConfig getInstance()
	{
		if(synConfig==null)
		{
			synConfig=new GamePlaneConfig();
		}
		return synConfig;
	}
	
	/**
	 * ��ʼ�������ļ�
	 * @param tempSharedPreferences
	 */
	public void initSynConfig(SharedPreferences tempSharedPreferences)
	{
		sharedPreferences=tempSharedPreferences;
		editor=sharedPreferences.edit();
	}
	
	/**
	 * ��ȡ�����ļ��е�ֵ
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getConfigValue(String key,String defaultValue)
	{
		return sharedPreferences.getString(key,defaultValue);
	}
	
	/**
	 * ���������ļ���ֵ
	 * @param key
	 * @param value
	 */
	public void setConfigValue(String key,String value)
	{
		editor.putString(key,value);
		editor.commit();
	}
}
