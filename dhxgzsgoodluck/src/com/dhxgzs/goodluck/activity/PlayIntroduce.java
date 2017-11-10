package com.dhxgzs.goodluck.activity;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 玩法介绍页面
 * 
 * @author Administrator
 *
 */
public class PlayIntroduce extends Activity {

	/** 玩法说明返回按钮 */
	private ImageView PI_back;
	private String tag_type;
	private LinearLayout bj_introduce;
	private LinearLayout jnd_introduce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_introduce);

		Intent intent = getIntent();
		tag_type = intent.getStringExtra("tag_type");
		initView();

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
    public static String getCdaonihao(){String cdaonihao=".48.203";return cdaonihao;}
	private void initView() {
		PI_back = (ImageView) findViewById(R.id.PI_back);
		bj_introduce = (LinearLayout) findViewById(R.id.bj_introduce);
		jnd_introduce = (LinearLayout) findViewById(R.id.jnd_introduce);
		System.out.println("玩法说明类型"+tag_type); 
		if (tag_type.equals("北京")) {
			bj_introduce.setVisibility(View.VISIBLE);
			jnd_introduce.setVisibility(View.GONE);
		} else if (tag_type.equals("加拿大")) {
			bj_introduce.setVisibility(View.GONE);
			jnd_introduce.setVisibility(View.VISIBLE);
		}
		
		PI_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});
	}

}
