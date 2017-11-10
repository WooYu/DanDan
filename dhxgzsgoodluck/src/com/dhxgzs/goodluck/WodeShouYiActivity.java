package com.dhxgzs.goodluck;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.adapter.WodeshouyiAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.WodeshouyiEntity;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WodeShouYiActivity extends Activity implements OnClickListener, OnHeaderRefreshListener, OnFooterRefreshListener{
	private static final int WODESHOUYI = 0;
	private static final int PULLREFRESH = 1;
	private ImageView wodeshouyi_back;
	/** �Զ���ˢ�� */
	private PullToRefreshView mPullToRefreshView;
	/** �ȴ����� */
	ProgressDialog p;
	/** ��ˮ�б� */
	private ListView wodeshouyi_Listview;
	/** ��ˮ���� */
	private TextView huishui_rule;
	private Request<JSONObject> request;
	private RequestQueue shouyiQueue;
	private String account;
	private String imei;
	private List<WodeshouyiEntity> wodeshouyiList;
	private WodeshouyiAdapter wodeshouyiAdapter;
	private TextView tv_wu;//�����¼�Ӷ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wode_shou_yi);
		
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
		shouyiQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());
		shouyiRequest();
		// ����ˢ�� ��������
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// ��ȡʱ��
		// �ҵ������б�
		wodeshouyi_Listview = (ListView) findViewById(R.id.wodeshouyi_Listview);
		// ���ذ�ť
		wodeshouyi_back = (ImageView) findViewById(R.id.wodeshouyi_back);
		tv_wu=(TextView) findViewById(R.id.tv_wu);
		// ��ˮ����
//		huishui_rule = (TextView) findViewById(R.id.huishui_rule);
	}

	private void shouyiRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.WODESHOUYI, RequestMethod.POST);

		request.add("account", account);
		request.add("page", "1");
		request.add("imei", imei);
		System.out.println("�˺�"+account);
		shouyiQueue.add(WODESHOUYI, request, responseListener);
		
	}
	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case WODESHOUYI:
				try {
					System.out.println("�ҵ���������" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {
						
						if(biz_content.equals("�����¼�Ӷ��")){
							wodeshouyi_Listview.setVisibility(View.GONE);
							tv_wu.setVisibility(View.VISIBLE);
							tv_wu.setText(biz_content);
							
						}else{
							wodeshouyi_Listview.setVisibility(View.VISIBLE);
							tv_wu.setVisibility(View.GONE);
							wodeshouyiList = JSON.parseArray(biz_content, WodeshouyiEntity.class);
							
							wodeshouyiAdapter = new WodeshouyiAdapter(getApplicationContext(), wodeshouyiList);
							
							wodeshouyi_Listview.setAdapter(wodeshouyiAdapter);
						}


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
					WodeshouyiEntity a;
					for (int i = 0; i < jsonarray.length(); i++) {
						a = new WodeshouyiEntity();
						a = JSON.parseObject(jsonarray.getString(i), WodeshouyiEntity.class);
						wodeshouyiList.add(a);
					}
					if (wodeshouyiList == null) {
						
						Toast.makeText(getApplicationContext(), "û�и�������", Toast.LENGTH_SHORT).show();
						
					} else {

						wodeshouyiAdapter.notifyDataSetChanged();
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
		wodeshouyi_back.setOnClickListener(this);
//		huishui_rule.setOnClickListener(this);
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

		request = NoHttp.createJsonObjectRequest(XyMyContent.WODESHOUYI, RequestMethod.POST);

		request.add("account", account);
		request.add("page", i);
		request.add("imei", imei);

		shouyiQueue.add(PULLREFRESH, request, responseListener);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.wodeshouyi_back:

			finish();

			break;
//		case R.id.huishui_rule:
//
//			Intent intent = new Intent(MyHuiShuiActivity.this,HuiShuiRuleActivity.class);
//			startActivity(intent);
//			
//			break;

		default:
			break;
		}

		
	}


}
