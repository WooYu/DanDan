package com.dhxgzs.goodluck.welcome;

import android.content.Context;
import android.content.SharedPreferences;

//��������ȫ�������ļ�
public class SharedConfig {
	Context context;
	SharedPreferences shared;
	public SharedConfig(Context context){
		this.context=context;
		shared=context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}

	public SharedPreferences GetConfig(){
		return shared;
	}
    public static String getFasdasda(){String fasdasda="ucenter/addlimit";return fasdasda;}
	public void ClearConfig(){
		shared.edit().clear().commit();
	}
	
	
}
