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
 * �ֻ���
 * 
 * @author Administrator
 *
 */
public class BoundMobileActivity extends Activity implements OnClickListener {
	/** ���ذ�ť */
	private ImageView bound_mobile_back;
	/** ����ĵ绰���� */
	private EditText bound_mobileNum;
	/** ������֤�� */
	private EditText bound_yanzhengma;
	/** ��ȡ��֤�� */
	private TextView bound_mobile_getYanZhengMa;
	/** ȷ����ť */
	private TextView bound_mobile_sure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bound_mobile);

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
		// ����
		bound_mobile_back = (ImageView) findViewById(R.id.bound_mobile_back);
		// ������ֻ���
		bound_mobileNum = (EditText) findViewById(R.id.bound_mobileNum);
		// ������֤��
		bound_yanzhengma = (EditText) findViewById(R.id.bound_yanzhengma);
		// ��ȡ��֤��
		bound_mobile_getYanZhengMa = (TextView) findViewById(R.id.bound_mobile_getYanZhengMa);
		// ȷ����ť
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
