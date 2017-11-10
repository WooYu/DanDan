package com.dhxgzs.goodluck.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.BoundMobileActivity;
import com.dhxgzs.goodluck.ForgetPasswordActivity;
import com.dhxgzs.goodluck.Login;
import com.dhxgzs.goodluck.MainActivity;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyApplication;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.DataClearManager;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.util.Utils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 设置页面
 * 
 * @author Administrator
 *
 */
public class SettingActivity extends Activity implements OnClickListener {

	private static final int PANDUAN_TIXIAN_MIMA = 0;
	/** 返回按钮 */
	private ImageView setting_back;
	/** 绑定银行卡 */
	private LinearLayout setting_bundBank;
	/** 修改密码 */
	private LinearLayout setting_xiugaiPassword;
	/** 提现密码 */
	private LinearLayout setting_tixianPassword;
	/** 绑定手机 */
	private LinearLayout setting_mobileBund;
	/** 清楚缓存 */
	private LinearLayout setting_cleanCache;
	/** 退出登录 */
	private LinearLayout setting_exit;
	/** 缓存大小 */
	private TextView clearCache;
	private Request<JSONObject> request;
	private RequestQueue setQueue;
	private String account;
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initView();
		setListener();

		 // 如果SDK的版本在4.4之上，那么应用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.statelan_bg));
            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//            rlFramePrimaryMain.setPadding(0, config.getPixelInsetTop(false), 0, config.getPixelInsetBottom());
        }


}
    // 设置沉浸状态栏
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

	private void initView() {
		setQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		// 返回按钮
		setting_back = (ImageView) findViewById(R.id.setting_back);
		// 绑定银行卡
		setting_bundBank = (LinearLayout) findViewById(R.id.setting_bundBank);
		// 修改密码
		setting_xiugaiPassword = (LinearLayout) findViewById(R.id.setting_xiugaiPassword);
		// 提现密码
		setting_tixianPassword = (LinearLayout) findViewById(R.id.setting_tixianPassword);
		// 绑定手机
		setting_mobileBund = (LinearLayout) findViewById(R.id.setting_mobileBund);
		// 清楚缓存
		setting_cleanCache = (LinearLayout) findViewById(R.id.setting_cleanCache);
		// 退出登录
		setting_exit = (LinearLayout) findViewById(R.id.setting_exit);
		// 缓存大小
		clearCache = (TextView) findViewById(R.id.clearCache);
		// 计算app缓存大小
		try {
			clearCache.setText(DataClearManager.getTotalCacheSize(this));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setListener() {

		setting_back.setOnClickListener(this);
		setting_bundBank.setOnClickListener(this);
		setting_xiugaiPassword.setOnClickListener(this);
		setting_tixianPassword.setOnClickListener(this);
		setting_mobileBund.setOnClickListener(this);
		setting_cleanCache.setOnClickListener(this);
		setting_exit.setOnClickListener(this);

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intenta = new Intent(SettingActivity.this, MainActivity.class);
		startActivity(intenta);
		finish();
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.setting_back:// 返回

			Intent intenta = new Intent(SettingActivity.this, MainActivity.class);
			startActivity(intenta);
			finish();
			break;
		case R.id.setting_bundBank:// 绑定银行卡

			Intent intent = new Intent(SettingActivity.this, Bound_BankCardActivity.class);
			startActivity(intent);
			break;
		case R.id.setting_xiugaiPassword:// 修改密码

			Intent intent1 = new Intent(SettingActivity.this, ForgetPasswordActivity.class);
			startActivity(intent1);

			break;
		case R.id.setting_tixianPassword:// 提现密码

			panDuanTiXianPsdRequest();

			break;
		case R.id.setting_mobileBund:// 绑定手机

			Intent intent2 = new Intent(SettingActivity.this, BoundMobileActivity.class);
			startActivity(intent2);

			break;
		case R.id.setting_cleanCache:// 清理缓存

			DataClearManager.clearAllCache(this);
			try {
				clearCache.setText(DataClearManager.getTotalCacheSize(this));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.setting_exit:// 退出登录

			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("退出").setMessage("是否退出当前账号？").setPositiveButton("是", new DialogInterface.OnClickListener() {

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
									Utils.start_Activity(SettingActivity.this, Login.class);
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

			break;
		default:
			break;
		}

	}

	private void panDuanTiXianPsdRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.PANDUANTIXIANMIMA_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		setQueue.add(PANDUAN_TIXIAN_MIMA, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			try {
				System.out.println("判断是提现密码还是修改提现密码界面" + response);
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");

				if (state.equals("success")) {
					if (biz_content.equals("未设置提现密码")) {
						Intent intent3 = new Intent(SettingActivity.this, TiXianPassWord.class);
						startActivity(intent3);
					} else if (biz_content.equals("已设置提现密码")) {

						Intent intent = new Intent(SettingActivity.this, XiuGaiTiXianPsdActivity.class);
						startActivity(intent);
					}
//					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

				} else if (state.equals("error")) {

					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void onStart(int what) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish(int what) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFailed(int what, String url, Object tag, Exception exception, int responseCode,
				long networkMillis) {
			// TODO Auto-generated method stub

		}
	};

}
