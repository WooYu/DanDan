package com.dhxgzs.goodluck.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.dhxgzs.goodluck.adapter.BoundBankCardAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.BankListEntity;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

/**
 * �����п�ҳ��
 * 
 * @author Administrator
 *
 */
public class Bound_BankCardActivity extends Activity implements OnClickListener {

	private static final int BANKLIST = 0;
	private static final int BOUNDBANK = 1;
	/** ���ذ�ť */
	private ImageView bound_back;
	/** �������� */
	private LimitEditText bound_kaihu_name;
	/** �������� */
	private TextView bound_bankType;
	/** ���п��� */
	private EditText bound_bankCard_Num;
	/** ������ַ */
	private LimitEditText bound_kaihu_Address;
	/** �������� */
	private EditText bound_tixian_psd;
	/** ȷ�ϰ�ť */
	private TextView bound_sure;
	private PopupWindow popup;
	private ListView chooseBankCard_Listview;
	private Request<JSONObject> request;
	private RequestQueue boundQueue;
	private String account;
	private String imei;
	private List<BankListEntity> bankList;
	private BoundBankCardAdapter bankListAdapter;
	private String username;
	private String cardname;
	private String cardnum;
	private String cardaddress;
	private String pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bound__bank_card);

		initView();
		setListenr();
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
		boundQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());
		// ���ذ�ť
		bound_back = (ImageView) findViewById(R.id.bound_back);
		// ��������
		bound_kaihu_name = (LimitEditText) findViewById(R.id.bound_kaihu_name);
		// ��������
		bound_bankType = (TextView) findViewById(R.id.bound_bankType);
		// ���п���
		bound_bankCard_Num = (EditText) findViewById(R.id.bound_bankCard_Num);
		// ������ַ
		bound_kaihu_Address = (LimitEditText) findViewById(R.id.bound_kaihu_Address);
		// ��������
		bound_tixian_psd = (EditText) findViewById(R.id.bound_tixian_psd);
		// ȷ�ϰ�ť
		bound_sure = (TextView) findViewById(R.id.bound_sure);

	}

	private void setListenr() {
		bound_back.setOnClickListener(this);
		bound_bankType.setOnClickListener(this);
		bound_sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.bound_back:// ���ذ�ť

			finish();

			break;

		case R.id.bound_bankType:// ѡ�����п�����

			bankPopupWindow();

			break;

		case R.id.bound_sure:// ȷ����ť
			username = bound_kaihu_name.getText().toString();
			cardname = bound_bankType.getText().toString();
			cardnum = bound_bankCard_Num.getText().toString();
			cardaddress = bound_kaihu_Address.getText().toString();
			pwd = bound_tixian_psd.getText().toString();
			if (!username.isEmpty() && !cardname.isEmpty() && !cardnum.isEmpty() && !cardaddress.isEmpty() && !pwd.isEmpty()
					&& !account.isEmpty() && !imei.isEmpty()) {

				boundBankCardRequest();
			} else {
				Toast.makeText(getApplicationContext(), "������δ��д����Ϣ", Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}

	}

	private void boundBankCardRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.BOUNDBANK_CARD_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("username", username);
		request.add("cardname", cardname);
		request.add("cardnum", cardnum);
		request.add("cardaddress", cardaddress);
		request.add("pwd", pwd);
		request.add("imei", imei);

		boundQueue.add(BOUNDBANK, request, responseListener);

	}

	@SuppressLint("InflateParams")
	private void bankPopupWindow() {
		View v = this.getLayoutInflater().inflate(R.layout.bank_popupwindow, null);
		popup = new PopupWindow(v, 500, 500, true);
		chooseBankCard_Listview = (ListView) v.findViewById(R.id.chooseBankCard_Listview);
		bankListRequest();

		// ���popupwindow Item ʱ ����ѡ�����������
		chooseBankCard_Listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				bound_bankType.setText(bankList.get(position).getType());

				popup.dismiss();

			}
		});
		popup.setFocusable(true);
		// ����������Ϊtrue�����ڵ����Ļ�Ŀհ�λ��Ҳ���˳�
		popup.setTouchable(true);
		popup.setFocusable(true);
		popup.setBackgroundDrawable(new BitmapDrawable());
		popup.setOutsideTouchable(true);
		popup.showAsDropDown(bound_bankType);

	}

	private void bankListRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.BANK_LIST_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		boundQueue.add(BANKLIST, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {
			switch (what) {
			case BANKLIST:
				try {
					System.out.println("���п��б�" + response);
					String state = response.get().getString("state");
					if (state.equals("success")) {

						String biz_content = response.get().getString("biz_content");

						bankList = JSON.parseArray(biz_content, BankListEntity.class);
						bankListAdapter = new BoundBankCardAdapter(getApplicationContext(), bankList);

						chooseBankCard_Listview.setAdapter(bankListAdapter);

					} else if (state.equals("error")) {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case BOUNDBANK:

				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "�����п����أ�"+biz_content);
					if (state.equals("success")) {
						
						SharedPreferencesUtils.putValue(getApplicationContext(), "BANK_CARD_NUM", cardnum);
						finish();
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					} else if (state.equals("error")) {				 
						   if(biz_content.indexOf("��������")!= -1){
								//δ������������or �����������
								Intent intent = new Intent(Bound_BankCardActivity.this, TiXianPassWord.class);
								startActivity(intent);							 
							} 
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
