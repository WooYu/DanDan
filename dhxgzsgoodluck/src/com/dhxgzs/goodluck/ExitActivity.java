package com.dhxgzs.goodluck;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyApplication;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.util.Utils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ExitActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
	}

	public void showDialog(Context context){
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		
		dialog.setTitle("退出").setMessage("您的账号已在其他手机登录,您被迫下线").setPositiveButton("是", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Utils.RemoveValue(context, MyConstants.Name);
				new Thread(new Runnable() {

					@Override
					public void run() {
						EMClient.getInstance().logout(true, new EMCallBack() {

							@Override
							public void onSuccess() {
								SharedPreferencesUtils.RemoveValue(getApplicationContext(), XyMyContent.LOGINSTATE);
								XyApplication.getInstance().exit();
								Utils.start_Activity(getParent(), Login.class);
								finish();
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										Toast.makeText(getApplicationContext(), "退出成功", Toast.LENGTH_SHORT).show();

									}
								}); 

							}

							@Override
							public void onProgress(int progress, String status) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onError(int code, String message) {
								// TODO Auto-generated method stub

							}
						});

					}
				}).start();
				
			}
		}).setNegativeButton("否", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		}).show();
		
	}
	@SuppressLint("InflateParams")
	public void setaaa(Context context) {

		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alertdialog_fangjianxianzhi_tishi, null);
		dialog.setView(layout);
		
		dialog.show();
		dialog.setCancelable(false);
		Window window = dialog.getWindow();
		window.setContentView(R.layout.alertdialog_fangjianxianzhi_tishi);
		Button queding = (Button) window.findViewById(R.id.Yqueding);
		TextView fangjianTishi = (TextView) window.findViewById(R.id.fangjianTishi);
		fangjianTishi.setText("您的账号已在其他手机登录,您被迫下线");

		// 点击确定
		queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						EMClient.getInstance().logout(true, new EMCallBack() {
							
							@Override
							public void onSuccess() {
								SharedPreferencesUtils.RemoveValue(getApplicationContext(), XyMyContent.LOGINSTATE);
								SharedPreferencesUtils.putBooleanValue(getApplicationContext(), "isLogin",false);
								XyApplication.getInstance().exit();
								Utils.start_Activity(ExitActivity.this, Login.class);
								finish();
//								Utils.start_Activity(getParent(), Login.class);
								dialog.cancel();
								
							}
							
							@Override
							public void onProgress(int arg0, String arg1) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onError(int arg0, String arg1) {
								// TODO Auto-generated method stub
								
							}
						});
						
					}
				}).start();
				

			}
		});
		// Button quxiao = (Button) window.findViewById(R.id.Yquxiao);
		// // 点击取消
		// quxiao.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// dialog.cancel();
		// }
		// });

	}
}
