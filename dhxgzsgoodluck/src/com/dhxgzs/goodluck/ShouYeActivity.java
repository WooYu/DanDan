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
	/** 主屏幕 */
	private LinearLayout zhupingmu = null;
	/** 菜单按钮 */
	private ImageView caidan;

	/** 轮播图 */
	private Kanner kanner;

	/** 客服按钮 */
	private ImageView kefu;

	/** 加号按钮 */
	private ImageView add;
	/** 总赚取的元宝数 */
	private TextView one_yuanbao_Num;
	/** 赚取率 */
	private TextView one_baifenshu;
	/** 注册人数 */
	private TextView one_register_renshu_Num;
	
	/** 北京28 */
	private ImageView one_beijing;
	
	/** 北京28玩法说明按钮 */
	private TextView one_beijing_explain;
	/** 加拿大28 */
	private ImageView one_jianada;
	/** 加拿大28玩法说明按钮 */
	private TextView one_jianada_explain;
	private TitlePopup titlepop;
	private String tag_type;
	
	private Request<JSONObject> request;
	private RequestQueue shouyeQueue;
	
	/** 轮播图数据 */
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

	

	



	/** popwindow弹窗方法 */
	private void initpopwindow() {

		titlepop = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titlepop.setItemOnClickListener(onitemClick);
		// 给标题栏弹窗添加子类

		titlepop.addAction(new ActionItem(getApplicationContext(), "充值", R.drawable.add_chongzhi));
		titlepop.addAction(new ActionItem(getApplicationContext(), "提现", R.drawable.add_tixian));
		// titlepop.addAction(new ActionItem(getBaseContext(), "举报"));

		// titlepop.addAction();
		// titlepop.addAction();

	}
 
	/** 点击添加按钮 后充值和提现的监听 */
	private OnItemOnClickListener onitemClick = new OnItemOnClickListener() {

		@Override
		public void onItemClick(ActionItem item, int position) {
			// mLoadingDialog.show();
			switch (position) {
			case 0:// 充值

				add.setImageResource(R.drawable.img_add);// 点击充值按钮后“X”图片替换成“+”
				Intent intent = new Intent(ShouYeActivity.this, QianBaoRechargeActivity.class);
				startActivity(intent);

				break;
			case 1:// 提现

				add.setImageResource(R.drawable.img_add);// 点击提现按钮后“X”图片替换成“+”
				panduanBoundBankCard();
				
				break;

			}
		}
	};
	

	private void initView() {
		shouyeQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		dBefore = calendar.getTime();   //得到前一天的时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
		str = sdf.format(dBefore);    //格式化前一天
		kongzhi();
		shouYeDataRequest();
		LunBoRequest();
		LunBoILoveYou();
		Tongzhi();
//		picAndNickRequest();
		// 点击主屏幕变为“+”
		zhupingmu = (LinearLayout) findViewById(R.id.zhupingmu);
		// 菜单按钮
		caidan = (ImageView) findViewById(R.id.caidan);
		//
		kanner = (Kanner) findViewById(R.id.kanner);
		// // 把图片加载到view上
		// kanner.setImagesRes(imagesRes);
		// 客服按钮
		kefu = (ImageView) findViewById(R.id.kefu);
		// 加号按钮
		add = (ImageView) findViewById(R.id.add);
		// 总赚取的元宝数
		one_yuanbao_Num = (TextView) findViewById(R.id.one_yuanbao_Num);
		// 赚取率
		one_baifenshu = (TextView) findViewById(R.id.one_baifenshu);
		// 注册人数
		one_register_renshu_Num = (TextView) findViewById(R.id.one_register_renshu_Num);
		// 北京28
		one_beijing = (ImageView) findViewById(R.id.one_beijing);
		// 北京28玩法说明按钮
		one_beijing_explain = (TextView) findViewById(R.id.one_beijing_explain);
		// 加拿大28
		one_jianada = (ImageView) findViewById(R.id.one_jianada);
		// 加拿大28玩法说明
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

	/** 轮播图请求接口 */
	private void LunBoRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.SHOUYE_LUNBO_URL, RequestMethod.POST);

		shouyeQueue.add(LUNBOTU, request, responseListener);
	}

	/** 首页数据请求接口 */
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

