package com.dhxgzs.goodluck.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
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

public class TiXianPassWord extends Activity implements OnClickListener {
	private static final int TIXIANMIMA = 0;
	/** 返回按钮 */
	private ImageView tixian_psd_back;
	/** 提现密码输入框 */
	private EditText tixian_Psd;
	/** 确认密码输入框 */
	private EditText tixian_surePsd;
	/** 确定按钮 */
	private TextView tixian_sureXiugai;
	private Request<JSONObject> request;
	private RequestQueue tixianQueue;
	private String account;
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ti_xian_pass_word);

		setinitView();
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

	private void setinitView() {
		tixianQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());

		tixian_psd_back = (ImageView) findViewById(R.id.tixian_psd_back);
		tixian_Psd = (EditText) findViewById(R.id.tixian_Psd);
		tixian_surePsd = (EditText) findViewById(R.id.tixian_surePsd);
		tixian_sureXiugai = (TextView) findViewById(R.id.tixian_sureXiugai);
		
		
		
	}

	private void setListener() {

		tixian_psd_back.setOnClickListener(this);
		tixian_sureXiugai.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tixian_psd_back:

			finish();

			break;
		case R.id.tixian_sureXiugai:
			if (tixian_Psd.getText().toString().equals(tixian_surePsd.getText().toString())&&!tixian_Psd.getText().toString().isEmpty()&&!tixian_surePsd.getText().toString().isEmpty()) {
				
				tiXainMiMaRequest();
			}else{
				Toast.makeText(getApplicationContext(), "两次输入密码不一致或未输入", Toast.LENGTH_SHORT).show();
			}


			break;

		default:
			break;
		}

	}

	private void tiXainMiMaRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.TIXIAN_PSD_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("password", tixian_Psd.getText().toString());
		request.add("imei", imei);

		tixianQueue.add(TIXIANMIMA, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {
			
			System.out.println("提现密码返回结果"+response);
			try {
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");
				if(state.equals("success")){
					SharedPreferencesUtils.putValue(getApplicationContext(), "oldPsd", tixian_Psd.getText().toString());
					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					finish();
				}else if(state.equals("error")){
					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
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
