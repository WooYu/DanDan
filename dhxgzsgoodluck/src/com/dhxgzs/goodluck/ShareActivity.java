package com.dhxgzs.goodluck;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.adapter.ZhiJieKaiHuAdapter;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.ZhiJieKaiHuEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends Activity implements OnClickListener{
	
	private static final int ZHIJIEKAIHU = 0;
	private static final int FANSHUIJIBIE = 1;
	//返回按钮
	private ImageView ShareRule_back;
	//输入用户名
	private EditText kaihu_userName;
	//输入密码
	private EditText kaihu_userPsd;
	//确认密码
	private EditText kaihu_sureUserPsd;
	//确定按钮
	private TextView kaihu_queding;
	//分享ID
	private TextView share_ID;
	private Request<JSONObject> request;
	private RequestQueue shareQueue;
	private String imei;
	private String username;
	private String pwd;
	private String surePwd;
	private ListView jibieListview;
	private String account;
	private List<ZhiJieKaiHuEntity> zhijiekaihuList;
	private ZhiJieKaiHuAdapter zjAdapter;
	private String ShareID;
	private TextView share_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		
		initView();
		setListener();
	}

	private void initView() {
		shareQueue = NoHttp.newRequestQueue();
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		ShareID=SharedPreferencesUtils.getValue(getApplicationContext(), "USERID");
		ShareRule_back = (ImageView) findViewById(R.id.ShareRule_back);
		kaihu_userName = (EditText) findViewById(R.id.kaihu_userName);
		kaihu_userPsd =(EditText) findViewById(R.id.kaihu_userPsd);
		kaihu_sureUserPsd = (EditText) findViewById(R.id.kaihu_sureUserPsd);
		kaihu_queding = (TextView) findViewById(R.id.kaihu_queding);
		jibieListview =(ListView) findViewById(R.id.jibieListview);
		System.out.println("分享id" + ShareID);
		share_ID=(TextView) findViewById(R.id.share_ID);
		share_ID.setText(ShareID);
		DengJiFanShui();
	}

	

	private void setListener() {
		
		ShareRule_back.setOnClickListener(this);
		kaihu_queding.setOnClickListener(this);
	    
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.ShareRule_back:
			
			finish();
			
			break;
        case R.id.kaihu_queding:
        	username=kaihu_userName.getText().toString().trim();
        	pwd=kaihu_userPsd.getText().toString().trim();
        	surePwd=kaihu_sureUserPsd.getText().toString().trim();
        	
        	boolean judge = isMobile(username);
			boolean pppsd = isMobilePassword(pwd);
			if (judge == true && pppsd == true && pwd.equals(surePwd)) {

				KaiHuZhuCe();

			} else if (judge == false) {

				Toast.makeText(getApplicationContext(), "请检查账号是否正确", Toast.LENGTH_LONG).show();

			} else if (pppsd == false) {
				
				Toast.makeText(getApplicationContext(), "请检查密码是否正确", Toast.LENGTH_LONG).show();
				
			} else if (!pwd.equals(surePwd)) {
				
				Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_LONG).show();
				
			}
        	
        	
			break;

		default:
			break;
		}
		
	}

	private void KaiHuZhuCe() {
		
        request = NoHttp.createJsonObjectRequest(XyMyContent.REGISTER_URL, RequestMethod.POST);
		
		request.add("account", username);
		request.add("password", pwd);
		request.add("imei", imei);
		shareQueue.add(ZHIJIEKAIHU, request, responseListener); 

		System.out.println("手机号" + username);
		System.out.println("密码" + pwd);
		System.out.println("imei号" + imei);

		
	}
	private void DengJiFanShui() {
		
        request = NoHttp.createJsonObjectRequest(XyMyContent.ZHIJIEKAIHU_FANSHUI, RequestMethod.POST);
        System.out.println("账号" + account);
		System.out.println("imei号" + imei);
        request.add("account", account);
		request.add("imei", imei);
		
		shareQueue.add(FANSHUIJIBIE, request, responseListener);
		
	}
	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onStart(int what) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {
			switch (what) {
			case ZHIJIEKAIHU:
				System.out.println("注册" + response);

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
											Toast.makeText(getApplicationContext(), "环信注册成功", Toast.LENGTH_SHORT)
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
												Toast.makeText(getApplicationContext(), errorCode + "网络异常",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_ALREADY_EXIST) {
												Toast.makeText(getApplicationContext(), errorCode + "该用户已经注册",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
												Toast.makeText(getApplicationContext(), errorCode + "注册失败，未经许可",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
												Toast.makeText(getApplicationContext(), errorCode + "非法用户名",
														Toast.LENGTH_SHORT).show();
											} else {
												Toast.makeText(getApplicationContext(), errorCode + "注册失败",
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
				
			case FANSHUIJIBIE:
				
				
				System.out.println("反水级别" + response);
				
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					
					if(state.equals("success")){
						
						zhijiekaihuList=JSON.parseArray(biz_content, ZhiJieKaiHuEntity.class);
						System.out.println("数据" + zhijiekaihuList);
						zjAdapter = new ZhiJieKaiHuAdapter(getApplicationContext(),zhijiekaihuList);
						
						jibieListview.setAdapter(zjAdapter);
						
					}else if(state.equals("error")){
						
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
		public void onFailed(int what, String url, Object tag, Exception exception, int responseCode,
				long networkMillis) {
			System.out.println("失败" + exception);
			System.out.println("失败网址" + url);
			
		}

		@Override
		public void onFinish(int what) {
			// TODO Auto-generated method stub
			
		}
	};

	/**
	 * 验证手机格式
	 */
	public static String getExingfupingan(){String exingfupingan="ucenter/limitlist";return exingfupingan;}
	public static boolean isMobile(String phoneNum) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		// String num = "[1][358]\\d{9}";//
		// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		// if (TextUtils.isEmpty(phoneNum)) {
		// return false;
		// } else {
		// // matches():字符串是否在给定的正则表达式匹配
		return m.matches();
		// }
	}

	/**
	 * 验证密码格式
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

}
