package com.dhxgzs.goodluck;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.MyUtil;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ע��ҳ��
 * 
 * @author Administrator
 *
 */
public class Register extends Activity implements OnClickListener {
	private static final int GET_YANZHENGMA = 0;
	private static final int REGSITER = 1;
	/** ���ذ�ť */
	private ImageView register_back;
	/** �绰���� */
	private EditText register_account;
	/** ��֤������� */
	private EditText register_YanZhengCode;
	/** ��ȡ��֤�밴ť */
	private TextView register_getcode;
	/**������*/
	private TextView register_yaoqingma;
	/** �������� */
	private EditText register_psd;
	/** ע�ᰴť */
	private TextView register;
	/** �û��ֻ��� */
	private String username;
	/** �û����� */
	private String pwd;
	/** ���ɵ���֤�� */
	private String aaa;
	private Request<JSONObject> request;
	private RequestQueue registerQueue;
	private String imei;
	/** ��ʱ�� */
	private TimeCount time;
	private String getYanzhengNum;
	private String myyaoqingma;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		time = new TimeCount(60000, 1000);
		initView();
		setListener();
	}

	private void initView() {
		registerQueue = NoHttp.newRequestQueue();
		// ����
		register_back = (ImageView) findViewById(R.id.register_back);
		// ����绰����
		register_account = (EditText) findViewById(R.id.register_account);
		// ������֤��
		register_YanZhengCode = (EditText) findViewById(R.id.register_YanZhengCode);
		// ��ȡ��֤��
		register_getcode = (TextView) findViewById(R.id.register_getcode);
		// ��������
		register_psd = (EditText) findViewById(R.id.register_psd);
		// ע�ᰴť
		register = (TextView) findViewById(R.id.register);
		//������
		register_yaoqingma=(TextView) findViewById(R.id.register_yaoqingma);

	}

	private void setListener() {

		register_back.setOnClickListener(this);
		register_getcode.setOnClickListener(this);
		register.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_back:// ���ذ�ť

			finish();
			break;
		case R.id.register_getcode:// ��ȡ��֤�밴ť
			// �õ��ֻ���
			username = register_account.getText().toString();
			imei = MyUtil.getIMEI(getApplicationContext());
			boolean judge1 = isMobile(username);
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

		case R.id.register:// ע�ᰴť
			System.out.println("С�ײ���");
			// ��ȡ�ֻ���
			username = register_account.getText().toString().trim();
			// ��ȡ�û�����
			pwd = register_psd.getText().toString().trim();
			myyaoqingma=register_yaoqingma.getText().toString().trim();
			// ��ȡ������
			getYanzhengNum = register_YanZhengCode.getText().toString().trim();
			boolean judge = isMobile(username);
			boolean pppsd = isMobilePassword(pwd);
			if (judge == true && pppsd == true && getYanzhengNum.equals(aaa)) {

				registerRequest();

			} else if (judge == false) {

				Toast.makeText(getApplicationContext(), "�����˺��Ƿ���ȷ", Toast.LENGTH_LONG).show();

			} else if (pppsd == false) {
				
				Toast.makeText(getApplicationContext(), "���������Ƿ���ȷ", Toast.LENGTH_LONG).show();
				
			} else if (!getYanzhengNum.equals(aaa)) {
				
				Toast.makeText(getApplicationContext(), "������֤���Ƿ���ȷ", Toast.LENGTH_LONG).show();
				
			}

			break;

		default:
			break;
		}

	}

	private void registerRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.REGISTER_URL, RequestMethod.POST);
		
		request.add("invitecode", myyaoqingma);
		request.add("account", username);
		request.add("password", pwd);
		request.add("imei", imei);
		registerQueue.add(REGSITER, request, responseListener);

		System.out.println("�ֻ���" + username);
		System.out.println("����" + pwd);
		System.out.println("imei��" + imei);

	}

	private void RequestYanzhengma() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.GET_YANZHENGMA_URL, RequestMethod.POST);

		request.add("account", username);
		request.add("type", "ע��Ӧ��");
		request.add("code", aaa);
		request.add("imei", imei);

		System.out.println("�ֻ���" + username);
		System.out.println("��֤��" + aaa);
		System.out.println("�ֻ�imei��" + imei);

		registerQueue.add(GET_YANZHENGMA, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {
			switch (what) {
			case GET_YANZHENGMA:// ��ȡ��֤�뷵�ؼ���
				System.out.println("��ȡ��֤��" + response);

				try {

					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						time.start();
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_LONG).show();
					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case REGSITER:
				System.out.println("ע��" + response);

				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									EMClient.getInstance().createAccount(username, "666666");
									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											Toast.makeText(getApplicationContext(), "����ע��ɹ�", Toast.LENGTH_SHORT)
													.show();
											finish();
										}
									});
								} catch (final HyphenateException e) {

									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											int errorCode = e.getErrorCode();
											if (errorCode == EMError.NETWORK_ERROR) {
												Toast.makeText(getApplicationContext(), errorCode + "�����쳣",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_ALREADY_EXIST) {
												Toast.makeText(getApplicationContext(), errorCode + "���û��Ѿ�ע��",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
												Toast.makeText(getApplicationContext(), errorCode + "ע��ʧ�ܣ�δ�����",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
												Toast.makeText(getApplicationContext(), errorCode + "�Ƿ��û���",
														Toast.LENGTH_SHORT).show();
											} else {
												Toast.makeText(getApplicationContext(), errorCode + "ע��ʧ��",
														Toast.LENGTH_SHORT).show();
											}

										}
									});
									e.printStackTrace();

								}

							}
						}).start();
						Toast.makeText(getApplicationContext(),biz_content, Toast.LENGTH_LONG).show();
					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(),biz_content, Toast.LENGTH_LONG).show();
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
			System.out.println("ʧ��" + exception);
			System.out.println("ʧ����ַ" + url);
			Toast.makeText(getApplicationContext(), "����ʱ ���Ժ�����", Toast.LENGTH_LONG).show();
		}
	};

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
			register_getcode.setText("������֤");
			register_getcode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// ��ʱ������ʾ
			register_getcode.setClickable(false);
			register_getcode.setText(millisUntilFinished / 1000 + "��");

		}
	}
}
