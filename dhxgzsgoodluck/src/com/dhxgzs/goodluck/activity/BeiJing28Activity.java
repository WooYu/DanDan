package com.dhxgzs.goodluck.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.ExitActivity;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.FangJianInfoEntity;
import com.dhxgzs.goodluck.entiey.FjZaiXianRenShuEntity;
import com.dhxgzs.goodluck.entiey.PeiLvEntity;
import com.dhxgzs.goodluck.entiey.PeiLvExplainEntity;
import com.dhxgzs.goodluck.entiey.PeiLvTeShuShuoMingBean;
import com.dhxgzs.goodluck.entiey.UserInfoEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.view.PeiLvSMDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 北京28页面
 * 
 * @author Administrator
 *
 */
public class BeiJing28Activity extends ExitActivity implements OnClickListener {

	private static final int FANGJIANINFO = 0;
	private static final int MYYUE = 1;
	private static final int ZAIXINRENSHU = 2;
	private static final int BEIJINGPEILVSHUOMING = 3;
	private static final int BEIJINGPEILVTESHUSHUOMING = 31;
	
	/** 北京28返回按钮 */
	private ImageView beijing28_back;
	/** 初级房人数 */
	private TextView BJ_chuji_person_Num, fir_huishui_pl, sec_huishui_pl, thr_huishui_pl;
	/** 中级房人数 */
	private TextView BJ_zhongji_person_Num;
	/** 高级房人数 */
	private TextView BJ_gaojiji_person_Num;
	/** 初级房image */
	private ImageView BJ_chujifang;
	/** 中级房image */
	private ImageView BJ_zhongjifang;
	/** 高级房image */
	private ImageView BJ_gaojifang;

	/** 房间类型 */
	private String fangjianType;
	/** 北京28或者加拿大28玩法类型 */
	private String wanfaType="北京";

	private Request<JSONObject> request;
	private RequestQueue fangjianInfoQueue;
	private String account;
	private String imei;
	private List<FangJianInfoEntity> fjiList;
	/** 初级房最大人数 */
	private int fir_Maxuser;
	/** 初级房vip房间最大人数 */
	private String fir_vipmaxuser;
	/** 进入初级房金额限制 */
	private int fir_enter;
	/** 中级房最大人数 */
	private int sec_Maxuser;
	/** 中级房vip房间最大人数 */
	private String sec_vipmaxuser;
	/** 进入中级房金额限制 */
	private int sec_enter;
	/** 高级房最大人数 */
	private int thr_Maxuser;
	/** 高级房vip房间最大人数 */
	private String thr_vipmaxuser;
	/** 进入高级房金额限制 */
	private float thr_enter;
	/** 当前余额 */
	private float balance;
	private List<FjZaiXianRenShuEntity> fjzxrsList;

	private String fir_vip1_personNum;
	private String fir_vip2_personNum;
	private String fir_vip3_personNum;
	private String fir_vip4_personNum;
	/** 初级房总人数 */
	private int fir_rmtotal;
	private String sec_vip1_personNum;
	private String sec_vip2_personNum;
	private String sec_vip3_personNum;
	private String sec_vip4_personNum;
	/** 中级房总人数 */
	private int sec_rmtotal;
	private String thr_vip1_personNum;
	private String thr_vip2_personNum;
	private String thr_vip3_personNum;
	private String thr_vip4_personNum;
	

	private String info0;
	private String info1;
	
	/** 高级房总人数 */
	private int thr_rmtotal;
	/*******************************************赔率说明*****************************************/
	/**初级赔率说明按钮*/
	private ImageView chuji_peilvshuoming;
	/**中级赔率说明按钮*/
	private ImageView zhongji_peilvshuoming;
	/**高级赔率说明按钮*/
	private ImageView gaoji_peilvshuoming;
	private String fangjian;
	private List<PeiLvExplainEntity> peiLvExplainList;
	private List<PeiLvExplainEntity> teshuPeiLvList;
	private TextView oddsData;
	private TextView oddsData1;
	private TextView oddsData2;
	private TextView oddsData3;
	private TextView oddsData4;
	private TextView oddsData5;
	private TextView oddsData6;
	private TextView oddsData7;
	private TextView oddsData8;
	private TextView oddsData9;
	private TextView oddsData10;
	private TextView oddsData11;
	private TextView oddsData12;
	private TextView oddsData13;
	private TextView peilv_play;
	private TextView peilv_play1;
	private TextView peilv_play2;
	private TextView peilv_play3;
	private TextView peilv_play4;
	private TextView peilv_play5;
	private TextView peilv_play6;
	private TextView peilv_play7;
	private TextView peilv_play8;
	private TextView peilv_play9;
	private TextView peilv_play10;
	private TextView peilv_play11;
	private TextView peilv_play12;
	private TextView peilv_play13;
	private TextView tvTeShuPeiLvShuMing;
	/*******************************************赔率说明*****************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beijing);

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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fangjianInfoRequest();// 房间信息请求
		yueRequest();// 余额接口请求
		zaiXianRenShuRequest();// 在线人数接口请求
	}

	/** 初始化控件 */
	private void initView() {
		fangjianInfoQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());

