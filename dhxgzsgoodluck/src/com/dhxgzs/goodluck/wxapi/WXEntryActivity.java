package com.dhxgzs.goodluck.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle; 
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.dhxgzs.goodluck.Login;
import com.dhxgzs.goodluck.MainActivity;
import com.dhxgzs.goodluck.app.Constant;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;

import org.json.JSONException;
import org.json.JSONObject;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	public  int WX_LOGIN = 1;

	private IWXAPI iwxapi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ����״̬��
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		iwxapi = WXAPIFactory.createWXAPI(this, Constant.WX_APPID, false);
		//���յ������Լ���¼��intent����handleIntent������������
		iwxapi.handleIntent(getIntent(), this);

	}


	@Override
	public void onReq(BaseReq baseReq) {
	}



	//����ص��������
	@Override
	public void onResp(BaseResp baseResp) {
		//΢�ŵ�¼ΪgetTypeΪ1������Ϊ0
		if (baseResp.getType() == WX_LOGIN){
			//��¼�ص�
			SendAuth.Resp resp = (SendAuth.Resp) baseResp;
			switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				String code = String.valueOf(resp.code);
				Toast.makeText(getApplicationContext(), "wx���أ�"+code,Toast.LENGTH_SHORT).show();
				//��ȡ�û���Ϣ
				    getAccessToken(code);
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED://�û��ܾ���Ȩ
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL://�û�ȡ��
				break;
			default:
				break;
			}
		}else{
			//����ɹ��ص�
			switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				//����ɹ�
				Toast.makeText(WXEntryActivity.this, "����ɹ�", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				//����ȡ��
				Toast.makeText(WXEntryActivity.this, "����ȡ��", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				//����ܾ�
				Toast.makeText(WXEntryActivity.this, "����ܾ�", Toast.LENGTH_LONG).show();
				break;
			}
		}
		finish();
	}
	private Request<JSONObject> request;
	private RequestQueue wxQueue;
	/** 
	 * ��ȡ��Ȩ���� 
	 */
	private void getWXAccessToken(String url) {  
		wxQueue = NoHttp.newRequestQueue();
		request = NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
 
		wxQueue.add(1, request, responseListener);

	}
	private void getAccessToken(String code) {
		//��ȡ��Ȩ
		String http = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constant.WX_APPID
				+ "&secret=" + Constant.WX_APPSECRET + "&code=" + code + "&grant_type=authorization_code";
		getWXAccessToken(http);
		/*     OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    access = jsonObject.getString("access_token");
                    openId = jsonObject.getString("openid");
                    Log.d("test","�õ�΢�ŷ��أ� access="+access+"//openId="+openId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(LoginActivity.ACTION_WXLOGINSUCCESS);
                intent.putExtra("openid", openId);
                sendBroadcast(intent);
                finish();
//                //��ȡ������Ϣ
//                String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access + "&openid=" + openId + "";
//                OkHttpUtils.ResultCallback<WeChatInfo> resultCallback = new OkHttpUtils.ResultCallback<WeChatInfo>() {
//                    @Override
//                    public void onSuccess(WeChatInfo response) {
//                        Log.i("TAG", response.toString());
//                        Toast.makeText(WXEntryActivity.this, response.toString(), Toast.LENGTH_LONG).show();
//                        finish();
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        Toast.makeText(WXEntryActivity.this, "��¼ʧ��1", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                OkHttpUtils.get(getUserInfo, resultCallback);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(WXEntryActivity.this, "��¼ʧ��2", Toast.LENGTH_SHORT).show();
            }
        };
        OkHttpUtils.get(http, resultCallback);*/
	}
	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			 String access = null;
             String openId = null;
             try {
 				  access = response.get().getString("access_token");
 				  openId = response.get().getString("openid");
 		 
                 Log.d("test","�õ�΢�ŷ��أ� access="+access+"//openId="+openId);
                 LoginWXRequest(openId);
             } catch (JSONException e) {
                 e.printStackTrace();
             }

            /* Intent intent = new Intent(Login.ACTION_WXLOGINSUCCESS);
             intent.putExtra("openid", openId);
             sendBroadcast(intent);
             finish();*/

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
	private void LoginWXRequest(String openid) {  

		request = NoHttp.createJsonObjectRequest(XyMyContent.LOGIN_URL, RequestMethod.POST);

		request.add("account", "");
		request.add("password", "");
		request.add("imei", "");
		request.add("openid", openid);

		wxQueue.add(2, request, responseListener2);

	}

	OnResponseListener<JSONObject> responseListener2 = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			

			try {
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");
				System.out.println("ע������" + biz_content);
//				if (state.equals("success")) {
//					JSONObject json = new JSONObject();
//					
//					final String loginname = json.getString("");
//					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
//					EMClient.getInstance().login(userName, "666666", new EMCallBack() {// �ص�
//						@Override
//						public void onSuccess() {
//							// EMClient.getInstance().groupManager().loadAllGroups();
//							// EMClient.getInstance().chatManager().loadAllConversations();
//							runOnUiThread(new Runnable() {
//
//								@Override
//								public void run() {
//									// TODO Auto-generated method stub
//
//									Toast.makeText(getApplicationContext(), "��¼����������ɹ���", Toast.LENGTH_SHORT).show();
//									Log.d("main", "��¼����������ɹ���");
//									SharedPreferencesUtils.putBooleanValue(getApplicationContext(), "isLogin",true);
//									System.out.println("ע�����"+userName);
//									SharedPreferencesUtils.putValue(getApplicationContext(), "phoneNum", userName);
//									Intent intent = new Intent(Login.this, MainActivity.class);
//									startActivity(intent);
//									finish();
//								}
//							});
//						}
//
//						@Override
//						public void onProgress(int progress, String status) {
//
//						}
//
//						@Override
//						public void onError(int code, String message) {
//							Log.d("main", "��¼���������ʧ�ܣ�");
//							System.out.println("��¼���������ʧ��"+code);
//						}
//					});
//
//				} else if (state.equals("error")) {
//					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
//				}

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
			  Toast.makeText(getApplicationContext(), "��¼���������ʧ�ܣ�code = " +what, Toast.LENGTH_SHORT).show();
              if (what == 200){
                  /**bug ����жϷ����û��Ѿ���¼ ��ô����loginout�ӿ�  ��ʾ�û����µ�¼*/
                  Toast.makeText(getApplicationContext(), "������������Ϣ�����Ժ����µ�¼..." , Toast.LENGTH_SHORT).show();
                 // logout();
                  finish();
              }

		}
	};
}
