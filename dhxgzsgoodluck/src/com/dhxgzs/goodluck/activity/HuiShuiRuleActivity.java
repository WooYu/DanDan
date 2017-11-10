package com.dhxgzs.goodluck.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.HuiShuiRuleEntity;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HuiShuiRuleActivity extends Activity {

	private static final int HUISHUIRULE = 0;
	private ImageView huishui_rule_back;
	private TextView kuisunMoney,kuisunMoney2,kuisunMoney3;
	private TextView huishuiBi, huishuiBi2, huishuiBi3; 
	private TextView sec_kuisunMoney, sec_kuisunMoney1, sec_kuisunMoney2, sec_kuisunMoney3, sec_kuisunMoney4,
			sec_kuisunMoney5;
	private TextView sec_huishuiBi, sec_huishuiBi1, sec_huishuiBi2, sec_huishuiBi3, sec_huishuiBi4, sec_huishuiBi5;
	private TextView th_kuisunMoney1,th_kuisunMoney2,th_kuisunMoney3,th_kuisunMoney4,th_kuisunMoney5,th_kuisunMoney6,th_kuisunMoney7;
	private TextView th_huishuiBi1,th_huishuiBi2,th_huishuiBi3,th_huishuiBi4,th_huishuiBi5,th_huishuiBi6,th_huishuiBi7;
	private String account;
	private String imei;
	private Request<JSONObject> request;
	private RequestQueue huishuiRuleQueue;
	private List<HuiShuiRuleEntity> hsrList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hui_shui_rule);

		initview();
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

	private void initview() {

		huishuiRuleQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		huishuiRuleRequest();
		huishui_rule_back = (ImageView) findViewById(R.id.huishui_rule_back);
		kuisunMoney = (TextView) findViewById(R.id.kuisunMoney);
		huishuiBi = (TextView) findViewById(R.id.huishuiBi);
		kuisunMoney2 =(TextView) findViewById(R.id.kuisunMoney2);
		huishuiBi2 = (TextView) findViewById(R.id.huishuiBi2);
		kuisunMoney3=(TextView) findViewById(R.id.kuisunMoney3);
		huishuiBi3 = (TextView) findViewById(R.id.huishuiBi3);
		
		
		sec_kuisunMoney = (TextView) findViewById(R.id.sec_kuisunMoney);
		sec_huishuiBi = (TextView) findViewById(R.id.sec_huishuiBi);
		sec_kuisunMoney1 = (TextView) findViewById(R.id.sec_kuisunMoney1);
		sec_huishuiBi1 = (TextView) findViewById(R.id.sec_huishuiBi1);
		sec_kuisunMoney2 = (TextView) findViewById(R.id.sec_kuisunMoney2);
		sec_huishuiBi2 = (TextView) findViewById(R.id.sec_huishuiBi2);
		sec_kuisunMoney3 = (TextView) findViewById(R.id.sec_kuisunMoney3);
		sec_huishuiBi3 = (TextView) findViewById(R.id.sec_huishuiBi3);
		sec_kuisunMoney4 = (TextView) findViewById(R.id.sec_kuisunMoney4);
		sec_huishuiBi4 = (TextView) findViewById(R.id.sec_huishuiBi4);
		sec_kuisunMoney5 = (TextView) findViewById(R.id.sec_kuisunMoney5);
		sec_huishuiBi5 = (TextView) findViewById(R.id.sec_huishuiBi5);
		
		th_kuisunMoney1 = (TextView) findViewById(R.id.th_kuisunMoney1);
		th_huishuiBi1 = (TextView) findViewById(R.id.th_huishuiBi1);
		th_kuisunMoney2 = (TextView) findViewById(R.id.th_kuisunMoney2);
		th_huishuiBi2 = (TextView) findViewById(R.id.th_huishuiBi2);
		th_kuisunMoney3 = (TextView) findViewById(R.id.th_kuisunMoney3);
		th_huishuiBi3 = (TextView) findViewById(R.id.th_huishuiBi3);
		th_kuisunMoney4 = (TextView) findViewById(R.id.th_kuisunMoney4);
		th_huishuiBi4 = (TextView) findViewById(R.id.th_huishuiBi4);
		th_kuisunMoney5 = (TextView) findViewById(R.id.th_kuisunMoney5);
		th_huishuiBi5 = (TextView) findViewById(R.id.th_huishuiBi5);
		th_kuisunMoney6 = (TextView) findViewById(R.id.th_kuisunMoney6);
		th_huishuiBi6 = (TextView) findViewById(R.id.th_huishuiBi6);
		th_kuisunMoney7 = (TextView) findViewById(R.id.th_kuisunMoney7);
		th_huishuiBi7 = (TextView) findViewById(R.id.th_huishuiBi7);
		
		huishui_rule_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}

	private void huishuiRuleRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.HUISHUIRULE_URL, RequestMethod.POST);

		request.add("imei", imei);
		request.add("account", account);

		huishuiRuleQueue.add(HUISHUIRULE, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			System.out.println("回水规则" + response);
			try {
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");
				 
				if (state.equals("success")) {

					hsrList = JSON.parseArray(biz_content, HuiShuiRuleEntity.class);

					kuisunMoney.setText(hsrList.get(0).getMinloss() + "~" + hsrList.get(0).getMaxloss());
					huishuiBi.setText(hsrList.get(0).getBackrate()+"%");
					
					kuisunMoney2.setText(hsrList.get(1).getMinloss() + "~" + hsrList.get(1).getMaxloss());				
					huishuiBi2.setText(hsrList.get(1).getBackrate()+"%");
					
					kuisunMoney3.setText(hsrList.get(2).getMinloss() + "~" + hsrList.get(2).getMaxloss());				
					huishuiBi3.setText(hsrList.get(2).getBackrate()+"%");

					sec_kuisunMoney.setText(hsrList.get(3).getMinloss() + "~" + hsrList.get(3).getMaxloss());
					sec_huishuiBi.setText(hsrList.get(3).getBackrate()+"%");

					sec_kuisunMoney1.setText(hsrList.get(4).getMinloss() + "~" + hsrList.get(4).getMaxloss());
					sec_huishuiBi1.setText(hsrList.get(4).getBackrate()+"%");

					sec_kuisunMoney2.setText(hsrList.get(5).getMinloss() + "~" + hsrList.get(5).getMaxloss());
					sec_huishuiBi2.setText(hsrList.get(5).getBackrate()+"%");

					sec_kuisunMoney3.setText(hsrList.get(6).getMinloss() + "~" + hsrList.get(6).getMaxloss());
					sec_huishuiBi3.setText(hsrList.get(6).getBackrate()+"%");

					sec_kuisunMoney4.setText(hsrList.get(7).getMinloss() + "~" + hsrList.get(7).getMaxloss());
					sec_huishuiBi4.setText(hsrList.get(7).getBackrate()+"%");

					sec_kuisunMoney5.setText(hsrList.get(8).getMinloss() + "~" + hsrList.get(8).getMaxloss());
					sec_huishuiBi5.setText(hsrList.get(8).getBackrate()+"%");
					
					
					th_kuisunMoney1.setText(hsrList.get(9).getMinloss() + "~" + hsrList.get(9).getMaxloss());
					th_huishuiBi1.setText(hsrList.get(9).getBackrate()+"%");
					th_kuisunMoney2.setText(hsrList.get(10).getMinloss() + "~" + hsrList.get(10).getMaxloss());
					th_huishuiBi2.setText(hsrList.get(10).getBackrate()+"%");
					th_kuisunMoney3.setText(hsrList.get(11).getMinloss() + "~" + hsrList.get(11).getMaxloss());
					th_huishuiBi3.setText(hsrList.get(11).getBackrate()+"%");
					th_kuisunMoney4.setText(hsrList.get(12).getMinloss() + "~" + hsrList.get(12).getMaxloss());
					th_huishuiBi4.setText(hsrList.get(12).getBackrate()+"%");
					th_kuisunMoney5.setText(hsrList.get(13).getMinloss() + "~" + hsrList.get(13).getMaxloss());
					th_huishuiBi5.setText(hsrList.get(13).getBackrate()+"%");
					th_kuisunMoney6.setText(hsrList.get(14).getMinloss() + "~" + hsrList.get(14).getMaxloss());
					th_huishuiBi6.setText(hsrList.get(14).getBackrate()+"%");
					th_kuisunMoney7.setText(hsrList.get(15).getMinloss() + "~" + hsrList.get(15).getMaxloss());
					th_huishuiBi7.setText(hsrList.get(15).getBackrate()+"%");

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
