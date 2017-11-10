package com.dhxgzs.goodluck.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.FangJianInfoEntity;
import com.dhxgzs.goodluck.entiey.FjZaiXianRenShuEntity;
import com.dhxgzs.goodluck.entiey.PeiLvExplainEntity;
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

/**
 * 加拿大28页面
 * 
 * @author Administrator
 *
 */
public class JiaNaDa28Activity extends Activity implements OnClickListener {

	private String info0;
	private String info1;
	
	private static final int ZAIXINRENSHU = 1;
	private static final int MYYUE = 2;
	private static final int FANGJIANINFO = 3;
	private static final int BEIJINGPEILVTESHUSHUOMING = 31;
	private static final int JNDPEILVSHUOMING = 0;
	/** 加拿大28返回按钮 */
	private ImageView jianada28_back;
	/** 初级房人数 */
	private TextView JND_chuji_person_Num, fir_huishui_pl, sec_huishui_pl, thr_huishui_pl;
	/** 中级房人数 */
	private TextView JND_zhongji_person_Num;
	/** 高级房人数 */
	private TextView JND_gaojiji_person_Num;
	/** 初级房image */
	private ImageView JND_chujifang;
	/** 中级房image */
	private ImageView JND_zhongjifang;
	/** 高级房image */
	private ImageView JND_gaojifang;
	/** 房间类型 */
	private String fangjianType;
	/** 北京28或者加拿大28玩法类型 */
	private String wanfaType="加拿大";

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
	/** 高级房总人数 */
	private int thr_rmtotal;

	/*******************************************赔率说明*****************************************/
	/**初级赔率说明按钮*/
	private ImageView jnd_chuji_peilvshuoming;
	/**中级赔率说明按钮*/
	private ImageView jdn_zhongji_peilvshuoming;
	/**高级赔率说明按钮*/
	private ImageView jnd_gaoji_peilvshuoming;
	private String fangjian;
	private List<PeiLvExplainEntity> peiLvExplainList;
 
