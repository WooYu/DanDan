package com.dhxgzs.goodluck;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.ShangHuEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.view.LimitEditText;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 银行卡转账
 * 
 * @author Administrator
 *
 */
public class BankActivity extends Activity implements OnClickListener {
	private static final int SHANGHUXINXI = 0;
	private static final int BANKCARDCHARGE = 1;
	/** 返回按钮 */
	private ImageView bank_zhuanzhang_back;
	/** 银行名称 */
	private TextView bank_zhuanzhang_bankName;
	/** 收款人 */
	private TextView bank_zhuanzhang_shoukuanPerson;
	/** 收款人复制按钮 */
	private ImageView bank_zhuanzhang_fuzhi_shoukuanPerson;
	/** 收款人账号 */
	private TextView bank_zhuanzhang_AccountBank;
	/** 收款人账号复制按钮 */
	private ImageView bank_zhuanzhang_fuzhi_AccountBank;
	/** 收款人开户银行 */
	private TextView bank_zhuanzhang_KaiHuBank;
	/** 收款人开户银行复制按钮 */
	private ImageView bank_zhuanzhang_fuzhi_KaiHuBank;
	/** 汇款银行 */
	private LimitEditText bank_zhuanzhang_inputBank;
	/** 汇款银行卡户名 */
	private LimitEditText bank_zhuanzhang_remitter;
	/** 汇款银行账号 */
	private EditText bank_zhuanzhang_bankNum;
	/** 汇款金额 */
	private EditText bank_zhuanzhang_remitMoney;
	/** 银行转账提交 */
	private TextView bank_zhuanzhang_tijiao;
	private Request<JSONObject> request;
	private RequestQueue bankcardQueue;
	private String account;
	private String imei;
	private String name;
	private String number;
	private String money;
	private String bankname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank);

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

		bankcardQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		/** 商户信息 */
		shanghuInfoRequest();
		bank_zhuanzhang_back = (ImageView) findViewById(R.id.bank_zhuanzhang_back);
		bank_zhuanzhang_bankName = (TextView) findViewById(R.id.bank_zhuanzhang_bankName);
		bank_zhuanzhang_shoukuanPerson = (TextView) findViewById(R.id.bank_zhuanzhang_shoukuanPerson);
		bank_zhuanzhang_fuzhi_shoukuanPerson = (ImageView) findViewById(R.id.bank_zhuanzhang_fuzhi_shoukuanPerson);
		bank_zhuanzhang_AccountBank = (TextView) findViewById(R.id.bank_zhuanzhang_AccountBank);
		bank_zhuanzhang_fuzhi_AccountBank = (ImageView) findViewById(R.id.bank_zhuanzhang_fuzhi_AccountBank);
		bank_zhuanzhang_KaiHuBank = (TextView) findViewById(R.id.bank_zhuanzhang_KaiHuBank);
		bank_zhuanzhang_fuzhi_KaiHuBank = (ImageView) findViewById(R.id.bank_zhuanzhang_fuzhi_KaiHuBank);
		bank_zhuanzhang_inputBank = (LimitEditText) findViewById(R.id.bank_zhuanzhang_inputBank);
		bank_zhuanzhang_remitter = (LimitEditText) findViewById(R.id.bank_zhuanzhang_remitter);
		bank_zhuanzhang_bankNum = (EditText) findViewById(R.id.bank_zhuanzhang_bankNum);
		bank_zhuanzhang_remitMoney = (EditText) findViewById(R.id.bank_zhuanzhang_remitMoney);
		bank_zhuanzhang_tijiao = (TextView) findViewById(R.id.bank_zhuanzhang_tijiao);

	}

	private void shanghuInfoRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.MERCHANTS_URL, RequestMethod.POST);
		request.add("account", account);
		request.add("imei", imei);
		request.add("type", "银行卡");

		bankcardQueue.add(SHANGHUXINXI, request, responseListener);

	}

	private void setListener() {

		bank_zhuanzhang_back.setOnClickListener(this);
		bank_zhuanzhang_fuzhi_shoukuanPerson.setOnClickListener(this);
		bank_zhuanzhang_fuzhi_AccountBank.setOnClickListener(this);
		bank_zhuanzhang_fuzhi_KaiHuBank.setOnClickListener(this);
		bank_zhuanzhang_tijiao.setOnClickListener(this);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bank_zhuanzhang_back:

			finish();

			break;
		case R.id.bank_zhuanzhang_fuzhi_shoukuanPerson:// 复制收款人名字

			if (MyUtil.getSDKVersionNumber() >= 11) {
				android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(bank_zhuanzhang_shoukuanPerson.getText().toString());
			} else {
				// 得到剪贴板管理器
				android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setPrimaryClip(
						ClipData.newPlainText(null, bank_zhuanzhang_shoukuanPerson.getText().toString()));
			}
			Toast.makeText(this, "复制到剪切板", Toast.LENGTH_SHORT).show();

			break;
		case R.id.bank_zhuanzhang_fuzhi_AccountBank:// 复制收款人账号

			if (MyUtil.getSDKVersionNumber() >= 11) {
				android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(bank_zhuanzhang_AccountBank.getText().toString());
			} else {
				// 得到剪贴板管理器
				android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager
						.setPrimaryClip(ClipData.newPlainText(null, bank_zhuanzhang_AccountBank.getText().toString()));
			}
			Toast.makeText(this, "复制到剪切板", Toast.LENGTH_SHORT).show();

			break;
		case R.id.bank_zhuanzhang_fuzhi_KaiHuBank:// 复制开户行

			if (MyUtil.getSDKVersionNumber() >= 11) {
				android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(bank_zhuanzhang_KaiHuBank.getText().toString());
			} else {
				// 得到剪贴板管理器
				android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager
						.setPrimaryClip(ClipData.newPlainText(null, bank_zhuanzhang_KaiHuBank.getText().toString()));
			}
			Toast.makeText(this, "复制到剪切板", Toast.LENGTH_SHORT).show();

			break;
		case R.id.bank_zhuanzhang_tijiao:

			bankname = bank_zhuanzhang_inputBank.getText().toString();
			name = bank_zhuanzhang_remitter.getText().toString();
			number = bank_zhuanzhang_bankNum.getText().toString();
			money = bank_zhuanzhang_remitMoney.getText().toString();

			if (!name.isEmpty() && !number.isEmpty() && !money.isEmpty() && !bankname.isEmpty()) {

				bankCardRechargeRequest();

			} else {
				Toast.makeText(getApplicationContext(), "信息填写不完整", Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}

	}

	private void bankCardRechargeRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.OFFLINERECHARGE_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("type", "微信");
		request.add("name", name);
		request.add("number", number);
		request.add("money", money);
		request.add("bankname", "");
		request.add("imei", imei);

		bankcardQueue.add(BANKCARDCHARGE, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case SHANGHUXINXI:
				try {
					System.out.println("银行卡商户信息" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						ShangHuEntity entity = new ShangHuEntity();

						entity = JSON.parseObject(biz_content, ShangHuEntity.class);

						bank_zhuanzhang_bankName.setText(entity.getBank());
						bank_zhuanzhang_shoukuanPerson.setText(entity.getName());
						bank_zhuanzhang_AccountBank.setText(entity.getAccount());
						bank_zhuanzhang_KaiHuBank.setText(entity.getBankaddress());

					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case BANKCARDCHARGE:

				try {
					System.out.println("银行卡提交充值状态信息" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

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

}
