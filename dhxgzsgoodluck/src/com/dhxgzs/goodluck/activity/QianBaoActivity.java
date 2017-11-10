package com.dhxgzs.goodluck.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.UserInfoEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Ǯ������
 * 
 * @author Administrator
 *
 */
public class QianBaoActivity extends Activity implements OnClickListener {

	private static final int PANDUANBOUNDSTATE = 0;
	private static final int QAINBAOYUE = 1;
	/** ���� */
	private ImageView Qianbao_back;
	/** Ǯ��Ԫ���� */
	private TextView qianbao_YuanBao_Num;
	/** �ҵ����п� */
	private LinearLayout qianbao_My_bank;
	/** ��ֵ */
	private LinearLayout qianbao_Recharge;
	/** ���� */
	private LinearLayout qianbao_tixian;
	/** ��ֵ��¼ */
	private LinearLayout qianbao_Recharge_notes;
	/** ���ּ�¼ */
	private LinearLayout qianbao_tixian_notes;
	private String yuanbaoNum;
	private Request<JSONObject> request;
	private RequestQueue panduanQueue;
	private String account;
	private String imei;
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qian_bao);

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
		panduanQueue = NoHttp.newRequestQueue();
		yuanbaoNum = SharedPreferencesUtils.getValue(getApplicationContext(), "yuanbaoNum");
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());

		qianbaoYueRequest();

		// ����
		Qianbao_back = (ImageView) findViewById(R.id.Qianbao_back);
		// Ǯ��Ԫ����
		qianbao_YuanBao_Num = (TextView) findViewById(R.id.qianbao_YuanBao_Num);
		// �ҵ����п�
		qianbao_My_bank = (LinearLayout) findViewById(R.id.qianbao_My_bank);
		// ��ֵ
		qianbao_Recharge = (LinearLayout) findViewById(R.id.qianbao_Recharge);
		// ����
		qianbao_tixian = (LinearLayout) findViewById(R.id.qianbao_tixian);
		// ��ֵ��¼
		qianbao_Recharge_notes = (LinearLayout) findViewById(R.id.qianbao_Recharge_notes);
		// ���ּ�¼
		qianbao_tixian_notes = (LinearLayout) findViewById(R.id.qianbao_tixian_notes);

	}

	private void qianbaoYueRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.ACCOUNTMONEY_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		panduanQueue.add(QAINBAOYUE, request, responseListener);

	}

	private void setListener() {
		Qianbao_back.setOnClickListener(this);
		qianbao_My_bank.setOnClickListener(this);
		qianbao_Recharge.setOnClickListener(this);
		qianbao_tixian.setOnClickListener(this);
		qianbao_Recharge_notes.setOnClickListener(this);
		qianbao_tixian_notes.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.Qianbao_back:
			finish();
			break;
		case R.id.qianbao_My_bank:
			type = 1;
			alreadyBoundBankRequest(type);

			break;
		case R.id.qianbao_Recharge:

			Intent intent = new Intent(QianBaoActivity.this, QianBaoRechargeActivity.class);
			startActivity(intent);
			break;
		case R.id.qianbao_tixian:
			type = 2;
			alreadyBoundBankRequest(type);

			break;
		case R.id.qianbao_Recharge_notes:

			Intent intent2 = new Intent(QianBaoActivity.this, RechargeNotesActivity.class);
			startActivity(intent2);
			break;
		case R.id.qianbao_tixian_notes:
			Intent intent3 = new Intent(QianBaoActivity.this, TiXian_NotesActivity.class);
			startActivity(intent3);
			break;

		default:
			break;
		}

	}

	private void alreadyBoundBankRequest(int type) {

		request = NoHttp.createJsonObjectRequest(XyMyContent.MYBANKCARD_INFO_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		panduanQueue.add(PANDUANBOUNDSTATE, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case PANDUANBOUNDSTATE:
				System.out.println("��״̬" + response);
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {
						if (type == 1) {

							Intent intent4 = new Intent(QianBaoActivity.this, MyBankCardActivity.class);
							startActivity(intent4);
						} else if (type == 2) {

							Intent intent1 = new Intent(QianBaoActivity.this, QianBao_TiXian_Activity.class);
							intent1.putExtra("YUANBAO", yuanbaoNum);
							startActivity(intent1);
						}

					} else if (state.equals("error")) {
						// ���δ�󶨵�����ʾ
						if (biz_content.equals("û�������˻���Ϣ")) {

							boundBankCard();

						} else {
							Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
						}

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case QAINBAOYUE:

				try {
					System.out.println("ʣ��Ԫ��������" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						UserInfoEntity entity = new UserInfoEntity();

						entity = JSON.parseObject(biz_content, UserInfoEntity.class);
						qianbao_YuanBao_Num.setText(entity.getMoney()+"Ԫ��");
					} else if (state.equals("error")) {
						
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			default:
				break;
			}

		}

		@Override
		public void onStart(int what) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFinish(int what) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFailed(int what, String url, Object tag, Exception exception, int responseCode,
				long networkMillis) {
			// TODO Auto-generated method stub

		}
	};

	@SuppressLint("InflateParams")
	private void boundBankCard() {

		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alertdialog_bound_tishi, null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.alertdialog_bound_tishi);
		Button queding = (Button) window.findViewById(R.id.Yqueding);

		// ���ȷ��
		queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(QianBaoActivity.this, Bound_BankCardActivity.class);
				startActivity(intent);
				dialog.cancel();
				finish();
			}
		});
		// Button quxiao = (Button) window.findViewById(R.id.Yquxiao);
		// // ���ȡ��
		// quxiao.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// dialog.cancel();
		// }
		// });

	}
}
