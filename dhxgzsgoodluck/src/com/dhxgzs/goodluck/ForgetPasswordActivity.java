package com.dhxgzs.goodluck;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.Register.TimeCount;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.MyUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 忘记密码页
 * 
 * @author Administrator
 *
 */
public class ForgetPasswordActivity extends Activity implements OnClickListener {
	private static final int FORGETPASSWORD = 0;
	private static final int RESETPASSWORD = 1;
	/** 返回按钮 */
	private ImageView forgetPsd_back;
	/** 电话号码 */
	private EditText forgetPsd_account;
	/** 验证码输入框 */
	private EditText forgetPsd_YanZhengCode;
	/** 获取验证码按钮 */
	private TextView forgetPsd_getcode;
	/** 输入新密码 */
	private EditText forgetPsd_psd;
	/** 重置密码 */
	private TextView forgetPsd;
	/** 计时器 */
	private TimeCount time;
	/** 手机号 */
	private String account;
	/** 确定密码 */
	private String password;
	/** 验证码 */
	private String aaa;
	/** imei号 */
	private String imei;
	private Request<JSONObject> request;
	private RequestQueue ForgetPsdQueue;
	private String getYanzhengNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		time = new TimeCount(60000, 1000);
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
		ForgetPsdQueue = NoHttp.newRequestQueue();
		// 返回
		forgetPsd_back = (ImageView) findViewById(R.id.forgetPsd_back);
		// 输入电话号码
		forgetPsd_account = (EditText) findViewById(R.id.forgetPsd_account);
		// 输入验证码
		forgetPsd_YanZhengCode = (EditText) findViewById(R.id.forgetPsd_YanZhengCode);
		// 获取验证码
		forgetPsd_getcode = (TextView) findViewById(R.id.forgetPsd_getcode);
		// 输入新密码
		forgetPsd_psd = (EditText) findViewById(R.id.forgetPsd_psd);
		// 重置密码
		forgetPsd = (TextView) findViewById(R.id.forgetPsd);

	}

	private void setListener() {

		forgetPsd_back.setOnClickListener(this);
		forgetPsd_getcode.setOnClickListener(this);
		forgetPsd.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.forgetPsd_back:// 返回按钮

			finish();
			break;
		case R.id.forgetPsd_getcode:// 获取验证码按钮
			account = forgetPsd_account.getText().toString().trim();
			password = forgetPsd_psd.getText().toString().trim();
			imei = MyUtil.getIMEI(getApplicationContext());
			boolean judge1 = isMobile(account);
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

		case R.id.forgetPsd:// 重置密码

			imei = MyUtil.getIMEI(getApplicationContext());
			// 获取手机号
			account = forgetPsd_account.getText().toString().trim();
			// 获取用户密码
			password = forgetPsd_psd.getText().toString().trim();
			// 获取邀请码
			getYanzhengNum = forgetPsd_YanZhengCode.getText().toString().trim();
			boolean judge = isMobile(account);
			boolean pppsd = isMobilePassword(password);
			
			
			if (judge == true && pppsd == true && getYanzhengNum.equals(aaa)) {

				// 重置密码
				resetRequest();

			} else {

				Toast.makeText(getApplicationContext(), "请检查账号密码是否正确", Toast.LENGTH_LONG).show();

			}

			break;

		default:
			break;
		}

	}

	/** 重置密码 */
	private void resetRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.FORGETPASSWORD_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("password", password);
		request.add("imei", imei);
		request.add("code", getYanzhengNum);

		System.out.println("1手机号" + account);
		System.out.println("1验证码" + getYanzhengNum);
		System.out.println("1手机imei号" + imei);
		System.out.println("1password" + password);
		ForgetPsdQueue.add(RESETPASSWORD, request, responseListener);

	}

	/** 请求验证码 */
	private void RequestYanzhengma() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.GET_YANZHENGMA_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("type", "找回密码");
		request.add("code", aaa);
		request.add("imei", imei);

		System.out.println("手机号" + account);
		System.out.println("验证码" + aaa);
		System.out.println("手机imei号" + imei);

		ForgetPsdQueue.add(FORGETPASSWORD, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case FORGETPASSWORD://发送验证码
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						//发送成功开始计时
						time.start();
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case RESETPASSWORD://重置密码

				
				try {
					System.out.println("重置密码"+response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                        finish();
					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

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
			// TODO Auto-generated method stub

		}
	};
	/**生成验证码发送给后台*/
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
			forgetPsd_getcode.setText("重新验证");
			forgetPsd_getcode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			forgetPsd_getcode.setClickable(false);
			forgetPsd_getcode.setText(millisUntilFinished / 1000 + "秒");

		}
	}
}
