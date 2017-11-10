package com.dhxgzs.goodluck.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.dhxgzs.goodluck.app.XyMyContent;
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
import android.widget.Toast;

/**
 * 修改个人资料
 * 
 * @author Administrator
 *
 */
public class PersonDataActivity extends Activity implements OnClickListener {

	private static final int XIUGAINICKNAME = 0;
	/** 返回按钮 */
	private ImageView person_data_back;
	/** 账号 */
	private TextView Person_data_userId;
	/** 昵称输入框 */
	private LimitEditText Person_Data_edit_NiCheng;
	/** 个性签名输入框 */
	private EditText Person_data_edit_GeQian;
	/** 确定 */
	private TextView Person_data_sure;
	/** 获取当前用户账号 */
	private String account;
	private Request<JSONObject> request;
	private RequestQueue xiugaiQueue;
	/** 昵称 */
	private String nickName;
	/** 个性签名 */
	private String signname;
	/** 手机imei号 */
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_data);

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
		xiugaiQueue = NoHttp.newRequestQueue();
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		person_data_back = (ImageView) findViewById(R.id.person_data_back);
		Person_data_userId = (TextView) findViewById(R.id.Person_data_userId);
		Person_Data_edit_NiCheng = (LimitEditText) findViewById(R.id.Person_Data_edit_NiCheng);
		Person_data_edit_GeQian = (EditText) findViewById(R.id.Person_data_edit_GeQian);
		Person_data_sure = (TextView) findViewById(R.id.Person_data_sure);

		Person_data_userId.setText(account);
		imei = MyUtil.getIMEI(getApplicationContext());

	}

	private void setListener() {

		person_data_back.setOnClickListener(this);
		Person_data_sure.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_data_back:

			finish();
			break;
		case R.id.Person_data_sure:// 确定修改个人资料
			nickName = Person_Data_edit_NiCheng.getText().toString().trim();
			signname = Person_data_edit_GeQian.getText().toString().trim();
			XiuGaiNickNameRequest();
//			Toast.makeText(getApplicationContext(), "点击了确定", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}

	/**修改个人资料接口*/
	private void XiuGaiNickNameRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.XIUGAINICKNAME_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("nickname", nickName);
		request.add("signname", signname);
		request.add("imei", imei);

		xiugaiQueue.add(XIUGAINICKNAME, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			System.out.println("修改个人资料数据" + response);

			try {
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
