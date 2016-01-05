package com.zj.zjgameplane;

import android.content.SharedPreferences;

public class GamePlaneConfig
{
	public static String maxscore="maxscore";
	
	
	private SharedPreferences sharedPreferences=null;//定义SharedPreferences对象
	private SharedPreferences.Editor editor=null;
	private static GamePlaneConfig synConfig=null;
	
	/**
	 * 获取配置文件操作对象
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
	 * 初始化配置文件
	 * @param tempSharedPreferences
	 */
	public void initSynConfig(SharedPreferences tempSharedPreferences)
	{
		sharedPreferences=tempSharedPreferences;
		editor=sharedPreferences.edit();
	}
	
	/**
	 * 获取配置文件中的值
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getConfigValue(String key,String defaultValue)
	{
		return sharedPreferences.getString(key,defaultValue);
	}
	
	/**
	 * 设置配置文件的值
	 * @param key
	 * @param value
	 */
	public void setConfigValue(String key,String value)
	{
		editor.putString(key,value);
		editor.commit();
	}
}
