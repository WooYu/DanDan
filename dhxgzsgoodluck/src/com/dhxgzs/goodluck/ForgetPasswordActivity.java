package com.dhxgzs.goodluck;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.Register.TimeCount;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.MyUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��������ҳ
 * 
 * @author Administrator
 *
 */
public class ForgetPasswordActivity extends Activity implements OnClickListener {
	private static final int FORGETPASSWORD = 0;
	private static final int RESETPASSWORD = 1;
	/** ���ذ�ť */
	private ImageView forgetPsd_back;
	/** �绰���� */
	private EditText forgetPsd_account;
	/** ��֤������� */
	private EditText forgetPsd_YanZhengCode;
	/** ��ȡ��֤�밴ť */
	private TextView forgetPsd_getcode;
	/** ���������� */
	private EditText forgetPsd_psd;
	/** �������� */
	private TextView forgetPsd;
	/** ��ʱ�� */
	private TimeCount time;
	/** �ֻ��� */
	private String account;
	/** ȷ������ */
	private String password;
	/** ��֤�� */
	private String aaa;
	/** imei�� */
	private String imei;
	private Request<JSONObject> request;
	private RequestQueue ForgetPsdQueue;
	private String getYanzhengNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		time = new TimeCount(60000, 1000);
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
		ForgetPsdQueue = NoHttp.newRequestQueue();
		// ����
		forgetPsd_back = (ImageView) findViewById(R.id.forgetPsd_back);
		// ����绰����
		forgetPsd_account = (EditText) findViewById(R.id.forgetPsd_account);
		// ������֤��
		forgetPsd_YanZhengCode = (EditText) findViewById(R.id.forgetPsd_YanZhengCode);
		// ��ȡ��֤��
		forgetPsd_getcode = (TextView) findViewById(R.id.forgetPsd_getcode);
		// ����������
		forgetPsd_psd = (EditText) findViewById(R.id.forgetPsd_psd);
		// ��������
		forgetPsd = (TextView) findViewById(R.id.forgetPsd);

	}

	private void setListener() {

		forgetPsd_back.setOnClickListener(this);
		forgetPsd_getcode.setOnClickListener(this);
		forgetPsd.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.forgetPsd_back:// ���ذ�ť

			finish();
			break;
		case R.id.forgetPsd_getcode:// ��ȡ��֤�밴ť
			account = forgetPsd_account.getText().toString().trim();
			password = forgetPsd_psd.getText().toString().trim();
			imei = MyUtil.getIMEI(getApplicationContext());
			boolean judge1 = isMobile(account);
			if (judge1 == true) {
				// �ֻ��Ÿ�ʽ������ȷ������֤��
				randomYanzhengma();
				System.out.println("aaa" + aaa);
				// ���Ͷ���
				RequestYanzhengma();

			} else {
				Toast.makeText(getApplicationContext(), "�����ֻ��Ÿ�ʽ�Ƿ���ȷ", Toast.LENGTH_LONG).show();
			}

			break;

		case R.id.forgetPsd:// ��������

			imei = MyUtil.getIMEI(getApplicationContext());
			// ��ȡ�ֻ���
			account = forgetPsd_account.getText().toString().trim();
			// ��ȡ�û�����
			password = forgetPsd_psd.getText().toString().trim();
			// ��ȡ������
			getYanzhengNum = forgetPsd_YanZhengCode.getText().toString().trim();
			boolean judge = isMobile(account);
			boolean pppsd = isMobilePassword(password);
			
			
			if (judge == true && pppsd == true && getYanzhengNum.equals(aaa)) {

				// ��������
				resetRequest();

			} else {

				Toast.makeText(getApplicationContext(), "�����˺������Ƿ���ȷ", Toast.LENGTH_LONG).show();

			}

			break;

		default:
			break;
		}

	}

	/** �������� */
	private void resetRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.FORGETPASSWORD_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("password", password);
		request.add("imei", imei);
		request.add("code", getYanzhengNum);

		System.out.println("1�ֻ���" + account);
		System.out.println("1��֤��" + getYanzhengNum);
		System.out.println("1�ֻ�imei��" + imei);
		System.out.println("1password" + password);
		ForgetPsdQueue.add(RESETPASSWORD, request, responseListener);

	}

	/** ������֤�� */
	private void RequestYanzhengma() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.GET_YANZHENGMA_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("type", "�һ�����");
		request.add("code", aaa);
		request.add("imei", imei);

		System.out.println("�ֻ���" + account);
		System.out.println("��֤��" + aaa);
		System.out.println("�ֻ�imei��" + imei);

		ForgetPsdQueue.add(FORGETPASSWORD, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case FORGETPASSWORD://������֤��
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						//���ͳɹ���ʼ��ʱ
						time.start();
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case RESETPASSWORD://��������

				
				try {
					System.out.println("��������"+response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
                        finish();
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
	/**������֤�뷢�͸���̨*/
	private void randomYanzhengma() {

		Random random = new Random();
		aaa = Integer.toString(random.nextInt(9999) + random.nextInt(999));

	}

	/**
	 * ��֤�ֻ���ʽ
	 */
	public static boolean isMobile(String phoneNum) {
		/*
		 * �ƶ���134��135��136��137��138��139��150��151��157(TD)��158��159��187��188
		 * ��ͨ��130��131��132��152��155��156��185��186 ���ţ�133��153��180��189����1349��ͨ��
		 * �ܽ��������ǵ�һλ�ض�Ϊ1���ڶ�λ�ض�Ϊ3��5��8������λ�õĿ���Ϊ0-9
		 */
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		// String num = "[1][358]\\d{9}";//
		// "[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��
		// if (TextUtils.isEmpty(phoneNum)) {
		// return false;
		// } else {
		// // matches():�ַ����Ƿ��ڸ�����������ʽƥ��
		return m.matches();
		// }
	}

	/**
	 * ��֤�����ʽ
	 */
	public static boolean isMobilePassword(String userPassword) {
		if (userPassword.isEmpty()) {
			return false;
		} else {
			Pattern psd = Pattern.compile("((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,20}$");
			Matcher mpsd = psd.matcher(userPassword);
			return mpsd.matches();
		}
	}

	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// ��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}

		@Override
		public void onFinish() {// ��ʱ���ʱ����
			forgetPsd_getcode.setText("������֤");
			forgetPsd_getcode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// ��ʱ������ʾ
			forgetPsd_getcode.setClickable(false);
			forgetPsd_getcode.setText(millisUntilFinished / 1000 + "��");

		}
	}
}
