package com.dhxgzs.goodluck.welcome;

import java.util.ArrayList;

import com.dhxgzs.goodluck.Login;
import com.dhxgzs.goodluck.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class WelcomeActivity extends Activity implements OnPageChangeListener,OnClickListener{
	private Context context;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private Button startButton;
	private LinearLayout indicatorLayout;
	private ArrayList<View> views;
	private ImageView[] indicators = null;
	private int[] images;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		context = this;
		// ���������ݷ�ʽ
		new CreateShut(this);
		// ��������ͼƬ
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  ������������ͼƬ ָʾ����page�Զ����
		images = new int[] { R.drawable.yindaoye, R.drawable.yindaoye2,};
		initView();
	}

	// ��ʼ����ͼ
	private void initView() {
		// ʵ������ͼ�ؼ�
		viewPager = (ViewPager) findViewById(R.id.viewpage);
		startButton = (Button) findViewById(R.id.start_Button);
		startButton.setOnClickListener(this);
		indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
		views = new ArrayList<View>();
		indicators = new ImageView[images.length]; // ����ָʾ�������С
		for (int i = 0; i < images.length; i++) {
			// ѭ������ͼƬ
			ImageView imageView = new ImageView(context);
			
			imageView.setBackgroundResource(images[i]);
			views.add(imageView);
			// ѭ������ָʾ��
			indicators[i] = new ImageView(context);
			indicators[i].setBackgroundResource(R.drawable.indicators_default);
			if (i == 0) {
				indicators[i].setBackgroundResource(R.drawable.indicators_now);
			}
			LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(15, 0, 0, 0);
			indicatorLayout.addView(indicators[i],lp);

		}
		pagerAdapter = new BasePagerAdapter(views);
		viewPager.setAdapter(pagerAdapter); // ����������
		viewPager.setOnPageChangeListener(this);
	}
	//��ť�ĵ���¼�
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.start_Button) {
			SharedPreferences shared = new SharedConfig(this).GetConfig();
			Editor editor = shared.edit();
			editor.putBoolean("First", false);
			editor.commit();
			
			startActivity(new Intent(WelcomeActivity.this, Login.class));
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			this.finish();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// ����viewpage
	@Override
	public void onPageSelected(int arg0) {
		// ��ʾ���һ��ͼƬʱ��ʾ��ť
		if (arg0 == indicators.length - 1) {
			startButton.setVisibility(View.VISIBLE);
		} else {
			startButton.setVisibility(View.INVISIBLE);
		}
		// ����ָʾ��ͼƬ
		for (int i = 0; i < indicators.length; i++) {
			indicators[arg0].setBackgroundResource(R.drawable.indicators_now);
			if (arg0 != i) {
				indicators[i]
						.setBackgroundResource(R.drawable.indicators_default);
			}
		}
	}
}
