package com.dhxgzs.goodluck;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.LunBoEntity;
import com.dhxgzs.goodluck.view.Kanner;
import com.dhxgzs.goodluck.view.MyViewPager;
import com.dhxgzs.goodluck.view.Kanner.OnBannerItemClickListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DynamicActivity extends FragmentActivity implements OnBannerItemClickListener {
	private static final int DYNAMICLUNBOTU = 0;
	/** �ֲ�ͼ */
	private Kanner kanner;
//	/** �ֲ�ͼƬ */
//	private int[] imagesRes = { R.drawable.banner_a, R.drawable.banner_b, R.drawable.banner_a };
	/** ���fragment */
	private ArrayList<Fragment> fragments;
	private MyViewPager gviewPager;
	private MyPagerAdapter adapter;
	private RadioGroup radioGroup1;
	private RadioButton r0;
	private RadioButton r1;
	private int screenWidth;// ��Ļ���
	private TextView textView;// ������
	private int offest; // TextVIew���� ������λ
	private float pre_position = 0;// ԭ��λ��
	private float current_position = 0;// ����λ��
	private Request<JSONObject> request;
	private RequestQueue lunboQueue;
	/** �ֲ�ͼ���� */
	private List<LunBoEntity> lbList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic);

		initView();
		setListener();
		fragments = new ArrayList<Fragment>();
		fragments.add(new NoticeFragment());
		fragments.add(new MyNewsFragment());
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		gviewPager.setAdapter(adapter);
		


}
   

	private void initView() {
		lunboQueue = NoHttp.newRequestQueue();
		LunBoRequest();
		kanner = (Kanner) findViewById(R.id.kanner);
		// ��ͼƬ���ص�view��
//		kanner.setImagesRes(imagesRes);
		gviewPager = (MyViewPager) findViewById(R.id.gviewPager);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		r0 = (RadioButton) findViewById(R.id.radio0);
		r1 = (RadioButton) findViewById(R.id.radio1);
		// ������������
		textSlide();
	}

	private void LunBoRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.SHOUYE_LUNBO_URL, RequestMethod.POST);

		lunboQueue.add(DYNAMICLUNBOTU, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {
			System.out.println("�ֲ�ͼ����" + response);
			try {
				String state = response.get().getString("state");
				if (state.equals("success")) {
					String biz_content = response.get().getString("biz_content");

					lbList = JSON.parseArray(biz_content, LunBoEntity.class);
					String[] imagesUrl = new String[lbList.size()];
					for (int i = 0; i < lbList.size(); i++) {

						imagesUrl[i] = lbList.get(i).getImage();

					}
					System.out.println("�ֲ�����" + lbList);
					kanner.setImagesUrl(imagesUrl);

//					Toast.makeText(getApplicationContext(), "��ȡ�ֲ�ͼ�ɹ�", Toast.LENGTH_SHORT).show();

				} else if (state.equals("error")) {

					Toast.makeText(getApplicationContext(), "��ȡ�ֲ�ͼʧ��", Toast.LENGTH_SHORT).show();

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

	private void textSlide() {
		// TODO Auto-generated method stub
		/****** RadioGroup ******/
		radioGroup1 = (RadioGroup) this.findViewById(R.id.radioGroup1);
		radioGroup1.performClick();
		radioGroup1.setOnCheckedChangeListener(mOnCheckedChangeListener);
		/**** RadioButton�������� *****/
		// �����Ļ���
		screenWidth = getScreenWidth();
		// ÿ���ƶ��Ŀ��
		offest = screenWidth / 2;
		// ��ʼ��������������
		textView = (TextView) this.findViewById(R.id.srcoll_text);
		// �趨���������Ŀ��
		textView.setWidth(offest);

	}

	private void setListener() {

		// ���ֲ�ͼ��ӵ���¼�
		kanner.setOnBannerItemClickListener(this);

	}

	// �����Ļ���
	private int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		return screenWidth;
	}

	// ���radioButton�л�fragmentҳ��
	private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			int position = 0;
			switch (checkedId) {
			case R.id.radio0:// ����
				gviewPager.setCurrentItem(0);
				position = 0;
				break;
			case R.id.radio1:// ���޹���
				gviewPager.setCurrentItem(1);
				position = 1;
				break;

			}

			// ����
			current_position = position * offest;
			animator_moveX(textView, pre_position, current_position);
			pre_position = current_position;

		}
	};

	protected void animator_moveX(TextView textView2, float pre_position2, float current_position2) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(textView2, "x", pre_position, current_position);
		animator.setDuration(200);
		animator.start();

	}

	/** ����ֲ�ͼ�¼� */
	@Override
	public void onClick(int position) {

//		Toast.makeText(getApplicationContext(), "�����" + position, Toast.LENGTH_SHORT).show();

	}

	class MyPagerAdapter extends FragmentStatePagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {

			return fragments.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

		}

		@Override
		public Fragment getItem(int position) {

			return fragments.get(position);
		}

	}
}
