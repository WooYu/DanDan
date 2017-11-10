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
import com.dhxgzs.goodluck.adapter.HuiShuiAdapter;
import com.dhxgzs.goodluck.adapter.RechargeNotesAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.HuiShuiEntity;
import com.dhxgzs.goodluck.entiey.RechargeNotesEntity;
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
import android.widget.ListView;
import android.widget.Toast;

/**
 * ��ֵ��¼ҳ��
 * 
 * @author Administrator
 *
 */
public class RechargeNotesActivity extends Activity
		implements OnClickListener, OnFooterRefreshListener, OnHeaderRefreshListener {

	private static final int GETRECHARGENOTES = 0;
	private static final int PULLREFRESH = 1;
	/** ���ذ�ť */

	private ImageView RechargeNotes_back;
	/** ��ֵ��¼ҳ�����ҳ */
	private ListView RechargeNotes_Listview;
	/** �Զ���ˢ�� */
	private PullToRefreshView mPullToRefreshView;
	/** �ȴ����� */
	ProgressDialog p;
	private String account;
	private String imei;
	private Request<JSONObject> request;
	private RequestQueue rnQueue;
	private List<RechargeNotesEntity> rnList;
	private RechargeNotesAdapter rnAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge_notes);

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

	/** ��ʼ���ؼ� */
	private void initView() {

		rnQueue = NoHttp.newRequestQueue();

		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());

		getRechargeNotesRequest();
		// ����ˢ�� ��������
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// ��ȡʱ��

		RechargeNotes_back = (ImageView) findViewById(R.id.RechargeNotes_back);
		RechargeNotes_Listview = (ListView) findViewById(R.id.RechargeNotes_Listview);

	}

	private void getRechargeNotesRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.RECHARGENOTES_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("page", "1");
		request.add("imei", imei);

		rnQueue.add(GETRECHARGENOTES, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case GETRECHARGENOTES:

				try {
					System.out.println("��ֵ��¼" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						rnList = JSON.parseArray(biz_content, RechargeNotesEntity.class);

						rnAdapter = new RechargeNotesAdapter(getApplicationContext(), rnList);

						RechargeNotes_Listview.setAdapter(rnAdapter);

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
					RechargeNotesEntity a;
					for (int i = 0; i < jsonarray.length(); i++) {
						a = new RechargeNotesEntity();
						a = JSON.parseObject(jsonarray.getString(i), RechargeNotesEntity.class);
						rnList.add(a);
					}
					if (rnList == null) {
						
						Toast.makeText(getApplicationContext(), "û�и�������", Toast.LENGTH_SHORT).show();
						
					} else {

						rnAdapter.notifyDataSetChanged();
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

	/** �ؼ����ü��� */
	private void setListener() {

		RechargeNotes_back.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.RechargeNotes_back:// ���ذ�ť

			finish();

			break;

		default:
			break;
		}

	}

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

		request = NoHttp.createJsonObjectRequest(XyMyContent.RECHARGENOTES_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("page", i);
		request.add("imei", imei);

		rnQueue.add(PULLREFRESH, request, responseListener);
	}

	/** ����ˢ�� */
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshView.onHeaderRefreshComplete("������:" + Calendar.getInstance().getTime().toLocaleString());
				mPullToRefreshView.onHeaderRefreshComplete();

				// Toast.makeText(this, "����ˢ�����!", 0).show();
			}

		}, 3000);

	}

}
