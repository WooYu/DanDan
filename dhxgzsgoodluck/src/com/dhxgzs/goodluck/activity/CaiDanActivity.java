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
 * 首页菜单页
 * 
 * @author Administrator
 *
 */
public class CaiDanActivity extends Activity implements OnClickListener {

	private static final int CAIDANUSERINFO = 0;
	private static final int CAIDANYUE = 1;
	private static final int PANDUANBOUNDBANKCARD = 3;
	/** 竖着的menu图标 */
	private ImageView shucaidan;
	/** 菜单页用户头像 */
	private CircleImageView menu_Photo;
	/** 菜单页用户名 */
	private TextView menu_username;
	/** 充值按钮 */
	private TextView menu_Recharge;
	/** 体现按钮 */
	private TextView menu_tixian;
	/** 余额数 */
	private TextView yue_num;
	/** 我的钱包 */
	private LinearLayout menu_myqianbao;
	/** 我的消息 */
	private LinearLayout menu_myMsg;
	/** 向上箭头 */
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

	/** 初始化控件 */
	private void initView() {

		cadanQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
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
						// 加载网络头像
						Glide.with(getApplicationContext()).load(infoEntity.getAvatar()).asBitmap()
								.error(R.drawable.touxiang).into(menu_Photo);
						infoEntity.getNickname();
						menu_username.setText(infoEntity.getNickname());

					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(), "用户信息获取失败", Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case CAIDANYUE:
				
				try {
					System.out.println("剩余元宝数数据" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						UserInfoEntity entity = new UserInfoEntity();

						entity = JSON.parseObject(biz_content, UserInfoEntity.class);
						yue_num.setText(entity.getMoney()+"元宝");
					} else if (state.equals("error")) {
						
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case PANDUANBOUNDBANKCARD:
				System.out.println("绑定状态" + response);
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {
						
						Intent intent0 = new Intent(CaiDanActivity.this, QianBao_TiXian_Activity.class);
						startActivity(intent0);

					} else if (state.equals("error")) {
						// 如果未绑定弹窗提示
						if (biz_content.equals("没有提现账户信息")) {

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

	/** 监听方法 */
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

		// 点击确定
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
		// 点击竖着的菜单图标结束Activity
		case R.id.shucaidan:
			finish();
			break;
		// 点击向上箭头结束Activity
		case R.id.menu_upBtn:
			finish();
			break;
		// 充值
		case R.id.menu_Recharge:

			Toast.makeText(getApplicationContext(), "充值", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(CaiDanActivity.this, QianBaoRechargeActivity.class);
			startActivity(intent);
			finish();
			break;
		// 提现
		case R.id.menu_tixian:
			Toast.makeText(getApplicationContext(), "提现", Toast.LENGTH_SHORT).show();
//			Intent intent0 = new Intent(CaiDanActivity.this, QianBao_TiXian_Activity.class);
//			startActivity(intent0);
			panduanBoundBankCard();
			
			break;
		// 我的钱包
		case R.id.menu_myqianbao:
			Toast.makeText(getApplicationContext(), "我的钱包", Toast.LENGTH_SHORT).show();
			Intent intent1 = new Intent(CaiDanActivity.this, QianBaoActivity.class);
			startActivity(intent1);
			finish();
			break;
		// 我的消息
		case R.id.menu_myMsg:
			Toast.makeText(getApplicationContext(), "我的消息", Toast.LENGTH_SHORT).show();
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

	/** 销毁activity时的动画方法 */
	@Override
	public void finish() {
		super.finish();
		// activity退出时动画方法
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
}
