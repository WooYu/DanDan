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
 * ����28ҳ��
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
	
	/** ����28���ذ�ť */
	private ImageView beijing28_back;
	/** ���������� */
	private TextView BJ_chuji_person_Num, fir_huishui_pl, sec_huishui_pl, thr_huishui_pl;
	/** �м������� */
	private TextView BJ_zhongji_person_Num;
	/** �߼������� */
	private TextView BJ_gaojiji_person_Num;
	/** ������image */
	private ImageView BJ_chujifang;
	/** �м���image */
	private ImageView BJ_zhongjifang;
	/** �߼���image */
	private ImageView BJ_gaojifang;

	/** �������� */
	private String fangjianType;
	/** ����28���߼��ô�28�淨���� */
	private String wanfaType="����";

	private Request<JSONObject> request;
	private RequestQueue fangjianInfoQueue;
	private String account;
	private String imei;
	private List<FangJianInfoEntity> fjiList;
	/** ������������� */
	private int fir_Maxuser;
	/** ������vip����������� */
	private String fir_vipmaxuser;
	/** ���������������� */
	private int fir_enter;
	/** �м���������� */
	private int sec_Maxuser;
	/** �м���vip����������� */
	private String sec_vipmaxuser;
	/** �����м���������� */
	private int sec_enter;
	/** �߼���������� */
	private int thr_Maxuser;
	/** �߼���vip����������� */
	private String thr_vipmaxuser;
	/** ����߼���������� */
	private float thr_enter;
	/** ��ǰ��� */
	private float balance;
	private List<FjZaiXianRenShuEntity> fjzxrsList;

	private String fir_vip1_personNum;
	private String fir_vip2_personNum;
	private String fir_vip3_personNum;
	private String fir_vip4_personNum;
	/** ������������ */
	private int fir_rmtotal;
	private String sec_vip1_personNum;
	private String sec_vip2_personNum;
	private String sec_vip3_personNum;
	private String sec_vip4_personNum;
	/** �м��������� */
	private int sec_rmtotal;
	private String thr_vip1_personNum;
	private String thr_vip2_personNum;
	private String thr_vip3_personNum;
	private String thr_vip4_personNum;
	

	private String info0;
	private String info1;
	
	/** �߼��������� */
	private int thr_rmtotal;
	/*******************************************����˵��*****************************************/
	/**��������˵����ť*/
	private ImageView chuji_peilvshuoming;
	/**�м�����˵����ť*/
	private ImageView zhongji_peilvshuoming;
	/**�߼�����˵����ť*/
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
	/*******************************************����˵��*****************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beijing);

		initView();
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fangjianInfoRequest();// ������Ϣ����
		yueRequest();// ���ӿ�����
		zaiXianRenShuRequest();// ���������ӿ�����
	}

	/** ��ʼ���ؼ� */
	private void initView() {
		fangjianInfoQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());

		fangjianInfoRequest();// ������Ϣ����
		yueRequest();// ���ӿ�����
		zaiXianRenShuRequest();// ���������ӿ�����
		fangjianType="����";
		teShuShuoMingRequest();//����˵��
		
		// ����28ҳ���ؼ�ͷ
		beijing28_back = (ImageView) findViewById(R.id.BJpk10_back);
		// ����������
		BJ_chuji_person_Num = (TextView) findViewById(R.id.BJpk10_chuji_person_Num);
		// �м�������
		BJ_zhongji_person_Num = (TextView) findViewById(R.id.BJpk10_zhongji_person_Num);
		// �߼�������
		BJ_gaojiji_person_Num = (TextView) findViewById(R.id.BJpk10_gaojiji_person_Num);

		fir_huishui_pl = (TextView) findViewById(R.id.BJpk10_fir_huishui_pl);
		sec_huishui_pl = (TextView) findViewById(R.id.BJpk10_sec_huishui_pl);
		thr_huishui_pl = (TextView) findViewById(R.id.BJpk10_thr_huishui_pl);
		// ������image
		BJ_chujifang = (ImageView) findViewById(R.id.BJpk10_chujifang);
		// �м���image
		BJ_zhongjifang = (ImageView) findViewById(R.id.BJpk10_zhongjifang);
		// �߼���image
		BJ_gaojifang = (ImageView) findViewById(R.id.BJpk10_gaojifang);
		
		chuji_peilvshuoming = (ImageView) findViewById(R.id.BJpk10_chuji_peilvshuoming);//��������˵��
		zhongji_peilvshuoming = (ImageView) findViewById(R.id.BJpk10_zhongji_peilvshuoming);//�м�����˵��
		gaoji_peilvshuoming = (ImageView) findViewById(R.id.BJpk10_gaoji_peilvshuoming);//�߼�����˵��
	}

	private void beijingPLrequest() {
		
		request = NoHttp.createJsonObjectRequest(XyMyContent.PEILVSHUOMING, RequestMethod.POST);
		if (fangjianType.equals("����")) {
			fangjian = "20";
		} else if (fangjianType.equals("�м�")) {
			fangjian = "21";
		} else if (fangjianType.equals("�߼�")) {
			fangjian = "22";
		}

		request.add("type", fangjian);
		request.add("imei", imei);
		request.add("account", account);

		fangjianInfoQueue.add(BEIJINGPEILVSHUOMING, request, responseListener);
		
	}
	
	private void teShuShuoMingRequest(){
		request =NoHttp.createJsonObjectRequest(XyMyContent.PEILV_TESHU_SHUOMING,RequestMethod.POST);
		if (fangjianType.equals("����")) {
			fangjian = "20";
		} else if (fangjianType.equals("�м�")) {
			fangjian = "21";
		} else if (fangjianType.equals("�߼�")) {
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
			// ������Ϣ
			case FANGJIANINFO:
				try {
					System.out.println("����������Ϣ" + response);
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
						fir_huishui_pl.setText("(��߻�ˮ " + fjiList.get(0).getBackrate() + "% )");
						sec_huishui_pl.setText("(��߻�ˮ " + fjiList.get(1).getBackrate() + "% )");
						thr_huishui_pl.setText("(��߻�ˮ " + fjiList.get(2).getBackrate() + "% )");

					} else if (state.equals("error")) {

						if (biz_content.equals("�����˺����������ֻ���¼,����������")) {

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
			// ʣ��Ԫ����
			case MYYUE:

				try {
					System.out.println("ʣ��Ԫ��������" + response);
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
			// ������������
			case ZAIXINRENSHU:

				try {
					System.out.println("����������������" + response);
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
						BJ_chuji_person_Num.setText(fir_rmtotal + "��");

						BJ_zhongji_person_Num.setText(sec_rmtotal + "��");

						BJ_gaojiji_person_Num.setText(thr_rmtotal + "��");

					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
				
			case BEIJINGPEILVTESHUSHUOMING:
				System.out.println("��������˵��" + response);
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
				
				System.out.println("����˵��" + response);
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

	/** ���ؼ����ü��� */
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
		// ���ذ�ť
		case R.id.BJpk10_back:

			finish();

			break;
		// ����28����������¼�
		case R.id.BJpk10_chujifang:
			fangjianType = "����";
			wanfaType = "����";
			if (fir_Maxuser <= fir_rmtotal) {

				Toast.makeText(getApplicationContext(), "��ǰ��������", Toast.LENGTH_SHORT).show();

			} else {

				// Toast.makeText(getApplicationContext(), "��ӭ���뱱��28������",
				// Toast.LENGTH_SHORT).show();
				Intent intent1 = new Intent(BeiJing28Activity.this, FangJianActivity.class);
				intent1.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
						.putExtra("VIP1", fir_vip1_personNum).putExtra("VIP2", fir_vip2_personNum)
						.putExtra("VIP3", fir_vip3_personNum).putExtra("VIP4", fir_vip4_personNum);
				startActivity(intent1);
			}
			break;
		// ����28�м�������¼�
		case R.id.BJpk10_zhongjifang:
			fangjianType = "�м�";
			wanfaType = "����";
			if (sec_Maxuser <= sec_rmtotal) {

				Toast.makeText(getApplicationContext(), "��ǰ��������", Toast.LENGTH_SHORT).show();

			} else {

				// Toast.makeText(getApplicationContext(), "��ӭ���뱱��28�м���",
				// Toast.LENGTH_SHORT).show();
				Intent intent2 = new Intent(BeiJing28Activity.this, FangJianActivity.class);
				intent2.putExtra("fangjianType", fangjianType).putExtra("wanfaType", wanfaType)
						.putExtra("VIP1", sec_vip1_personNum).putExtra("VIP2", sec_vip2_personNum)
						.putExtra("VIP3", sec_vip3_personNum).putExtra("VIP4", sec_vip4_personNum);
				startActivity(intent2);
			}
			break;
		// ����28�߼�������¼�
		case R.id.BJpk10_gaojifang:
			fangjianType = "�߼�";
			wanfaType = "����";
			// �жϵ�ǰ����Ƿ��ܽ���߼���
			if (balance <= thr_enter) {
				boundBankCard();
			} else {
				if (thr_Maxuser <= thr_rmtotal) {

					Toast.makeText(getApplicationContext(), "��ǰ��������", Toast.LENGTH_SHORT).show();

				} else {

					// Toast.makeText(getApplicationContext(), "��ӭ���뱱��28�߼���",
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
			fangjianType = "����";
			beijingPLrequest();
			
			break;
		case R.id.BJpk10_zhongji_peilvshuoming:
			fangjianType = "�м�";
			beijingPLrequest();
			
			break;
		case R.id.BJpk10_gaoji_peilvshuoming:
			fangjianType = "�߼�";
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
		fangjianTishi.setText("�˻�����" + fjiList.get(2).getEnter() + "Ԫ�����ֵ����뷿��");

		// ���ȷ��
		queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.cancel();

			}
		});
		// Button quxiao = (Button) window.findViewById(R.id.Yquxiao);
		// // ���ȡ��
		// quxiao.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// dialog.cancel();
		// }
		// });

	}

	/** ����activityʱ�Ķ������� */
	@Override
	public void finish() {
		super.finish();
		// activity�˳�ʱ��������
		overridePendingTransition(R.anim.activity_anim_man, R.anim.activity_left_to_right_close);
	}
}
