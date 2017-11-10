package com.dhxgzs.goodluck.app;

import java.util.LinkedList;
import java.util.List;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.yolanda.nohttp.NoHttp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class XyApplication extends Application {
	private static XyApplication instance;
	 public static Context appContext;
	private List<Activity> mlist = new LinkedList<Activity>();

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		instance = this;
		NoHttp.initialize(instance);
		
		// 初始化环信
		EMOptions options = new EMOptions();
		EaseUI.getInstance().init(getApplicationContext(), options);
		//初始化XyAppHelper类，并调用其中的方法
		XyAppHelper.getInstance().init(this.getApplicationContext());
		appContext = getApplicationContext();
	}

	public synchronized static XyApplication getInstance() {
		if (null == instance) {
			instance = new XyApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		if (!mlist.contains(activity)) {
			mlist.add(activity);
		}
	}

	public void exit() {
		for (int i = 0; i < mlist.size(); i++) {
			if (!mlist.get(i).isFinishing()) {
				mlist.get(i).finish();
			}
		}
	}
}
