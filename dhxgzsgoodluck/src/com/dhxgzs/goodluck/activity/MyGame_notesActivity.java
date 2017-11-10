package com.dhxgzs.goodluck.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.adapter.MyGameNotesAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.HuiShuiEntity;
import com.dhxgzs.goodluck.entiey.MyGameNotesEntity;
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
import android.widget.Toast;

public class MyGame_notesActivity extends Activity
		implements OnClickListener, OnHeaderRefreshListener, OnFooterRefreshListener {

	private static final int GAMENOTES = 0;
	private static final int PULLREFRESH = 1;
	private ImageView my_gameNotes_back;
	private ListView my_gameNotesListview;
	private MyGameNotesAdapter mgnAdapter;
	private List<MyGameNotesEntity> mgnList;
	private String account;
	private String imei;
	private String GameType;
	private String StartTime;
	private String EndTime;
	private Request<JSONObject> request;
	private RequestQueue gameNotesQueue;
	/** 自定义刷新 */
	private PullToRefreshView mPullToRefreshView;
	/** 等待动画 */
	ProgressDialog p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_game_notes);

		Intent intent = getIntent();
		GameType = intent.getStringExtra("GameType");
		StartTime = intent.getStringExtra("StartTime");
		EndTime = intent.getStringExtra("EndTime");
		initview();
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

	private void setListener() {

		my_gameNotes_back.setOnClickListener(this);

	}

	private void initview() {

		gameNotesQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());

		GameNotesRequest();
		// 上拉刷新 下拉加载
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// 获取时间
		my_gameNotes_back = (ImageView) findViewById(R.id.my_gameNotes_back);
		my_gameNotesListview = (ListView) findViewById(R.id.my_gameNotesListview);
	}

	private void GameNotesRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.GAMENOTE_URL, RequestMethod.POST);

		if (GameType.equals("全部")) {
			GameType = "0";
		} else if (GameType.equals("北京28")) {
			GameType = "10";
		} else if (GameType.equals("加拿大28")) {
			GameType = "11";
		}
		request.add("account", account);
		request.add("type", GameType);
		request.add("start", StartTime);
		request.add("end", EndTime);
		request.add("page", "1");
		request.add("imei", imei);

		gameNotesQueue.add(GAMENOTES, request, responseListener);
	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case GAMENOTES:
				System.out.println("我的游戏记录数据" + response);

				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						mgnList = JSON.parseArray(biz_content, MyGameNotesEntity.class);

						mgnAdapter = new MyGameNotesAdapter(getApplicationContext(), mgnList);

						my_gameNotesListview.setAdapter(mgnAdapter);
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
					MyGameNotesEntity a;
					for (int i = 0; i < jsonarray.length(); i++) {
						a = new MyGameNotesEntity();
						a = JSON.parseObject(jsonarray.getString(i), MyGameNotesEntity.class);
						mgnList.add(a);
					}
					if (mgnList != null) {

						mgnAdapter.notifyDataSetChanged();

					} else {

						Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
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

		request = NoHttp.createJsonObjectRequest(XyMyContent.GAMENOTE_URL, RequestMethod.POST);

		if (GameType.equals("全部")) {
			GameType = "0";
		} else if (GameType.equals("北京28")) {
			GameType = "10";
		} else if (GameType.equals("加拿大28")) {
			GameType = "11";
		}
		request.add("account", account);
		request.add("type", GameType);
		request.add("start", StartTime);
		request.add("end", EndTime);
		request.add("page", i);
		request.add("imei", imei);
		gameNotesQueue.add(PULLREFRESH, request, responseListener);
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
		case R.id.my_gameNotes_back:

			finish();

			break;

		default:
			break;
		}

	}

}