	/*******************************************赔率说明*****************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jianada);

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
		jianada28_back = (ImageView) findViewById(R.id.JND_back);
		// 初级房人数
		JND_chuji_person_Num = (TextView) findViewById(R.id.JND_chuji_person_Num);
		// 中级房人数
		JND_zhongji_person_Num = (TextView) findViewById(R.id.JND_zhongji_person_Num);
		// 高级房人数
		JND_gaojiji_person_Num = (TextView) findViewById(R.id.JND_gaojiji_person_Num);

 		fir_huishui_pl = (TextView) findViewById(R.id.jnd_fir_huishui_pl);
		sec_huishui_pl = (TextView) findViewById(R.id.jnd_sec_huishui_pl);
		thr_huishui_pl = (TextView) findViewById(R.id.jnd_thr_huishui_pl); 

		// 初级房image
		JND_chujifang = (ImageView) findViewById(R.id.JND_chujifang);
		// 中级房image
		JND_zhongjifang = (ImageView) findViewById(R.id.JND_zhongjifang);
		// 高级房image
		JND_gaojifang = (ImageView) findViewById(R.id.JND_gaojifang);
		
		jnd_chuji_peilvshuoming = (ImageView) findViewById(R.id.jnd_chuji_peilvshuoming);//初级赔率说明
		jdn_zhongji_peilvshuoming= (ImageView) findViewById(R.id.jdn_zhongji_peilvshuoming);//中级赔率说明
		jnd_gaoji_peilvshuoming = (ImageView) findViewById(R.id.jnd_gaoji_peilvshuoming);//高级赔率说明
	}

	private void teShuShuoMingRequest() {
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
		request.add("type", "11");
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
			// 房间信息
			case FANGJIANINFO:
				try {
					System.out.println("加拿大房间信息" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						fjiList = JSON.parseArray(biz_content, FangJianInfoEntity.class);

						fir_Maxuser = Integer.parseInt(fjiList.get(3).getMaxuser());
						fir_vipmaxuser = fjiList.get(3).getVipmaxuser();
						fir_enter = Integer.parseInt(fjiList.get(3).getEnter());

						sec_Maxuser = Integer.parseInt(fjiList.get(4).getMaxuser());
						sec_vipmaxuser = fjiList.get(4).getVipmaxuser();
						sec_enter = Integer.parseInt(fjiList.get(4).getEnter());

						thr_Maxuser = Integer.parseInt(fjiList.get(5).getMaxuser());
						thr_vipmaxuser = fjiList.get(5).getVipmaxuser();
						thr_enter = Float.parseFloat(fjiList.get(5).getEnter());
						
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_fir_vipmaxuser", fir_vipmaxuser);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_sec_vipmaxuser", sec_vipmaxuser);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_thr_vipmaxuser", thr_vipmaxuser);
						
						// BJ_chuji_person_Num.setText(text);
						// BJ_zhongji_person_Num.setText(text);
						// BJ_gaojiji_person_Num.setText(text);
						 fir_huishui_pl.setText("(最高回水 " + fjiList.get(3).getBackrate() + "% )");
						sec_huishui_pl.setText("(最高回水 " + fjiList.get(4).getBackrate() + "% )");
						thr_huishui_pl.setText("(最高回水 " + fjiList.get(5).getBackrate() + "% )"); 

					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

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
					System.out.println("加拿大房间在线人数" + response);
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

						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_fir_vip1_personNum",
								fir_vip1_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_fir_vip2_personNum",
								fir_vip2_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_fir_vip3_personNum",
								fir_vip3_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_fir_vip4_personNum",
								fir_vip4_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_sec_vip1_personNum",
								sec_vip1_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_sec_vip2_personNum",
								sec_vip2_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_sec_vip3_personNum",
								sec_vip3_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_sec_vip4_personNum",
								sec_vip4_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_thr_vip1_personNum",
								thr_vip1_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_thr_vip2_personNum",
								thr_vip2_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_thr_vip3_personNum",
								thr_vip3_personNum);
						SharedPreferencesUtils.putValue(getApplicationContext(), "JND_thr_vip4_personNum",
								thr_vip4_personNum);

						JND_chuji_person_Num.setText(fir_rmtotal + "  ");

						JND_zhongji_person_Num.setText(sec_rmtotal + "  ");

						JND_gaojiji_person_Num.setText(thr_rmtotal + "  ");
					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case JNDPEILVSHUOMING:
				System.out.println("赔率说明" + response);
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {

						peiLvExplainList = JSON.parseArray(biz_content, PeiLvExplainEntity.class);
						//zanDlerlog();
						PeiLvSMDialog.getInstance(JiaNaDa28Activity.this).zanDlerlog(JiaNaDa28Activity.this, peiLvExplainList);
					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	private TextView tvTeShuPeiLvShuMing;

	/** 给控件设置监听 */
	private void setListener() {

		jianada28_back.setOnClickListener(this);
		JND_chujifang.setOnClickListener(this);
		JND_zhongjifang.setOnClickListener(this);
		JND_gaojifang.setOnClickListener(this);
		
		jnd_chuji_peilvshuoming.setOnClickListener(this);
		jdn_zhongji_peilvshuoming.setOnClickListener(this);
		jnd_gaoji_peilvshuoming.setOnClickListener(this);
	}

/*	protected void zanDlerlog() {
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(com.dhxgzs.goodluck.R.layout.alertdialog_shouye,
				null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(com.dhxgzs.goodluck.R.layout.alertdialog_shouye);
		TextView odd_close = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.odd_close);
		oddsData = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData);
		oddsData1 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData1);
		oddsData2 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData2);
		oddsData3 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData3);
		oddsData4 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData4);
		oddsData5 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData5);
		oddsData6 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData6);
		oddsData7 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData7);
		oddsData8 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData8);
		oddsData9 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData9);
		oddsData10 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData10);
		oddsData11 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData11);
		oddsData12 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData12);
		oddsData13 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.oddsData13);

		
		tvTeShuPeiLvShuMing =(TextView) window.findViewById(R.id.tv_teshu_shuo_ming);
		tvTeShuPeiLvShuMing.setText("(1) 单注10-20000，总注80000封顶;\n(2) 大小单双20000封顶，极值5000封顶，猜数字5000封顶，组合10000封顶，红绿蓝20000封顶，豹子5000封顶"+"\n(3) "+info0+" \n(4) "+info1);

		
		peilv_play = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play);
		peilv_play1 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play1);
		peilv_play2 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play2);
		peilv_play3 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play3);
		peilv_play4 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play4);
		peilv_play5 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play5);
		peilv_play6 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play6);
		peilv_play7 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play7);
		peilv_play8 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play8);
		peilv_play9 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play9);
		peilv_play10 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play10);
		peilv_play11 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play11);
		peilv_play12 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play12);
		peilv_play13 = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.peilv_play13);

		oddsData.setText(peiLvExplainList.get(0).getOdds());
		oddsData1.setText(peiLvExplainList.get(1).getOdds());
		oddsData2.setText(peiLvExplainList.get(2).getOdds());
		oddsData3.setText(peiLvExplainList.get(3).getOdds());
		oddsData4.setText(peiLvExplainList.get(4).getOdds());
		oddsData5.setText(peiLvExplainList.get(5).getOdds());
		oddsData6.setText(peiLvExplainList.get(6).getOdds());
		oddsData7.setText(peiLvExplainList.get(7).getOdds());
		oddsData8.setText(peiLvExplainList.get(8).getOdds());
		oddsData9.setText(peiLvExplainList.get(9).getOdds());
		oddsData10.setText(peiLvExplainList.get(10).getOdds());
		oddsData11.setText(peiLvExplainList.get(11).getOdds());
		oddsData12.setText(peiLvExplainList.get(12).getOdds());
	//	oddsData13.setText(peiLvExplainList.get(13).getOdds());

		peilv_play.setText(peiLvExplainList.get(0).getItem());
		peilv_play1.setText(peiLvExplainList.get(1).getItem());
		peilv_play2.setText(peiLvExplainList.get(2).getItem());
		peilv_play3.setText(peiLvExplainList.get(3).getItem());
		peilv_play4.setText(peiLvExplainList.get(4).getItem());
		peilv_play5.setText(peiLvExplainList.get(5).getItem());
		peilv_play6.setText(peiLvExplainList.get(6).getItem());
		peilv_play7.setText(peiLvExplainList.get(7).getItem());
		peilv_play8.setText(peiLvExplainList.get(8).getItem());
		peilv_play9.setText(peiLvExplainList.get(9).getItem());
		peilv_play10.setText(peiLvExplainList.get(10).getItem());
		peilv_play11.setText(peiLvExplainList.get(11).getItem());
		peilv_play12.setText(peiLvExplainList.get(12).getItem());
	//	peilv_play13.setText(peiLvExplainList.get(13).getItem());

		odd_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();

			}
		});

		
	}*/

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 返回按钮
		case R.id.JND_back:

