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
 * 登陆界面
 * 
 * @author Administrator
 *
 */
public class Login extends Activity implements OnClickListener {
	private static final int LOGIN = 0;
	/** 返回按钮 */
	private ImageView login_back;
	/** 账号 */
	private EditText login_account;
	/** 密码 */
	private EditText login_psd;
	/** 登录按钮 */
	private TextView login,login_wx;
	/** 忘记密码 */
	private TextView login_forget_psd;
	/** 注册 */
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
		// 返回按钮
		login_back = (ImageView) findViewById(R.id.login_back);
		// 登录账号
		login_account = (EditText) findViewById(R.id.login_account);
		// 登录密码
		login_psd = (EditText) findViewById(R.id.login_psd);
		// 登录
		login = (TextView) findViewById(R.id.login);
		// 忘记密码
		login_forget_psd = (TextView) findViewById(R.id.login_forget_psd);
		// 注册
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
			// 登录账号
			userName = login_account.getText().toString().trim();
			password = login_psd.getText().toString().trim();
			imei = MyUtil.getIMEI(getApplicationContext());
			boolean judge = isMobile(userName);
			boolean pppsd = isMobilePassword(password);
			if (judge == true && pppsd == true) {

				LoginRequest();

			} else {

				Toast.makeText(getApplicationContext(), "请检查账号密码是否正确", Toast.LENGTH_LONG).show();

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
				System.out.println("注册数据" + biz_content);
				if (state.equals("success")) {
					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					EMClient.getInstance().login(userName, "666666", new EMCallBack() {// 回调
						@Override
						public void onSuccess() {
							// EMClient.getInstance().groupManager().loadAllGroups();
							// EMClient.getInstance().chatManager().loadAllConversations();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub

									Toast.makeText(getApplicationContext(), "登录聊天服务器成功！", Toast.LENGTH_SHORT).show();
									Log.d("main", "登录聊天服务器成功！");
									SharedPreferencesUtils.putBooleanValue(getApplicationContext(), "isLogin",true);
									System.out.println("注册号码"+userName);
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
							Log.d("main", "登录聊天服务器失败！");
							System.out.println("登录聊天服务器失败"+code);
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
			  Toast.makeText(getApplicationContext(), "登录聊天服务器失败！code = " +what, Toast.LENGTH_SHORT).show();
              if (what == 200){
                  /**bug 如果判断返回用户已经登录 那么调用loginout接口  提示用户重新登录*/
                  Toast.makeText(getApplicationContext(), "正在清理缓存信息，请稍后重新登录..." , Toast.LENGTH_SHORT).show();
                 // logout();
                  finish();
              }

		}
	};
	

	/**
	 * 验证手机格式
	 */
	public static boolean isMobile(String phoneNum) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		// String num = "[1][358]\\d{9}";//
		// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		// if (TextUtils.isEmpty(phoneNum)) {
		// return false;
		// } else {
		// // matches():字符串是否在给定的正则表达式匹配
		return m.matches();
		// }
	}
 
	/**
	 * 验证密码格式
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
