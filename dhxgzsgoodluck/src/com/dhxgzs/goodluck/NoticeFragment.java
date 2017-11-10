package com.dhxgzs.goodluck;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.activity.NoticeWebViewActivity;
import com.dhxgzs.goodluck.adapter.NoticeAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.NoticeEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.view.PullToRefreshView;
import com.dhxgzs.goodluck.view.PullToRefreshView.OnFooterRefreshListener;
import com.dhxgzs.goodluck.view.PullToRefreshView.OnHeaderRefreshListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class NoticeFragment extends Fragment implements OnFooterRefreshListener, OnHeaderRefreshListener {

	private static final int NOTICE = 0;
	private static final int PULLREFRESH = 1;
	/** �Զ���ˢ�� */
	private PullToRefreshView mPullToRefreshView;
	/** �ȴ����� */
	ProgressDialog p;
	private ListView notice_Listview;
	private View view;
	private String account;
	private String imei;
	private Request<JSONObject> request;
	private RequestQueue noticeQueue;
	private List<NoticeEntity> noticeList;
	private NoticeAdapter noticeAdapter;
	private String noticeUrl;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_notice, null);

		initview();

		return view;
	}

	private void initview() {

		noticeQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getActivity(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getActivity());

		noticeRequest();
		// ����ˢ�� ��������
		mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// ��ȡʱ��
		notice_Listview = (ListView) view.findViewById(R.id.notice_Listview);

		notice_Listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				noticeUrl = noticeList.get(position).getBody();

				Intent intent = new Intent(getActivity(), NoticeWebViewActivity.class);
				intent.putExtra("noticeUrl", noticeUrl);
				startActivity(intent);

			}
		});
	}

	private void noticeRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.NOTICE_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);
		request.add("page", "1");

		noticeQueue.add(NOTICE, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case NOTICE:
				try {
					System.out.println("֪ͨ����" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {
						

						noticeList = JSON.parseArray(biz_content, NoticeEntity.class);

						noticeAdapter = new NoticeAdapter(getActivity(), noticeList);

						notice_Listview.setAdapter(noticeAdapter);
					} else if (state.equals("error")) {

						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

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
					NoticeEntity a;
					for (int i = 0; i < jsonarray.length(); i++) {
						a = new NoticeEntity();
						a = JSON.parseObject(jsonarray.getString(i), NoticeEntity.class);
						noticeList.add(a);
					}
					if (noticeList == null) {

						Toast.makeText(getActivity(), "û�и�������", Toast.LENGTH_SHORT).show();

					} else {

						noticeAdapter.notifyDataSetChanged();
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

				// if (i <= Integer.parseInt(total)) {

				PullTorequest(i);
				i++;
				System.out.println("i��ֵ" + i);
				mPullToRefreshView.onFooterRefreshComplete();
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

		request = NoHttp.createJsonObjectRequest(XyMyContent.NOTICE_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("page", i);
		request.add("imei", imei);

		noticeQueue.add(PULLREFRESH, request, responseListener);
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
