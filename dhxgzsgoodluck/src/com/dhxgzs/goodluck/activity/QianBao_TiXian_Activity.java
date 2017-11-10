package com.dhxgzs.goodluck.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.UserInfoEntity;
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
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
 * 提现页面
 * 
 * @author Administrator
 *
 */
public class QianBao_TiXian_Activity extends Activity implements OnClickListener {

	private static final int TIXIAN = 0;
	private static final int YUEEREQUEST = 1;
	/** 返回按钮 */
	private ImageView tixian_back;
	/** 账号 */
	private TextView tixian_zhanghao;
	/** 余额 */
	private TextView tixian_yu_e;
	/** 提现金额 */
	private EditText tixian_money;
	/** 提现密码 */
	private EditText tixian_psdInput;
	/** 提交按钮 */
	private TextView tixian_tijiao;
	private String yuanbaoNum;
	private String account;
	private String imei;
	private String cardNum;
	private Request<JSONObject> request;
	private RequestQueue tixianQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qian_bao__ti_xian_);
//		Intent intent = getIntent();
//		yuanbaoNum = intent.getStringExtra("YUANBAO");
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
		tixianQueue = NoHttp.newRequestQueue();
		// 从绑定银行卡页面获取卡号
		cardNum = SharedPreferencesUtils.getValue(getApplicationContext(), "BANK_CARD_NUM");
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		yueRequest();
		// 返回按钮
		tixian_back = (ImageView) findViewById(R.id.tixian_back);
		// 账号
		tixian_zhanghao = (TextView) findViewById(R.id.tixian_zhanghao);
		// 余额
		tixian_yu_e = (TextView) findViewById(R.id.tixian_yu_e);
		// 提现金额
		tixian_money = (EditText) findViewById(R.id.tixian_money);
		// 提现密码
		tixian_psdInput = (EditText) findViewById(R.id.tixian_psdInput);
		// 提交按钮
		tixian_tijiao = (TextView) findViewById(R.id.tixian_tijiao);
		
		tixian_zhanghao.setText(cardNum);
	}

	private void yueRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.ACCOUNTMONEY_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		tixianQueue.add(YUEEREQUEST, request, responseListener);

	}

	private void tiXianRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.TIXIAN_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("password", tixian_psdInput.getText().toString().trim());
		request.add("money", tixian_money.getText().toString().trim());
		request.add("imei", imei);

		tixianQueue.add(TIXIAN, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case YUEEREQUEST:
				try {
					System.out.println("剩余元宝数数据"+response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if(state.equals("success")){
						
						
						UserInfoEntity entity = new UserInfoEntity();
						
						entity=JSON.parseObject(biz_content, UserInfoEntity.class);
						tixian_yu_e.setText(entity.getMoney()+"元宝");
						
					}else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
						
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case TIXIAN:
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
						finish();
					
					} else if (state.equals("error")) {
						
						//Log.d("test","错误码：" +biz_content);
						/*2017/08/21
						 * by JiuYue
						 * 判断后台返回错误码
						if 错误msg= 提现密码错误  
						跳转 设置提现密码界面*/
						if(biz_content.indexOf("密码错误")!= -1){
							
							Intent intent = new Intent(QianBao_TiXian_Activity.this, TiXianPassWord.class);
							startActivity(intent);
							finish();
						} 
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

	private void setListener() {

		tixian_back.setOnClickListener(this);
		tixian_tijiao.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tixian_back:

			finish();

			break;
		case R.id.tixian_tijiao:
			if (!tixian_psdInput.getText().toString().trim().isEmpty()
					&& !tixian_money.getText().toString().trim().isEmpty()) {

				tiXianRequest();

			} else {

				Toast.makeText(getApplicationContext(), "您的信息未填写完整", Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}

	}

}