		fangjianInfoRequest();// 房间信息请求
		yueRequest();// 余额接口请求
		zaiXianRenShuRequest();// 在线人数接口请求
		fangjianType="初级";
		teShuShuoMingRequest();//特殊说明
		
		// 北京28页返回箭头
		beijing28_back = (ImageView) findViewById(R.id.BJpk10_back);
		// 初级房人数
		BJ_chuji_person_Num = (TextView) findViewById(R.id.BJpk10_chuji_person_Num);
		// 中级房人数
		BJ_zhongji_person_Num = (TextView) findViewById(R.id.BJpk10_zhongji_person_Num);
		// 高级房人数
		BJ_gaojiji_person_Num = (TextView) findViewById(R.id.BJpk10_gaojiji_person_Num);

		fir_huishui_pl = (TextView) findViewById(R.id.BJpk10_fir_huishui_pl);
		sec_huishui_pl = (TextView) findViewById(R.id.BJpk10_sec_huishui_pl);
		thr_huishui_pl = (TextView) findViewById(R.id.BJpk10_thr_huishui_pl);
		// 初级房image
		BJ_chujifang = (ImageView) findViewById(R.id.BJpk10_chujifang);
		// 中级房image
		BJ_zhongjifang = (ImageView) findViewById(R.id.BJpk10_zhongjifang);
		// 高级房image
		BJ_gaojifang = (ImageView) findViewById(R.id.BJpk10_gaojifang);
		
