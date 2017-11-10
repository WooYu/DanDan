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
 * �޸�����ҳ��
 * 
 * @author Administrator
 *
 */
public class XiuGai_PasswordActivity extends Activity implements OnClickListener {

	/** ���ذ�ť */
	private ImageView xiugai_back;
	/** ԭ���� */
	private EditText xiugai_oldPsd;
	/** ������ */
	private EditText xiugai_newPsd;
	/** ȷ������ */
	private EditText xiugai_surePsd;
	/** �޸İ�ť */
	private TextView xiugai_sureXiugai;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiu_gai__password);

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
		// ���ذ�ť
		xiugai_back = (ImageView) findViewById(R.id.xiugai_back);
		// ԭ����
		xiugai_oldPsd = (EditText) findViewById(R.id.xiugai_oldPsd);
		// ������
		xiugai_newPsd = (EditText) findViewById(R.id.xiugai_newPsd);
		// ȷ������
		xiugai_surePsd = (EditText) findViewById(R.id.xiugai_surePsd);
		// �޸İ�ť
		xiugai_sureXiugai = (TextView) findViewById(R.id.xiugai_sureXiugai);

	}

	private void setListener() {
		xiugai_back.setOnClickListener(this);
		xiugai_sureXiugai.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.xiugai_back://���ذ�ť
			
			finish();
			
			break;
			
		case R.id.xiugai_sureXiugai://ȷ���޸İ�ť
			
			
			break;
			
			
		default:
			break;
		}

	}

}
