package com.dhxgzs.goodluck.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.chatroom.BaseActivity;
import com.dhxgzs.goodluck.chatroom.ChatActivity;
import com.dhxgzs.goodluck.entiey.LunBoEntity;
import com.dhxgzs.goodluck.entiey.ShouYeData;
import com.dhxgzs.goodluck.entiey.UserInfoEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.hyphenate.EMChatRoomChangeListener;
import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMPageResult;
import com.hyphenate.exceptions.HyphenateException;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����28����ҳ��
 * 
 * @author Administrator
 *
 */
public class FangJianActivity extends BaseActivity implements OnClickListener {

	private static final int PICANDNICK = 0;
	/** ����ҳ���ذ�ť */
	private ImageView fangjian_back;
	/** vip����1�������� */
	private TextView Bj_FJ1_ZaiXianPerson_Num;
	/** vip����2�������� */
	private TextView Bj_FJ2_ZaiXianPerson_Num;
	/** vip����3�������� */
	private TextView Bj_FJ3_ZaiXianPerson_Num;
	/** vip����4�������� */
	private TextView Bj_FJ4_ZaiXianPerson_Num;

	/** vip����1ͼƬ */
	private RelativeLayout Bj_FJ1_image;
	/** vip����2ͼƬ */
	private RelativeLayout Bj_FJ2_image;
	/** vip����3ͼƬ */
	private RelativeLayout Bj_FJ3_image;
	/** vip����4ͼƬ */
	private RelativeLayout Bj_FJ4_image;

	/******************************** ���������ݿ�ʼ *************************************/

