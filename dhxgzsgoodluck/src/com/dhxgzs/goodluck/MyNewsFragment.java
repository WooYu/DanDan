package com.dhxgzs.goodluck;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.activity.MessageWebViewActivity;
import com.dhxgzs.goodluck.activity.NoticeWebViewActivity;
import com.dhxgzs.goodluck.adapter.MyNewsAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.NoticeEntity;
import com.dhxgzs.goodluck.entiey.TiXianNotesEntity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("InflateParams")
public class MyNewsFragment extends Fragment implements OnFooterRefreshListener, OnHeaderRefreshListener {
	private static final int MYNEWS = 0;
	private static final int PULLREFRESH = 1;
	/** 自定义刷新 */
	private PullToRefreshView mPullToRefreshView;
	/** 等待动画 */
	ProgressDialog p;
	private ListView myNews_Listview;
	private View view;
	private String account;
	private String imei;
	private Request<JSONObject> request;
	private RequestQueue MyNewsQueue;
	private List<NoticeEntity> MyNewsList;
	private MyNewsAdapter newsAdapter;
	private String noticeUrl;
	private TextView tisi;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_my_news, null);
		initview();
		return view;
	}

	private void initview() {
		MyNewsQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getActivity(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getActivity());

		MyNewsRequest();
		// 上拉刷新 下拉加载
		mPullToRefreshView = (PullToRefreshView) view.findViewById(R.id.main_pull_refresh_view);

		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setLastUpdated(new Date().toLocaleString());// 获取时间
		myNews_Listview = (ListView) view.findViewById(R.id.myNews_Listview);
		tisi = (TextView) view.findViewById(R.id.tisi);
		myNews_Listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				noticeUrl = MyNewsList.get(position).getBody();
				System.out.println("我的消息网址" + noticeUrl);
				Intent intent = new Intent(getActivity(), MessageWebViewActivity.class);
				intent.putExtra("messageUrl", noticeUrl);
				startActivity(intent);

			}
		});

	}

	private void MyNewsRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.MYNEWS_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("page", "1");
		request.add("imei", imei);

		MyNewsQueue.add(MYNEWS, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case MYNEWS:
				try {
					System.out.println("我的消息" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						if (!biz_content.equals("暂无信息")) {
							MyNewsList = JSON.parseArray(biz_content, NoticeEntity.class);

							newsAdapter = new MyNewsAdapter(getActivity(), MyNewsList);

							myNews_Listview.setAdapter(newsAdapter);
						} else {
							myNews_Listview.setVisibility(View.GONE);
							tisi.setVisibility(View.VISIBLE);
							tisi.setText(biz_content);
							Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

						}

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
						MyNewsList.add(a);
					}
					if (MyNewsList == null) {

						Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();

					} else {

						newsAdapter.notifyDataSetChanged();
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

		request = NoHttp.createJsonObjectRequest(XyMyContent.MYNEWS_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("page", i);
		request.add("imei", imei);

		MyNewsQueue.add(PULLREFRESH, request, responseListener);
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

}
