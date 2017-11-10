package com.dhxgzs.goodluck;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.MyUtil;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 注册页面
 * 
 * @author Administrator
 *
 */
public class Register extends Activity implements OnClickListener {
	private static final int GET_YANZHENGMA = 0;
	private static final int REGSITER = 1;
	/** 返回按钮 */
	private ImageView register_back;
	/** 电话号码 */
	private EditText register_account;
	/** 验证码输入框 */
	private EditText register_YanZhengCode;
	/** 获取验证码按钮 */
	private TextView register_getcode;
	/**邀请码*/
	private TextView register_yaoqingma;
	/** 输入密码 */
	private EditText register_psd;
	/** 注册按钮 */
	private TextView register;
	/** 用户手机号 */
	private String username;
	/** 用户密码 */
	private String pwd;
	/** 生成的验证码 */
	private String aaa;
	private Request<JSONObject> request;
	private RequestQueue registerQueue;
	private String imei;
	/** 计时器 */
	private TimeCount time;
	private String getYanzhengNum;
	private String myyaoqingma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		time = new TimeCount(60000, 1000);
		initView();
		setListener();
	}

	private void initView() {
		registerQueue = NoHttp.newRequestQueue();
		// 返回
		register_back = (ImageView) findViewById(R.id.register_back);
		// 输入电话号码
		register_account = (EditText) findViewById(R.id.register_account);
		// 输入验证码
		register_YanZhengCode = (EditText) findViewById(R.id.register_YanZhengCode);
		// 获取验证码
		register_getcode = (TextView) findViewById(R.id.register_getcode);
		// 输入密码
		register_psd = (EditText) findViewById(R.id.register_psd);
		// 注册按钮
		register = (TextView) findViewById(R.id.register);
		//邀请码
		register_yaoqingma=(TextView) findViewById(R.id.register_yaoqingma);

	}

	private void setListener() {

		register_back.setOnClickListener(this);
		register_getcode.setOnClickListener(this);
		register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_back:// 返回按钮

			finish();
			break;
		case R.id.register_getcode:// 获取验证码按钮
			// 得到手机号
			username = register_account.getText().toString();
			imei = MyUtil.getIMEI(getApplicationContext());
			boolean judge1 = isMobile(username);
			if (judge1 == true) {
				// 手机号格式输入正确请求验证码
				randomYanzhengma();
				System.out.println("aaa" + aaa);
				// 发送短信
				RequestYanzhengma();

			} else {
				Toast.makeText(getApplicationContext(), "请检查手机号格式是否正确", Toast.LENGTH_LONG).show();
			}

			break;

		case R.id.register:// 注册按钮
			System.out.println("小米测试");
			// 获取手机号
			username = register_account.getText().toString().trim();
			// 获取用户密码
			pwd = register_psd.getText().toString().trim();
			myyaoqingma=register_yaoqingma.getText().toString().trim();
			// 获取邀请码
			getYanzhengNum = register_YanZhengCode.getText().toString().trim();
			boolean judge = isMobile(username);
			boolean pppsd = isMobilePassword(pwd);
			if (judge == true && pppsd == true && getYanzhengNum.equals(aaa)) {

				registerRequest();

			} else if (judge == false) {

				Toast.makeText(getApplicationContext(), "请检查账号是否正确", Toast.LENGTH_LONG).show();

			} else if (pppsd == false) {
				
				Toast.makeText(getApplicationContext(), "请检查密码是否正确", Toast.LENGTH_LONG).show();
				
			} else if (!getYanzhengNum.equals(aaa)) {
				
				Toast.makeText(getApplicationContext(), "请检查验证码是否正确", Toast.LENGTH_LONG).show();
				
			}

			break;

		default:
			break;
		}

	}

	private void registerRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.REGISTER_URL, RequestMethod.POST);
		
		request.add("invitecode", myyaoqingma);
		request.add("account", username);
		request.add("password", pwd);
		request.add("imei", imei);
		registerQueue.add(REGSITER, request, responseListener);

		System.out.println("手机号" + username);
		System.out.println("密码" + pwd);
		System.out.println("imei号" + imei);

	}

	private void RequestYanzhengma() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.GET_YANZHENGMA_URL, RequestMethod.POST);

		request.add("account", username);
		request.add("type", "注册应用");
		request.add("code", aaa);
		request.add("imei", imei);

		System.out.println("手机号" + username);
		System.out.println("验证码" + aaa);
		System.out.println("手机imei号" + imei);

		registerQueue.add(GET_YANZHENGMA, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {
			switch (what) {
			case GET_YANZHENGMA:// 获取验证码返回监听
				System.out.println("获取验证码" + response);

				try {

					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						time.start();
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_LONG).show();
					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case REGSITER:
				System.out.println("注册" + response);

				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									EMClient.getInstance().createAccount(username, "666666");
									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											Toast.makeText(getApplicationContext(), "环信注册成功", Toast.LENGTH_SHORT)
													.show();
											finish();
										}
									});
								} catch (final HyphenateException e) {

									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											int errorCode = e.getErrorCode();
											if (errorCode == EMError.NETWORK_ERROR) {
												Toast.makeText(getApplicationContext(), errorCode + "网络异常",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_ALREADY_EXIST) {
												Toast.makeText(getApplicationContext(), errorCode + "该用户已经注册",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
												Toast.makeText(getApplicationContext(), errorCode + "注册失败，未经许可",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
												Toast.makeText(getApplicationContext(), errorCode + "非法用户名",
														Toast.LENGTH_SHORT).show();
											} else {
												Toast.makeText(getApplicationContext(), errorCode + "注册失败",
														Toast.LENGTH_SHORT).show();
											}

										}
									});
									e.printStackTrace();

								}

							}
						}).start();
						Toast.makeText(getApplicationContext(),biz_content, Toast.LENGTH_LONG).show();
					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(),biz_content, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
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
			System.out.println("失败" + exception);
			System.out.println("失败网址" + url);
			Toast.makeText(getApplicationContext(), "请求超时 请稍后再试", Toast.LENGTH_LONG).show();
		}
	};

	private void randomYanzhengma() {

		Random random = new Random();
		aaa = Integer.toString(random.nextInt(9999) + random.nextInt(999));

	}

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
	public static boolean isMobilePassword(String userPassword) {
		if (userPassword.isEmpty()) {
			return false;
		} else {
			Pattern psd = Pattern.compile("((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,20}$");
			Matcher mpsd = psd.matcher(userPassword);
			return mpsd.matches();
		}
	}

	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			register_getcode.setText("重新验证");
			register_getcode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			register_getcode.setClickable(false);
			register_getcode.setText(millisUntilFinished / 1000 + "秒");

		}
	}
}
