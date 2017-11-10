package com.dhxgzs.goodluck.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.dhxgzs.goodluck.adapter.MyGameNotesAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
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
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Ͷע��¼ҳ
 * 
 * @author Administrator
 *
 */

public class BettingRecord extends Activity
		implements OnClickListener, OnHeaderRefreshListener, OnFooterRefreshListener {

	private static final int BETNOTES = 0;
	private static final int PULLREFRESH = 1;
	private ListView bet_note_listview;
	private List<MyGameNotesEntity> betList;
	private MyGameNotesAdapter betAdapter;
	private ImageView touzhu_back;
	private String account;
	private String imei;
	private Request<JSONObject> request;
	private RequestQueue betQueue;
	private String wanfaType;
	/** �Զ���ˢ�� */
	private PullToRefreshView mPullToRefreshView;
	/** �ȴ����� */
	ProgressDialog p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_betting_record);

		initView();
		setListener();
		 // ���SDK�İ汾��4.4֮�ϣ���ôӦ�ó���ʽ״̬��
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
    // ���ó���״̬��
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

		betQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// �淨����
		wanfaType = SharedPreferencesUtils.getValue(getApplicationContext(), "wanfaType");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());

		betNotesRequest();
		// ����ˢ�� ��������
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// ��ȡʱ��
		bet_note_listview = (ListView) findViewById(R.id.bet_note_listview);
		touzhu_back = (ImageView) findViewById(R.id.touzhu_back);

	}

	private void betNotesRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.GAMENOTE_URL, RequestMethod.POST);

		if (wanfaType.equals("����")) {
			wanfaType = "10";
		} else if (wanfaType.equals("���ô�")) {
			wanfaType = "11";
		}

		request.add("account", account);
		request.add("type", wanfaType);
		request.add("page", "1");
		request.add("imei", imei);

		System.out.println(account);
		System.out.println(wanfaType);
		System.out.println(imei);
		betQueue.add(BETNOTES, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case BETNOTES:
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						betList = JSON.parseArray(biz_content, MyGameNotesEntity.class);

						betAdapter = new MyGameNotesAdapter(getApplicationContext(), betList);

						bet_note_listview.setAdapter(betAdapter);
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
						betList.add(a);
					}
					if (betList == null) {

						Toast.makeText(getApplicationContext(), "û�и�������", Toast.LENGTH_SHORT).show();

					} else {

						betAdapter.notifyDataSetChanged();
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

	// ��������
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();

				// if (i <= Integer.parseInt(total)) {

				PullTorequest(i);
				i++;
				System.out.println("i��ֵ" + i);
				// } else {
				// Toast.makeText(getActivity(), "û�и�������",
				// Toast.LENGTH_LONG).show();
				// }

				// gridViewData.add(R.drawable.pic1);
				// myAdapter.setGridViewData(gridViewData);
				// Toast.makeText(this,"���ظ�������!", 0).show();
			}

		}, 3000);

	}

	protected void PullTorequest(int i) {

		request = NoHttp.createJsonObjectRequest(XyMyContent.GAMENOTE_URL, RequestMethod.POST);

		if (wanfaType.equals("����")) {
			wanfaType = "10";
		} else if (wanfaType.equals("���ô�")) {
			wanfaType = "11";
		}

		request.add("account", account);
		request.add("type", wanfaType);
		request.add("page", i);
		request.add("imei", imei);

		betQueue.add(PULLREFRESH, request, responseListener);
	}

	/** ����ˢ�� */
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshView.onHeaderRefreshComplete("������:" + Calendar.getInstance().getTime().toLocaleString());
				mPullToRefreshView.onHeaderRefreshComplete();
				betNotesRequest();
				// Toast.makeText(this, "����ˢ�����!", 0).show();
			}

		}, 3000);

	}

	private void setListener() {

		touzhu_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.touzhu_back:

			finish();

			break;

		default:
			break;
		}

	}

}
