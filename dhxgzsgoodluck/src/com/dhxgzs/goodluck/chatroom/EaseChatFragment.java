package com.dhxgzs.goodluck.chatroom;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.dhxgzs.goodluck.KeFuActivity;
import com.dhxgzs.goodluck.Login;
import com.dhxgzs.goodluck.MainActivity;
import com.dhxgzs.goodluck.ShouYeActivity;
import com.dhxgzs.goodluck.activity.BettingRecord;
import com.dhxgzs.goodluck.activity.Bound_BankCardActivity;
import com.dhxgzs.goodluck.activity.PlayIntroduce;
import com.dhxgzs.goodluck.activity.TrendMap; 
import com.dhxgzs.goodluck.adapter.MyAdapter;
import com.dhxgzs.goodluck.adapter.WanFaGridViewAdapter;
import com.dhxgzs.goodluck.adapter.ZuiXinLotteryNotesAdapter;
import com.dhxgzs.goodluck.app.Constant;
import com.dhxgzs.goodluck.app.XyMyContent; 
import com.dhxgzs.goodluck.entiey.MinbettEntity;
import com.dhxgzs.goodluck.entiey.PeiLvEntity;
import com.dhxgzs.goodluck.entiey.PeiLvExplainEntity;
import com.dhxgzs.goodluck.entiey.UserInfoEntity;
import com.dhxgzs.goodluck.entiey.ZuiXinGuessingNotesEntity;
import com.dhxgzs.goodluck.entiey.ZuiXinLotteryNotesEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.view.ActionItem;
import com.dhxgzs.goodluck.view.CountdownView;
import com.dhxgzs.goodluck.view.PeiLvSMDialog;
import com.dhxgzs.goodluck.view.TitlePopup;
import com.dhxgzs.goodluck.view.CountdownView.OnCountdownEndListener;
import com.dhxgzs.goodluck.view.TitlePopup.OnItemOnClickListener;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessage.ChatType;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.hyphenate.easeui.ui.EaseBaseFragment;
import com.hyphenate.easeui.ui.EaseGroupRemoveListener;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseAlertDialog.AlertDialogUser;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.hyphenate.easeui.widget.EaseChatInputMenu.ChatInputMenuListener;
import com.hyphenate.easeui.widget.EaseChatMessageList;
import com.hyphenate.easeui.widget.EaseVoiceRecorderView;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil; 
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * you can new an EaseChatFragment to use or you can inherit it to expand. You
 * need call setArguments to pass chatType and userId <br/>
 * <br/>
 * you can see ChatActivity in demo for your reference
 *
 */
/**
 * @author Alex
 *
 */
