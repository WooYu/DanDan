package com.dhxgzs.goodluck.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SharedPreferencesUtils {
	/**
	 * 移除SharedPreference
	 * 
	 * @param context
	 * @param key
	 */
	public static final void RemoveValue(Context context, String key) {
		Editor editor = getSharedPreference(context).edit();
		editor.remove(key);
		boolean result = editor.commit();
		if (!result) {
			Log.e("移除Shared", "save " + key + " failed");
		}
	}

	private static final SharedPreferences getSharedPreference(Context context) {
		return context.getSharedPreferences("MyInfo", Context.MODE_PRIVATE);
	}

	/**
	 * 获取SharedPreference �?
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static final String getValue(Context context, String key) {
		return getSharedPreference(context).getString(key, "");
	}

	public static final Boolean getBooleanValue(Context context, String key) {
		return getSharedPreference(context).getBoolean(key, false);
	}

	public static final void putBooleanValue(Context context, String key,
			boolean bl) {
		Editor edit = getSharedPreference(context).edit();
		edit.putBoolean(key, bl);
		edit.commit();
	}

	public static final int getIntValue(Context context, String key) {
		return getSharedPreference(context).getInt(key, 0);
	}

	public static final long getLongValue(Context context, String key,
			long default_data) {
		return getSharedPreference(context).getLong(key, default_data);
	}

	public static final boolean putLongValue(Context context, String key,
			Long value) {
		Editor editor = getSharedPreference(context).edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	public static final Boolean hasValue(Context context, String key) {
		return getSharedPreference(context).contains(key);
	}

	/**
	 * 设置SharedPreference �?
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final boolean putValue(Context context, String key,
			String value) {
		value = value == null ? "" : value;
		Editor editor = getSharedPreference(context).edit();
		editor.putString(key, value);
		boolean result = editor.commit();
		if (!result) {
			return false;
		}
		return true;
	}
	
}