		chuji_peilvshuoming = (ImageView) findViewById(R.id.BJpk10_chuji_peilvshuoming);//初级赔率说明
		zhongji_peilvshuoming = (ImageView) findViewById(R.id.BJpk10_zhongji_peilvshuoming);//中级赔率说明
		gaoji_peilvshuoming = (ImageView) findViewById(R.id.BJpk10_gaoji_peilvshuoming);//高级赔率说明
	}

	private void beijingPLrequest() {
		
		request = NoHttp.createJsonObjectRequest(XyMyContent.PEILVSHUOMING, RequestMethod.POST);
		if (fangjianType.equals("初级")) {
			fangjian = "20";
		} else if (fangjianType.equals("中级")) {
			fangjian = "21";
		} else if (fangjianType.equals("高级")) {
			fangjian = "22";
		}

		request.add("type", fangjian);
		request.add("imei", imei);
		request.add("account", account);

		fangjianInfoQueue.add(BEIJINGPEILVSHUOMING, request, responseListener);
		
	}
	
	private void teShuShuoMingRequest(){
		request =NoHttp.createJsonObjectRequest(XyMyContent.PEILV_TESHU_SHUOMING,RequestMethod.POST);
		if (fangjianType.equals("初级")) {
			fangjian = "20";
		} else if (fangjianType.equals("中级")) {
			fangjian = "21";
		} else if (fangjianType.equals("高级")) {
			fangjian = "22";
		}

		request.add("type", fangjian);
		request.add("imei", imei);
		request.add("account", account);
		fangjianInfoQueue.add(BEIJINGPEILVTESHUSHUOMING, request, responseListener);
		
		
	}

	private void zaiXianRenShuRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.FANGJIANZAIXINRENSHU_URL, RequestMethod.POST);

		request.add("imei", imei);
		request.add("account", account);
		request.add("type", "10");
		fangjianInfoQueue.add(ZAIXINRENSHU, request, responseListener);

	}

	private void yueRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.ACCOUNTMONEY_URL, RequestMethod.POST);

		request.add("imei", imei);
		request.add("account", account);

		fangjianInfoQueue.add(MYYUE, request, responseListener);

	}

	private void fangjianInfoRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.FANGJIANINFO_URL, RequestMethod.POST);

		request.add("imei", imei);
		request.add("account", account);

		fangjianInfoQueue.add(FANGJIANINFO, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		


		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			// 房间信息
			case FANGJIANINFO:
				try {
					System.out.println("北京房间信息" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						fjiList = JSON.parseArray(biz_content, FangJianInfoEntity.class);

						fir_Maxuser = Integer.parseInt(fjiList.get(0).getMaxuser());
						fir_vipmaxuser = fjiList.get(0).getVipmaxuser();
						fir_enter = Integer.parseInt(fjiList.get(0).getEnter());

						sec_Maxuser = Integer.parseInt(fjiList.get(1).getMaxuser());
						sec_vipmaxuser = fjiList.get(1).getVipmaxuser();
						sec_enter = Integer.parseInt(fjiList.get(1).getEnter());

						thr_Maxuser = Integer.parseInt(fjiList.get(2).getMaxuser());
						thr_vipmaxuser = fjiList.get(2).getVipmaxuser();
						thr_enter = Float.parseFloat(fjiList.get(2).getEnter());
					 

						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vipmaxuser", fir_vipmaxuser);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vipmaxuser", sec_vipmaxuser);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vipmaxuser", thr_vipmaxuser);
						// BJ_chuji_person_Num.setText(text);
						// BJ_zhongji_person_Num.setText(text);
						// BJ_gaojiji_person_Num.setText(text);
						fir_huishui_pl.setText("(最高回水 " + fjiList.get(0).getBackrate() + "% )");
						sec_huishui_pl.setText("(最高回水 " + fjiList.get(1).getBackrate() + "% )");
						thr_huishui_pl.setText("(最高回水 " + fjiList.get(2).getBackrate() + "% )");

					} else if (state.equals("error")) {

						if (biz_content.equals("您的账号已在其他手机登录,您被迫下线")) {

							setaaa(getApplicationContext()); 
							
						} else {
							Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_LONG).show();
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			// 剩余元宝数
			case MYYUE:

				try {
					System.out.println("剩余元宝数数据" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						UserInfoEntity entity = new UserInfoEntity();

						entity = JSON.parseObject(biz_content, UserInfoEntity.class);
						balance = Float.parseFloat(entity.getMoney());

					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			// 房间在线人数
			case ZAIXINRENSHU:

				try {
					System.out.println("北京房间在线人数" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						fjzxrsList = JSON.parseArray(biz_content, FjZaiXianRenShuEntity.class);

						fir_vip1_personNum = fjzxrsList.get(0).getVip1();
						fir_vip2_personNum = fjzxrsList.get(0).getVip2();
						fir_vip3_personNum = fjzxrsList.get(0).getVip3();
						fir_vip4_personNum = fjzxrsList.get(0).getVip4();
						fir_rmtotal = Integer.parseInt(fjzxrsList.get(0).getRmtotal());

						sec_vip1_personNum = fjzxrsList.get(1).getVip1();
						sec_vip2_personNum = fjzxrsList.get(1).getVip2();
						sec_vip3_personNum = fjzxrsList.get(1).getVip3();
						sec_vip4_personNum = fjzxrsList.get(1).getVip4();
						sec_rmtotal = Integer.parseInt(fjzxrsList.get(1).getRmtotal());

						thr_vip1_personNum = fjzxrsList.get(2).getVip1();
						thr_vip2_personNum = fjzxrsList.get(2).getVip2();
						thr_vip3_personNum = fjzxrsList.get(2).getVip3();
						thr_vip4_personNum = fjzxrsList.get(2).getVip4();
						thr_rmtotal = Integer.parseInt(fjzxrsList.get(2).getRmtotal());

						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vip1_personNum",
								fir_vip1_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vip2_personNum",
								fir_vip2_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vip3_personNum",
								fir_vip3_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_fir_vip4_personNum",
								fir_vip4_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vip1_personNum",
								sec_vip1_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vip2_personNum",
								sec_vip2_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vip3_personNum",
								sec_vip3_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_sec_vip4_personNum",
								sec_vip4_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vip1_personNum",
								thr_vip1_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vip2_personNum",
								thr_vip2_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vip3_personNum",
								thr_vip3_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "BJ_thr_vip4_personNum",
								thr_vip4_personNum);
						BJ_chuji_person_Num.setText(fir_rmtotal + "人");

						BJ_zhongji_person_Num.setText(sec_rmtotal + "人");

						BJ_gaojiji_person_Num.setText(thr_rmtotal + "人");

					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
				
			case BEIJINGPEILVTESHUSHUOMING:
				System.out.println("特殊赔率说明" + response);
				try{
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("biz_content", biz_content+"");
					if(state.equals("success")){
						JSONObject  jsonObject = new JSONObject(biz_content);
						info0 =jsonObject.getString("info0");
						info1 =jsonObject.getString("info1");
						
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
				
				break;
				
			case BEIJINGPEILVSHUOMING:
				
				System.out.println("赔率说明" + response);
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						peiLvExplainList = JSON.parseArray(biz_content, PeiLvExplainEntity.class);
						//zanDlerlog();
						PeiLvSMDialog.getInstance(BeiJing28Activity.this).zanDlerlog(BeiJing28Activity.this, peiLvExplainList);
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

	/** 给控件设置监听 */
	private void setListener() {

		beijing28_back.setOnClickListener(this);

		BJ_chujifang.setOnClickListener(this);
		BJ_zhongjifang.setOnClickListener(this);
		BJ_gaojifang.setOnClickListener(this);

		chuji_peilvshuoming.setOnClickListener(this);
		zhongji_peilvshuoming.setOnClickListener(this);
		gaoji_peilvshuoming.setOnClickListener(this);
	}

 

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 返回按钮
		case R.id.BJpk10_back:

			finish();

			break;
		// 北京28初级房点击事件
		case R.id.BJpk10_chujifang:
			fangjianType = "初级";
			wanfaType = "北京";
			if (fir_Maxuser <= fir_rmtotal) {

				Toast.makeText(getApplicationContext(), "当前房间已满", Toast.LENGTH_SHORT).show();

			} else {

				// Toast.makeText(getApplicationContext(), "欢迎进入北京28初级房",
				// Toast.LENGTH_SHORT).show();
				Intent intent1 = new Intent(BeiJing28Activity.this, FangJianActivity.class);
				intent1.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
						.putExtra("VIP1", fir_vip1_personNum).putExtra("VIP2", fir_vip2_personNum)
						.putExtra("VIP3", fir_vip3_personNum).putExtra("VIP4", fir_vip4_personNum);
				startActivity(intent1);
			}
			break;
		// 北京28中级房点击事件
		case R.id.BJpk10_zhongjifang:
			fangjianType = "中级";
			wanfaType = "北京";
			if (sec_Maxuser <= sec_rmtotal) {

				Toast.makeText(getApplicationContext(), "当前房间已满", Toast.LENGTH_SHORT).show();

			} else {

				// Toast.makeText(getApplicationContext(), "欢迎进入北京28中级房",
				// Toast.LENGTH_SHORT).show();
				Intent intent2 = new Intent(BeiJing28Activity.this, FangJianActivity.class);
				intent2.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
						.putExtra("VIP1", sec_vip1_personNum).putExtra("VIP2", sec_vip2_personNum)
						.putExtra("VIP3", sec_vip3_personNum).putExtra("VIP4", sec_vip4_personNum);
				startActivity(intent2);
			}
			break;
		// 北京28高级房点击事件
		case R.id.BJpk10_gaojifang:
			fangjianType = "高级";
			wanfaType = "北京";
			// 判断当前余额是否能进入高级房
			if (balance <= thr_enter) {
				boundBankCard();
			} else {
				if (thr_Maxuser <= thr_rmtotal) {

					Toast.makeText(getApplicationContext(), "当前房间已满", Toast.LENGTH_SHORT).show();

				} else {

					// Toast.makeText(getApplicationContext(), "欢迎进入北京28高级房",
					// Toast.LENGTH_SHORT).show();
					Intent intent3 = new Intent(BeiJing28Activity.this, FangJianActivity.class);
					intent3.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
							.putExtra("VIP1", thr_vip1_personNum).putExtra("VIP2", thr_vip2_personNum)
							.putExtra("VIP3", thr_vip3_personNum).putExtra("VIP4", thr_vip4_personNum);
					startActivity(intent3);
				}
			}
			break;
		case R.id.BJpk10_chuji_peilvshuoming:
			fangjianType = "初级";
			beijingPLrequest();
			
			break;
		case R.id.BJpk10_zhongji_peilvshuoming:
			fangjianType = "中级";
			beijingPLrequest();
			
			break;
		case R.id.BJpk10_gaoji_peilvshuoming:
			fangjianType = "高级";
			beijingPLrequest();
			
			break;
		default:
			break;
		}

	}

	@SuppressLint("InflateParams")
	private void boundBankCard() {

		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alertdialog_fangjianxianzhi_tishi, null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.alertdialog_fangjianxianzhi_tishi);
		Button queding = (Button) window.findViewById(R.id.Yqueding);
		TextView fangjianTishi = (TextView) window.findViewById(R.id.fangjianTishi);
		fangjianTishi.setText("账户余额不足" + fjiList.get(2).getEnter() + "元，请充值后进入房间");

		// 点击确定
		queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.cancel();

			}
		});
		// Button quxiao = (Button) window.findViewById(R.id.Yquxiao);
		// // 点击取消
		// quxiao.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// dialog.cancel();
		// }
		// });

	}

	/** 销毁activity时的动画方法 */
	@Override
	public void finish() {
		super.finish();
		// activity退出时动画方法
		overridePendingTransition(R.anim.activity_anim_man, R.anim.activity_left_to_right_close);
	}
}
