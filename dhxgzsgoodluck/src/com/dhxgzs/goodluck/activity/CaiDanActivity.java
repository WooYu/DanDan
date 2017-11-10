package com.dhxgzs.goodluck.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dhxgzs.goodluck.MineActivity;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.ShouYeActivity;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.UserInfoEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.view.CircleImageView;
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
 * ��ҳ�˵�ҳ
 * 
 * @author Administrator
 *
 */
public class CaiDanActivity extends Activity implements OnClickListener {

	private static final int CAIDANUSERINFO = 0;
	private static final int CAIDANYUE = 1;
	private static final int PANDUANBOUNDBANKCARD = 3;
	/** ���ŵ�menuͼ�� */
	private ImageView shucaidan;
	/** �˵�ҳ�û�ͷ�� */
	private CircleImageView menu_Photo;
	/** �˵�ҳ�û��� */
	private TextView menu_username;
	/** ��ֵ��ť */
	private TextView menu_Recharge;
	/** ���ְ�ť */
	private TextView menu_tixian;
	/** ����� */
	private TextView yue_num;
	/** �ҵ�Ǯ�� */
	private LinearLayout menu_myqianbao;
	/** �ҵ���Ϣ */
	private LinearLayout menu_myMsg;
	/** ���ϼ�ͷ */
	private ImageView menu_upBtn;
	private Request<JSONObject> request;
	private RequestQueue cadanQueue;
	private String account;
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cai_dan);

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

	/** ��ʼ���ؼ� */
	private void initView() {

		cadanQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());
		caiDanYueRequest();
		UpDataUserInfoRequest();

		shucaidan = (ImageView) findViewById(R.id.shucaidan);
		menu_Photo = (CircleImageView) findViewById(R.id.menu_Photo);
		menu_username = (TextView) findViewById(R.id.menu_username);
		menu_Recharge = (TextView) findViewById(R.id.menu_Recharge);
		menu_tixian = (TextView) findViewById(R.id.menu_tixian);
		yue_num = (TextView) findViewById(R.id.yue_num);
		menu_myqianbao = (LinearLayout) findViewById(R.id.menu_myqianbao);
		menu_myMsg = (LinearLayout) findViewById(R.id.menu_myMsg);
		menu_upBtn = (ImageView) findViewById(R.id.menu_upBtn);
	}

	private void caiDanYueRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.ACCOUNTMONEY_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		cadanQueue.add(CAIDANYUE, request, responseListener);

	}

	private void UpDataUserInfoRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.UPDATAUSERINFO_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		cadanQueue.add(CAIDANUSERINFO, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case CAIDANUSERINFO:
				try {

					String state = response.get().getString("state");

					if (state.equals("success")) {

						String biz_content = response.get().getString("biz_content");
						UserInfoEntity infoEntity = new UserInfoEntity();
						infoEntity = JSON.parseObject(biz_content, UserInfoEntity.class);
						// ��������ͷ��
						Glide.with(getApplicationContext()).load(infoEntity.getAvatar()).asBitmap()
								.error(R.drawable.touxiang).into(menu_Photo);
						infoEntity.getNickname();
						menu_username.setText(infoEntity.getNickname());

					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(), "�û���Ϣ��ȡʧ��", Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case CAIDANYUE:
				
				try {
					System.out.println("ʣ��Ԫ��������" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						UserInfoEntity entity = new UserInfoEntity();

						entity = JSON.parseObject(biz_content, UserInfoEntity.class);
						yue_num.setText(entity.getMoney()+"Ԫ��");
					} else if (state.equals("error")) {
						
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case PANDUANBOUNDBANKCARD:
				System.out.println("��״̬" + response);
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {
						
						Intent intent0 = new Intent(CaiDanActivity.this, QianBao_TiXian_Activity.class);
						startActivity(intent0);

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

	/** �������� */
	private void setListener() {

		shucaidan.setOnClickListener(this);
		menu_Recharge.setOnClickListener(this);
		menu_tixian.setOnClickListener(this);
		menu_myqianbao.setOnClickListener(this);
		menu_myMsg.setOnClickListener(this);
		menu_upBtn.setOnClickListener(this);

	}

	@SuppressLint("InflateParams")
	protected void boundBankCard() {
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
                
				Intent intent = new Intent(CaiDanActivity.this, Bound_BankCardActivity.class);
				startActivity(intent);
				dialog.cancel();
//				finish();
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// ������ŵĲ˵�ͼ�����Activity
		case R.id.shucaidan:
			finish();
			break;
		// ������ϼ�ͷ����Activity
		case R.id.menu_upBtn:
			finish();
			break;
		// ��ֵ
		case R.id.menu_Recharge:

			Toast.makeText(getApplicationContext(), "��ֵ", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(CaiDanActivity.this, QianBaoRechargeActivity.class);
			startActivity(intent);
			finish();
			break;
		// ����
		case R.id.menu_tixian:
			Toast.makeText(getApplicationContext(), "����", Toast.LENGTH_SHORT).show();
//			Intent intent0 = new Intent(CaiDanActivity.this, QianBao_TiXian_Activity.class);
//			startActivity(intent0);
			panduanBoundBankCard();
			
			break;
		// �ҵ�Ǯ��
		case R.id.menu_myqianbao:
			Toast.makeText(getApplicationContext(), "�ҵ�Ǯ��", Toast.LENGTH_SHORT).show();
			Intent intent1 = new Intent(CaiDanActivity.this, QianBaoActivity.class);
			startActivity(intent1);
			finish();
			break;
		// �ҵ���Ϣ
		case R.id.menu_myMsg:
			Toast.makeText(getApplicationContext(), "�ҵ���Ϣ", Toast.LENGTH_SHORT).show();
			finish();
			break;
		default:
			break;
		}

	}

	private void panduanBoundBankCard() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.MYBANKCARD_INFO_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		cadanQueue.add(PANDUANBOUNDBANKCARD, request, responseListener);
		
	}

	/** ����activityʱ�Ķ������� */
	@Override
	public void finish() {
		super.finish();
		// activity�˳�ʱ��������
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
}