public class EaseChatFragment extends EaseBaseFragment
implements EMMessageListener, OnClickListener, OnCountdownEndListener {
	protected static final String TAG = "EaseChatFragment";
	protected static final int REQUEST_CODE_MAP = 1;
	protected static final int REQUEST_CODE_CAMERA = 2;
	protected static final int REQUEST_CODE_LOCAL = 3;

	/**��СͶע����*/
	private static final int ZUIXIAOBET = 0;
	private RequestQueue minbettQueue;
	private List<MinbettEntity> minbetList;


	/**
	 * params to fragment
	 */
	protected Bundle fragmentArgs;
	protected int chatType;
	protected String toChatUsername;
	protected EaseChatMessageList messageList;
	protected EaseChatInputMenu inputMenu;

	protected EMConversation conversation;

	protected InputMethodManager inputManager;
	protected ClipboardManager clipboard;

	protected Handler handler = new Handler();
	protected File cameraFile;
	protected EaseVoiceRecorderView voiceRecorderView;
	protected SwipeRefreshLayout swipeRefreshLayout;
	protected ListView listView;

	protected boolean isloading;
	protected boolean haveMoreData = true;
	protected int pagesize = 20;
	protected GroupListener groupListener;
	protected EMMessage contextMeview1numessage;

	static final int ITEM_TAKE_PICTURE = 4;
	static final int ITEM_PICTURE = 5;
	static final int ITEM_LOCATION = 6;
	/**����˵��*/
	private static final int PEILVSHUOMING = 8;
	/**��������*/
	private static final int PEILVSHEZHI = 7;
	/**����10�ڿ�����¼*/
	private static final int LOTTERYNOTES = 9;
	/**���¾��¼�¼����*/
	private static final int ZUIXINGUESSINGNOTES = 10;
	/**Ͷעҳ������� */
	private static final int BETPAGEYUE = 11;
	/**�ж��Ƿ�Ͷע */
	private static final int PANDUANSHIFOUBET = 12;
	/**Ͷע*/
	private static final int BET = 13;
	/**�������� */
	private static final int KAIJIANGGENGXIN = 14;
	/**��С��ע*/
	private static final int ZUIXINLOTTER = 15;
	/**���뷿������*/
	private static final int JIONROOM = 16;
	/**�뿪����*/
	private static final int LEAVEROOM = 17;
	/**ӯ��*/
	private static final int YINGKUI = 18;

	protected int[] itemStrings = { R.string.attach_take_pic, R.string.attach_picture, R.string.attach_location };
	protected int[] itemdrawables = { R.drawable.ease_chat_takepic_selector, R.drawable.ease_chat_image_selector,
			R.drawable.ease_chat_location_selector };
	protected int[] itemIds = { ITEM_TAKE_PICTURE, ITEM_PICTURE, ITEM_LOCATION };
	private EMChatRoomChangeListener chatRoomChangeListener;
	private boolean isMessageListInited;
	protected MyItemClickListener extendMenuItemClickListener;

	// ע��㲥
	LocalBroadcastManager broadcastManager;
	private BroadcastReceiver mBroadcastReceiver;

	private PopupWindow popup;
	private ArrayList<Fragment> fragments;
	private ViewPager touzhu_viewPager;
	View view;
	private View view1;
	private View view2;
	private View view3;
	private ArrayList<View> viewList;
	private TextView daxiao;
	private int view1num;
	private int view3num;
	private int view2num;

	private TextView winningValues1,winningValues2,winningValues3;
	private TextView specialPlay_winningValues;
	private TextView view2_winningValues;
	private TextView odds_explain;
	/** Ͷע��� */
	private EditText touzhuMoney;
	/** Ͷע��ť */
	private TextView touzhu_btn;

	private String touzhu_num;
	private ImageView left_image;
	private TextView title;
	private ImageView right_image;
	private ImageView right_image1;
	private TitlePopup titlepop;
	private RelativeLayout touzhu_zhupingmu = null;
	private String fangjianType;
	private String wanfaType;
	private String vipType;
	private String account;
	private String imei;
	private Request<JSONObject> request;
	private RequestQueue touzhuQueue;

	private List<PeiLvEntity> plList,wanfa1list;
	/***�޸�  jiuyue*/
	/**�淨3������ �޸�Ϊ gridview*/
	private GridView wanfa1_gridview,wanfa2_gridview,wanfa3_gridview;
	private WanFaGridViewAdapter wanFaAdapter;
	private MyAdapter myadapter1,myadapter2,myadapter3;
	/** ����˵�����ʿؼ� */
	private List<PeiLvExplainEntity> peiLvExplainList;

	private String fangjian;
	/******************************************* ����10�ڿ�����¼ ******************************************/
	private LinearLayout ll2;
	private ListView lotteryNotes_Listview;
	private List<ZuiXinLotteryNotesEntity> zxlnList;
	private ZuiXinLotteryNotesAdapter zxlnAdapter;

	/******************************************* ����10�ڿ�����¼ ******************************************/
	/******************************************** ���¾��¼�¼ *******************************************************/
	private String expect;
	private String timeType;
	private TextView timeType2;
	private CountdownView timeType1;
	private RelativeLayout rllfengpanzhong;
	private LinearLayout lldaojishi;
	private TextView chatRoomExpect;
	/** ��ʱ�� */
	private TimeCount time;
	/** ���10�ڿ�����¼�������� */
	private TextView ten_lotteryNoteExcept;
	/** ���10�ڿ������� */
	private TextView zuixinkaijiangNum;
	/******************************************** ���¾��¼�¼ *******************************************************/
	private String getBttypeid;
	private String touZhuType;
	/******************************************** Ͷעҳ���ӿ� *******************************************************/
	private TextView touzhupage_yue;
	private String daojishiTag="2";
	private String nick_name;
	private String picImageUrl;
	/** ��ǰ�Ǹ�Ͷ��������Ͷע */
	private String gentou_touzhu_type;
	private String dgVipUrl;
	/**ӯ����ͷ*/
	private TextView yingkuijiantou;
	/******************************************** Ͷעҳ���ӿ� *******************************************************/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.ease_fragment_chat, container, false);

		setview();

		return view;
	}

	private void setview() {

		touzhuQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getActivity(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getActivity());
		fangjianType = SharedPreferencesUtils.getValue(getActivity(), "fangjianType");
		wanfaType = SharedPreferencesUtils.getValue(getActivity(), "wanfaType");
		vipType = SharedPreferencesUtils.getValue(getActivity(), "vipType");
		nick_name = SharedPreferencesUtils.getValue(getActivity(), "NICK_NAME");
		picImageUrl = SharedPreferencesUtils.getValue(getActivity(), "PIC");
		dgVipUrl = SharedPreferencesUtils.getValue(getActivity(), "DGVIP");
		System.out.println("������Ϣ" + wanfaType + fangjianType + vipType);
		// ���¾�������
		zuiXinGuessingRequest();
		// ��������
		peilvRequest();

		// ��ǰ�������
		touzhupagerYue();
		// ҳ���м���ʾ��һ������
		zuiXinLotteryRequest();
		//ӯ������
		yingkuiRequest();
		System.out.println("imei2" + imei);
		// ע��㲥������ ��������EaseChatPrimaryMenu �Ĺ㲥��ͼ
		broadcastManager = LocalBroadcastManager.getInstance(getActivity());
		mBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals("TOU_ZHU_BTN")) {
					// Toast.makeText(getActivity().getApplicationContext(),
					// "���", Toast.LENGTH_SHORT).show();
					// Ͷע����
					System.out.println("���������������������������������");
					// Toast.makeText(getActivity(), "���",
					// Toast.LENGTH_SHORT).show();
					initPopupWindow();

				} else {
					// Toast.makeText(getActivity().getApplicationContext(),
					// "�ٴε��", Toast.LENGTH_SHORT).show();
				}

			}

		};

		// �㲥������
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction("TOU_ZHU_BTN");
		broadcastManager.registerReceiver(mBroadcastReceiver, mFilter);// ע�������
		// " + " �ŵ���
		initpopwindow();

		left_image = (ImageView) view.findViewById(com.dhxgzs.goodluck.R.id.left_image);
		title = (TextView) view.findViewById(com.dhxgzs.goodluck.R.id.title);
		right_image = (ImageView) view.findViewById(com.dhxgzs.goodluck.R.id.right_image);
		right_image1 = (ImageView) view.findViewById(com.dhxgzs.goodluck.R.id.right_image1);
		touzhu_zhupingmu = (RelativeLayout) view.findViewById(com.dhxgzs.goodluck.R.id.touzhu_zhupingmu);
		ll2 = (LinearLayout) view.findViewById(com.dhxgzs.goodluck.R.id.ll2);
		// ������
		timeType2 = (TextView) view.findViewById(com.dhxgzs.goodluck.R.id.timeType2);
		// ����ʱ
		timeType1 = (CountdownView) view.findViewById(com.dhxgzs.goodluck.R.id.timeType1);
		// ��ǰͶע����
		chatRoomExpect = (TextView) view.findViewById(com.dhxgzs.goodluck.R.id.chatRoomExpect);
		// �����д�ؼ�
		rllfengpanzhong = (RelativeLayout) view.findViewById(com.dhxgzs.goodluck.R.id.rllfengpanzhong);
		// ����ʱ��ؼ�
		lldaojishi = (LinearLayout) view.findViewById(com.dhxgzs.goodluck.R.id.lldaojishi);
		// ���10�ڿ�����¼��������
		ten_lotteryNoteExcept = (TextView) view.findViewById(com.dhxgzs.goodluck.R.id.ten_lotteryNoteExcept);
		zuixinkaijiangNum = (TextView) view.findViewById(com.dhxgzs.goodluck.R.id.zuixinkaijiangNum);
		// Ͷעҳ�����
		touzhupage_yue = (TextView) view.findViewById(com.dhxgzs.goodluck.R.id.touzhupage_yue);
		yingkuijiantou =(TextView) view.findViewById(com.dhxgzs.goodluck.R.id.yingkuijiantou);
		timeType1.setOnCountdownEndListener(this);
		ll2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ����10�ڿ�����¼
				LotteryNotePopupWindow();

			}

		});
		touzhu_zhupingmu.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:

					titlepop.dismiss();
					right_image.setImageResource(com.dhxgzs.goodluck.R.drawable.img_add);

					break;
				case MotionEvent.ACTION_MOVE:
					titlepop.dismiss();
					right_image.setImageResource(com.dhxgzs.goodluck.R.drawable.img_add);

					break;
				case MotionEvent.ACTION_UP:
					titlepop.dismiss();
					right_image.setImageResource(com.dhxgzs.goodluck.R.drawable.img_add);

					break;

				default:
					break;
				}
				return false;
			}
		});

	}


	/**
	 * ����10�ڿ�����¼
	 */
	private void zuiXinLotteryRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINLOTTERYNOTES_URL, RequestMethod.POST);
		String type = "10" ;
		if (wanfaType.equals("����")) {
			type = "10";
		} else if (wanfaType.equals("���ô�")) {
			type = "11";
		}
		request.add("type", type);
		request.add("imei", imei);
		request.add("account", account);
		touzhuQueue.add(ZUIXINLOTTER, request, responseListener);

	}

	/** Ͷעҳ������� */
	private void touzhupagerYue() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.ACCOUNTMONEY_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		touzhuQueue.add(BETPAGEYUE, request, responseListener);

	}

	/**
	 * ���¾��¼�¼����
	 */
	private void zuiXinGuessingRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINGUESSINGNOTES_URL, RequestMethod.POST);

		
		if (wanfaType.equals("����")) {

			request.add("type", "10");
		} else if (wanfaType.equals("���ô�")) {
			request.add("type", "11");
		}

		request.add("imei", imei);
		request.add("account", account);
		touzhuQueue.add(ZUIXINGUESSINGNOTES, request, responseListener);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		fragmentArgs = getArguments();
		// check if single chat or group chat
		chatType = fragmentArgs.getInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
		// userId you are chat with or group id
		toChatUsername = fragmentArgs.getString(EaseConstant.EXTRA_USER_ID);

		super.onActivityCreated(savedInstanceState);
	}

	/**
	 * init view
	 */
	protected void initView() {
		voiceRecorderView = (EaseVoiceRecorderView) getView().findViewById(R.id.voice_recorder);
		messageList = (EaseChatMessageList) getView().findViewById(R.id.message_list);
		// if (chatType != EaseConstant.CHATTYPE_SINGLE)
		// ��ʾ�û��ǳ�
		messageList.setShowUserNick(true);

		listView = messageList.getListView();

		extendMenuItemClickListener = new MyItemClickListener();
		inputMenu = (EaseChatInputMenu) getView().findViewById(R.id.input_menu);
		registerExtendMenuItem();
		// init input menu
		inputMenu.init(null);
		// inputMenu.setCustomEmojiconMenu();
		inputMenu.setChatInputMenuListener(new ChatInputMenuListener() {

			@Override
			public void onSendMessage(String content) {
				sendTextMessage(content);
			}

			@Override
			public void onBigExpressionClicked(EaseEmojicon emojicon) {
				sendBigExpressionMessage(emojicon.getName(), emojicon.getIdentityCode());
			}

			@Override
			public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}

		});

		swipeRefreshLayout = messageList.getSwipeRefreshLayout();
		swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light,
				R.color.holo_orange_light, R.color.holo_red_light);

		inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@SuppressLint("InflateParams")
	private void LotteryNotePopupWindow() {

		View v = getActivity().getLayoutInflater().inflate(com.dhxgzs.goodluck.R.layout.lotterynotes_popupwindow,
				null);
		popup = new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT, 500, true);

		lotteryNotes_Listview = (ListView) v.findViewById(com.dhxgzs.goodluck.R.id.lotteryNotes_Listview);
		// ������¼����
		LotteryNoteRequest();

		popup.setFocusable(true);
		// ����������Ϊtrue�����ڵ����Ļ�Ŀհ�λ��Ҳ���˳�
		popup.setTouchable(true);
		popup.setFocusable(true);
		popup.setBackgroundDrawable(new BitmapDrawable());
		popup.setOutsideTouchable(true);
		popup.showAsDropDown(ll2, 0, 0, Gravity.CENTER);

	}

	/**
	 * ����10�ڿ�����¼
	 */
	private void LotteryNoteRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINLOTTERYNOTES_URL, RequestMethod.POST);
		String type = "10";
		if (wanfaType.equals("����")) {
			type = "10";
		} else if (wanfaType.equals("���ô�")) {
			type = "11";
		}
 
		request.add("type", type);
		request.add("imei", imei);
		request.add("account", account);
		touzhuQueue.add(LOTTERYNOTES, request, responseListener);
	}

	/**
	 * �������ýӿ�
	 */
	private void peilvRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.PEILVSHEZHI_URL, RequestMethod.POST);
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

		touzhuQueue.add(PEILVSHEZHI, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case PEILVSHEZHI:
				try {
				 
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+PEILVSHEZHI+""+biz_content);

					if (state.equals("success")) {

						plList = JSON.parseArray(biz_content, PeiLvEntity.class);

					} else if (state.equals("error")) {

						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case PEILVSHUOMING:
				 
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+PEILVSHUOMING+""+biz_content);
					if (state.equals("success")) {

						peiLvExplainList = JSON.parseArray(biz_content, PeiLvExplainEntity.class);
						//zanDlerlog();
						PeiLvSMDialog.getInstance(getActivity()).zanDlerlog(getActivity(), peiLvExplainList);
					} else if (state.equals("error")) {

						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case LOTTERYNOTES:
 

				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+PEILVSHEZHI+""+biz_content);
					if (state.equals("success")) {

						zxlnList = JSON.parseArray(biz_content, ZuiXinLotteryNotesEntity.class);

						zxlnAdapter = new ZuiXinLotteryNotesAdapter(getContext(), zxlnList);

						lotteryNotes_Listview.setAdapter(zxlnAdapter);
						//ten_lotteryNoteExcept.setText(zxlnList.get(0).getExpect());
						

					} else if (state.equals("error")) {

						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case ZUIXINGUESSINGNOTES:
 
				try {

					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+LOTTERYNOTES+""+biz_content);
					 
					if (state.equals("success")) {
 

						ZuiXinGuessingNotesEntity zEntity = new ZuiXinGuessingNotesEntity();

						zEntity = JSON.parseObject(biz_content, ZuiXinGuessingNotesEntity.class);

						expect = zEntity.getExpect();
						timeType = zEntity.getTime();
						/* ten_lotteryNoteExcept.setText(expect);
						 zuixinkaijiangNum.setText(text)*/
						 
						chatRoomExpect.setText(expect);

						if (timeType.equals("��ͣ��")) {

							rllfengpanzhong.setVisibility(View.VISIBLE);
							lldaojishi.setVisibility(View.GONE);
							timeType2.setText(timeType);

						} else if (timeType.equals("ά����")) {
							time = new TimeCount(2000, 1000);
							time.start();
							rllfengpanzhong.setVisibility(View.VISIBLE);
							lldaojishi.setVisibility(View.GONE);
							timeType2.setText(timeType);

						} else {

							int fengpanTime = Integer.parseInt(timeType);
							if (fengpanTime <= 30) {

								lldaojishi.setVisibility(View.GONE);
								rllfengpanzhong.setVisibility(View.VISIBLE);
								timeType2.setText("������");
								time = new TimeCount(32000, 1000);
								time.start();
							} else if (fengpanTime > 30) {

								rllfengpanzhong.setVisibility(View.GONE);
								lldaojishi.setVisibility(View.VISIBLE);
								daojishiTag = "1";
								Long long1 = Long.parseLong(String.valueOf(fengpanTime - 30));

								timeType1.start(long1 * 1000);

							}
						}

					} else if (state.equals("error")) {
						if (getActivity() == null) {
							Log.d("test","getActivityΪ��");
						} else {
							Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();
							Log.d("test","���¾��¼�¼����"+biz_content);
						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case BETPAGEYUE:

				try {
				 
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+BETPAGEYUE+""+biz_content);
					if (state.equals("success")) {

						UserInfoEntity entity = new UserInfoEntity();

						entity = JSON.parseObject(biz_content, UserInfoEntity.class);
						touzhupage_yue.setText(entity.getMoney());
					} else if (state.equals("error")) {
						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case PANDUANSHIFOUBET:
 
				try {

					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+PANDUANSHIFOUBET+""+biz_content);
					if (state.equals("success")) {
 

						ZuiXinGuessingNotesEntity zEntity = new ZuiXinGuessingNotesEntity();

						zEntity = JSON.parseObject(biz_content, ZuiXinGuessingNotesEntity.class);

						expect = zEntity.getExpect();
						timeType = zEntity.getTime();
						// ten_lotteryNoteExcept.setText(expect);
						// chatRoomExpect.setText(expect);
						if (timeType.equals("��ͣ��")) {
							Toast.makeText(getActivity(), "��ͣ�۲���Ͷע", Toast.LENGTH_SHORT).show();
						} else if (timeType.equals("ά����")) {
							Toast.makeText(getActivity(), "ά���в���Ͷע", Toast.LENGTH_SHORT).show();
						} else {

							int fengpanTime = Integer.parseInt(timeType);
							Log.d("test", "timeType:"+fengpanTime);
							if (fengpanTime <= 30) {

								Toast.makeText(getActivity(), "�����в���Ͷע", Toast.LENGTH_SHORT).show();
							} else if (fengpanTime > 30) {
								if (gentou_touzhu_type.equals("����Ͷע")) {
									touzhu_num = touzhuMoney.getText().toString();
								   if(touzhu_num == null || touzhu_num.length() <= 0)  {
										
									}
								}
								// String content = "�� 85665222 �� " + "Ͷע���� ˫ "
								// +
								// "Ͷע��� " + touzhu_num;
/*								System.out.println("Ͷע����id���������е��ֶζ�Ӧ:" + getBttypeid);
								System.out.println("������������(����,�м�,�߼�):" + fangjianType);
								System.out.println("vip�������ƣ�vip1,vip2,vip3,vip4��:" + vipType);
								System.out.println("�˺�:" + account);
								System.out.println("�ֻ�Ψһ��ʶ:" + imei);
								System.out.println("���:" + touzhu_num);
								System.out.println("�ں�:" + expect);*/
								betRequest(getBttypeid, fangjianType, vipType, account, imei, touzhu_num, expect);

							}
						}

					} else if (state.equals("error")) {

						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case BET:
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+BET+""+biz_content);
					if (state.equals("success")) {
						String content = expect + "��" + "    Ͷע����:   " + touZhuType + "\n"+ "Ͷע���:     " + touzhu_num + "Ԫ��";
						sendTextMessage(content);
						touzhupagerYue();
						// Ͷע���ȡ��ǰ����һ�ڿ���ʱ��+30�����µ�ǰҳ�����
						KaijiangRequest();
						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

					} else if (state.equals("error")) {

						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case KAIJIANGGENGXIN:
				//System.out.println("��Ҫ�ǻ�ȡ��ǰ����ʱʱ��" + response);
				try {

					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+KAIJIANGGENGXIN+""+biz_content);
					if (state.equals("success")) {

						ZuiXinGuessingNotesEntity zEntity = new ZuiXinGuessingNotesEntity();

						zEntity = JSON.parseObject(biz_content, ZuiXinGuessingNotesEntity.class);

						expect = zEntity.getExpect();
						timeType = zEntity.getTime();

						int currentTime = Integer.parseInt(timeType);
						// Ͷע���ȡ��ǰ����һ�ڿ���ʱ��+30�����µ�ǰҳ�����
						daojishiTag = "2";
						Log.d("test","Ͷע���ȡ�ĵ���ʱ" + timeType);
						time = new TimeCount((currentTime * 1000) + 45000, 1000);

						time.start();

					} else if (state.equals("error")) {

						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();
						Log.d("test","��ȡ��ǰ����:" + biz_content);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case ZUIXINLOTTER:
				//System.out.println("������Ļ�м������ʾ��ǰ�����Ϳ�������" + response);

				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					Log.d("test", "biz_content"+ZUIXINLOTTER+""+biz_content);
					if (state.equals("success")) {

						zxlnList = JSON.parseArray(biz_content, ZuiXinLotteryNotesEntity.class);

						ten_lotteryNoteExcept.setText(zxlnList.get(0).getExpect());
						zuixinkaijiangNum.setText(zxlnList.get(0).getResult());
						//System.out.println(zxlnList.get(0).getExpect() + ":" + zxlnList.get(0).getResult());
					} else if (state.equals("error")) {

						Toast.makeText(getActivity(), biz_content, Toast.LENGTH_SHORT).show();
						
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case JIONROOM:
				Log.d("test","��Ա���뷿��:" + response);
			//	System.out.println("��Ա���뷿��" + response);
				break;
			case LEAVEROOM:
				Log.d("test","��Ա�뿪����:" + response);
				//System.out.println("��Ա�뿪����" + response);
				break;
			case YINGKUI:
				Log.d("test","ӯ��ָʾ����:" + response);
				//System.out.println("ӯ��ָʾ����"+response);

				try {
					String state =response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					String status = response.get().getString("status");
					Log.d("test", "biz_content"+YINGKUI+""+biz_content);
					if(state.equals("success")){
						//						if (getActivity() == null) {
						//							System.out.println("getActivityΪ��");
						//						} else {
						if(status.equals("1")){
							yingkuijiantou.setTextColor(Color.GREEN);
							yingkuijiantou.setText(biz_content);
						}else if(status.equals("2")){
							yingkuijiantou.setText(biz_content);
							yingkuijiantou.setTextColor(Color.RED);
						}else{
							yingkuijiantou.setText(biz_content);
						}
						//						}

					}else if(state.equals("error")){

						showChatroomToast(biz_content);

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
			Log.d("test","�쳣��Ϣ:" + exception); 
			

		}
	};

	/** ��ǰ��������ʱ */
	@Override
	public void onEnd(CountdownView cv) {
		rllfengpanzhong.setVisibility(View.VISIBLE);
		lldaojishi.setVisibility(View.GONE);
		daojishiTag = "1";
		time = new TimeCount(32000, 1000);
		time.start();
		// Toast.makeText(getActivity(), "����", Toast.LENGTH_SHORT).show();

	}

	/**
	 * ������������
	 */
	protected void KaijiangRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINGUESSINGNOTES_URL, RequestMethod.POST);

		if (wanfaType.equals("����")) {

			request.add("type", "10");
		} else if (wanfaType.equals("���ô�")) {
			request.add("type", "11");
		}

		request.add("imei", imei);
		request.add("account", account);
		touzhuQueue.add(KAIJIANGGENGXIN, request, responseListener);

	}

	/**
	 * Ͷע
	 * 
	 * @param getBttypeid2
	 *            �˺�
	 * @param fangjianType2
	 *            ������������(����,�м�,�߼�)
	 * @param vipType2
	 *            vip�������ƣ�vip1,vip2,vip3,vip4��
	 * @param account2
	 *            �ں�
	 * @param imei2
	 *            Ͷע����id���������е��ֶζ�Ӧ��
	 * @param touzhu_num2
	 *            ���
	 * @param expect2
	 *            �ֻ�Ψһ��ʶ
	 */
	protected void betRequest(String getBttypeid2, String fangjianType2, String vipType2, String account2, String imei2,
			String touzhu_num2, String expect2) {

		request = NoHttp.createJsonObjectRequest(XyMyContent.BET_URL, RequestMethod.POST);

		request.add("account", account2);
		request.add("room", fangjianType2);
		request.add("vip", vipType2);
		request.add("expect", expect2);
		request.add("bttypeid", getBttypeid2);

		request.add("money", touzhu_num2);
		request.add("imei", imei2);

		touzhuQueue.add(BET, request, responseListener);

	}

	private int width;
	private int height;
	private int xWidth;
	private int xHeight;
	private TextView zuixiao_bet;
	private TextView both_bet;
	private ImageView lfanyeBtn,rfanyeBtn; 
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	protected void initPopupWindow() {
		// if (popup == null) {
		// LayoutInflater layoutInflater = (LayoutInflater) getActivity()
		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// View view =
		// layoutInflater.inflate(R.layout.popwindow_layout,null);
		// ����һ��PopuWidow����
		// popWindow = new
		// PopupWindow(view,LinearLayout.LayoutParams.FILL_PARENT, 200);
		if (getActivity() == null) {
			Log.d("test", "getActivityΪ��"); 
		} else {
			Log.d("test", "getActivity��Ϊ��");  
			width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
			height = getActivity().getWindowManager().getDefaultDisplay().getHeight();

			View v = getActivity().getLayoutInflater().inflate(R.layout.popu_touzhu, null);
			popup = new PopupWindow(v, width - 20, height / 2, true);
			// }
			touzhu_viewPager = (ViewPager) v.findViewById(R.id.touzhu_viewpager);
			odds_explain = (TextView) v.findViewById(com.dhxgzs.goodluck.R.id.odds_explain);
			touzhuMoney = (EditText) v.findViewById(com.dhxgzs.goodluck.R.id.touzhuMoney);
			touzhu_btn = (TextView) v.findViewById(com.dhxgzs.goodluck.R.id.touzhu_btn);
			zuixiao_bet = (TextView) v.findViewById(com.dhxgzs.goodluck.R.id.zuixiao_bet);
			both_bet = (TextView) v.findViewById(com.dhxgzs.goodluck.R.id.both_bet);
			lfanyeBtn = (ImageView) v.findViewById(com.dhxgzs.goodluck.R.id.lfanyeBtn);
			rfanyeBtn = (ImageView) v.findViewById(com.dhxgzs.goodluck.R.id.rfanyeBtn);


			zuixiao_bet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					/**
					 * 1����������
					 * 
					 * 2����������
					 * 3������fangjianType �ֱ�д��touzhuMoneyֵ
					 * 
					 */
					request = NoHttp.createJsonObjectRequest(XyMyContent.ROOM, RequestMethod.POST);

					request.add("account", account);
					request.add("imei", imei);

					touzhuQueue.add(ZUIXIAOBET, request, responseListener);

				}
				//�������� �ֱ�д�� ���ݸ�ʽ���£�
				/**
				 * {"state":"success","biz_content":[
				 * {"lotteryname":"����28","roomname":"������","maxuser":500,"vipmaxuser":200,"backrate":1,"enter":0,"minbett":10},
				 * {"lotteryname":"����28","roomname":"�м���","maxuser":500,"vipmaxuser":200,"backrate":18,"enter":0,"minbett":50},
				 * {"lotteryname":"����28","roomname":"�߼���","maxuser":500,"vipmaxuser":200,"backrate":18,"enter":10000,"minbett":500},
				 * {"lotteryname":"���ô�28","roomname":"������","maxuser":500,"vipmaxuser":200,"backrate":1,"enter":0,"minbett":10},
				 * {"lotteryname":"���ô�28","roomname":"�м���","maxuser":500,"vipmaxuser":200,"backrate":18,"enter":0,"minbett":50},
				 * {"lotteryname":"���ô�28","roomname":"�߼���","maxuser":500,"vipmaxuser":200,"backrate":18,"enter":10000,"minbett":500}]}

				 */
				OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

					@Override
					public void onSucceed(int what, Response<JSONObject> response) {
						try {
							String state = response.get().getString("state");
							String biz_content = response.get().getString("biz_content");
							if (state.equals("success")) {
								Log.d("test", "��С��ע���ݣ�"+biz_content);

								minbetList = JSON.parseArray(biz_content, MinbettEntity.class);

								if (fangjianType.equals("����")){
									touzhuMoney.setText(minbetList.get(0).getMinbett());

								}else if (fangjianType.equals("�м�")){

									touzhuMoney.setText(minbetList.get(1).getMinbett());

								} else if (fangjianType.equals("�߼�")) {

									touzhuMoney.setText(minbetList.get(2).getMinbett());

								}
								Log.d("test", "minbett="+touzhuMoney.toString());

							} else if (state.equals("error")) {
								Toast.makeText(getContext(), biz_content, Toast.LENGTH_SHORT).show();
							}

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

					}
				};


			});


			both_bet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (!touzhuMoney.getText().toString().isEmpty()) {

						int a = Integer.parseInt(touzhuMoney.getText().toString());
						int b = a * 2;
						touzhuMoney.setText(String.valueOf(b));
					}

				}
			});
			touzhu_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					gentou_touzhu_type = "����Ͷע";
					DetermineWhetherBetRequest();
					popup.dismiss();
					popup = null;
				}
			});
			odds_explain.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					oddsExplanRequest();

				}
			});

			popup.setFocusable(true);
			// ����������Ϊtrue�����ڵ����Ļ�Ŀհ�λ��Ҳ���˳�
			popup.setTouchable(true);
			popup.setFocusable(true);
			popup.setBackgroundDrawable(new BitmapDrawable());
			popup.setOutsideTouchable(true);
			popup.showAtLocation(inputMenu, Gravity.BOTTOM, 0, 0);
			if (plList != null) {
				addinitview();// ��Ӳ��ַ���
			}
			lfanyeBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(touzhu_viewPager.getCurrentItem() != 0){ 
						Log.d("test", "��");
						touzhu_viewPager.setCurrentItem(touzhu_viewPager.getCurrentItem() - 1, true);
					    } 
				}
			});
			rfanyeBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(touzhu_viewPager.getCurrentItem() != 2){ 
						Log.d("test", "��");
						touzhu_viewPager.setCurrentItem(touzhu_viewPager.getCurrentItem() + 1, true);
					    }   
				}
			});
		}
	}

	protected void DetermineWhetherBetRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.ZUIXINGUESSINGNOTES_URL, RequestMethod.POST);

		if (wanfaType.equals("����")) {

			request.add("type", "10");
		} else if (wanfaType.equals("���ô�")) {
			request.add("type", "11");
		}

		request.add("imei", imei);
		request.add("account", account);
		touzhuQueue.add(PANDUANSHIFOUBET, request, responseListener);

	}
	/**
	 * Ͷע��������˵��
	 */
	protected void oddsExplanRequest() {

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

		touzhuQueue.add(PEILVSHUOMING, request, responseListener);
	}

	@SuppressLint({ "InflateParams", "NewApi" })
	private void addinitview() {

		LayoutInflater inflater = getActivity().getLayoutInflater();
		view1 = inflater.inflate(com.dhxgzs.goodluck.R.layout.fragment_dxds, null);
		view2 = inflater.inflate(com.dhxgzs.goodluck.R.layout.fragment_csz, null);
		view3 = inflater.inflate(com.dhxgzs.goodluck.R.layout.fragment_specialplay, null);

		// view1 �ؼ���ʼ��
		wanfa1_gridview = (GridView)view1.findViewById(com.dhxgzs.goodluck.R.id.wanfa1_gridView);
		winningValues1 = (TextView) view1.findViewById(com.dhxgzs.goodluck.R.id.winningValues1);
		wanfa1_gridview.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);// ����Ϊ��ѡģʽ
		wanfa1list=plList.subList(0,10);

		myadapter1 = new MyAdapter(wanfa1list,getContext());
		wanfa1_gridview.setAdapter(myadapter1);
		wanfa1_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// ����������
				myadapter1.setSeclection(arg2);
				myadapter1.notifyDataSetChanged();

				Log.d("test","����ˣ�"+arg2+"item:"+plList.get(arg2).getItem()+"odds:"+plList.get(arg2).getBttypeid());
				getBttypeid = plList.get(arg2).getBttypeid();
				touZhuType = plList.get(arg2).getItem();
				winningValues1.setText(plList.get(arg2).getNums());
			}

		});
		 
		wanfa2_gridview = (GridView)view2.findViewById(com.dhxgzs.goodluck.R.id.wanfa2_gridView);
		winningValues2 = (TextView) view2.findViewById(com.dhxgzs.goodluck.R.id.winningValues2);
		List<PeiLvEntity> wanfa2list=plList.subList(10,38);

		myadapter2 = new MyAdapter(wanfa2list,getContext());
		wanfa2_gridview.setAdapter(myadapter2);
		wanfa2_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// ����������
				myadapter2.setSeclection(arg2);
				myadapter2.notifyDataSetChanged();

				arg2+=10;
				Log.d("test","����ˣ�"+arg2+"item:"+plList.get(arg2).getItem()+"odds:"+plList.get(arg2).getBttypeid());
				getBttypeid = plList.get(arg2).getBttypeid();
				touZhuType = plList.get(arg2).getItem();
				winningValues2.setText(plList.get(arg2).getNums());
			}

		});
 

		wanfa3_gridview = (GridView)view3.findViewById(com.dhxgzs.goodluck.R.id.wanfa3_gridView);
		winningValues3 = (TextView) view3.findViewById(com.dhxgzs.goodluck.R.id.winningValues3);
		List<PeiLvEntity> wanfa3list=plList.subList(38,42);

		myadapter3 = new MyAdapter(wanfa3list,getContext());
		wanfa3_gridview.setAdapter(myadapter3);
		wanfa3_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// ����������
				myadapter3.setSeclection(arg2);
				myadapter3.notifyDataSetChanged();

				arg2+=38;
				Log.d("test","����ˣ�"+arg2+"item:"+plList.get(arg2).getItem()+"odds:"+plList.get(arg2).getBttypeid());
				getBttypeid = plList.get(arg2).getBttypeid();
				touZhuType = plList.get(arg2).getItem();
				winningValues3.setText(plList.get(arg2).getNums());
			}

		});
	 
		// ���ü�������
		viewList = new ArrayList<View>();// ��Ҫ��ҳ��ʾ��Viewװ��������
		viewList.add(view1);
		viewList.add(view2);
		viewList.add(view3);

		PagerAdapter pagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return viewList.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				// TODO Auto-generated method stub
				container.removeView(viewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				container.addView(viewList.get(position));

				return viewList.get(position);
			}
		};

		touzhu_viewPager.setAdapter(pagerAdapter);
		touzhu_viewPager.setCurrentItem(0); 
		
	}
	  
	protected void setUpView() {
		title.setText(toChatUsername);
		if (chatType == EaseConstant.CHATTYPE_SINGLE) {
			// set title
			if (EaseUserUtils.getUserInfo(toChatUsername) != null) {
				EaseUser user = EaseUserUtils.getUserInfo(toChatUsername);
				if (user != null) {
					title.setText(user.getNick());
				}
			}
			// titleBar.setRightImageResource(R.drawable.ease_mm_title_remove);
		} else {
			// Ⱥ��titleBar �Ҳ��ͼƬ
			// titleBar.setRightImageResource(com.qiangudhx.xingyun28.R.drawable.add);
			// titleBar.setLeftImageResource(com.qiangudhx.xingyun28.R.drawable.back);
			// if (chatType == EaseConstant.CHATTYPE_GROUP) {
			// // group chat
			// EMGroup group =
			// EMClient.getInstance().groupManager().getGroup(toChatUsername);
			// if (group != null)
			// titleBar.setTitle(group.getGroupName());
			// // listen the event that user moved out group or group is
			// // dismissed
			// groupListener = new GroupListener();
			// EMClient.getInstance().groupManager().addGroupChangeListener(groupListener);
			// } else {
			onChatRoomViewCreation();
			// }

		}
		if (chatType != EaseConstant.CHATTYPE_CHATROOM) {
			onConversationInit();
			onMessageListInit();
		}

		// ���ذ�ť
		left_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		// ��Ӱ�ť
		right_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (chatType == EaseConstant.CHATTYPE_SINGLE) {
					emptyHistory();
				} else {

					titlepop.show(right_image);
					right_image.setImageResource(com.dhxgzs.goodluck.R.drawable.img_add_an);
					toGroupDetails();
				}
			}
		});
		// �ͷ���ť
		right_image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Toast.makeText(getActivity(), "�˹�����δ����",
				// Toast.LENGTH_LONG).show();
				//				showChatroomToast("�˹�����δ����");
				Intent intent =new Intent(getActivity(),KeFuActivity.class);
				startActivity(intent);
			}
		});

		setRefreshLayoutListener();

		// show forward message if the message is not null
		String forward_msg_id = getArguments().getString("forward_msg_id");
		if (forward_msg_id != null) {
			forwardMessage(forward_msg_id);
		}
		/** �����������ĸ��ֿؼ����� */
		setChatFragmentListener(new EaseChatFragmentHelper() {
			/**
			 * ������Ϣ��չ����
			 */
			@Override
			public void onSetMessageAttributes(EMMessage message) {
				// TODO Auto-generated method stub

				// ����Ҫ������չ��Ϣ�û��ǳ�
				message.setAttribute(Constant.USER_NAME, nick_name);
				// ���ͳ�ȥ���Ñ����^��
				message.setAttribute(Constant.HEAD_IMAGE_URL, picImageUrl);
				//���ͳ�ȥ���û��ȼ�
				message.setAttribute(Constant.USER_VIP, dgVipUrl);
			}

			/**
			 * �����Զ���chatrow�ṩ��
			 * 
			 * @return
			 */
			@Override
			public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
				// TODO Auto-generated method stub
				return null;
			}

			/**
			 * ��Ϣ���ݿ򳤰��¼�
			 */
			@Override
			public void onMessageBubbleLongClick(EMMessage message) {
				// TODO Auto-generated method stub

			}

			/**
			 * ��Ϣ���ݿ����¼�
			 */
			@Override
			public boolean onMessageBubbleClick(EMMessage message) {

				if (!message.getUserName().equals("8001") && !message.getUserName().equals("8002")&&!message.getUserName().equals("8003")) {
 
					// �������276112888937054736
					// 276112888643453468
					// ��ȡ������ڵ���Ϣ
					String fString = message.getBody().toString();
					// �����û�пո��ַ���
					String aaaa = fString.replace(" ", "");
					// ȥ�����з����
					String bbbb = aaaa.replace("\n", "");
					// ȥ��ð��
					String cccc = bbbb.replace(":", "");
					// ȥ��˫���ŷ����
					String dddd = cccc.replace("\"", "");
					System.out.println("�����û�пո��ַ���   " + aaaa);
					System.out.println("ȥ�����з����  " + bbbb);
					// txt:"808889��Ͷע����:С��Ͷע���:10Ԫ��"
					System.out.println("ȥ��ð�ŷ����  " + cccc);
					// txt"808889��Ͷע����С��Ͷע���10Ԫ��"
					System.out.println("ȥ��˫���ŷ����  " + dddd);
					// txt808889��Ͷע����С��Ͷע���10Ԫ��
					// 01234567890123456789012345678901234
					String gentouNick = message.getFrom();
					// �ں�
					String qihao = dddd.substring(dddd.indexOf("xt") + 2, dddd.indexOf("��"));
					// Ͷע����
					String betType = dddd.substring(dddd.indexOf("��") + 1, dddd.indexOf("Ͷע���"));
					// Ͷע���
					String betMoney = dddd.substring(dddd.indexOf("��") + 1, dddd.indexOf("Ԫ"));
					System.out.println("��ȡ�ں�         " + qihao);
					System.out.println("Ͷע����         " + betType);
					System.out.println("Ͷע���         " + betMoney);
					if (expect.equals(qihao)) {

						boundBankCard(gentouNick, qihao, betType, betMoney);
					} else {
						Toast.makeText(getActivity(), "���޸�Ͷ��ǰ��", Toast.LENGTH_SHORT).show();
					}
				}
				return true;
			}

			/**
			 * ��չ������item����¼�,���Ҫ����EaseChatFragment���еĵ���¼���return true
			 * 
			 * @param view
			 * @param itemId
			 * @return
			 */
			@Override
			public boolean onExtendMenuItemClick(int itemId, View view) {
				// TODO Auto-generated method stub
				return false;
			}

			/**
			 * ����Ự����
			 */
			@Override
			public void onEnterToChatDetails() {
				// TODO Auto-generated method stub

			}

			/**
			 * �û�ͷ�񳤰�����¼�
			 * 
			 * @param username
			 */
			@Override
			public void onAvatarLongClick(String username) {
				// TODO Auto-generated method stub

			}

			/**
			 * �û�ͷ�����¼�
			 * 
			 * @param username
			 */
			@Override
			public void onAvatarClick(String username) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * ����� + �� �ŵ�����popupwindow
	 */
	protected void initpopwindow() {
		titlepop = new TitlePopup(getActivity(), LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titlepop.setItemOnClickListener(onitemClick);
		// �������������������

		titlepop.addAction(new ActionItem(getContext(), "Ͷע��¼", com.dhxgzs.goodluck.R.drawable.add_chongzhi));
		titlepop.addAction(new ActionItem(getContext(), "�淨����", com.dhxgzs.goodluck.R.drawable.add_tixian));
		titlepop.addAction(new ActionItem(getContext(), "����ͼ", com.dhxgzs.goodluck.R.drawable.add_tixian));

	}

	/** �����Ӱ�ť ���ֵ�����ֵļ��� */
	private OnItemOnClickListener onitemClick = new OnItemOnClickListener() {

		@Override
		public void onItemClick(ActionItem item, int position) {
			// mLoadingDialog.show();
			switch (position) {
			case 0:// Ͷע��¼

				right_image.setImageResource(com.dhxgzs.goodluck.R.drawable.img_add);// �����ֵ��ť��X��ͼƬ�滻�ɡ�+��
				Intent intent = new Intent(getActivity(), BettingRecord.class);
				startActivity(intent);
				//System.out.println("���λ��" + position);

				break;
			case 1:// �淨����

				right_image.setImageResource(com.dhxgzs.goodluck.R.drawable.img_add);// ������ְ�ť��X��ͼƬ�滻�ɡ�+��
				Intent intent1 = new Intent(getActivity(), PlayIntroduce.class);
				intent1.putExtra("tag_type", wanfaType);
				startActivity(intent1);
				//System.out.println("���λ��" + position);
				break;
			case 2:// ����ͼ

				right_image.setImageResource(com.dhxgzs.goodluck.R.drawable.img_add);// ������ְ�ť��X��ͼƬ�滻�ɡ�+��
				Intent intent2 = new Intent(getActivity(), TrendMap.class);
				startActivity(intent2);
				//System.out.println("���λ��" + position);
				break;

			}
		}
	};

	/**
	 * register extend menu, item id need > 3 if you override this method and
	 * keep exist item
	 */
	protected void registerExtendMenuItem() {
		for (int i = 0; i < itemStrings.length; i++) {
			inputMenu.registerExtendMenuItem(itemStrings[i], itemdrawables[i], itemIds[i], extendMenuItemClickListener);
		}
	}

	protected void onConversationInit() {
		conversation = EMClient.getInstance().chatManager().getConversation(toChatUsername,
				EaseCommonUtils.getConversationType(chatType), true);
		conversation.markAllMessagesAsRead();
		// the view1number of messages loaded into conversation is
		// getChatOptions().getview1numberOfMessagesLoaded
		// you can change this view1number
		final List<EMMessage> msgs = conversation.getAllMessages();
		int msgCount = msgs != null ? msgs.size() : 0;
		if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
			String msgId = null;
			if (msgs != null && msgs.size() > 0) {
				msgId = msgs.get(0).getMsgId();
			}
			conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount);
		}

	}

	@SuppressLint("ClickableViewAccessibility")
	protected void onMessageListInit() {
		messageList.init(toChatUsername, chatType,
				chatFragmentHelper != null ? chatFragmentHelper.onSetCustomChatRowProvider() : null);
		setListItemClickListener();

		messageList.getListView().setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				hideKeyboard();
				inputMenu.hideExtendMenuContainer();
				return false;
			}
		});

		isMessageListInited = true;
	}

	protected void setListItemClickListener() {
		messageList.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

			// ͷ�����¼�
			@Override
			public void onUserAvatarClick(String username) {

				if (chatFragmentHelper != null) {
					chatFragmentHelper.onAvatarClick(username);
				}
			}

			@Override
			public void onUserAvatarLongClick(String username) {
				if (chatFragmentHelper != null) {
					chatFragmentHelper.onAvatarLongClick(username);
				}
			}

			// �ط���Ϣ��ť����¼�
			@Override
			public void onResendClick(final EMMessage message) {
				new EaseAlertDialog(getActivity(), R.string.resend, R.string.confirm_resend, null,
						new AlertDialogUser() {
					@Override
					public void onResult(boolean confirmed, Bundle bundle) {
						if (!confirmed) {
							return;
						}
						resendMessage(message);
					}
				}, true).show();
			}

			// ���ݿ򳤰��¼�
			@Override
			public void onBubbleLongClick(EMMessage message) {
				contextMeview1numessage = message;
				if (chatFragmentHelper != null) {
					chatFragmentHelper.onMessageBubbleLongClick(message);
				}
			}

			// ���ݿ����¼�
			@Override
			public boolean onBubbleClick(EMMessage message) {
				if (chatFragmentHelper == null) {
					return false;
				}
				return chatFragmentHelper.onMessageBubbleClick(message);

			}

		});
	}

	protected void setRefreshLayoutListener() {
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						if (listView.getFirstVisiblePosition() == 0 && !isloading && haveMoreData) {
							List<EMMessage> messages;
							try {
								if (chatType == EaseConstant.CHATTYPE_SINGLE) {
									messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
											pagesize);
								} else {
									messages = conversation.loadMoreMsgFromDB(messageList.getItem(0).getMsgId(),
											pagesize);
								}
							} catch (Exception e1) {
								swipeRefreshLayout.setRefreshing(false);
								return;
							}
							if (messages.size() > 0) {
								messageList.refreshSeekTo(messages.size() - 1);
								if (messages.size() != pagesize) {
									haveMoreData = false;
								}
							} else {
								haveMoreData = false;
							}

							isloading = false;

						} else {
							Toast.makeText(getActivity(), getResources().getString(R.string.no_more_messages),
									Toast.LENGTH_SHORT).show();
						}
						swipeRefreshLayout.setRefreshing(false);
					}
				}, 600);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
				if (cameraFile != null && cameraFile.exists())
					sendImageMessage(cameraFile.getAbsolutePath());
			} else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
				if (data != null) {
					Uri selectedImage = data.getData();
					if (selectedImage != null) {
						sendPicByUri(selectedImage);
					}
				}
			} else if (requestCode == REQUEST_CODE_MAP) { // location
				double latitude = data.getDoubleExtra("latitude", 0);
				double longitude = data.getDoubleExtra("longitude", 0);
				String locationAddress = data.getStringExtra("address");
				if (locationAddress != null && !locationAddress.equals("")) {
					sendLocationMessage(latitude, longitude, locationAddress);
				} else {
					Toast.makeText(getActivity(), R.string.unable_to_get_loaction, Toast.LENGTH_SHORT).show();
				}

			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (isMessageListInited)
			messageList.refresh();
		EaseUI.getInstance().pushActivity(getActivity());
		// register the event listener when enter the foreground
		EMClient.getInstance().chatManager().addMessageListener(this);

		if (chatType == EaseConstant.CHATTYPE_GROUP) {
			EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		// unregister this event listener when this activity enters the
		// background
		EMClient.getInstance().chatManager().removeMessageListener(this);

		// remove activity from foreground activity list
		EaseUI.getInstance().popActivity(getActivity());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (groupListener != null) {
			EMClient.getInstance().groupManager().removeGroupChangeListener(groupListener);
		}

		if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
			EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
		}

		if (chatRoomChangeListener != null) {
			EMClient.getInstance().chatroomManager().removeChatRoomChangeListener(chatRoomChangeListener);
		}

	}

	public void onBackPressed() {
		if (inputMenu.onBackPressed()) {
			getActivity().finish();
			if (chatType == EaseConstant.CHATTYPE_GROUP) {
				EaseAtMessageHelper.get().removeAtMeGroup(toChatUsername);
				EaseAtMessageHelper.get().cleanToAtUserList();
			}
			if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
				EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
			}
		}
	}

	protected void onChatRoomViewCreation() {
		final ProgressDialog pd = ProgressDialog.show(getActivity(), "", "Joining......");
		EMClient.getInstance().chatroomManager().joinChatRoom(toChatUsername, new EMValueCallBack<EMChatRoom>() {

			@Override
			public void onSuccess(final EMChatRoom value) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (getActivity().isFinishing() || !toChatUsername.equals(value.getId()))
							return;
						pd.dismiss();
						EMChatRoom room = EMClient.getInstance().chatroomManager().getChatRoom(toChatUsername);
						if (room != null) {
							// if (room.getName().equals("bj-fir-vip1")) {

							title.setText(wanfaType + fangjianType + vipType);
							// } else {

							// title.setText(room.getName());
							// }
							//System.out.println("!!!!!!!" + room.getName());
							EMLog.d(TAG, "join room success : " + room.getName());
						} else {
							System.out.println("@@@@@@@@" + toChatUsername);
							title.setText(toChatUsername);
						}
						addChatRoomChangeListenr();
						onConversationInit();
						onMessageListInit();
					}
				});
			}

			@Override
			public void onError(final int error, String errorMsg) {
				// TODO Auto-generated method stub
				EMLog.d(TAG, "join room failure : " + error);
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						pd.dismiss();
					}
				});
				getActivity().finish();
			}
		});
	}

	protected void addChatRoomChangeListenr() {
		chatRoomChangeListener = new EMChatRoomChangeListener() {

			@Override
			public void onChatRoomDestroyed(String roomId, String roomName) {
				if (roomId.equals(toChatUsername)) {
					showChatroomToast(" room : " + roomId + " with room name : " + roomName + " was destroyed");
					getActivity().finish();
				}
			}

			@Override
			public void onMemberJoined(String roomId, String participant) {
				System.out.println("����id" + roomId);
				// showChatroomToast("��Ա : " + participant + " ���뷿�� : " +
				// wanfaType + fangjianType + vipType);
				// String content = "��Ա" + participant + "���뷿��";
				// sendTextMessage(content);
				// �˴�ͨ����������̨���͵�ǰ����iD,��Ҫ֪���ķ����Ա���ú�̨ģ��8001���ͳ�Ա��������뿪����
				renjionRoom(roomId, participant);
			}

			@Override
			public void onMemberExited(String roomId, String roomName, String participant) {
				// showChatroomToast("��Ա : " + participant + " �뿪���� : " + roomId
				// + " �������� : " + wanfaType + fangjianType + vipType);
				// String content = "��Ա" + participant + "�뿪����";
				// sendTextMessage(content);
				// �˴�ͨ����������̨���͵�ǰ����iD,��Ҫ֪���ķ����Ա���ú�̨ģ��8001����:��Ա��������뿪����
				//				renleaveRoom(roomId, participant);
			}

			@Override
			public void onMemberKicked(String roomId, String roomName, String participant) {
				if (roomId.equals(toChatUsername)) {
					String curUser = EMClient.getInstance().getCurrentUser();
					if (curUser.equals(participant)) {
						EMClient.getInstance().chatroomManager().leaveChatRoom(toChatUsername);
						getActivity().finish();
					} else {
						// showChatroomToast("member : " + participant + " was
						// kicked from the room : " + roomId
						// + " room name : " + roomName);
					}
				}
			}

		};

		EMClient.getInstance().chatroomManager().addChatRoomChangeListener(chatRoomChangeListener);
	}

	protected void renjionRoom(String roomId, String participant) {

		request = NoHttp.createJsonObjectRequest(XyMyContent.JION_AND_LEAVE_ROOM, RequestMethod.POST);

		request.add("roomid", roomId);
		request.add("account", participant);
		request.add("type", "1");
		System.out.println("���뷿������");
		touzhuQueue.add(JIONROOM, request, responseListener);
	}

	protected void renleaveRoom(String roomId, String participant) {
		request = NoHttp.createJsonObjectRequest(XyMyContent.JION_AND_LEAVE_ROOM, RequestMethod.POST);

		request.add("roomid", roomId);
		request.add("account", participant);
		request.add("type", "2");

		touzhuQueue.add(LEAVEROOM, request, responseListener);

	}

	protected void showChatroomToast(final String toastContent) {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getActivity(), toastContent, Toast.LENGTH_SHORT).show();
			}
		});
	}

	// implement methods in EMMessageListener
	@Override
	public void onMessageReceived(List<EMMessage> messages) {
		for (EMMessage message : messages) {
			String username = null;
			// group message message.getChatType() == ChatType.GroupChat ||
			if (message.getChatType() == ChatType.ChatRoom) {
				username = message.getTo();

				System.out.println("�յ�" + message.getUserName() + "����Ϣ");
				if(message.getUserName().equals("8001")){
					System.out.println("�յ�8001����Ϣ");
					// ҳ���м���ʾ��һ������
					zuiXinLotteryRequest();
					touzhupagerYue();
					zuiXinGuessingRequest();
					yingkuiRequest();
				} 
			} else {
				// single chat message
				username = message.getFrom();
			}

			// if the message is for current conversation
			if (username.equals(toChatUsername) || message.getTo().equals(toChatUsername)) {
				messageList.refreshSelectLast();
				//���û����е���ʾ����
				//EaseUI.getInstance().getNotifier().vibrateAndPlayTone(message);
			} else {
				EaseUI.getInstance().getNotifier().onNewMsg(message);
			}
		}
	}


	private void yingkuiRequest() {
		request = NoHttp.createJsonObjectRequest(XyMyContent.YINGKUI_URL, RequestMethod.POST);

		request.add("account", account);
		if (wanfaType.equals("����")) {

			request.add("type", "10");
		} else if (wanfaType.equals("���ô�")) {
			request.add("type", "11");
		}
		request.add("imei", imei);

		touzhuQueue.add(YINGKUI, request, responseListener);


	}

	@Override
	public void onCmdMessageReceived(List<EMMessage> messages) {

	}

	@Override
	public void onMessageReadAckReceived(List<EMMessage> messages) {
		if (isMessageListInited) {
			messageList.refresh();
		}
	}

	@Override
	public void onMessageDeliveryAckReceived(List<EMMessage> messages) {
		if (isMessageListInited) {
			messageList.refresh();
		}
	}

	@Override
	public void onMessageChanged(EMMessage emMessage, Object change) {
		if (isMessageListInited) {
			messageList.refresh();
		}
	}

	/**
	 * handle the click event for extend menu
	 *
	 */
	class MyItemClickListener implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {

		@Override
		public void onClick(int itemId, View view) {
			if (chatFragmentHelper != null) {
				if (chatFragmentHelper.onExtendMenuItemClick(itemId, view)) {
					return;
				}
			}
			switch (itemId) {
			case ITEM_TAKE_PICTURE:
				selectPicFromCamera();
				break;
			case ITEM_PICTURE:
				selectPicFromLocal();
				break;
			case ITEM_LOCATION:
				startActivityForResult(new Intent(getActivity(), EaseBaiduMapActivity.class), REQUEST_CODE_MAP);
				break;

			default:
				break;
			}
		}

	}

	/**
	 * input @
	 * 
	 * @param username
	 */
	protected void inputAtUsername(String username, boolean autoAddAtSymbol) {
		if (EMClient.getInstance().getCurrentUser().equals(username) || chatType != EaseConstant.CHATTYPE_GROUP) {
			return;
		}
		EaseAtMessageHelper.get().addAtUser(username);
		EaseUser user = EaseUserUtils.getUserInfo(username);
		if (user != null) {
			username = user.getNick();
		}
		if (autoAddAtSymbol)
			inputMenu.insertText("@" + username + " ");
		else
			inputMenu.insertText(username + " ");
	}

	/**
	 * input @
	 * 
	 * @param username
	 */
	protected void inputAtUsername(String username) {
		inputAtUsername(username, true);
	}

	// send message
	protected void sendTextMessage(String content) {
		if (EaseAtMessageHelper.get().containsAtUsername(content)) {
			sendAtMessage(content);
		} else {
			EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
			sendMessage(message);
		}
	}

	/**
	 * send @ message, only support group chat message
	 * 
	 * @param content
	 */
	private void sendAtMessage(String content) {
		if (chatType != EaseConstant.CHATTYPE_GROUP) {
			EMLog.e(TAG, "only support group chat message");
			return;
		}
		EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
		EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
		if (EMClient.getInstance().getCurrentUser().equals(group.getOwner())
				&& EaseAtMessageHelper.get().containsAtAll(content)) {
			message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseConstant.MESSAGE_ATTR_VALUE_AT_MSG_ALL);
		} else {
			message.setAttribute(EaseConstant.MESSAGE_ATTR_AT_MSG, EaseAtMessageHelper.get()
					.atListToJsonArray(EaseAtMessageHelper.get().getAtMessageUsernames(content)));
		}
		sendMessage(message);

	}

	protected void sendBigExpressionMessage(String name, String identityCode) {
		EMMessage message = EaseCommonUtils.createExpressionMessage(toChatUsername, name, identityCode);
		sendMessage(message);
	}

	protected void sendVoiceMessage(String filePath, int length) {
		EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
		sendMessage(message);
	}

	protected void sendImageMessage(String imagePath) {
		EMMessage message = EMMessage.createImageSendMessage(imagePath, false, toChatUsername);
		sendMessage(message);
	}

	protected void sendLocationMessage(double latitude, double longitude, String locationAddress) {
		EMMessage message = EMMessage.createLocationSendMessage(latitude, longitude, locationAddress, toChatUsername);
		sendMessage(message);
	}

	protected void sendVideoMessage(String videoPath, String thumbPath, int videoLength) {
		EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
		sendMessage(message);
	}

	protected void sendFileMessage(String filePath) {
		EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
		sendMessage(message);
	}

	protected void sendMessage(EMMessage message) {
		if (message == null) {
			return;
		}
		if (chatFragmentHelper != null) {
			// set extension
			chatFragmentHelper.onSetMessageAttributes(message);
		}
		if (chatType == EaseConstant.CHATTYPE_GROUP) {
			message.setChatType(ChatType.GroupChat);
		} else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
			message.setChatType(ChatType.ChatRoom);
		}
		// send message
		EMClient.getInstance().chatManager().sendMessage(message);
		// refresh ui
		if (isMessageListInited) {
			messageList.refreshSelectLast();
		}
	}

	public void resendMessage(EMMessage message) {
		message.setStatus(EMMessage.Status.CREATE);
		EMClient.getInstance().chatManager().sendMessage(message);
		messageList.refresh();
	}

	// ===================================================================================

	/**
	 * send image
	 * 
	 * @param selectedImage
	 */
	protected void sendPicByUri(Uri selectedImage) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;

			if (picturePath == null || picturePath.equals("null")) {
				Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}
			sendImageMessage(picturePath);
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				Toast toast = Toast.makeText(getActivity(), R.string.cant_find_pictures, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;

			}
			sendImageMessage(file.getAbsolutePath());
		}

	}

	/**
	 * send file
	 * 
	 * @param uri
	 */
	protected void sendFileByUri(Uri uri) {
		String filePath = null;
		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = null;

			try {
				cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					filePath = cursor.getString(column_index);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			filePath = uri.getPath();
		}
		if (filePath == null) {
			return;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			Toast.makeText(getActivity(), R.string.File_does_not_exist, Toast.LENGTH_SHORT).show();
			return;
		}
		// limit the size < 10M
		if (file.length() > 10 * 1024 * 1024) {
			Toast.makeText(getActivity(), R.string.The_file_is_not_greater_than_10_m, Toast.LENGTH_SHORT).show();
			return;
		}
		sendFileMessage(filePath);
	}

	/**
	 * capture new image
	 */
	protected void selectPicFromCamera() {
		if (!EaseCommonUtils.isSdcardExist()) {
			Toast.makeText(getActivity(), R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
			return;
		}

		cameraFile = new File(PathUtil.getInstance().getImagePath(),
				EMClient.getInstance().getCurrentUser() + System.currentTimeMillis() + ".jpg");
		// noinspection ResultOfMethodCallIgnored
		cameraFile.getParentFile().mkdirs();
		startActivityForResult(
				new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
				REQUEST_CODE_CAMERA);
	}

	/**
	 * select local image
	 */
	protected void selectPicFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");

		} else {
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_LOCAL);
	}

	/**
	 * clear the conversation history
	 * 
	 */
	protected void emptyHistory() {
		String msg = getResources().getString(R.string.Whether_to_empty_all_chats);
		new EaseAlertDialog(getActivity(), null, msg, null, new AlertDialogUser() {

			@Override
			public void onResult(boolean confirmed, Bundle bundle) {
				if (confirmed) {
					EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
					messageList.refresh();
				}
			}
		}, true).show();
	}

	/**
	 * open group detail
	 * 
	 */
	protected void toGroupDetails() {
		if (chatType == EaseConstant.CHATTYPE_GROUP) {
			EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
			if (group == null) {
				Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
				return;
			}
			if (chatFragmentHelper != null) {
				chatFragmentHelper.onEnterToChatDetails();
			}
		} else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
			if (chatFragmentHelper != null) {
				chatFragmentHelper.onEnterToChatDetails();
			}
		}
	}

	/**
	 * hide
	 */
	protected void hideKeyboard() {
		if (getActivity().getWindow()
				.getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getActivity().getCurrentFocus() != null)
				inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * forward message
	 * 
	 * @param forward_msg_id
	 */
	protected void forwardMessage(String forward_msg_id) {
		final EMMessage forward_msg = EMClient.getInstance().chatManager().getMessage(forward_msg_id);
		EMMessage.Type type = forward_msg.getType();
		switch (type) {
		case TXT:
			if (forward_msg.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
				sendBigExpressionMessage(((EMTextMessageBody) forward_msg.getBody()).getMessage(),
						forward_msg.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null));
			} else {
				// get the content and send it
				String content = ((EMTextMessageBody) forward_msg.getBody()).getMessage();
				sendTextMessage(content);
			}
			break;
		case IMAGE:
			// send image
			String filePath = ((EMImageMessageBody) forward_msg.getBody()).getLocalUrl();
			if (filePath != null) {
				File file = new File(filePath);
				if (!file.exists()) {
					// send thumb nail if original image does not exist
					filePath = ((EMImageMessageBody) forward_msg.getBody()).thumbnailLocalPath();
				}
				sendImageMessage(filePath);
			}
			break;
		default:
			break;
		}

		if (forward_msg.getChatType() == EMMessage.ChatType.ChatRoom) {
			EMClient.getInstance().chatroomManager().leaveChatRoom(forward_msg.getTo());
		}
	}

	/**
	 * listen the group event
	 * 
	 */
	class GroupListener extends EaseGroupRemoveListener {

		@Override
		public void onUserRemoved(final String groupId, String groupName) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					if (toChatUsername.equals(groupId)) {
						Toast.makeText(getActivity(), R.string.you_are_group, Toast.LENGTH_LONG).show();
						Activity activity = getActivity();
						if (activity != null && !activity.isFinishing()) {
							activity.finish();
						}
					}
				}
			});
		}

		@Override
		public void onGroupDestroyed(final String groupId, String groupName) {
			// prompt group is dismissed and finish this activity
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					if (toChatUsername.equals(groupId)) {
						Toast.makeText(getActivity(), R.string.the_current_group, Toast.LENGTH_LONG).show();
						Activity activity = getActivity();
						if (activity != null && !activity.isFinishing()) {
							activity.finish();
						}
					}
				}
			});
		}

	}

	protected EaseChatFragmentHelper chatFragmentHelper;


	public void setChatFragmentListener(EaseChatFragmentHelper chatFragmentHelper) {
		this.chatFragmentHelper = chatFragmentHelper;
	}

	public interface EaseChatFragmentHelper {
		/**
		 * set message attribute
		 */
		void onSetMessageAttributes(EMMessage message);

		/**
		 * enter to chat detail
		 */
		void onEnterToChatDetails();

		/**
		 * on avatar clicked
		 * 
		 * @param username
		 */
		void onAvatarClick(String username);

		/**
		 * on avatar long pressed
		 * 
		 * @param username
		 */
		void onAvatarLongClick(String username);

		/**
		 * on message bubble clicked
		 */
		boolean onMessageBubbleClick(EMMessage message);

		/**
		 * on message bubble long pressed
		 */
		void onMessageBubbleLongClick(EMMessage message);

		/**
		 * on extend menu item clicked, return true if you want to override
		 * 
		 * @param view
		 * @param itemId
		 * @return
		 */
		boolean onExtendMenuItemClick(int itemId, View view);

		/**
		 * on set custom chat row provider
		 * 
		 * @return
		 */
		EaseCustomChatRowProvider onSetCustomChatRowProvider();
	}

	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// ��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}

		@Override
		public void onFinish() {// ��ʱ���ʱ����
			System.out.println("��ȡ��TAGֵ��" + daojishiTag);
			if (daojishiTag.equals("1")) {
				System.out.println("���̺�����");
				// ���¾�������
				zuiXinGuessingRequest();
				//zuiXinLotteryRequest();
			} else if (daojishiTag.equals("2")) {
				// System.out.println("������ˢ�����");
				// // ��ǰ�������
				
				touzhupagerYue();
				 
			}

			// register_getcode.setText("������֤");
			// register_getcode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// ��ʱ������ʾ
			// register_getcode.setClickable(false);
			// register_getcode.setText(millisUntilFinished / 1000 + "��");

		}
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		if (popup != null) {
			popup = null;
		}
		super.onDetach();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@����");
	}

	@SuppressLint("InflateParams")
	private void boundBankCard(String gentouNick, String qihao, String betType, String betMoney) {

		final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(com.dhxgzs.goodluck.R.layout.alertdialog_gentou,
				null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(com.dhxgzs.goodluck.R.layout.alertdialog_gentou);
		Button queding = (Button) window.findViewById(com.dhxgzs.goodluck.R.id.Yqueding);
		Button quxiao = (Button) window.findViewById(com.dhxgzs.goodluck.R.id.Yquxiao);
		TextView gentou_wanjiaName = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.gentou_wanjiaName);
		TextView gentou_QiShu = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.gentou_QiShu);
		TextView gentou_leibei = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.gentou_leibei);
		TextView gentou_Money = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.gentou_Money);

		expect = qihao;
		touzhu_num = betMoney;
		gentou_wanjiaName.setText(gentouNick);
		gentou_QiShu.setText(qihao);
		gentou_leibei.setText(betType);
		gentou_Money.setText(betMoney);
		System.out.println(plList);
		if ((betType.trim()).equals("��")) {
			getBttypeid = plList.get(0).getBttypeid();
			touZhuType = plList.get(0).getItem();
		} else if ((betType.trim()).equals("С")) {
			getBttypeid = plList.get(1).getBttypeid();
			touZhuType = plList.get(1).getItem();
		} else if ((betType.trim()).equals("��")) {
			getBttypeid = plList.get(2).getBttypeid();
			touZhuType = plList.get(2).getItem();
		} else if ((betType.trim()).equals("˫")) {
			getBttypeid = plList.get(3).getBttypeid();
			touZhuType = plList.get(3).getItem();
		} else if ((betType.trim()).equals("����")) {
			getBttypeid = plList.get(4).getBttypeid();
			touZhuType = plList.get(4).getItem();
		} else if ((betType.trim()).equals("��")) {
			getBttypeid = plList.get(5).getBttypeid();
			touZhuType = plList.get(5).getItem();
		} else if ((betType.trim()).equals("С��")) {
			getBttypeid = plList.get(6).getBttypeid();
			touZhuType = plList.get(6).getItem();
		} else if ((betType.trim()).equals("��˫")) {
			getBttypeid = plList.get(7).getBttypeid();
			touZhuType = plList.get(7).getItem();
		} else if ((betType.trim()).equals("С˫")) {
			getBttypeid = plList.get(8).getBttypeid();
			touZhuType = plList.get(8).getItem();
		} else if ((betType.trim()).equals("��С")) {
			getBttypeid = plList.get(9).getBttypeid();
			touZhuType = plList.get(9).getItem();

		} else if ((betType.trim()).equals("0")) {
			getBttypeid = plList.get(10).getBttypeid();
			touZhuType = plList.get(10).getItem();
		} else if ((betType.trim()).equals("1")) {
			getBttypeid = plList.get(11).getBttypeid();
			touZhuType = plList.get(11).getItem();
		} else if ((betType.trim()).equals("2")) {
			getBttypeid = plList.get(12).getBttypeid();
			touZhuType = plList.get(12).getItem();
		} else if ((betType.trim()).equals("3")) {
			getBttypeid = plList.get(13).getBttypeid();
			touZhuType = plList.get(13).getItem();
		} else if ((betType.trim()).equals("4")) {
			getBttypeid = plList.get(14).getBttypeid();
			touZhuType = plList.get(14).getItem();
		} else if ((betType.trim()).equals("5")) {
			getBttypeid = plList.get(15).getBttypeid();
			touZhuType = plList.get(15).getItem();
		} else if ((betType.trim()).equals("6")) {
			getBttypeid = plList.get(16).getBttypeid();
			touZhuType = plList.get(16).getItem();
		} else if ((betType.trim()).equals("7")) {
			getBttypeid = plList.get(17).getBttypeid();
			touZhuType = plList.get(17).getItem();
		} else if ((betType.trim()).equals("8")) {
			getBttypeid = plList.get(18).getBttypeid();
			touZhuType = plList.get(18).getItem();
		} else if ((betType.trim()).equals("9")) {
			getBttypeid = plList.get(19).getBttypeid();
			touZhuType = plList.get(19).getItem();
		} else if ((betType.trim()).equals("10")) {
			getBttypeid = plList.get(20).getBttypeid();
			touZhuType = plList.get(20).getItem();
		} else if ((betType.trim()).equals("11")) {
			getBttypeid = plList.get(21).getBttypeid();
			touZhuType = plList.get(21).getItem();
		} else if ((betType.trim()).equals("12")) {
			getBttypeid = plList.get(22).getBttypeid();
			touZhuType = plList.get(22).getItem();
		} else if ((betType.trim()).equals("13")) {
			getBttypeid = plList.get(23).getBttypeid();
			touZhuType = plList.get(23).getItem();
		} else if ((betType.trim()).equals("14")) {
			getBttypeid = plList.get(24).getBttypeid();
			touZhuType = plList.get(24).getItem();
		} else if ((betType.trim()).equals("15")) {
			getBttypeid = plList.get(25).getBttypeid();
			touZhuType = plList.get(25).getItem();
		} else if ((betType.trim()).equals("16")) {
			getBttypeid = plList.get(26).getBttypeid();
			touZhuType = plList.get(26).getItem();
		} else if ((betType.trim()).equals("17")) {
			getBttypeid = plList.get(27).getBttypeid();
			touZhuType = plList.get(27).getItem();
		} else if ((betType.trim()).equals("18")) {
			getBttypeid = plList.get(28).getBttypeid();
			touZhuType = plList.get(28).getItem();
		} else if ((betType.trim()).equals("19")) {
			getBttypeid = plList.get(29).getBttypeid();
			touZhuType = plList.get(29).getItem();
		} else if ((betType.trim()).equals("20")) {
			getBttypeid = plList.get(30).getBttypeid();
			touZhuType = plList.get(30).getItem();
		} else if ((betType.trim()).equals("21")) {
			getBttypeid = plList.get(31).getBttypeid();
			touZhuType = plList.get(31).getItem();
		} else if ((betType.trim()).equals("22")) {
			getBttypeid = plList.get(32).getBttypeid();
			touZhuType = plList.get(32).getItem();
		} else if ((betType.trim()).equals("23")) {
			getBttypeid = plList.get(33).getBttypeid();
			touZhuType = plList.get(33).getItem();
		} else if ((betType.trim()).equals("24")) {
			getBttypeid = plList.get(34).getBttypeid();
			touZhuType = plList.get(34).getItem();
		} else if ((betType.trim()).equals("25")) {
			getBttypeid = plList.get(35).getBttypeid();
			touZhuType = plList.get(35).getItem();
		} else if ((betType.trim()).equals("26")) {
			getBttypeid = plList.get(36).getBttypeid();
			touZhuType = plList.get(36).getItem();
		} else if ((betType.trim()).equals("27")) {
			getBttypeid = plList.get(37).getBttypeid();
			touZhuType = plList.get(37).getItem();

		} else if ((betType.trim()).equals("��")) {
			getBttypeid = plList.get(38).getBttypeid();
			touZhuType = plList.get(38).getItem();
		} else if ((betType.trim()).equals("��")) {
			getBttypeid = plList.get(39).getBttypeid();
			touZhuType = plList.get(39).getItem();
		} else if ((betType.trim()).equals("��")) {
			getBttypeid = plList.get(40).getBttypeid();
			touZhuType = plList.get(40).getItem();
		} else if ((betType.trim()).equals("����")) {
			getBttypeid = plList.get(41).getBttypeid();
			touZhuType = plList.get(41).getItem();
		}
		System.out.println("��Ͷ����1" + betType);
		System.out.println("��Ͷ����1" + betMoney);
		System.out.println("��Ͷ����" + getBttypeid);
		System.out.println("��Ͷ����" + touZhuType);
		// ���ȷ��
		queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				gentou_touzhu_type = "��Ͷ";
				DetermineWhetherBetRequest();
				dialog.cancel();

			}
		});

		// ���ȡ��
		quxiao.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.cancel();
			}
		});

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}
}
