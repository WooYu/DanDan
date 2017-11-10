package com.dhxgzs.goodluck;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.ShangHuEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
 * 支付宝转账页面
 * 
 * @author Administrator
 *
 */
public class AlipayActivity extends Activity implements OnClickListener {

	private static final int SHANGHUXINXI = 0;
	private static final int APLIPAYTIJIAO = 1;
	/** 返回按钮 */
	private ImageView WeiXin_back;
	/** 收款账号 */
	private TextView shoukuanzhanghao;
	/** 账号复制按钮 */
	private ImageView fuzhi_zhanghao;
	/** 收款户名 */
	private TextView shoukuanhuming;
	/** 户名复制按钮 */
	private ImageView fuzhi_huming;
	/** 微信提交按钮 */
	private TextView WeiXin_tijiao;
	/** 微信户名 */
	private EditText WeiXin_huming;
	/** 微信账号 */
	private EditText WeiXin_zhanghao;
	/** 微信转账金额 */
	private EditText WeiXin_money;
	private Request<JSONObject> request;
	private RequestQueue AlipayQueue;
	private String account;
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wei_xin);

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
		AlipayQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());

		shanghuInfoRequest();

		WeiXin_back = (ImageView) findViewById(R.id.WeiXin_back);
		shoukuanzhanghao = (TextView) findViewById(R.id.shoukuanzhanghao);
		fuzhi_zhanghao = (ImageView) findViewById(R.id.fuzhi_zhanghao);
		shoukuanhuming = (TextView) findViewById(R.id.shoukuanhuming);
		fuzhi_huming = (ImageView) findViewById(R.id.fuzhi_huming);
		WeiXin_tijiao = (TextView) findViewById(R.id.WeiXin_tijiao);
		WeiXin_huming = (EditText) findViewById(R.id.WeiXin_huming);
		WeiXin_zhanghao = (EditText) findViewById(R.id.WeiXin_zhanghao);
		WeiXin_money = (EditText) findViewById(R.id.WeiXin_money);

	}

	private void shanghuInfoRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.MERCHANTS_URL, RequestMethod.POST);
		request.add("account", account);
		request.add("imei", imei);
		request.add("type", "支付宝");

		AlipayQueue.add(SHANGHUXINXI, request, responseListener);
	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case SHANGHUXINXI:

				try {
					System.out.println("支付宝商户信息" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						ShangHuEntity entity = new ShangHuEntity();

						entity = JSON.parseObject(biz_content, ShangHuEntity.class);

						shoukuanzhanghao.setText(entity.getAccount());
						shoukuanhuming.setText(entity.getName());
					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case APLIPAYTIJIAO:

				try {
					System.out.println("支付宝提交充值状态信息" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					
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
			System.out.println("请求失败" + exception);
			System.out.println("请求失败1" + url);

		}
	};
	private String AplipayHuMing;
	private String AplipayNum;
	private String AplipayRechargeMoney;

	private void setListener() {

		WeiXin_back.setOnClickListener(this);
		fuzhi_zhanghao.setOnClickListener(this);
		fuzhi_huming.setOnClickListener(this);
		WeiXin_tijiao.setOnClickListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.WeiXin_back:

			finish();

			break;
		case R.id.WeiXin_tijiao:
			AplipayHuMing = WeiXin_huming.getText().toString();
			AplipayNum = WeiXin_zhanghao.getText().toString();
			AplipayRechargeMoney = WeiXin_money.getText().toString();
			if (!AplipayHuMing.isEmpty() && !AplipayNum.isEmpty() && !AplipayRechargeMoney.isEmpty()) {

				aplipayRechargeRequest();

			} else {

				Toast.makeText(getApplicationContext(), "信息未填写完整", Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.fuzhi_zhanghao:// 复制收款账号

			if (MyUtil.getSDKVersionNumber() >= 11) {
				android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(shoukuanzhanghao.getText().toString());
			} else {
				// 得到剪贴板管理器
				android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setPrimaryClip(ClipData.newPlainText(null, shoukuanzhanghao.getText().toString()));
			}
			Toast.makeText(this, "复制到剪切板", Toast.LENGTH_SHORT).show();

			break;

		case R.id.fuzhi_huming:// 复制收款户名

			if (MyUtil.getSDKVersionNumber() >= 11) {
				android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(shoukuanhuming.getText().toString());
			} else {
				// 得到剪贴板管理器
				android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setPrimaryClip(ClipData.newPlainText(null, shoukuanhuming.getText().toString()));
			}
			Toast.makeText(this, "复制到剪切板", Toast.LENGTH_SHORT).show();

			break;

		default:
			break;
		}

	}

	private void aplipayRechargeRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.OFFLINERECHARGE_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("type", "支付宝");
		request.add("name", AplipayHuMing);
		request.add("number", AplipayNum);
		request.add("money", AplipayRechargeMoney);
		request.add("bankname", "");
		request.add("imei", imei);

		AlipayQueue.add(APLIPAYTIJIAO, request, responseListener);
	}

}
