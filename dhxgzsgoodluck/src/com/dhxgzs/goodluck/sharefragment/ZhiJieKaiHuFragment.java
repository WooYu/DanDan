package com.dhxgzs.goodluck.sharefragment;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ZhiJieKaiHuFragment extends Fragment implements OnClickListener{

	private View view;
	private static final int ZHIJIEKAIHU = 0;
	private static final int FANSHUIJIBIE = 1;
	//���ذ�ť
	private ImageView ShareRule_back;
	//�����û���
	private EditText kaihu_userName;
	//��������
	private EditText kaihu_userPsd;
	//ȷ������
	private EditText kaihu_sureUserPsd;
	//ȷ����ť
	private TextView kaihu_queding;
	//����ID
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
//	private TextView share_id;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_zhijiekaihu, null);

		initView();
		setListener();
		
		return view;
	}
	private void initView() {
		shareQueue = NoHttp.newRequestQueue();
		// imei��
		imei = MyUtil.getIMEI(getActivity());
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getActivity(), "phoneNum");
		ShareID=SharedPreferencesUtils.getValue(getActivity(), "USERID");
		ShareRule_back = (ImageView)view.findViewById(R.id.ShareRule_back);
		kaihu_userName = (EditText) view.findViewById(R.id.kaihu_userName);
		kaihu_userPsd =(EditText) view.findViewById(R.id.kaihu_userPsd);
		kaihu_sureUserPsd = (EditText) view.findViewById(R.id.kaihu_sureUserPsd);
		kaihu_queding = (TextView) view.findViewById(R.id.kaihu_queding);
		jibieListview =(ListView) view.findViewById(R.id.jibieListview);
//		System.out.println("����id" + ShareID);
//		share_ID=(TextView) view.findViewById(R.id.share_ID);
//		share_ID.setText(ShareID);
		DengJiFanShui();
	}

	

	private void setListener() {
		
		kaihu_queding.setOnClickListener(this);
	    
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
        case R.id.kaihu_queding:
        	username=kaihu_userName.getText().toString().trim();
        	pwd=kaihu_userPsd.getText().toString().trim();
        	surePwd=kaihu_sureUserPsd.getText().toString().trim();
        	
        	boolean judge = isMobile(username);
			boolean pppsd = isMobilePassword(pwd);
			if (judge == true && pppsd == true && pwd.equals(surePwd)) {

				KaiHuZhuCe();

			} else if (judge == false) {

				Toast.makeText(getActivity(), "�����˺��Ƿ���ȷ", Toast.LENGTH_LONG).show();

			} else if (pppsd == false) {
				
				Toast.makeText(getActivity(), "���������Ƿ���ȷ", Toast.LENGTH_LONG).show();
				
			} else if (!pwd.equals(surePwd)) {
				
				Toast.makeText(getActivity(), "�����������벻һ��", Toast.LENGTH_LONG).show();
				
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
		request.add("invitecode", ShareID);
		shareQueue.add(ZHIJIEKAIHU, request, responseListener); 

		System.out.println("�ֻ���" + username);
		System.out.println("����" + pwd);
		System.out.println("imei��" + imei);

		
	}
	private void DengJiFanShui() {
		
        request = NoHttp.createJsonObjectRequest(XyMyContent.ZHIJIEKAIHU_FANSHUI, RequestMethod.POST);
        System.out.println("�˺�" + account);
		System.out.println("imei��" + imei);
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
									getActivity().runOnUiThread(new Runnable() {

										@Override
										public void run() {
											Toast.makeText(getActivity(), "����ע��ɹ�", Toast.LENGTH_SHORT)
													.show();
											
										}
									});
								} catch (final HyphenateException e) {

									getActivity().runOnUiThread(new Runnable() {

										@Override
										public void run() {
											int errorCode = e.getErrorCode();
											if (errorCode == EMError.NETWORK_ERROR) {
												Toast.makeText(getActivity(), errorCode + "�����쳣",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_ALREADY_EXIST) {
												Toast.makeText(getActivity(), errorCode + "���û��Ѿ�ע��",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
												Toast.makeText(getActivity(), errorCode + "ע��ʧ�ܣ�δ�����",
														Toast.LENGTH_SHORT).show();
											} else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
												Toast.makeText(getActivity(), errorCode + "�Ƿ��û���",
														Toast.LENGTH_SHORT).show();
											} else {
												Toast.makeText(getActivity(), errorCode + "ע��ʧ��",
														Toast.LENGTH_SHORT).show();
											}

										}
									});
									e.printStackTrace();

								}

							}
						}).start();
						Toast.makeText(getActivity(),biz_content, Toast.LENGTH_LONG).show();
					} else if (state.equals("error")) {
						Toast.makeText(getActivity(),biz_content, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
				
			case FANSHUIJIBIE:
				
				
				System.out.println("��ˮ����" + response);
				
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					
					if(state.equals("success")){
						
						zhijiekaihuList=JSON.parseArray(biz_content, ZhiJieKaiHuEntity.class);
						System.out.println("����" + zhijiekaihuList);
						zjAdapter = new ZhiJieKaiHuAdapter(getActivity(),zhijiekaihuList);
						
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
			System.out.println("ʧ��" + exception);
			System.out.println("ʧ����ַ" + url);
			
		}

		@Override
		public void onFinish(int what) {
			// TODO Auto-generated method stub
			
		}
	};

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
}
