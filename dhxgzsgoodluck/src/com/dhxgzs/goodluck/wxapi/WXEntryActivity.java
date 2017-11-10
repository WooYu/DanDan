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
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		iwxapi = WXAPIFactory.createWXAPI(this, Constant.WX_APPID, false);
		//接收到分享以及登录的intent传递handleIntent方法，处理结果
		iwxapi.handleIntent(getIntent(), this);

	}


	@Override
	public void onReq(BaseReq baseReq) {
	}



	//请求回调结果处理
	@Override
	public void onResp(BaseResp baseResp) {
		//微信登录为getType为1，分享为0
		if (baseResp.getType() == WX_LOGIN){
			//登录回调
			SendAuth.Resp resp = (SendAuth.Resp) baseResp;
			switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				String code = String.valueOf(resp.code);
				Toast.makeText(getApplicationContext(), "wx返回："+code,Toast.LENGTH_SHORT).show();
				//获取用户信息
				    getAccessToken(code);
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
				break;
			default:
				break;
			}
		}else{
			//分享成功回调
			switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				//分享成功
				Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				//分享取消
				Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				//分享拒绝
				Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();
				break;
			}
		}
		finish();
	}
	private Request<JSONObject> request;
	private RequestQueue wxQueue;
	/** 
	 * 获取授权口令 
	 */
	private void getWXAccessToken(String url) {  
		wxQueue = NoHttp.newRequestQueue();
		request = NoHttp.createJsonObjectRequest(url, RequestMethod.GET);
 
		wxQueue.add(1, request, responseListener);

	}
	private void getAccessToken(String code) {
		//获取授权
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
                    Log.d("test","拿到微信返回： access="+access+"//openId="+openId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(LoginActivity.ACTION_WXLOGINSUCCESS);
                intent.putExtra("openid", openId);
                sendBroadcast(intent);
                finish();
//                //获取个人信息
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
//                        Toast.makeText(WXEntryActivity.this, "登录失败1", Toast.LENGTH_SHORT).show();
//                    }
//                };
//                OkHttpUtils.get(getUserInfo, resultCallback);
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(WXEntryActivity.this, "登录失败2", Toast.LENGTH_SHORT).show();
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
 		 
                 Log.d("test","拿到微信返回： access="+access+"//openId="+openId);
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
				System.out.println("注册数据" + biz_content);
//				if (state.equals("success")) {
//					JSONObject json = new JSONObject();
//					
//					final String loginname = json.getString("");
//					Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
//					EMClient.getInstance().login(userName, "666666", new EMCallBack() {// 回调
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
//									Toast.makeText(getApplicationContext(), "登录聊天服务器成功！", Toast.LENGTH_SHORT).show();
//									Log.d("main", "登录聊天服务器成功！");
//									SharedPreferencesUtils.putBooleanValue(getApplicationContext(), "isLogin",true);
//									System.out.println("注册号码"+userName);
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
//							Log.d("main", "登录聊天服务器失败！");
//							System.out.println("登录聊天服务器失败"+code);
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
			  Toast.makeText(getApplicationContext(), "登录聊天服务器失败！code = " +what, Toast.LENGTH_SHORT).show();
              if (what == 200){
                  /**bug 如果判断返回用户已经登录 那么调用loginout接口  提示用户重新登录*/
                  Toast.makeText(getApplicationContext(), "正在清理缓存信息，请稍后重新登录..." , Toast.LENGTH_SHORT).show();
                 // logout();
                  finish();
              }

		}
	};
}
