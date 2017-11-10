package com.dhxgzs.goodluck.welcome;

import com.dhxgzs.goodluck.Login;
import com.dhxgzs.goodluck.MainActivity;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public class MainActivity1 extends Activity {

	private boolean first; // �ж��Ƿ��һ�δ����
	private View view;
	private Context context;
	private Animation animation;
	private NetManager netManager;
	private SharedPreferences shared;
	private static int TIME = 1000; // ������������ӳ�ʱ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.activity_main1, null);
		setContentView(view);
		context = this; // �õ�������
		shared = new SharedConfig(context).GetConfig(); // �õ������ļ�
		netManager = new NetManager(context); // �õ����������
	}

	@Override
	protected void onResume() {
		into();
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	// ����������ķ���
	private void into() {
		if (netManager.isOpenNetwork()) {
			// �������������ж��Ƿ��һ�ν��룬����ǵ�һ������뻶ӭ����
			first = shared.getBoolean("First", true);

			// ���ö���Ч����alpha����animĿ¼�µ�alpha.xml�ļ��ж��嶯��Ч��
			animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
			// ��view���ö���Ч��
			view.startAnimation(animation);
			animation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation arg0) {
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
				}

				// ����������������Ķ������ڶ���������ʱ����һ���̣߳�����߳��а�һ��Handler,��
				// �����Handler�е���goHome��������ͨ��postDelayed����ʹ��������ӳ�500����ִ�У��ﵽ
				// �ﵽ������ʾ��һ��500�����Ч��
				@Override
				public void onAnimationEnd(Animation arg0) {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent;

							// �����һ�Σ����������ҳWelcomeActivity
							if (SharedPreferencesUtils.getBooleanValue(getApplicationContext(), "isLogin") == true) {
								intent = new Intent(MainActivity1.this, MainActivity.class);
							} else {
								intent = new Intent(MainActivity1.this, Login.class);
							}
							startActivity(intent);
							// ����Activity���л�Ч��
							overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
							MainActivity1.this.finish();
						}
					}, TIME);
				}
			});
		} else {
			// ������粻���ã��򵯳��Ի��򣬶������������
			Builder builder = new Builder(context);
			builder.setTitle("û�п��õ�����");
			builder.setMessage("�Ƿ�������������?");
			builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = null;
					try {
						String sdkVersion = android.os.Build.VERSION.SDK;
						if (Integer.valueOf(sdkVersion) > 10) {
							intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						} else {
							intent = new Intent();
							ComponentName comp = new ComponentName("com.android.settings",
									"com.android.settings.WirelessSettings");
							intent.setComponent(comp);
							intent.setAction("android.intent.action.VIEW");
						}
						MainActivity1.this.startActivity(intent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			builder.setNegativeButton("ȡ��", new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					MainActivity1.this.finish();
				}
			});
			builder.show();
		}
	}
}
