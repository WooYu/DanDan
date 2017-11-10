package com.dhxgzs.goodluck;

import com.dhxgzs.goodluck.R;
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
 * 手机绑定
 * 
 * @author Administrator
 *
 */
public class BoundMobileActivity extends Activity implements OnClickListener {
	/** 返回按钮 */
	private ImageView bound_mobile_back;
	/** 输入的电话号码 */
	private EditText bound_mobileNum;
	/** 输入验证码 */
	private EditText bound_yanzhengma;
	/** 获取验证码 */
	private TextView bound_mobile_getYanZhengMa;
	/** 确定按钮 */
	private TextView bound_mobile_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bound_mobile);

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
		// 返回
		bound_mobile_back = (ImageView) findViewById(R.id.bound_mobile_back);
		// 输入的手机号
		bound_mobileNum = (EditText) findViewById(R.id.bound_mobileNum);
		// 输入验证码
		bound_yanzhengma = (EditText) findViewById(R.id.bound_yanzhengma);
		// 获取验证码
		bound_mobile_getYanZhengMa = (TextView) findViewById(R.id.bound_mobile_getYanZhengMa);
		// 确定按钮
		bound_mobile_sure = (TextView) findViewById(R.id.bound_mobile_sure);

	}

	private void setListener() {

		bound_yanzhengma.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bound_mobile_back:
			finish();
			break;
		case R.id.bound_mobile_getYanZhengMa:

			break;
		case R.id.bound_mobile_sure:

			break;

		default:
			break;
		}

	}

}
