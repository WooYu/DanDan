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
 * ����ҳ��
 * 
 * @author Administrator
 *
 */
public class SettingActivity extends Activity implements OnClickListener {

	private static final int PANDUAN_TIXIAN_MIMA = 0;
	/** ���ذ�ť */
	private ImageView setting_back;
	/** �����п� */
	private LinearLayout setting_bundBank;
	/** �޸����� */
	private LinearLayout setting_xiugaiPassword;
	/** �������� */
	private LinearLayout setting_tixianPassword;
	/** ���ֻ� */
	private LinearLayout setting_mobileBund;
	/** ������� */
	private LinearLayout setting_cleanCache;
	/** �˳���¼ */
	private LinearLayout setting_exit;
	/** �����С */
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

		 // ���SDK�İ汾��4.4֮�ϣ���ôӦ�ó���ʽ״̬��
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
    // ���ó���״̬��
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
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());
		// ���ذ�ť
		setting_back = (ImageView) findViewById(R.id.setting_back);
		// �����п�
		setting_bundBank = (LinearLayout) findViewById(R.id.setting_bundBank);
		// �޸�����
		setting_xiugaiPassword = (LinearLayout) findViewById(R.id.setting_xiugaiPassword);
		// ��������
		setting_tixianPassword = (LinearLayout) findViewById(R.id.setting_tixianPassword);
		// ���ֻ�
		setting_mobileBund = (LinearLayout) findViewById(R.id.setting_mobileBund);
		// �������
		setting_cleanCache = (LinearLayout) findViewById(R.id.setting_cleanCache);
		// �˳���¼
		setting_exit = (LinearLayout) findViewById(R.id.setting_exit);
		// �����С
		clearCache = (TextView) findViewById(R.id.clearCache);
		// ����app�����С
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
		case R.id.setting_back:// ����

			Intent intenta = new Intent(SettingActivity.this, MainActivity.class);
			startActivity(intenta);
			finish();
			break;
		case R.id.setting_bundBank:// �����п�

			Intent intent = new Intent(SettingActivity.this, Bound_BankCardActivity.class);
			startActivity(intent);
			break;
		case R.id.setting_xiugaiPassword:// �޸�����

			Intent intent1 = new Intent(SettingActivity.this, ForgetPasswordActivity.class);
			startActivity(intent1);

			break;
		case R.id.setting_tixianPassword:// ��������

			panDuanTiXianPsdRequest();

			break;
		case R.id.setting_mobileBund:// ���ֻ�

			Intent intent2 = new Intent(SettingActivity.this, BoundMobileActivity.class);
			startActivity(intent2);

			break;
		case R.id.setting_cleanCache:// ������

			DataClearManager.clearAllCache(this);
			try {
				clearCache.setText(DataClearManager.getTotalCacheSize(this));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.setting_exit:// �˳���¼

			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("�˳�").setMessage("�Ƿ��˳���ǰ�˺ţ�").setPositiveButton("��", new DialogInterface.OnClickListener() {

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
											Toast.makeText(getApplicationContext(), "�˳��ɹ�", Toast.LENGTH_SHORT).show();

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
			}).setNegativeButton("��", new DialogInterface.OnClickListener() {

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
				System.out.println("�ж����������뻹���޸������������" + response);
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");

				if (state.equals("success")) {
					if (biz_content.equals("δ������������")) {
						Intent intent3 = new Intent(SettingActivity.this, TiXianPassWord.class);
						startActivity(intent3);
					} else if (biz_content.equals("��������������")) {

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