	private List<EMChatRoom> chatRoomList;
	private boolean isLoading;
	private boolean isFirstLoading = true;
	private boolean hasMoreData = true;
	private String cursor;
	private int pagenum = 0;
	private final int pagesize = 24;
	private int pageCount = -1;
	private List<EMChatRoom> rooms;
	private String vip1RoomId;
	private String vip2RoomId;
	private String vip3RoomId;
	private String vip4RoomId;
	/** �������� */
	private String fangjianType;
	/** ����28���߼��ô�28�淨���� */
	private String wanfaType;
	private String fangjianName;
	private String fangjianId;
	private String bj_fir_vip1, bj_fir_vip2, bj_fir_vip3, bj_fir_vip4, bj_sec_vip1, bj_sec_vip2, bj_sec_vip3,
	bj_sec_vip4, bj_thr_vip1, bj_thr_vip2, bj_thr_vip3, bj_thr_vip4, cakeno_fir_vip1, cakeno_fir_vip2,
	cakeno_fir_vip3, cakeno_fir_vip4, cakeno_sec_vip1, cakeno_sec_vip2, cakeno_sec_vip3, cakeno_sec_vip4,
	cakeno_thr_vip1, cakeno_thr_vip2, cakeno_thr_vip3, cakeno_thr_vip4;
	private String VIP1;
	private String VIP2;
	private String VIP3;
	private String VIP4;
	/** ����28������vip������������ */
	private int BJ_fir_vip1_personNum;
	private int BJ_fir_vip2_personNum;
	private int BJ_fir_vip3_personNum;
	private int BJ_fir_vip4_personNum;
	/** ����28�м���vip������������ */
	private int BJ_sec_vip1_personNum;
	private int BJ_sec_vip2_personNum;
	private int BJ_sec_vip3_personNum;
	private int BJ_sec_vip4_personNum;
	/** ����28�߼���vip������������ */
	private int BJ_thr_vip1_personNum;
	private int BJ_thr_vip2_personNum;
	private int BJ_thr_vip3_personNum;
	private int BJ_thr_vip4_personNum;
	/** ���ô�28������vip������������ */
	private int JND_fir_vip1_personNum;
	private int JND_fir_vip2_personNum;
	private int JND_fir_vip3_personNum;
	private int JND_fir_vip4_personNum;
	/** ���ô�28�м���vip������������ */
	private int JND_sec_vip1_personNum;
	private int JND_sec_vip2_personNum;
	private int JND_sec_vip3_personNum;
	private int JND_sec_vip4_personNum;
	/** ���ô�28�߼���vip������������ */
	private int JND_thr_vip1_personNum;
	private int JND_thr_vip2_personNum;
	private int JND_thr_vip3_personNum;
	private int JND_thr_vip4_personNum;
	/** ����28������vip������������ */
	private int BJ_fir_vipmaxuser;
	/** ����28�м���vip������������ */
	private int BJ_sec_vipmaxuser;
	/** ����28�߼���vip������������ */
	private int BJ_thr_vipmaxuser;
	/** ���ô�28������vip������������ */
	private int JND_fir_vipmaxuser;
	/** ���ô�28�м���vip������������ */
	private int JND_sec_vipmaxuser;
	/** ���ô�28�߼���vip������������ */
	private int JND_thr_vipmaxuser;
	private Request<JSONObject> request;
	private RequestQueue shouyeQueue;
	private String account;
	private String imei;
	/******************************** ���������ݽ��� ***************************************/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.dhxgzs.goodluck.R.layout.activity_fang_jian);
		Intent intent = getIntent();
		fangjianType = intent.getStringExtra("fangjianType");
		wanfaType = intent.getStringExtra("wanfaType");
		VIP1 = intent.getStringExtra("VIP1");
		VIP2 = intent.getStringExtra("VIP2");
		VIP3 = intent.getStringExtra("VIP3");
		VIP4 = intent.getStringExtra("VIP4");
		initView();
		chatRoom();
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

	private void chatRoom() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		chatRoomList = new ArrayList<EMChatRoom>();
		rooms = new ArrayList<EMChatRoom>();

		loadAndShowData();
		// �ڻỰҳ��ע���������������Ա���ߺ������ұ�ɾ��
		EMClient.getInstance().chatroomManager().addChatRoomChangeListener(new EMChatRoomChangeListener() {
			@Override
			public void onChatRoomDestroyed(String roomId, String roomName) {
				chatRoomList.clear();
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						loadAndShowData();
					}

				});
			}

			@Override
			public void onMemberJoined(String roomId, String participant) {
			}

			@Override
			public void onMemberExited(String roomId, String roomName, String participant) {

			}

			@Override
			public void onMemberKicked(String roomId, String roomName, String participant) {
			}

		});

	}

	private void initView() {
		shouyeQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());
		SharedPreferencesUtils.putValue(getApplicationContext(), "wanfaType", wanfaType);
		picAndNickRequest();
		if (wanfaType.equals("����")) {
			BJ_fir_vipmaxuser = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_fir_vipmaxuser"));
			BJ_sec_vipmaxuser = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_sec_vipmaxuser"));
			BJ_thr_vipmaxuser = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_thr_vipmaxuser"));

			BJ_fir_vip1_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_fir_vip1_personNum"));
			BJ_fir_vip2_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_fir_vip2_personNum"));
			BJ_fir_vip3_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_fir_vip3_personNum"));
			BJ_fir_vip4_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_fir_vip4_personNum"));

			BJ_sec_vip1_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_sec_vip1_personNum"));
			BJ_sec_vip2_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_sec_vip2_personNum"));
			BJ_sec_vip3_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_sec_vip3_personNum"));
			BJ_sec_vip4_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_sec_vip4_personNum"));

			BJ_thr_vip1_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_thr_vip1_personNum"));
			BJ_thr_vip2_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_thr_vip2_personNum"));
			BJ_thr_vip3_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_thr_vip3_personNum"));
			BJ_thr_vip4_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "BJ_thr_vip4_personNum"));
		} else if (wanfaType.equals("���ô�")) {

			JND_fir_vipmaxuser = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_fir_vipmaxuser"));
			JND_sec_vipmaxuser = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_sec_vipmaxuser"));
			JND_thr_vipmaxuser = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_thr_vipmaxuser"));

			JND_fir_vip1_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_fir_vip1_personNum"));
			JND_fir_vip2_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_fir_vip2_personNum"));
			JND_fir_vip3_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_fir_vip3_personNum"));
			JND_fir_vip4_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_fir_vip4_personNum"));

			JND_sec_vip1_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_sec_vip1_personNum"));
			JND_sec_vip2_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_sec_vip2_personNum"));
			JND_sec_vip3_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_sec_vip3_personNum"));
			JND_sec_vip4_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_sec_vip4_personNum"));

			JND_thr_vip1_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_thr_vip1_personNum"));
			JND_thr_vip2_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_thr_vip2_personNum"));
			JND_thr_vip3_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_thr_vip3_personNum"));
			JND_thr_vip4_personNum = Integer
					.parseInt(SharedPreferencesUtils.getValue(getApplicationContext(), "JND_thr_vip4_personNum"));
		}
		fangjian_back = (ImageView) findViewById(R.id.fangjian_back);

		Bj_FJ1_ZaiXianPerson_Num = (TextView) findViewById(R.id.Bj_FJ1_ZaiXianPerson_Num);
		Bj_FJ2_ZaiXianPerson_Num = (TextView) findViewById(R.id.Bj_FJ2_ZaiXianPerson_Num);
		Bj_FJ3_ZaiXianPerson_Num = (TextView) findViewById(R.id.Bj_FJ3_ZaiXianPerson_Num);
		Bj_FJ4_ZaiXianPerson_Num = (TextView) findViewById(R.id.Bj_FJ4_ZaiXianPerson_Num);

		Bj_FJ1_image = (RelativeLayout) findViewById(R.id.Bj_FJ1_image);
		Bj_FJ2_image = (RelativeLayout) findViewById(R.id.Bj_FJ2_image);
		Bj_FJ3_image = (RelativeLayout) findViewById(R.id.Bj_FJ3_image);
		Bj_FJ4_image = (RelativeLayout) findViewById(R.id.Bj_FJ4_image);

		Bj_FJ1_ZaiXianPerson_Num.setText("����" + VIP1 + "��");
		Bj_FJ2_ZaiXianPerson_Num.setText("����" + VIP2 + "��");
		Bj_FJ3_ZaiXianPerson_Num.setText("����" + VIP3 + "��");
		Bj_FJ4_ZaiXianPerson_Num.setText("����" + VIP4 + "��");
	}

	private void picAndNickRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.UPDATAUSERINFO_URL,RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		shouyeQueue.add(PICANDNICK, request, responseListener);

	}
	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {

			case PICANDNICK:
				System.out.println("��ȡ������Ϣ" + response);
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {
						UserInfoEntity userInfoEntity = new UserInfoEntity();
						userInfoEntity = JSON.parseObject(biz_content, UserInfoEntity.class);
/*						// ��������ͷ��
											Glide.with(getApplicationContext()).load(userInfoEntity.getAvatar()).asBitmap()
													.error(R.drawable.touxiang).into(mine_Photo);
											mine_name.setText(userInfoEntity.getNickname());
											mine_qianming.setText(userInfoEntity.getSignname());*/

						SharedPreferencesUtils.putValue(getApplicationContext(), "NICK_NAME", userInfoEntity.getNickname());
						SharedPreferencesUtils.putValue(getApplicationContext(), "PIC", userInfoEntity.getAvatar());
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
	private void setListener() {
		fangjian_back.setOnClickListener(this);
		Bj_FJ1_image.setOnClickListener(this);
		Bj_FJ2_image.setOnClickListener(this);
		Bj_FJ3_image.setOnClickListener(this);
		Bj_FJ4_image.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fangjian_back:
			finish();
			break;
		case R.id.Bj_FJ1_image:
			SharedPreferencesUtils.putValue(getApplicationContext(), "vipType", "vip1");
			if (wanfaType.equals("����")) {

				if (fangjianType.equals("����")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					System.out.println(wanfaType + fangjianType + bj_fir_vip1);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_fir_vipmaxuser <= BJ_fir_vip1_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {

						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_fir_vip1));
						//						Toast.makeText(getApplicationContext(), "VIP1", Toast.LENGTH_SHORT).show();
					}

				} else if (fangjianType.equals("�м�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					System.out.println(wanfaType + fangjianType + bj_sec_vip1);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_sec_vipmaxuser <= BJ_sec_vip1_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_sec_vip1));
						//						Toast.makeText(getApplicationContext(), "VIP1", Toast.LENGTH_SHORT).show();
					}
				} else if (fangjianType.equals("�߼�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					System.out.println(wanfaType + fangjianType + bj_thr_vip1);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_thr_vipmaxuser <= BJ_thr_vip1_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_thr_vip1));
						//						Toast.makeText(getApplicationContext(), "VIP1", Toast.LENGTH_SHORT).show();
					}
				}

			} else if (wanfaType.equals("���ô�")) {

				if (fangjianType.equals("����")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					System.out.println(wanfaType + fangjianType + cakeno_fir_vip1);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_fir_vipmaxuser <= JND_fir_vip1_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_fir_vip1));
					}
				} else if (fangjianType.equals("�м�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					System.out.println(wanfaType + fangjianType + cakeno_sec_vip1);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_sec_vipmaxuser <= JND_sec_vip1_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_sec_vip1));
					}
				} else if (fangjianType.equals("�߼�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					System.out.println(wanfaType + fangjianType + cakeno_thr_vip1);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_thr_vipmaxuser <= JND_thr_vip1_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_thr_vip1));
					}
				}

			}

			break;
		case R.id.Bj_FJ2_image:
			SharedPreferencesUtils.putValue(getApplicationContext(), "vipType", "vip2");
			if (wanfaType.equals("����")) {

				if (fangjianType.equals("����")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_fir_vipmaxuser <= BJ_fir_vip2_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_fir_vip2));
						//						Toast.makeText(getApplicationContext(), "VIP2", Toast.LENGTH_SHORT).show();
					}
				} else if (fangjianType.equals("�м�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_sec_vipmaxuser <= BJ_sec_vip2_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_sec_vip2));
						//						Toast.makeText(getApplicationContext(), "VIP2", Toast.LENGTH_SHORT).show();
					}
				} else if (fangjianType.equals("�߼�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_thr_vipmaxuser <= BJ_thr_vip2_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_thr_vip2));
						//						Toast.makeText(getApplicationContext(), "VIP2", Toast.LENGTH_SHORT).show();
					}
				}

			} else if (wanfaType.equals("���ô�")) {

				if (fangjianType.equals("����")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_fir_vipmaxuser <= JND_fir_vip2_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_fir_vip2));
					}
				} else if (fangjianType.equals("�м�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_sec_vipmaxuser <= JND_sec_vip2_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_sec_vip2));
					}
				} else if (fangjianType.equals("�߼�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_thr_vipmaxuser <= JND_thr_vip2_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_thr_vip2));
					}
				}

			}

			break;
		case R.id.Bj_FJ3_image:
			SharedPreferencesUtils.putValue(getApplicationContext(), "vipType", "vip3");
			if (wanfaType.equals("����")) {

				if (fangjianType.equals("����")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_fir_vipmaxuser <= BJ_fir_vip3_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_fir_vip3));
						//						Toast.makeText(getApplicationContext(), "VIP3", Toast.LENGTH_SHORT).show();
					}
				} else if (fangjianType.equals("�м�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_sec_vipmaxuser <= BJ_sec_vip3_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_sec_vip3));
						//						Toast.makeText(getApplicationContext(), "VIP3", Toast.LENGTH_SHORT).show();
					}
				} else if (fangjianType.equals("�߼�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_thr_vipmaxuser <= BJ_thr_vip3_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_thr_vip3));
						//						Toast.makeText(getApplicationContext(), "VIP3", Toast.LENGTH_SHORT).show();
					}
				}

			} else if (wanfaType.equals("���ô�")) {

				if (fangjianType.equals("����")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_fir_vipmaxuser <= JND_fir_vip3_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_fir_vip3));
					}
				} else if (fangjianType.equals("�м�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_sec_vipmaxuser <= JND_sec_vip3_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_sec_vip3));
					}
				} else if (fangjianType.equals("�߼�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_thr_vipmaxuser <= JND_thr_vip3_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_thr_vip3));
					}
				}

			}
			break;
		case R.id.Bj_FJ4_image:
			SharedPreferencesUtils.putValue(getApplicationContext(), "vipType", "vip4");
			if (wanfaType.equals("����")) {

				if (fangjianType.equals("����")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_fir_vipmaxuser <= BJ_fir_vip4_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_fir_vip4));
						//						Toast.makeText(getApplicationContext(), "VIP4", Toast.LENGTH_SHORT).show();
					}
				} else if (fangjianType.equals("�м�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_sec_vipmaxuser <= BJ_sec_vip4_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_sec_vip4));
						//						Toast.makeText(getApplicationContext(), "VIP4", Toast.LENGTH_SHORT).show();
					}
				} else if (fangjianType.equals("�߼�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (BJ_thr_vipmaxuser <= BJ_thr_vip4_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", bj_thr_vip4));
						//						Toast.makeText(getApplicationContext(), "VIP4", Toast.LENGTH_SHORT).show();
					}
				}

			} else if (wanfaType.equals("���ô�")) {

				if (fangjianType.equals("����")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_fir_vipmaxuser <= JND_fir_vip4_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_fir_vip4));
					}
				} else if (fangjianType.equals("�м�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_sec_vipmaxuser <= JND_sec_vip4_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_sec_vip4));
					}
				} else if (fangjianType.equals("�߼�")) {
					SharedPreferencesUtils.putValue(getApplicationContext(), "fangjianType", fangjianType);
					// �жϵ�ǰ���������Ƿ�����
					if (JND_thr_vipmaxuser <= JND_thr_vip4_personNum) {

						Toast.makeText(getApplicationContext(), "������������", Toast.LENGTH_SHORT).show();

					} else {
						startActivity(new Intent(FangJianActivity.this, ChatActivity.class).putExtra("chatType", 3)
								.putExtra("userId", cakeno_thr_vip4));
					}
				}

			}
			break;

		default:
			break;
		}
	}

	private void loadAndShowData() {
		new Thread(new Runnable() {

			public void run() {
				try {
					isLoading = true;
					pagenum += 1;
					final EMPageResult<EMChatRoom> result = EMClient.getInstance().chatroomManager()
							.fetchPublicChatRoomsFromServer(pagenum, pagesize);
					// get chat room list
					final List<EMChatRoom> chatRooms = result.getData();
					pageCount = result.getPageCount();
					Log.d("test","����PC��"+result.getPageCount());
					Log.d("test","����Data��"+result.getData());
					runOnUiThread(new Runnable() {

						public void run() {
							chatRoomList.addAll(chatRooms);
							if (isFirstLoading) {
								isFirstLoading = false;
								System.out.println(chatRoomList);

								for (int i = 0; i <= 23; i++) {

									fangjianName = chatRoomList.get(i).getName();
									fangjianId = chatRoomList.get(i).getId();
									System.out.println(fangjianName);
									System.out.println(fangjianId);

									if (fangjianName.equals("bj-fir-vip1")) {

										bj_fir_vip1 = fangjianId;

									} else if (fangjianName.equals("bj-fir-vip2")) {

										bj_fir_vip2 = fangjianId;

									} else if (fangjianName.equals("bj-fir-vip3")) {

										bj_fir_vip3 = fangjianId;

									} else if (fangjianName.equals("bj-fir-vip4")) {

										bj_fir_vip4 = fangjianId;

									} else if (fangjianName.equals("bj-sec-vip1")) {

										bj_sec_vip1 = fangjianId;

									} else if (fangjianName.equals("bj-sec-vip2")) {

										bj_sec_vip2 = fangjianId;

									} else if (fangjianName.equals("bj-sec-vip3")) {

										bj_sec_vip3 = fangjianId;

									} else if (fangjianName.equals("bj-sec-vip4")) {

										bj_sec_vip4 = fangjianId;

									} else if (fangjianName.equals("bj-thr-vip1")) {

										bj_thr_vip1 =fangjianId;

									} else if (fangjianName.equals("bj-thr-vip2")) {

										bj_thr_vip2 = fangjianId;

									} else if (fangjianName.equals("bj-thr-vip3")) {

										bj_thr_vip3 = fangjianId;

									} else if (fangjianName.equals("bj-thr-vip4")) {

										bj_thr_vip4 = fangjianId;

									} else if (fangjianName.equals("cakeno-fir-vip1")) {

										cakeno_fir_vip1 =fangjianId;

									} else if (fangjianName.equals("cakeno-fir-vip2")) {

										cakeno_fir_vip2 = fangjianId;

									} else if (fangjianName.equals("cakeno-fir-vip3")) {

										cakeno_fir_vip3 = fangjianId;

									} else if (fangjianName.equals("cakeno-fir-vip4")) {

										cakeno_fir_vip4 = fangjianId;

									} else if (fangjianName.equals("cakeno-sec-vip1")) {

										cakeno_sec_vip1 = fangjianId;

									} else if (fangjianName.equals("cakeno-sec-vip2")) {

										cakeno_sec_vip2 = fangjianId;

									} else if (fangjianName.equals("cakeno-sec-vip3")) {

										cakeno_sec_vip3 = fangjianId;

									} else if (fangjianName.equals("cakeno-sec-vip4")) {

										cakeno_sec_vip4 = fangjianId;

									} else if (fangjianName.equals("cakeno-thr-vip1")) {

										cakeno_thr_vip1 = fangjianId;

									} else if (fangjianName.equals("cakeno-thr-vip2")) {

										cakeno_thr_vip2 = fangjianId;

									} else if (fangjianName.equals("cakeno-thr-vip3")) {

										cakeno_thr_vip3 = fangjianId;

									} else if (fangjianName.equals("cakeno-thr-vip4")) {

										cakeno_thr_vip4 = fangjianId;

									}
								}
								rooms.addAll(chatRooms);
							} else {
								if (chatRooms.size() < pagesize) {
									hasMoreData = false;
								}
							}
							isLoading = false;
						}
					});
				} catch (HyphenateException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						public void run() {
							isLoading = false;
						}
					});
				}
			}
		}).start();
	}
}
