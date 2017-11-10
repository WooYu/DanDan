package com.dhxgzs.goodluck;

import com.dhxgzs.goodluck.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主界面充值页
 * 
 * @author Administrator
 *
 */
public class RechargeActivity extends Activity implements OnClickListener {
	/** 支付宝 */
	private LinearLayout zhuye_chongzhi_alipay;
	/** 微信 */
	private LinearLayout zhuye_chongzhi_weixin;
	/** 银联 */
	private LinearLayout zhuye_chongzhi_unionpay;
	/** 去转账 */
	private TextView zhuye_quzhuanzhang;
	/** 支付宝选择按钮 */
	private ImageView zhuye_alipay_kongxin;
	/** 微信选择按钮 */
	private ImageView zhuye_weixin_kongxin;
	/** 银行选择按钮 */
	private ImageView zhuye_unionpay_kongxin;
	/** 选中支付类型 */
	private String payType = "支付宝";
	private String tanTag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recharge);
		tanTag="1";
		initView();
		setListener();
		//chongzhiBgDialog();
		

}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(tanTag.equals("1")){
			tanTag="2";
		}else if(tanTag.equals("2")){
			//chongzhiBgDialog();
		}
		
	}
/*    @SuppressLint("InflateParams")
	private void chongzhiBgDialog() {
    	final AlertDialog dialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.alertdialog_chongzhi_bg,
				null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(com.dhxgzs.goodluck.R.layout.alertdialog_chongzhi_bg);
		ImageView chongzhi_bg = (ImageView) window.findViewById(R.id.chongzhi_bg);
		
		chongzhi_bg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				dialog.cancel();
			}
		});
		
	}
	*/

	private void initView() {
		// 支付宝
		zhuye_chongzhi_alipay = (LinearLayout) findViewById(R.id.zhuye_chongzhi_alipay);
		// 微信
		zhuye_chongzhi_weixin = (LinearLayout) findViewById(R.id.zhuye_chongzhi_weixin);
		// 银联
		zhuye_chongzhi_unionpay = (LinearLayout) findViewById(R.id.zhuye_chongzhi_unionpay);
		// 去转账
		zhuye_quzhuanzhang = (TextView) findViewById(R.id.zhuye_quzhuanzhang);
		// 支付宝选择按钮
		zhuye_alipay_kongxin = (ImageView) findViewById(R.id.zhuye_alipay_kongxin);
		// 微信选择按钮
		zhuye_weixin_kongxin = (ImageView) findViewById(R.id.zhuye_weixin_kongxin);
		// 银行选择按钮
		zhuye_unionpay_kongxin = (ImageView) findViewById(R.id.zhuye_unionpay_kongxin);
		zhuye_alipay_kongxin.setImageResource(R.drawable.icon_shixin);
	}

	private void setListener() {

		zhuye_chongzhi_alipay.setOnClickListener(this);
		zhuye_chongzhi_weixin.setOnClickListener(this);
		zhuye_chongzhi_unionpay.setOnClickListener(this);
		zhuye_quzhuanzhang.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.zhuye_chongzhi_alipay:
			payType = "支付宝";// 支付宝标示“1”
			zhuye_alipay_kongxin.setImageResource(R.drawable.icon_shixin);
			zhuye_weixin_kongxin.setImageResource(R.drawable.icon_kongxin);
			zhuye_unionpay_kongxin.setImageResource(R.drawable.icon_kongxin);
			
			break;
		case R.id.zhuye_chongzhi_weixin:
			payType = "微信";// 微信标示“2”
			zhuye_alipay_kongxin.setImageResource(R.drawable.icon_kongxin);
			zhuye_weixin_kongxin.setImageResource(R.drawable.icon_shixin);
			zhuye_unionpay_kongxin.setImageResource(R.drawable.icon_kongxin);
			break;
		case R.id.zhuye_chongzhi_unionpay:
			payType = "银行卡";// 银行卡标示“3”
			zhuye_alipay_kongxin.setImageResource(R.drawable.icon_kongxin);
			zhuye_weixin_kongxin.setImageResource(R.drawable.icon_kongxin);
			zhuye_unionpay_kongxin.setImageResource(R.drawable.icon_shixin);
			break;
		case R.id.zhuye_quzhuanzhang:// 转账按钮

			// 转账方法
			QuZhuanZhang(payType);
			
			break;

		default:
			break;
		}

	}

	/** 转账方法 */
	private void QuZhuanZhang(String payType2) {
		if (payType2.equals("支付宝")) {
			Intent intent1 = new Intent(RechargeActivity.this,AlipayActivity.class);
			startActivity(intent1);
		}
		if (payType2.equals("微信")) {
			Intent intent = new Intent(RechargeActivity.this,WeiXinActivity.class);
			startActivity(intent);
		} else if (payType2.equals("银行卡")) {
			Intent intent3 = new Intent(RechargeActivity.this,BankActivity.class);
			startActivity(intent3);
		}

	}

}
