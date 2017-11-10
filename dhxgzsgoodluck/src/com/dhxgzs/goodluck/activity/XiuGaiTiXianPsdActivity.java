package com.dhxgzs.goodluck.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Administrator
 *
 */
public class XiuGaiTiXianPsdActivity extends Activity implements OnClickListener {
	private static final int XIUGAITIXIANMIMA = 0;
	/** ���ذ�ť */
	private ImageView xiugaiTiXian_back;
	/** ���������� */
	private EditText xiugaiTiXian_oldPsd;
	/** ���������� */
	private EditText xiugaiTiXian_newPsd;
	/** ����ȷ������ */
	private EditText xiugaiTiXian_surePsd;
	/** ȷ���޸İ�ť */
	private TextView xiugaiTiXian_sureXiugai;
	private String account;
	private String imei;
	private String oldPsd;
	private Request<JSONObject> request;
	private RequestQueue xiugaiTixianQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiu_gai_ti_xian_psd);

		setinitView();
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

	private void setinitView() {
		xiugaiTixianQueue = NoHttp.newRequestQueue();
		oldPsd = SharedPreferencesUtils.getValue(getApplicationContext(), "oldPsd");
		System.out.println("�����룺" + oldPsd);
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());
		xiugaiTiXian_back = (ImageView) findViewById(R.id.xiugaiTiXian_back);
		xiugaiTiXian_oldPsd = (EditText) findViewById(R.id.xiugaiTiXian_oldPsd);
		xiugaiTiXian_newPsd = (EditText) findViewById(R.id.xiugaiTiXian_newPsd);
		xiugaiTiXian_surePsd = (EditText) findViewById(R.id.xiugaiTiXian_surePsd);
		xiugaiTiXian_sureXiugai = (TextView) findViewById(R.id.xiugaiTiXian_sureXiugai);
	}

	private void setListener() {

		xiugaiTiXian_back.setOnClickListener(this);
		xiugaiTiXian_sureXiugai.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.xiugaiTiXian_back:

			finish();

			break;
		case R.id.xiugaiTiXian_sureXiugai:
			// �������ȷ��������ͬ��������������������������ͬ ������������ͬʱ���������޸�
			if (xiugaiTiXian_newPsd.getText().toString().equals(xiugaiTiXian_surePsd.getText().toString())) {

				xiugaiTiXianRequest();

			} else if (!xiugaiTiXian_newPsd.getText().toString().equals(xiugaiTiXian_surePsd.getText().toString())) {
				
				Toast.makeText(getApplicationContext(), "������������벻һ��", Toast.LENGTH_SHORT).show();
				
			}

			break;

		default:
			break;
		}

	}

	private void xiugaiTiXianRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.TIXIAN_PSD_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("password", xiugaiTiXian_newPsd.getText().toString());
		request.add("oldpwd", xiugaiTiXian_oldPsd.getText().toString());
		request.add("imei", imei);

		xiugaiTixianQueue.add(XIUGAITIXIANMIMA, request, responseListener);
	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			try {
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");
				if (state.equals("success")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "oldPsd",
							xiugaiTiXian_oldPsd.getText().toString());
					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					finish();
				} else if (state.equals("error")) {

					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
