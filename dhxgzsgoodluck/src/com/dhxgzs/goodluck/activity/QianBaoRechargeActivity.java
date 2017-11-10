package com.dhxgzs.goodluck.activity;

import com.dhxgzs.goodluck.AlipayActivity;
import com.dhxgzs.goodluck.BankActivity;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.RechargeActivity;
import com.dhxgzs.goodluck.WeiXinActivity;
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Ǯ����ֵҳ
 * 
 * @author Administrator
 *
 */
public class QianBaoRechargeActivity extends Activity implements OnClickListener {

	/** ���ذ�ť */
	private ImageView chongzhi_back;
	/** ֧���� */
	private LinearLayout chongzhi_alipay;
	/** ΢�� */
	private LinearLayout chongzhi_weixin;
	/** ���� */
	private LinearLayout chongzhi_unionpay;
	/** ȥת�� */
	private TextView quzhuanzhang;
	/** ֧����ѡ��ť */
	private ImageView alipay_kongxin;
	/** ΢��ѡ��ť */
	private ImageView weixin_kongxin;
	/** ����ѡ��ť */
	private ImageView unionpay_kongxin;
	/** Ǯ����ֵ��ʽ��־ */
	private String QB_payType = "1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qian_bao_recharge);

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
		chongzhi_back = (ImageView) findViewById(R.id.chongzhi_back);
		// ֧����
		chongzhi_alipay = (LinearLayout) findViewById(R.id.chongzhi_alipay);
		// ΢��
		chongzhi_weixin = (LinearLayout) findViewById(R.id.chongzhi_weixin);
		// ����
		chongzhi_unionpay = (LinearLayout) findViewById(R.id.chongzhi_unionpay);
		// ȥת��
		quzhuanzhang = (TextView) findViewById(R.id.quzhuanzhang);
		// ֧����ѡ��ť
		alipay_kongxin = (ImageView) findViewById(R.id.alipay_kongxin);
		// ΢��ѡ��ť
		weixin_kongxin = (ImageView) findViewById(R.id.weixin_kongxin);
		// ����ѡ��ť
		unionpay_kongxin = (ImageView) findViewById(R.id.unionpay_kongxin);
		alipay_kongxin.setImageResource(R.drawable.icon_shixin);
	}

	private void setListener() {

		chongzhi_back.setOnClickListener(this);
		chongzhi_alipay.setOnClickListener(this);
		chongzhi_weixin.setOnClickListener(this);
		chongzhi_unionpay.setOnClickListener(this);
		quzhuanzhang.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.chongzhi_back:

			finish();

			break;
		case R.id.chongzhi_alipay:
			QB_payType = "1";
			alipay_kongxin.setImageResource(R.drawable.icon_shixin);
			weixin_kongxin.setImageResource(R.drawable.icon_kongxin);
			unionpay_kongxin.setImageResource(R.drawable.icon_kongxin);
			break;
		case R.id.chongzhi_weixin:
			QB_payType = "2";
			alipay_kongxin.setImageResource(R.drawable.icon_kongxin);
			weixin_kongxin.setImageResource(R.drawable.icon_shixin);
			unionpay_kongxin.setImageResource(R.drawable.icon_kongxin);
			break;
		case R.id.chongzhi_unionpay:
			QB_payType = "3";
			alipay_kongxin.setImageResource(R.drawable.icon_kongxin);
			weixin_kongxin.setImageResource(R.drawable.icon_kongxin);
			unionpay_kongxin.setImageResource(R.drawable.icon_shixin);
			break;
		case R.id.quzhuanzhang:
			// Ǯ��ҳȥת�˷���
			QBchongZhi(QB_payType);

			break;

		default:
			break;
		}

	}

	/** Ǯ��ҳȥת�˷��� */
	private void QBchongZhi(String qB_payType2) {
		if (qB_payType2.equals("1")) {
			
			Intent intent1 = new Intent(QianBaoRechargeActivity.this,AlipayActivity.class);
			startActivity(intent1);
		}
		if (qB_payType2.equals("2")) {
			Intent intent1 = new Intent(QianBaoRechargeActivity.this,WeiXinActivity.class);
			startActivity(intent1);
		} else if (qB_payType2.equals("3")) {
			Intent intent1 = new Intent(QianBaoRechargeActivity.this,BankActivity.class);
			startActivity(intent1);
		}
	}

}