//						Toast.makeText(getApplicationContext(), "获取成功", Toast.LENGTH_SHORT).show();

					} else if (state.equals("error")) {
						
					if(biz_content.equals("您的账号已在其他手机登录,您被迫下线")){
							
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
						// 如果未绑定弹窗提示
						if (biz_content.equals("没有提现账户信息")) {

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
							dialog.setTitle("系统提示").setMessage(biz_content)
									.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

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
							dialog.setTitle("系统提示").setMessage(biz_content)
									.setPositiveButton("知道了", new DialogInterface.OnClickListener() {

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
				System.out.println("你要的appp" + response);
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
		// 给轮播图添加点击事件
		kanner.setOnBannerItemClickListener(this);
		one_beijing.setOnClickListener(this);
		one_beijing_explain.setOnClickListener(this);
		one_jianada.setOnClickListener(this);
		one_jianada_explain.setOnClickListener(this);
		// 点击主屏幕恢复“+”号状态
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
		System.out.println("前一天時間"+str);
	}







	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 添加跳转动画
		case R.id.caidan:

			Intent intent = new Intent(ShouYeActivity.this, CaiDanActivity.class);
			AnimationUtil.setLayout(R.anim.activity_open, R.anim.activity_close);
			startActivity(intent);

			break;
		// 客服按钮
		case R.id.kefu:
			Intent intent8 =new Intent(ShouYeActivity.this,KeFuActivity.class);
			startActivity(intent8);
//			Toast.makeText(getApplicationContext(), "此功能暂未开放", Toast.LENGTH_SHORT).show();

			break;
		// 加号按钮
		case R.id.add:

			add.setImageResource(R.drawable.img_add_an);// 点击“+”号后图标切换为“X”号
			titlepop.show(add);
			break;
		// 北京28
		case R.id.one_beijing:
//			Toast.makeText(getApplicationContext(), "欢迎进入北京28", Toast.LENGTH_SHORT).show();

			// 点击跳转到北京28界面
			Intent BJ28intent = new Intent(ShouYeActivity.this, BeiJing28Activity.class);
			SharedPreferencesUtils.putValue(getApplicationContext(), "wanfaType", "北京");
			AnimationUtil.setLayout(R.anim.activity_right_to_left_open, R.anim.activity_anim_man);
			startActivity(BJ28intent);

			break;
		// 北京28玩法说明按钮
		case R.id.one_beijing_explain:
			 tag_type="北京";
			Intent intent2 = new Intent(ShouYeActivity.this,PlayIntroduce.class);
			intent2.putExtra("tag_type", tag_type);
			startActivity(intent2);
			break;
		// 加拿大28
		case R.id.one_jianada:
//			Toast.makeText(getApplicationContext(), "欢迎进入加拿大28", Toast.LENGTH_SHORT).show();

			// 点击跳转到加拿大28界面
			Intent JND28intent = new Intent(ShouYeActivity.this, JiaNaDa28Activity.class);
			SharedPreferencesUtils.putValue(getApplicationContext(), "wanfaType", "加拿大");
			AnimationUtil.setLayout(R.anim.activity_right_to_left_open, R.anim.activity_anim_man);
			startActivity(JND28intent);

			break;
		// 加拿大28玩法说明
		case R.id.one_jianada_explain:
			tag_type="加拿大";
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

		// 点击确定
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

	/** 点击轮播图事件 */
	@Override
	public void onClick(int position) {

//		Toast.makeText(getApplicationContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
if(position==2){
			
			Intent intent = new Intent(ShouYeActivity.this,SharekActivity.class);
			startActivity(intent);
		}else if(position==3){
			tag_type="加拿大";
			Intent intent = new Intent(ShouYeActivity.this,PlayIntroduce.class);
			intent.putExtra("tag_type", tag_type);
			startActivity(intent);
		}else if(position==4){
			tag_type="北京";
			Intent intent = new Intent(ShouYeActivity.this,PlayIntroduce.class);
			intent.putExtra("tag_type", tag_type);
			startActivity(intent);
//		}else if(position==5){
//			tag_type="北京";
//			Intent intent = new Intent(ShouYeActivity.this,PlayIntroduce.class);
//			intent.putExtra("tag_type", tag_type);
//			startActivity(intent);
		}else{
			Intent intent = new Intent(ShouYeActivity.this,SharekActivity.class);
			startActivity(intent);
		}
	}

	
}
