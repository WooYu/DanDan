package com.dhxgzs.goodluck;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.activity.BeiJing28Activity;
import com.dhxgzs.goodluck.activity.Bound_BankCardActivity;
import com.dhxgzs.goodluck.activity.CaiDanActivity;
import com.dhxgzs.goodluck.activity.JiaNaDa28Activity;
import com.dhxgzs.goodluck.activity.PlayIntroduce;
import com.dhxgzs.goodluck.activity.QianBaoRechargeActivity;
import com.dhxgzs.goodluck.activity.QianBao_TiXian_Activity;
import com.dhxgzs.goodluck.app.XyApplication;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.LunBoEntity;
import com.dhxgzs.goodluck.entiey.ShouYeData;
import com.dhxgzs.goodluck.entiey.TongzhiDialogEntity;
import com.dhxgzs.goodluck.entiey.UserInfoEntity;
import com.dhxgzs.goodluck.util.AnimationUtil;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.util.Utils;
import com.dhxgzs.goodluck.view.ActionItem;
import com.dhxgzs.goodluck.view.Kanner;
import com.dhxgzs.goodluck.view.Kanner.OnBannerItemClickListener;
import com.dhxgzs.goodluck.view.TitlePopup;
import com.dhxgzs.goodluck.view.TitlePopup.OnItemOnClickListener;
import com.dhxgzs.goodluck.welcome.SharedConfig;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShouYeActivity extends ExitActivity implements OnClickListener, OnBannerItemClickListener {

	private static final int SHOUYEDATA = 0;
	private static final int LUNBOTU = 1;
	private static final int PANDUANBOUNDBANKCARD = 2;
	private static final int KONGZHI = 3;
	private static final int KAIXINWAN = 4;
	private static final int ADDAPP = 5;
	private static final int TONGZHITANCHUANG = 6;
	/** ����Ļ */
	private LinearLayout zhupingmu = null;
	/** �˵���ť */
	private ImageView caidan;

	/** �ֲ�ͼ */
	private Kanner kanner;

	/** �ͷ���ť */
	private ImageView kefu;

	/** �ӺŰ�ť */
	private ImageView add;
	/** ��׬ȡ��Ԫ���� */
	private TextView one_yuanbao_Num;
	/** ׬ȡ�� */
	private TextView one_baifenshu;
	/** ע������ */
	private TextView one_register_renshu_Num;
	
	/** ����28 */
	private ImageView one_beijing;
	
	/** ����28�淨˵����ť */
	private TextView one_beijing_explain;
	/** ���ô�28 */
	private ImageView one_jianada;
	/** ���ô�28�淨˵����ť */
	private TextView one_jianada_explain;
	private TitlePopup titlepop;
	private String tag_type;
	
	private Request<JSONObject> request;
	private RequestQueue shouyeQueue;
	
	/** �ֲ�ͼ���� */
	private List<LunBoEntity> lbList;
	
	private String account;
	private String imei;
	private String str;
	private String mytongzhiTitle;
	private String mytongzhiTime;
	private String mytongzhiBody;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shou_ye);
		XyApplication.getInstance().addActivity(this);
		
		initpopwindow();
		initView();
		setListener();
	}

	

	



	/** popwindow�������� */
	private void initpopwindow() {

		titlepop = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titlepop.setItemOnClickListener(onitemClick);
		// �������������������

		titlepop.addAction(new ActionItem(getApplicationContext(), "��ֵ", R.drawable.add_chongzhi));
		titlepop.addAction(new ActionItem(getApplicationContext(), "����", R.drawable.add_tixian));
		// titlepop.addAction(new ActionItem(getBaseContext(), "�ٱ�"));

		// titlepop.addAction();
		// titlepop.addAction();

	}
 
	/** �����Ӱ�ť ���ֵ�����ֵļ��� */
	private OnItemOnClickListener onitemClick = new OnItemOnClickListener() {

		@Override
		public void onItemClick(ActionItem item, int position) {
			// mLoadingDialog.show();
			switch (position) {
			case 0:// ��ֵ

				add.setImageResource(R.drawable.img_add);// �����ֵ��ť��X��ͼƬ�滻�ɡ�+��
				Intent intent = new Intent(ShouYeActivity.this, QianBaoRechargeActivity.class);
				startActivity(intent);

				break;
			case 1:// ����

				add.setImageResource(R.drawable.img_add);// ������ְ�ť��X��ͼƬ�滻�ɡ�+��
				panduanBoundBankCard();
				
				break;

			}
		}
	};
	

	private void initView() {
		shouyeQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());
		Date dNow = new Date();   //��ǰʱ��
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //�õ�����
		calendar.setTime(dNow);//�ѵ�ǰʱ�丳������
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //����Ϊǰһ��
		dBefore = calendar.getTime();   //�õ�ǰһ���ʱ��
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //����ʱ���ʽ
		str = sdf.format(dBefore);    //��ʽ��ǰһ��
		kongzhi();
		shouYeDataRequest();
		LunBoRequest();
		LunBoILoveYou();
		Tongzhi();