			finish();

			break;
		// 加拿大28初级房点击事件
		case R.id.JND_chujifang:

			fangjianType = "初级";
			wanfaType = "加拿大";
			if (fir_Maxuser <= fir_rmtotal) {

				Toast.makeText(getApplicationContext(), "房间人数已满", Toast.LENGTH_SHORT).show();

			} else {

//				Toast.makeText(getApplicationContext(), "欢迎进入加拿大28初级房", Toast.LENGTH_SHORT).show();
				Intent intent1 = new Intent(JiaNaDa28Activity.this, FangJianActivity.class);
				intent1.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
						.putExtra("VIP1", fir_vip1_personNum).putExtra("VIP2", fir_vip2_personNum)
						.putExtra("VIP3", fir_vip3_personNum).putExtra("VIP4", fir_vip4_personNum);
				startActivity(intent1);
			}
			break;
		// 加拿大28中级房点击事件
		case R.id.JND_zhongjifang:

			fangjianType = "中级";
			wanfaType = "加拿大";
			if (sec_Maxuser <= sec_rmtotal) {
				Toast.makeText(getApplicationContext(), "房间人数已满", Toast.LENGTH_SHORT).show();
			} else {

//				Toast.makeText(getApplicationContext(), "欢迎进入加拿大28中级房", Toast.LENGTH_SHORT).show();
				Intent intent2 = new Intent(JiaNaDa28Activity.this, FangJianActivity.class);
				intent2.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
						.putExtra("VIP1", sec_vip1_personNum).putExtra("VIP2", sec_vip2_personNum)
						.putExtra("VIP3", sec_vip3_personNum).putExtra("VIP4", sec_vip4_personNum);
				startActivity(intent2);
			}
			break;
		// 加拿大28高级房点击事件
		case R.id.JND_gaojifang:
			fangjianType = "高级";
			wanfaType = "加拿大";
			// 判断当前余额是否能进入高级房
			 if (balance <= thr_enter) {
			 boundBankCard();
			 } else {
			if (thr_Maxuser <= thr_rmtotal) {
				Toast.makeText(getApplicationContext(), "房间人数已满", Toast.LENGTH_SHORT).show();
			} else {
//				Toast.makeText(getApplicationContext(), "欢迎进入加拿大28高级房", Toast.LENGTH_SHORT).show();
				Intent intent3 = new Intent(JiaNaDa28Activity.this, FangJianActivity.class);
				intent3.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
						.putExtra("VIP1", thr_vip1_personNum).putExtra("VIP2", thr_vip2_personNum)
						.putExtra("VIP3", thr_vip3_personNum).putExtra("VIP4", thr_vip4_personNum);
				startActivity(intent3);
			}
			 }
			break;
		case R.id.jnd_chuji_peilvshuoming:
			fangjianType = "初级";
			jndPLrequest();
			break;
		case R.id.jdn_zhongji_peilvshuoming:
			fangjianType = "中级";
			jndPLrequest();
			break;
		case R.id.jnd_gaoji_peilvshuoming:
			fangjianType = "高级";
			jndPLrequest();
			break;
		default:
			break;
		}

	}

	private void jndPLrequest() {
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

		fangjianInfoQueue.add(JNDPEILVSHUOMING, request, responseListener);
		
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
