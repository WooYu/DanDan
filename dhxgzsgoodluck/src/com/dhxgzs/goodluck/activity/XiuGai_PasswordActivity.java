package com.dhxgzs.goodluck.activity;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 修改密码页面
 * 
 * @author Administrator
 *
 */
public class XiuGai_PasswordActivity extends Activity implements OnClickListener {

	/** 返回按钮 */
	private ImageView xiugai_back;
	/** 原密码 */
	private EditText xiugai_oldPsd;
	/** 新密码 */
	private EditText xiugai_newPsd;
	/** 确认密码 */
	private EditText xiugai_surePsd;
	/** 修改按钮 */
	private TextView xiugai_sureXiugai;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiu_gai__password);

		initView();
		setListener();

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
		// 返回按钮
		xiugai_back = (ImageView) findViewById(R.id.xiugai_back);
		// 原密码
		xiugai_oldPsd = (EditText) findViewById(R.id.xiugai_oldPsd);
		// 新密码
		xiugai_newPsd = (EditText) findViewById(R.id.xiugai_newPsd);
		// 确认密码
		xiugai_surePsd = (EditText) findViewById(R.id.xiugai_surePsd);
		// 修改按钮
		xiugai_sureXiugai = (TextView) findViewById(R.id.xiugai_sureXiugai);

	}

	private void setListener() {
		xiugai_back.setOnClickListener(this);
		xiugai_sureXiugai.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.xiugai_back://返回按钮
			
			finish();
			
			break;
			
		case R.id.xiugai_sureXiugai://确定修改按钮
			
			
			break;
			
			
		default:
			break;
		}

	}

}