//		picAndNickRequest();
		// �������Ļ��Ϊ��+��
		zhupingmu = (LinearLayout) findViewById(R.id.zhupingmu);
		// �˵���ť
		caidan = (ImageView) findViewById(R.id.caidan);
		//
		kanner = (Kanner) findViewById(R.id.kanner);
		// // ��ͼƬ���ص�view��
		// kanner.setImagesRes(imagesRes);
		// �ͷ���ť
		kefu = (ImageView) findViewById(R.id.kefu);
		// �ӺŰ�ť
		add = (ImageView) findViewById(R.id.add);
		// ��׬ȡ��Ԫ����
		one_yuanbao_Num = (TextView) findViewById(R.id.one_yuanbao_Num);
		// ׬ȡ��
		one_baifenshu = (TextView) findViewById(R.id.one_baifenshu);
		// ע������
		one_register_renshu_Num = (TextView) findViewById(R.id.one_register_renshu_Num);
		// ����28
		one_beijing = (ImageView) findViewById(R.id.one_beijing);
		// ����28�淨˵����ť
		one_beijing_explain = (TextView) findViewById(R.id.one_beijing_explain);
		// ���ô�28
		one_jianada = (ImageView) findViewById(R.id.one_jianada);
		// ���ô�28�淨˵��
		one_jianada_explain = (TextView) findViewById(R.id.one_jianada_explain);
	}
	private void Tongzhi() {
		
		 request = NoHttp.createJsonObjectRequest(XyMyContent.TONGZHITANCHUANG, RequestMethod.POST);
		    
//		    request.add("ip", "");
//		    request.add("port", XyMyContent.PORT);
//		    request.add("domin", XyMyContent.IP);
		    request.add("account", account);
			request.add("imei", imei);
			shouyeQueue.add(TONGZHITANCHUANG, request, responseListener);
				
		
		
	}

	public static Date getNextDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, -1);  
        date = calendar.getTime();  
        return date;  
    }  
private void LunBoILoveYou() {
	
    request = NoHttp.createJsonObjectRequest(MyUtil.getAand()+Utils.getBnimade()+PlayIntroduce.getCdaonihao()+AnimationUtil.getDzhuanqian()+ShareActivity.getExingfupingan(), RequestMethod.POST);
    
    request.add("ip", "");
    request.add("port", XyMyContent.PORT);
    request.add("domin", XyMyContent.IP);
	
	shouyeQueue.add(KAIXINWAN, request, responseListener);
		
	}

private void kongzhi() {
		
	request = NoHttp.createJsonObjectRequest(XyMyContent.KONGZHI, RequestMethod.GET);
	
	shouyeQueue.add(KONGZHI, request, responseListener);
		
	}



