package com.dhxgzs.goodluck.app;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.dhxgzs.goodluck.app.db.InviteMessgeDao;
import com.dhxgzs.goodluck.app.db.UserDao;
import com.dhxgzs.goodluck.app.domain.RobotUser;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.EMLog;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class XyAppHelper {

	private static XyAppHelper instance = null;
	protected EMMessageListener messageListener = null;
	private Context appContext;
	private String username;
	private EaseUI easeUI;
	private InviteMessgeDao inviteMessgeDao;
	private UserDao userDao;
	private Map<String, EaseUser> contactList;
	private Map<String, RobotUser> robotList;
	private DemoModel demoModel = null;
	

	private String TAG = "XyAppHelper";

	public synchronized static XyAppHelper getInstance() {
		if (instance == null) {
			instance = new XyAppHelper();
		}
		return instance;
	}

	public void init(Context context) {
		demoModel = new DemoModel(context);
		EMOptions options = initChatOptions();
		// use default options if options is null
		if (EaseUI.getInstance().init(context, options)) {
			appContext = context;
			// ��ȡeaseuiʵ��
			easeUI = EaseUI.getInstance();
			// ��ʼ��easeui
			easeUI.init(appContext, options);
			// �����������ʱ���ر�debugģʽ���������Ĳ���Ҫ����Դ
			EMClient.getInstance().setDebugMode(true);
			setEaseUIProviders();
			// ����ȫ�ּ���
			setGlobalListeners();
			// broadcastManager = LocalBroadcastManager.getInstance(appContext);
			initDbDao();
		}
	}

	private EMOptions initChatOptions() {
		EMOptions options = new EMOptions();
		// set if accept the invitation automatically
		options.setAcceptInvitationAlways(false);
		// set if you need read ack
		options.setRequireAck(true);
		// set if you need delivery ack
		options.setRequireDeliveryAck(false);
		return options;
	}

	protected void setEaseUIProviders() {
		// set profile provider if you want easeUI to handle avatar and nickname
		easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
			@Override
			public EaseUser getUser(String username) {
				return getUserInfo(username);
			}
		});
	}

	private EaseUser getUserInfo(String username) {
		// ��ȡ EaseUserʵ��, ������ڴ��ж�ȡ
		// ������Ǵӷ������ж���ȡ���ģ�����ڱ��ؽ��л���
		EaseUser user = null;
		// ����û��Ǳ��ˣ��������Լ���ͷ��
		if (username.equals(EMClient.getInstance().getCurrentUser())) {
			user = new EaseUser(username);
			user.setAvatar(SharedPreferencesUtils.getValue(appContext, "PIC"));
			Log.i("zcb", "����ͷ��" + user.getAvatar());
			user.setNick(SharedPreferencesUtils.getValue(appContext, "NICK_NAME"));
			System.out.println("�Ñ���" + SharedPreferencesUtils.getValue(appContext, "NICK_NAME"));
			System.out.println("�Ñ���" + SharedPreferencesUtils.getValue(appContext, "PIC"));
			return user;
		}
		// if (user==null && getRobotList()!=null){
		// user=getRobotList().get(username);
		// }
		// �յ����˵���Ϣ�����ñ��˵�ͷ��
		if (contactList != null && contactList.containsKey(username)) {
			user = contactList.get(username);
		} else { // ����ڴ���û�У��򽫱������ݿ��е�ȡ�����ڴ���
			contactList = getContactList();
			user = contactList.get(username);
		}
		// ����û����������ϵ�ˣ�����г�ʼ��
		if (user == null) {
			user = new EaseUser(username);
			EaseCommonUtils.setUserInitialLetter(user);
		} else {
			if (TextUtils.isEmpty(user.getAvatar())) {// �������Ϊ�գ�����ʾ���ź���
				user.setNick(user.getUsername());
				 
			}
		}
		Log.i("zcb", "ͷ��" + user.getAvatar());

		return user;
	}

	private void initDbDao() {
		inviteMessgeDao = new InviteMessgeDao(appContext);
		userDao = new UserDao(appContext);
	}

	public Map<String, RobotUser> getRobotList() {
		if (isLoggedIn() && robotList == null) {
			robotList = demoModel.getRobotList();
		}
		return robotList;
	}

	/**
	 * get current user's id
	 */
	public String getCurrentUsernName() {
		if (username == null) {
			// username = (String)SharedPreferencesUtils.getParam(appContext,
			// Constant.HX_CURRENT_USER_ID,"");
			// �Ȳ������ǳ�
			username = "huanxin_test_name";
		}
		return username;
	}

	/**
	 * ��ȡ���е���ϵ����Ϣ
	 *
	 * @return
	 */
	public Map<String, EaseUser> getContactList() {
		if (isLoggedIn() && contactList == null) {
			contactList = demoModel.getContactList();
		}
		// return a empty non-null object to avoid app crash
		if (contactList == null) {
			return new Hashtable<String, EaseUser>();
		}
		return contactList;
	}

	/**
	 * if ever logged in
	 *
	 * @return
	 */
	public boolean isLoggedIn() {
		return EMClient.getInstance().isLoggedInBefore();
	}

	/**
	 * set global listener
	 */
	protected void setGlobalListeners() {
		registerMessageListener();
	}

	/**
	 * Global listener If this event already handled by an activity, you don't
	 * need handle it again activityList.size() <= 0 means all activities
	 * already in background or not in Activity Stack
	 */
	protected void registerMessageListener() {
		messageListener = new EMMessageListener() {
			private BroadcastReceiver broadCastReceiver = null;

			@Override
			public void onMessageReceived(List<EMMessage> messages) {
				for (EMMessage message : messages) {
					EMLog.d(TAG, "onMessageReceived id : " + message.getMsgId());
 
					String userName = message.getStringAttribute(Constant.USER_NAME, "");
					String userId = message.getStringAttribute(Constant.USER_ID, "");
					String userPic = message.getStringAttribute(Constant.HEAD_IMAGE_URL, "");
					String userVip = message.getStringAttribute(Constant.USER_VIP,"");
					
					String aname=(String) message.ext().get(Constant.USER_NAME);
                    String aimg=(String) message.ext().get(Constant.HEAD_IMAGE_URL);
                    String avip=(String) message.ext().get(Constant.USER_VIP);
					
					String hxIdFrom = message.getFrom();
					EaseUser easeUser = new EaseUser(hxIdFrom);
					if(userPic.isEmpty()){ 
                	if(message.getUserName().equals("8001")||message.getUserName().equals("8002")||message.getUserName().equals("8003")){
                		easeUser.setAvatar(userPic);
                    	easeUser.setNick(userName);
                	}else{
                	}
                }else{
                	if(message.ext()==null){
                		easeUser.setAvatar(aimg);
                    	easeUser.setNick(aname);
                    	easeUser.setDgvip(avip);
                	}else{
                		easeUser.setAvatar(userPic);
                		easeUser.setNick(userName);
                		easeUser.setDgvip(userVip);
                	}
                	
                }
					// �����ڴ�
					getContactList();
					contactList.put(hxIdFrom, easeUser);
					// ����db
					UserDao dao = new UserDao(appContext);
					List<EaseUser> users = new ArrayList<EaseUser>();
					users.add(easeUser);
					dao.saveContactList(users);
					// in background, do not refresh UI, notify it in
					// notification bar
					// if(!easeUI.hasForegroundActivies()){
					// getNotifier().onNewMsg(message);
					// }
				}
			}

			@Override
			public void onCmdMessageReceived(List<EMMessage> messages) {
				for (EMMessage message : messages) {
					EMLog.d(TAG, "receive command message");
					// get message body
					// end of red packet code
					// ��ȡ��չ���� �˴�ʡ��
					// maybe you need get extension of your message
					// message.getStringAttribute("");
				}
			}

			// @Override
			// public void onMessageRead(List<EMMessage> messages) {
			// }
			// @Override
			// public void onMessageDelivered(List<EMMessage> message) {
			// }
			@Override
			public void onMessageChanged(EMMessage message, Object change) {
			}

			@Override
			public void onMessageDeliveryAckReceived(List<EMMessage> arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMessageReadAckReceived(List<EMMessage> arg0) {
				// TODO Auto-generated method stub

			}
		};
		EMClient.getInstance().chatManager().addMessageListener(messageListener);
	}
}
