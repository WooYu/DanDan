package com.dhxgzs.goodluck;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
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
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 微信转账页面
 * 
 * @author Administrator
 *
 */
public class WeiXinActivity extends Activity implements OnClickListener {

	private static final int SHANGHUXINXI = 0;
	private static final int WEIXINCHONGZHI = 1;
	private ImageView Alipay_back;
	/** 二维码图片 */
	private ImageView ErWeima_img;
	/** 微信昵称 */
	private EditText weiXin_NickName;
	/** 微信账号 */
	private EditText weiXin_num;
	/** 微信转账金额 */
	private EditText weiXin_ZhuanzhangMoney;
	/** 微信提交 */
	private TextView weiXin_tijiao;
	private Request<JSONObject> request;
	private RequestQueue weiXinQueue;
	private String account;
	private String imei;
	private String name;
	private String number;
	private String money;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alipay);

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
		weiXinQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		/** 商户信息 */
		shanghuInfoRequest();
		Alipay_back = (ImageView) findViewById(R.id.Alipay_back);
		ErWeima_img = (ImageView) findViewById(R.id.ErWeima_img);
		weiXin_NickName = (EditText) findViewById(R.id.weiXin_NickName);
		weiXin_num = (EditText) findViewById(R.id.weiXin_num);
		weiXin_ZhuanzhangMoney = (EditText) findViewById(R.id.weiXin_ZhuanzhangMoney);
		weiXin_tijiao = (TextView) findViewById(R.id.weiXin_tijiao);
	}

	private void setListener() {

		Alipay_back.setOnClickListener(this);
		weiXin_tijiao.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.Alipay_back:

			finish();

			break;
		case R.id.weiXin_tijiao:
			name=weiXin_NickName.getText().toString();
			number=weiXin_num.getText().toString();
			money=weiXin_ZhuanzhangMoney.getText().toString();
			if(!name.isEmpty()&&!number.isEmpty()&&!money.isEmpty()){
				
				weiXinRechargeRequest();
				
			}else {
				Toast.makeText(getApplicationContext(), "信息填写不完整", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 微信充值请求
	 */
	private void weiXinRechargeRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.OFFLINERECHARGE_URL, RequestMethod.POST);
		
		request.add("account", account);
		request.add("type", "微信");
		request.add("name", name);
		request.add("number", number);
		request.add("money", money);
		request.add("bankname", "");
		request.add("imei", imei);
		
		weiXinQueue.add(WEIXINCHONGZHI, request, responseListener);
	}
	/**
	 * 商户信息请求
	 */
	private void shanghuInfoRequest() {
		
		request = NoHttp.createJsonObjectRequest(XyMyContent.MERCHANTS_URL, RequestMethod.POST);
		request.add("account", account);
		request.add("imei", imei);
		request.add("type", "微信");
        
		weiXinQueue.add(SHANGHUXINXI, request, responseListener);
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
						Glide.with(getApplicationContext()).load(entity.getAccount()).asBitmap()
								.error(R.drawable.ic_launcher).into(ErWeima_img);

					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case WEIXINCHONGZHI:
				
				try {
					System.out.println("微信提交充值状态信息" + response);
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
			// TODO Auto-generated method stub

		}
	};

}