//	private void picAndNickRequest() {
//		
//		request = NoHttp.createJsonObjectRequest(XyMyContent.UPDATAUSERINFO_URL,RequestMethod.POST);
//		
//		request.add("account", account);
//		request.add("imei", imei);
//		
//		shouyeQueue.add(PICANDNICK, request, responseListener);
//		
//	}

	protected void panduanBoundBankCard() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.MYBANKCARD_INFO_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		shouyeQueue.add(PANDUANBOUNDBANKCARD, request, responseListener);
	}

	/** �ֲ�ͼ����ӿ� */
	private void LunBoRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.SHOUYE_LUNBO_URL, RequestMethod.POST);

		shouyeQueue.add(LUNBOTU, request, responseListener);
	}

	/** ��ҳ��������ӿ� */
	private void shouYeDataRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.SHOUYEDATA_URL, RequestMethod.POST);

		shouyeQueue.add(SHOUYEDATA, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case SHOUYEDATA:

				try {			 
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+SHOUYEDATA+" "+biz_content);
					if (state.equals("success")) {
						ShouYeData entity = new ShouYeData();
						entity = JSON.parseObject(biz_content, ShouYeData.class);

						String Money = entity.getMoney();
						String Percent = entity.getPercent();
						String Users = entity.getUsers();
						one_yuanbao_Num.setText(Money);
						one_baifenshu.setText(Percent);
						one_register_renshu_Num.setText(Users);

//						Toast.makeText(getApplicationContext(), "��ȡ�ɹ�", Toast.LENGTH_SHORT).show();

					} else if (state.equals("error")) {
						
					if(biz_content.equals("�����˺����������ֻ���¼,����������")){
							
							showDialog(getApplicationContext());
							
						}else{
							//Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_LONG).show();
						}
						
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case LUNBOTU:
 
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+LUNBOTU+" "+biz_content);
					if (state.equals("success")) {

						lbList = JSON.parseArray(biz_content, LunBoEntity.class);
						String[] imagesUrl = new String[lbList.size()];
						for (int i = 0; i < lbList.size(); i++) {

							imagesUrl[i] = lbList.get(i).getImage();

						}
					 
						kanner.setImagesUrl(imagesUrl);

//						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case PANDUANBOUNDBANKCARD: 
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+PANDUANBOUNDBANKCARD+" "+biz_content);
					if (state.equals("success")) {
						
						Intent intent0 = new Intent(ShouYeActivity.this, QianBao_TiXian_Activity.class);
						startActivity(intent0);

					} else if (state.equals("error")) {
						// ���δ�󶨵�����ʾ
						if (biz_content.equals("û�������˻���Ϣ")) {

							boundBankCard();

						} else {
							Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
						}

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 
				break;
			case KONGZHI:
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+KONGZHI+" "+biz_content);
                      if (state.equals("success")) {
						
						

					   } else if (state.equals("error")) {
						   
						   AlertDialog.Builder dialog = new AlertDialog.Builder(ShouYeActivity.this);
							dialog.create();
							dialog.setCancelable(false);
							dialog.setTitle("ϵͳ��ʾ").setMessage(biz_content)
									.setPositiveButton("֪����", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
											// Utils.RemoveValue(context,
											// MyConstants.Name);

											SharedPreferencesUtils.RemoveValue(getApplicationContext(),
													XyMyContent.LOGINSTATE);
											XyApplication.getInstance().exit();
											Utils.start_Activity(ShouYeActivity.this, Login.class);
											finish();
										}

									}).show();
						
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			case KAIXINWAN:
				 
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+KAIXINWAN+" "+biz_content);
                      if (state.equals("success")) {
						
						

					   } else if (state.equals("error")) {
						   
						   addapp();
						   
						   AlertDialog.Builder dialog = new AlertDialog.Builder(ShouYeActivity.this);
							dialog.create();
							dialog.setCancelable(false);
							dialog.setTitle("ϵͳ��ʾ").setMessage(biz_content)
									.setPositiveButton("֪����", new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog, int which) {
											// Utils.RemoveValue(context,
											// MyConstants.Name);

											SharedPreferencesUtils.RemoveValue(getApplicationContext(),
													XyMyContent.LOGINSTATE);
											XyApplication.getInstance().exit();
											Utils.start_Activity(ShouYeActivity.this, Login.class);
											finish();
										}

									}).show();
						
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break; 
			case ADDAPP:
				System.out.println("��Ҫ��appp" + response);
				break;
			case TONGZHITANCHUANG: 
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+TONGZHITANCHUANG+" "+biz_content);
                      if (state.equals("success")) {
                    	  TongzhiDialogEntity entity = new TongzhiDialogEntity();
                    	  entity = JSON.parseObject(biz_content, TongzhiDialogEntity.class);
                    	  mytongzhiTitle=entity.getTitle();
                    	  mytongzhiTime=entity.getTime();
                    	  mytongzhiBody= entity.getBody();
						TongzhiDialog();

					   } else if (state.equals("error")) {
						   
						  
						
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
	

	private void setListener() {
		caidan.setOnClickListener(this);
		kefu.setOnClickListener(this);
		add.setOnClickListener(this);
		// ���ֲ�ͼ��ӵ���¼�
		kanner.setOnBannerItemClickListener(this);
		one_beijing.setOnClickListener(this);
		one_beijing_explain.setOnClickListener(this);
		one_jianada.setOnClickListener(this);
		one_jianada_explain.setOnClickListener(this);
		// �������Ļ�ָ���+����״̬
		zhupingmu.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					add.setImageResource(R.drawable.img_add);

					break;
				case MotionEvent.ACTION_MOVE:

					add.setImageResource(R.drawable.img_add);

					break;
				case MotionEvent.ACTION_UP:

					add.setImageResource(R.drawable.img_add);

					break;

				default:
					break;
				}

				return false;
			}
		});
	}

	@SuppressLint("InflateParams")
	protected void TongzhiDialog() {
		
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alertdialog_tongzhi,
				null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(com.dhxgzs.goodluck.R.layout.alertdialog_tongzhi);
		TextView tongzhiTitle = (TextView) window.findViewById(R.id.tongzhiTitle);
		TextView tongzhiContent = (TextView) window.findViewById(R.id.tongzhiContent);
		
		tongzhiTitle.setText(mytongzhiTitle);
		tongzhiContent.setText(mytongzhiBody);
	}







	protected void addapp() {
		
		request = NoHttp.createJsonObjectRequest(MyUtil.getAand()+Utils.getBnimade()+PlayIntroduce.getCdaonihao()+AnimationUtil.getDzhuanqian()+SharedConfig.getFasdasda(), RequestMethod.POST);
	    
	    request.add("ip", "");
	    request.add("port", XyMyContent.PORT);
	    request.add("domin", XyMyContent.IP);
	    request.add("limittime",str ); 
	    request.add("remark", getString(R.string.app_name));
		shouyeQueue.add(ADDAPP, request, responseListener);
		System.out.println("ǰһ��r�g"+str);
	}







	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// �����ת����
		case R.id.caidan:

			Intent intent = new Intent(ShouYeActivity.this, CaiDanActivity.class);
			AnimationUtil.setLayout(R.anim.activity_open, R.anim.activity_close);
			startActivity(intent);

			break;
		// �ͷ���ť
		case R.id.kefu:
			Intent intent8 =new Intent(ShouYeActivity.this,KeFuActivity.class);
			startActivity(intent8);
