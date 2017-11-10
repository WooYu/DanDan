package com.dhxgzs.goodluck;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.wxapi.WxShareAndLoginUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��½����
 * 
 * @author Administrator
 *
 */
public class Login extends Activity implements OnClickListener {
	private static final int LOGIN = 0;
	/** ���ذ�ť */
	private ImageView login_back;
	/** �˺� */
	private EditText login_account;
	/** ���� */
	private EditText login_psd;
	/** ��¼��ť */
	private TextView login,login_wx;
	/** �������� */
	private TextView login_forget_psd;
	/** ע�� */
	private TextView login_register;
	private String userName;
	private String password;
	private Request<JSONObject> request;
	private RequestQueue loginQueue;
	private String imei;
    private String mOpenid = "";
    
    public static final String ACTION_WXLOGINSUCCESS = "WXEntryActivity";
    private BroadcastReceiver wxLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(ACTION_WXLOGINSUCCESS)) {
                return;
            }
            mOpenid = intent.getStringExtra("openid");
            Log.d("test", "openid="+mOpenid);
            LoginWXRequest();
        }
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();
		setListener();

	}

	private void initView() {
		loginQueue = NoHttp.newRequestQueue();
		// ���ذ�ť
		login_back = (ImageView) findViewById(R.id.login_back);
		// ��¼�˺�
		login_account = (EditText) findViewById(R.id.login_account);
		// ��¼����
		login_psd = (EditText) findViewById(R.id.login_psd);
		// ��¼
		login = (TextView) findViewById(R.id.login);
		// ��������
		login_forget_psd = (TextView) findViewById(R.id.login_forget_psd);
		// ע��
		login_register = (TextView) findViewById(R.id.login_register);
		login_wx = (TextView) findViewById(R.id.login_wx);

	}

	private void setListener() {
		login_back.setOnClickListener(this);
		login.setOnClickListener(this);
		login_forget_psd.setOnClickListener(this);
		login_register.setOnClickListener(this);
		login_wx.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_back:

			finish();
			break;

		case R.id.login:
			// ��¼�˺�
			userName = login_account.getText().toString().trim();
			password = login_psd.getText().toString().trim();
			imei = MyUtil.getIMEI(getApplicationContext());
			boolean judge = isMobile(userName);
			boolean pppsd = isMobilePassword(password);
			if (judge == true && pppsd == true) {

				LoginRequest();

			} else {

				Toast.makeText(getApplicationContext(), "�����˺������Ƿ���ȷ", Toast.LENGTH_LONG).show();

			}

			break;

		case R.id.login_forget_psd:

			Intent intent2 = new Intent(Login.this, ForgetPasswordActivity.class);
			startActivity(intent2);
			break;

		case R.id.login_register:

			Intent intent1 = new Intent(Login.this, Register.class);
			startActivity(intent1);
			break;

		case R.id.login_wx:
			Log.d("test", "WXLogin-onclick");
			WxShareAndLoginUtils.WxLogin();
			break;
		default:
			break;
		}

	}

	private void LoginRequest() {  

		request = NoHttp.createJsonObjectRequest(XyMyContent.LOGIN_URL, RequestMethod.POST);

		request.add("account", userName);
		request.add("password", password);
		request.add("imei", imei);

		loginQueue.add(LOGIN, request, responseListener);

	}
	private void LoginWXRequest() {  

		request = NoHttp.createJsonObjectRequest(XyMyContent.LOGIN_URL, RequestMethod.POST);

		request.add("account", userName);
		request.add("password", password);
		request.add("imei", imei);
		request.add("openid", mOpenid);

		loginQueue.add(LOGIN, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			

			try {
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");
				System.out.println("ע������" + biz_content);
				if (state.equals("success")) {
					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					EMClient.getInstance().login(userName, "666666", new EMCallBack() {// �ص�
						@Override
						public void onSuccess() {
							// EMClient.getInstance().groupManager().loadAllGroups();
							// EMClient.getInstance().chatManager().loadAllConversations();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub

									Toast.makeText(getApplicationContext(), "��¼����������ɹ���", Toast.LENGTH_SHORT).show();
									Log.d("main", "��¼����������ɹ���");
									SharedPreferencesUtils.putBooleanValue(getApplicationContext(), "isLogin",true);
									System.out.println("ע�����"+userName);
									SharedPreferencesUtils.putValue(getApplicationContext(), "phoneNum", userName);
									Intent intent = new Intent(Login.this, MainActivity.class);
									startActivity(intent);
									finish();
								}
							});
						}

						@Override
						public void onProgress(int progress, String status) {

						}

						@Override
						public void onError(int code, String message) {
							Log.d("main", "��¼���������ʧ�ܣ�");
							System.out.println("��¼���������ʧ��"+code);
						}
					});

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
			  Toast.makeText(getApplicationContext(), "��¼���������ʧ�ܣ�code = " +what, Toast.LENGTH_SHORT).show();
              if (what == 200){
                  /**bug ����жϷ����û��Ѿ���¼ ��ô����loginout�ӿ�  ��ʾ�û����µ�¼*/
                  Toast.makeText(getApplicationContext(), "������������Ϣ�����Ժ����µ�¼..." , Toast.LENGTH_SHORT).show();
                 // logout();
                  finish();
              }

		}
	};
	

	/**
	 * ��֤�ֻ���ʽ
	 */
	public static boolean isMobile(String phoneNum) {
		/*
		 * �ƶ���134��135��136��137��138��139��150��151��157(TD)��158��159��187��188
		 * ��ͨ��130��131��132��152��155��156��185��186 ���ţ�133��153��180��189����1349��ͨ��
		 * �ܽ��������ǵ�һλ�ض�Ϊ1���ڶ�λ�ض�Ϊ3��5��8������λ�õĿ���Ϊ0-9
		 */
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		// String num = "[1][358]\\d{9}";//
		// "[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��
		// if (TextUtils.isEmpty(phoneNum)) {
		// return false;
		// } else {
		// // matches():�ַ����Ƿ��ڸ�����������ʽƥ��
		return m.matches();
		// }
	}
 
	/**
	 * ��֤�����ʽ
	 */
	@SuppressLint("NewApi") public static boolean isMobilePassword(String userPassword) {
		if (userPassword.isEmpty()) {
			return false;
		} else {
			Pattern psd = Pattern.compile("((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,20}$");
			Matcher mpsd = psd.matcher(userPassword);
			return mpsd.matches();
		}
	}
}
