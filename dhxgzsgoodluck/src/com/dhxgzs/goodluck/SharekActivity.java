package com.dhxgzs.goodluck;

import java.util.ArrayList;

import com.dhxgzs.goodluck.sharefragment.FenXiangLianJieFragment;
import com.dhxgzs.goodluck.sharefragment.ZhiJieKaiHuFragment;
import com.dhxgzs.goodluck.view.MyViewPager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SharekActivity extends FragmentActivity implements OnClickListener{
	private ImageView icon_fenxiang;
	private ImageView vipShare_back;
	private MyViewPager share_viewPager;
	private MyPagerAdapter adapter;
	/** 添加fragment */
	private ArrayList<Fragment> fragments;
	private RadioGroup s_radioGroup1;
	private RadioButton s_r0;
	private RadioButton s_r1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharek);
		initView();
		setListener();
		fragments = new ArrayList<Fragment>();
		fragments.add(new ZhiJieKaiHuFragment());
		fragments.add(new FenXiangLianJieFragment());
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		share_viewPager.setAdapter(adapter);
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

	private void initView() {

		icon_fenxiang = (ImageView) findViewById(R.id.icon_fenxing);
		vipShare_back = (ImageView) findViewById(R.id.vipShare_back);
		s_radioGroup1 = (RadioGroup) findViewById(R.id.s_radioGroup1);
		share_viewPager = (MyViewPager) findViewById(R.id.share_viewPager);
		s_r0 = (RadioButton) findViewById(R.id.s_radio0);
		s_r1 = (RadioButton) findViewById(R.id.s_radio1);
	}

	private void setListener() {

		icon_fenxiang.setOnClickListener(this);
		vipShare_back.setOnClickListener(this);
		s_radioGroup1.performClick();
		s_radioGroup1.setOnCheckedChangeListener(mOnCheckedChangeListener);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.icon_fenxing:
//
//			UMImage thumb = new UMImage(ShareActivity.this, R.drawable.ic_launcher);
//			UMWeb web = new UMWeb("");
//			web.setTitle("真好玩，我推荐");
//			web.setThumb(thumb);
//			web.setDescription("PC蛋蛋，一款充满惊险刺激和竞猜乐趣的APP,点击注册，马上开启...");
//
//			ShareBoardConfig config = new ShareBoardConfig();
//
//			config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
//			config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
//			config.setCancelButtonVisibility(true);
//			config.setTitleVisibility(false);
//			config.setIndicatorVisibility(false);
//			config.setCancelButtonVisibility(false);
//			new ShareAction(ShareActivity.this).withMedia(web)
//					.setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
//					.setCallback(umShareListener).open(config);
//
//			break;
		case R.id.vipShare_back:
			finish();
			break;

		default:
			break;
		}

	}

//	private UMShareListener umShareListener = new UMShareListener() {
//
//		@Override
//		public void onStart(SHARE_MEDIA arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onResult(SHARE_MEDIA arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onError(SHARE_MEDIA arg0, Throwable arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onCancel(SHARE_MEDIA arg0) {
//			// TODO Auto-generated method stub
//
//		}
//	};
	// 点击radioButton切换fragment页面
		private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int position = 0;
				switch (checkedId) {
				case R.id.s_radio0:// 热赞
					share_viewPager.setCurrentItem(0);
					position = 0;
					break;
				case R.id.s_radio1:// 我赞过的
					share_viewPager.setCurrentItem(1);
					position = 1;
					break;

				}

				

			}
		};
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
