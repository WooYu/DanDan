package com.dhxgzs.goodluck.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.BankCardInfoEntity;
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
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的银行卡页面
 * 
 * @author Administrator
 *
 */
public class MyBankCardActivity extends Activity implements OnClickListener {

	private static final int MYCARDINFO = 0;
	/** 返回按钮 */
	private ImageView myBankCard_back;
	/** 手机号 */
	private TextView myBankCard_PhoneNum;
	/** 开户姓名 */
	private TextView myBankCard__userName;
	/** 银行名称 */
	private TextView myBankCard_Bankname;
	/** 卡号 */
	private TextView myBankCard_CardNum;
	/** 开户地址 */
	private TextView myBankCard_Address;
	/** 修改 */
	private TextView myBankCard_Xiugai;

	private Request<JSONObject> request;
	private RequestQueue MyCardBankQueue;
	private String account;
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_bank_card);

		setInitView();
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

	private void setInitView() {
		MyCardBankQueue = NoHttp.newRequestQueue();

		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		MyBankCardInfoRequest();
		// 返回按钮
		myBankCard_back = (ImageView) findViewById(R.id.myBankCard_back);
		// 手机号
		myBankCard_PhoneNum = (TextView) findViewById(R.id.myBankCard_PhoneNum);
		// 开户姓名
		myBankCard__userName = (TextView) findViewById(R.id.myBankCard__userName);
		// 银行名称
		myBankCard_Bankname = (TextView) findViewById(R.id.myBankCard_Bankname);
		// 卡号
		myBankCard_CardNum = (TextView) findViewById(R.id.myBankCard_CardNum);
		// 开户地址
		myBankCard_Address = (TextView) findViewById(R.id.myBankCard_Address);
		// 修改按钮
		myBankCard_Xiugai = (TextView) findViewById(R.id.myBankCard_Xiugai);

	}

	private void MyBankCardInfoRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.MYBANKCARD_INFO_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		MyCardBankQueue.add(MYCARDINFO, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			try {
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");
				if (state.equals("success")) {

					BankCardInfoEntity entity = new BankCardInfoEntity();
					entity = JSON.parseObject(biz_content, BankCardInfoEntity.class);

					myBankCard_PhoneNum.setText(entity.getAccount());
					myBankCard__userName.setText(entity.getUsername());
					myBankCard_Bankname.setText(entity.getCard());
					myBankCard_CardNum.setText(entity.getCardnum());
					myBankCard_Address.setText(entity.getCardaddress());

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

	private void setListener() {

		myBankCard_back.setOnClickListener(this);
		myBankCard_PhoneNum.setOnClickListener(this);
		myBankCard__userName.setOnClickListener(this);
		myBankCard_Bankname.setOnClickListener(this);
		myBankCard_CardNum.setOnClickListener(this);
		myBankCard_Address.setOnClickListener(this);
		myBankCard_Xiugai.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.myBankCard_back:

			finish();
			break;
		case R.id.myBankCard_Xiugai:

			Intent intent = new Intent(MyBankCardActivity.this, Bound_BankCardActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}

	}

}