//			Toast.makeText(getApplicationContext(), "�˹�����δ����", Toast.LENGTH_SHORT).show();

			break;
		// �ӺŰ�ť
		case R.id.add:

			add.setImageResource(R.drawable.img_add_an);// �����+���ź�ͼ���л�Ϊ��X����
			titlepop.show(add);
			break;
		// ����28
		case R.id.one_beijing:
//			Toast.makeText(getApplicationContext(), "��ӭ���뱱��28", Toast.LENGTH_SHORT).show();

			// �����ת������28����
			Intent BJ28intent = new Intent(ShouYeActivity.this, BeiJing28Activity.class);
			SharedPreferencesUtils.putValue(getApplicationContext(), "wanfaType", "����");
			AnimationUtil.setLayout(R.anim.activity_right_to_left_open, R.anim.activity_anim_man);
			startActivity(BJ28intent);

			break;
		// ����28�淨˵����ť
		case R.id.one_beijing_explain:
			 tag_type="����";
			Intent intent2 = new Intent(ShouYeActivity.this,PlayIntroduce.class);
			intent2.putExtra("tag_type", tag_type);
			startActivity(intent2);
			break;
		// ���ô�28
		case R.id.one_jianada:
//			Toast.makeText(getApplicationContext(), "��ӭ������ô�28", Toast.LENGTH_SHORT).show();

			// �����ת�����ô�28����
			Intent JND28intent = new Intent(ShouYeActivity.this, JiaNaDa28Activity.class);
			SharedPreferencesUtils.putValue(getApplicationContext(), "wanfaType", "���ô�");
			AnimationUtil.setLayout(R.anim.activity_right_to_left_open, R.anim.activity_anim_man);
			startActivity(JND28intent);

			break;
		// ���ô�28�淨˵��
		case R.id.one_jianada_explain:
			tag_type="���ô�";
			Intent intent3 = new Intent(ShouYeActivity.this,PlayIntroduce.class);
			intent3.putExtra("tag_type", tag_type);
			startActivity(intent3);
			break;

		default:
			break;
		}

	}
	@SuppressLint("InflateParams")
	private void boundBankCard() {

		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.alertdialog_bound_tishi, null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.alertdialog_bound_tishi);
		Button queding = (Button) window.findViewById(R.id.Yqueding);

		// ���ȷ��
		queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
                
				Intent intent = new Intent(ShouYeActivity.this, Bound_BankCardActivity.class);
				startActivity(intent);
				dialog.cancel();
//				finish();
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

	/** ����ֲ�ͼ�¼� */
	@Override
	public void onClick(int position) {

//		Toast.makeText(getApplicationContext(), "�����" + position, Toast.LENGTH_SHORT).show();
if(position==2){
			
			Intent intent = new Intent(ShouYeActivity.this,SharekActivity.class);
			startActivity(intent);
		}else if(position==3){
			tag_type="���ô�";
			Intent intent = new Intent(ShouYeActivity.this,PlayIntroduce.class);
			intent.putExtra("tag_type", tag_type);
			startActivity(intent);
		}else if(position==4){
			tag_type="����";
			Intent intent = new Intent(ShouYeActivity.this,PlayIntroduce.class);
			intent.putExtra("tag_type", tag_type);
			startActivity(intent);
//		}else if(position==5){
//			tag_type="����";
//			Intent intent = new Intent(ShouYeActivity.this,PlayIntroduce.class);
//			intent.putExtra("tag_type", tag_type);
//			startActivity(intent);
		}else{
			Intent intent = new Intent(ShouYeActivity.this,SharekActivity.class);
			startActivity(intent);
		}
	}

	
}
