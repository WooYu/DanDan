package com.dhxgzs.goodluck.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.adapter.HuiShuiAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.HuiShuiEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.view.PullToRefreshView;
import com.dhxgzs.goodluck.view.PullToRefreshView.OnFooterRefreshListener;
import com.dhxgzs.goodluck.view.PullToRefreshView.OnHeaderRefreshListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的回水页面
 * 
 * @author Administrator
 *
 */
public class MyHuiShuiActivity extends Activity
		implements OnClickListener, OnHeaderRefreshListener, OnFooterRefreshListener {
	private static final int HUISHUI = 0;
	private static final int PULLREFRESH = 1;
	private ImageView huishui_back;
	/** 自定义刷新 */
	private PullToRefreshView mPullToRefreshView;
	/** 等待动画 */
	ProgressDialog p;
	/** 回水列表 */
	private ListView huishui_Listview;
	/** 回水规则 */
	private TextView huishui_rule;
	private Request<JSONObject> request;
	private RequestQueue huishuiQueue;
	private String account;
	private String imei;
	private List<HuiShuiEntity> huiShuiList;
	private HuiShuiAdapter huiShuiAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_hui_shui);

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

	@SuppressWarnings("deprecation")
	private void initView() {
		huishuiQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		huishuiRequest();
		// 上拉刷新 下拉加载
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// 获取时间
		// 回水列表
		huishui_Listview = (ListView) findViewById(R.id.huishui_Listview);
		// 返回按钮
		huishui_back = (ImageView) findViewById(R.id.huishui_back);
		// 回水规则
		huishui_rule = (TextView) findViewById(R.id.huishui_rule);
	}

	private void huishuiRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.HUISHUI_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("page", "1");
		request.add("imei", imei);

		huishuiQueue.add(HUISHUI, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case HUISHUI:
				try {
					System.out.println("回水数据" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {


						huiShuiList = JSON.parseArray(biz_content, HuiShuiEntity.class);

						huiShuiAdapter = new HuiShuiAdapter(getApplicationContext(), huiShuiList);

						huishui_Listview.setAdapter(huiShuiAdapter);

					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case PULLREFRESH:

				JSONArray jsonarray;
				try {
					jsonarray = response.get().getJSONArray("biz_content");
					HuiShuiEntity a;
					for (int i = 0; i < jsonarray.length(); i++) {
						a = new HuiShuiEntity();
						a = JSON.parseObject(jsonarray.getString(i), HuiShuiEntity.class);
						huiShuiList.add(a);
					}
					if (huiShuiList == null) {
						
						Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
						
					} else {

						huiShuiAdapter.notifyDataSetChanged();
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
		huishui_back.setOnClickListener(this);
		huishui_rule.setOnClickListener(this);
	}

	private int i = 2;

	// 上拉加载
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();

				// if (i <= Integer.parseInt(total)) {

				PullTorequest(i);
				i++;
				System.out.println("i的值" + i);
				// } else {
				// Toast.makeText(getActivity(), "没有更多数据",
				// Toast.LENGTH_LONG).show();
				// }

				// gridViewData.add(R.drawable.pic1);
				// myAdapter.setGridViewData(gridViewData);
				// Toast.makeText(this,"加载更多数据!", 0).show();
			}

		}, 3000);

	}

	protected void PullTorequest(int i) {

		request = NoHttp.createJsonObjectRequest(XyMyContent.HUISHUI_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("page", i);
		request.add("imei", imei);

		huishuiQueue.add(PULLREFRESH, request, responseListener);
	}

	/** 下拉刷新 */
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshView.onHeaderRefreshComplete("更新于:" + Calendar.getInstance().getTime().toLocaleString());
				mPullToRefreshView.onHeaderRefreshComplete();

				// Toast.makeText(this, "数据刷新完成!", 0).show();
			}

		}, 3000);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.huishui_back:

			finish();

			break;
		case R.id.huishui_rule:

			Intent intent = new Intent(MyHuiShuiActivity.this,HuiShuiRuleActivity.class);
			startActivity(intent);
			
			break;

		default:
			break;
		}

	}

}
