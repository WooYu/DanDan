package com.dhxgzs.goodluck;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.activity.RechargeWebActivity;
import com.dhxgzs.goodluck.util.AnimationUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	private TabHost tabHost;
	private long firstTime=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		tabHost = getTabHost();
		add("首页", R.drawable.itembg, new Intent(this, ShouYeActivity.class));
		add("充值", R.drawable.itembg1, new Intent(this, RechargeActivity.class));
		add("动态", R.drawable.itembg2, new Intent(this, DynamicActivity.class));
		add("我的", R.drawable.itembg3, new Intent(this, MineActivity.class));
	}
	private void setTranslucentStatus(boolean on) {
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
	private void add(String title, int bg, Intent intent) {
		// TODO Auto-generated method stub
		View localView = LayoutInflater.from(this.tabHost.getContext()).inflate(R.layout.tab_item, null);
		// 这个文件下可设置tabhost																									
		// 的图片大小和字体颜色

		// (ImageView)
		(localView.findViewById(R.id.tabImg)).setBackgroundResource(bg);
		((TextView) localView.findViewById(R.id.tabText)).setText(title);
		TabHost.TabSpec localTabSpec = tabHost.newTabSpec(title).setIndicator(localView).setContent(intent);
		tabHost.addTab(localTabSpec);
	}
	@Override
	protected void onPause() {
		if (AnimationUtil.ANIM_IN != 0 && AnimationUtil.ANIM_OUT != 0) {
			super.overridePendingTransition(AnimationUtil.ANIM_IN,
					AnimationUtil.ANIM_OUT);
			AnimationUtil.clear();
		}
		super.onPause();
	}
	
	 public boolean dispatchKeyEvent(KeyEvent event) {
	        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK &&event.getAction() == KeyEvent.ACTION_DOWN) {
//	            mMenu.showContent();
	            if(System.currentTimeMillis()-firstTime>2000){
	            	 
	                Toast.makeText(this,"再点击一次将退出程序",Toast.LENGTH_SHORT).show();
	                firstTime=System.currentTimeMillis();
	            }else{
	                finish();
	                
	            }
	            return true;
	        }
	        return super.dispatchKeyEvent(event);
	    }

}
