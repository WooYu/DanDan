package com.dhxgzs.goodluck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.activity.Game_NotesActivity;
import com.dhxgzs.goodluck.activity.MyHuiShuiActivity;
import com.dhxgzs.goodluck.activity.PersonDataActivity;
import com.dhxgzs.goodluck.activity.QianBaoActivity;
import com.dhxgzs.goodluck.activity.SettingActivity;
import com.dhxgzs.goodluck.activity.Share_ErWeiMaActivity;
import com.dhxgzs.goodluck.activity.ZhangBian_NotesActivity;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.UserInfoEntity;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �ҵ�ҳ��
 * 
 * @author Administrator
 *
 */

@SuppressLint("SdCardPath")
public class MineActivity extends Activity implements OnClickListener {
	private static final int UPPHOTO = 0;
	private static final int UPDATAUSERINFO = 1;
	private static final int ACCOUNTMONEY = 2;
	/** �ҵ�ҳ���û�ͷ�� */
	private ImageView mine_Photo;
	/** �ҵ�ҳ���û��� */
	private TextView mine_name;
	/** �ҵ�ҳ���ҵ�ǩ�� */
	private TextView mine_qianming;
	/** �ҵ�ҳ��������Ҽ�ͷ */
	private ImageView mine_write_right;
	/** Ǯ�� */
	private RelativeLayout my_qianbao;
	/** Ǯ����Ԫ������ */
	private TextView mine_yuanbao_num;
	/** �ҵĻ�ˮ */
	private RelativeLayout my_huishui;
	/** ���˳齱 */
	private RelativeLayout my_xingyunjilu;
	/** �ҵ��˱��¼ */
	private RelativeLayout my_zhangbian_notes;
	/** �ҵ���Ϸ��¼ */
	private RelativeLayout my_game_notes;
	/** ���� */
	private RelativeLayout my_game_biaoqing;
	/** �ҵķ��� */
	private RelativeLayout my_share;
	/**�ҵ�����*/
	private RelativeLayout my_wodeshouyi;
	/** ���� */
	private RelativeLayout my_setting;
	/** ���� */
	private RelativeLayout my_about;
	
	private Bitmap head;
	private String fileName;
	private File file;
	/** ���ر���·�� */
	public static String path = "/sdcard/xingyun28/";

	private Request<JSONObject> request;
	private RequestQueue upPhotoQueue;
	private String account;
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mine);

		initView();
		setlistener();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UpDataUserInfoRequest();
		// �û�ʣ��Ԫ����
		AccountMoneyRequest();
	}

	private void initView() {
		upPhotoQueue = NoHttp.newRequestQueue();
		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());
		// �����û�������Ϣ��ͷ���ǳ�
		UpDataUserInfoRequest();
		// �û�ʣ��Ԫ����
		AccountMoneyRequest();
		// �ҵ�ҳ���û�ͷ��
		mine_Photo = (ImageView) findViewById(R.id.mine_Photo);
		// �ҵ�ҳ���û���
		mine_name = (TextView) findViewById(R.id.mine_name);
		// �ҵ�ҳ���ҵ�ǩ��
		mine_qianming = (TextView) findViewById(R.id.mine_qianming);
		// �ҵ�ҳ��������Ҽ�ͷ
		mine_write_right = (ImageView) findViewById(R.id.mine_write_right);
		// Ǯ��
		my_qianbao = (RelativeLayout) findViewById(R.id.my_qianbao);
		// Ǯ����Ԫ������
		mine_yuanbao_num = (TextView) findViewById(R.id.mine_yuanbao_num);
		// �ҵĻ�ˮ
		my_huishui = (RelativeLayout) findViewById(R.id.my_huishui);
		// ���˳齱 
		my_xingyunjilu = (RelativeLayout) findViewById(R.id.my_xingyunjilu);
		// �ҵ��˱��¼
		my_zhangbian_notes = (RelativeLayout) findViewById(R.id.my_zhangbian_notes);
		// �ҵ���Ϸ��¼
		my_game_notes = (RelativeLayout) findViewById(R.id.my_game_notes);
		// ���� 
		my_game_biaoqing = (RelativeLayout) findViewById(R.id.my_game_biaoqing);
		// �ҵķ���
		my_share = (RelativeLayout) findViewById(R.id.my_share);
		//�ҵ�����
		my_wodeshouyi=(RelativeLayout) findViewById(R.id.my_wodeshouyi);
		// ����
		my_setting = (RelativeLayout) findViewById(R.id.my_setting);
		// ����
		my_about = (RelativeLayout) findViewById(R.id.my_about);
		// getBitmap();
	}

	/** �û�ʣ��Ԫ���� */
	private void AccountMoneyRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.ACCOUNTMONEY_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		upPhotoQueue.add(ACCOUNTMONEY, request, responseListener);

	}

	/** �����û�������Ϣ��ͷ���ǳ� */
	private void UpDataUserInfoRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.UPDATAUSERINFO_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		upPhotoQueue.add(UPDATAUSERINFO, request, responseListener);

	}

	// private void getBitmap() {
	// if (fileName.isEmpty()) {
	// mine_Photo.setImageResource(R.drawable.touxiang);
	//// Glide.with(this).load(picpath).asBitmap().error(R.drawable.touxiang).into(mine_Photo);
	// } else {
	// System.out.println("########" + fileName);
	// Bitmap bitmap = BitmapFactory.decodeFile(fileName);
	// mine_Photo.setImageBitmap(bitmap);
	// }
	//
	// }
	private void setlistener() {
		// �ҵ�ҳ���û�ͷ��
		mine_Photo.setOnClickListener(this);
		// �ҵ�ҳ��������Ҽ�ͷ
		mine_write_right.setOnClickListener(this);
		// Ǯ��
		my_qianbao.setOnClickListener(this);
		// �ҵĻ�ˮ
		my_huishui.setOnClickListener(this);
		// ���˳齱 
		my_xingyunjilu.setOnClickListener(this);
		// �ҵ��˱��¼
		my_zhangbian_notes.setOnClickListener(this);
		// �ҵ���Ϸ��¼
		my_game_notes.setOnClickListener(this);
		//����
		my_game_biaoqing.setOnClickListener(this);
		// �ҵķ���
		my_share.setOnClickListener(this);
		//�ҵ�����
		my_wodeshouyi.setOnClickListener(this);
		// ����
		my_setting.setOnClickListener(this);
		// ����
		my_about.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.mine_Photo:// �ҵ�ҳ���û�ͷ��
			ShowPickDialog();// ����ʵ�ַ���
			// Toast.makeText(getApplicationContext(), "�ҵ�ͷ��",
			// Toast.LENGTH_SHORT).show();
			break;
		case R.id.mine_write_right:// �ҵ�ҳ��������Ҽ�ͷ
			// Toast.makeText(getApplicationContext(), "�ҵ�ҳ��������Ҽ�ͷ",
			// Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MineActivity.this, PersonDataActivity.class);
			startActivity(intent);
			break;
		case R.id.my_qianbao:// Ǯ��
			// Toast.makeText(getApplicationContext(), "Ǯ��",
			// Toast.LENGTH_SHORT).show();
			Intent intent1 = new Intent(MineActivity.this, QianBaoActivity.class);
			startActivity(intent1);
			break;
		case R.id.my_huishui:// �ҵĻ�ˮ
			// Toast.makeText(getApplicationContext(), "�ҵĻ�ˮ",
			// Toast.LENGTH_SHORT).show();
			Intent intent2 = new Intent(MineActivity.this, MyHuiShuiActivity.class);
			startActivity(intent2);
			break;
		case R.id.my_xingyunjilu:// ���˳齱
			 Toast.makeText(getApplicationContext(), "�˹�����δ����",
			 Toast.LENGTH_SHORT).show();
//			Intent intent2 = new Intent(MineActivity.this, MyHuiShuiActivity.class);
//			startActivity(intent2);
			break;
		case R.id.my_zhangbian_notes:// �ҵ��˱��¼
			// Toast.makeText(getApplicationContext(), "�ҵ��˱��¼",
			// Toast.LENGTH_SHORT).show();
			Intent intent3 = new Intent(MineActivity.this, ZhangBian_NotesActivity.class);
			startActivity(intent3);
			break;
		case R.id.my_game_notes:// �ҵ���Ϸ��¼
			// Toast.makeText(getApplicationContext(), "�ҵ���Ϸ��¼",
			// Toast.LENGTH_SHORT).show();
			Intent intent4 = new Intent(MineActivity.this, Game_NotesActivity.class);
			startActivity(intent4);
			break;
		case R.id.my_game_biaoqing:// �ҵı���
			 Toast.makeText(getApplicationContext(), "�˹�����δ����",
			 Toast.LENGTH_SHORT).show();
//			Intent intent2 = new Intent(MineActivity.this, MyHuiShuiActivity.class);
//			startActivity(intent2);
			break;
		case R.id.my_share:// �ҵķ���
			// Toast.makeText(getApplicationContext(), "�ҵķ���",
			// Toast.LENGTH_SHORT).show();
			Intent intent5 = new Intent(MineActivity.this, SharekActivity.class);
			startActivity(intent5);
			break;
		case R.id.my_wodeshouyi:// �ҵķ���
			// Toast.makeText(getApplicationContext(), "�ҵķ���",
			// Toast.LENGTH_SHORT).show();
			Intent intent8 = new Intent(MineActivity.this, WodeShouYiActivity.class);
			startActivity(intent8);
			break;
		case R.id.my_setting:// ����
			// Toast.makeText(getApplicationContext(), "����",
			// Toast.LENGTH_SHORT).show();
			Intent intent6 = new Intent(MineActivity.this, SettingActivity.class);
			startActivity(intent6);
			finish();
			break;
		case R.id.my_about:// ����
			// Toast.makeText(getApplicationContext(), "����",
			// Toast.LENGTH_SHORT).show();
			Intent intent7 = new Intent(MineActivity.this, AboutActivity.class);
			startActivity(intent7);
			break;

		default:
			break;
		}

	}

	private void ShowPickDialog() {

		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.alertdialog);
		window.setGravity(Gravity.BOTTOM);

		TextView takephoto = (TextView) window.findViewById(R.id.tv_content1);
		takephoto.setText("����");
		takephoto.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SdCardPath")
			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent2.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
				startActivityForResult(intent2, 2);
				dialog.cancel();
			}
		});
		TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
		tv_xiangce.setText("���");
		tv_xiangce.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(Intent.ACTION_PICK, null);
				intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent1, 1);

				dialog.cancel();
			}
		});

		TextView tv_quxiao = (TextView) window.findViewById(R.id.tv_content3);
		tv_quxiao.setText("ȡ��");
		tv_quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				startPhotoZoom(data.getData());
				break;
			case 2:
				File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
				startPhotoZoom(Uri.fromFile(temp));
				break;
			case 3:
				/**
				 * �ǿ��жϴ��һ��Ҫ��֤���������֤�Ļ��� �ڼ���֮��������ֲ����⣬Ҫ���²ü�������
				 * ��ǰ����ʱ���ᱨNullException�����Ը��ݲ�ͬ����ں��ʵ� �ط����жϴ����������
				 * 
				 */
				if (data != null) {
					Bundle extras = data.getExtras();
					head = extras.getParcelable("data");
					if (head != null) {

						try {
							setPicToView(head);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} // ������SD����
						mine_Photo.setImageBitmap(head);// ��ImageView��ʾ����
						// �ϴ�������Ƭ������
						file = new File(fileName);

						System.out.println("�ϴ�ͼƬ����" + file);
						upPhotoRequest(file);

					}
				}
				break;

			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/** ���ļ��ķ�ʽ�ϴ�ͼƬ����̨ */
	private void upPhotoRequest(File file) {

		request = NoHttp.createJsonObjectRequest(XyMyContent.UPPHOTO_URL, RequestMethod.POST);
		Log.d("test","file��"+file);
		request.add("account", account);
		request.add("imei", imei);
		request.add("", file);
		
		upPhotoQueue.add(UPPHOTO, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			switch (what) {
			case UPPHOTO:

				try {
					System.out.println("�ϴ�ͷ��" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");

					if (state.equals("success")) {
						UpDataUserInfoRequest();
						// Toast.makeText(getApplicationContext(), biz_content,
						// Toast.LENGTH_SHORT).show();

					} else if (state.equals("error")) {
						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case UPDATAUSERINFO:

		
				try {
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					System.out.println("��ȡ������Ϣ" + biz_content);
					if (state.equals("success")) {
						UserInfoEntity userInfoEntity = new UserInfoEntity();
						userInfoEntity = JSON.parseObject(biz_content, UserInfoEntity.class);
						// ��������ͷ��
						Glide.with(getApplicationContext()).load(userInfoEntity.getAvatar()).asBitmap()
								.error(R.drawable.touxiang).into(mine_Photo);
						mine_name.setText(userInfoEntity.getNickname());
						mine_qianming.setText(userInfoEntity.getSignname());
						SharedPreferencesUtils.putValue(getApplicationContext(), "USERID", userInfoEntity.getUserid());
						SharedPreferencesUtils.putValue(getApplicationContext(), "DGVIP", userInfoEntity.getDengji());
						System.out.println("����id" + userInfoEntity.getUserid());
						// Toast.makeText(getApplicationContext(), "��ȡ�ɹ�",
						// Toast.LENGTH_SHORT).show();
					} else if (state.equals("error")) {

						Toast.makeText(getApplicationContext(), biz_content, Toast.LENGTH_SHORT).show();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case ACCOUNTMONEY:

				try {
					System.out.println("ʣ��Ԫ��������" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						UserInfoEntity entity = new UserInfoEntity();

						entity = JSON.parseObject(biz_content, UserInfoEntity.class);
						mine_yuanbao_num.setText(entity.getMoney() + "Ԫ��");
						SharedPreferencesUtils.putValue(getApplicationContext(), "yuanbaoNum", entity.getMoney());

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

	/**
	 * �ü�ͼƬ�ķ���
	 * 
	 * @param data
	 */
	private void startPhotoZoom(Uri data) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		// ������� crop=true �������ڿ�����intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", true);
		// asceptX asceptY �ǿ�ߵı���
		intent.putExtra("asceptX", 1);
		intent.putExtra("asceptY", 1);
		// outputX outpoutY
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);

	}

	/**
	 * �������֮���ͼƬ
	 * 
	 * @param data
	 * @throws IOException
	 */
	private void setPicToView(Bitmap mBitmap) throws IOException {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			return;
		}

		FileOutputStream b = null;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();// �����ļ���
		}

		File mfile = new File(path, "head.jpg");
		if (!mfile.exists()) {
			mfile.createNewFile();// �����ļ���
		}
		fileName = path + "head.jpg";
		// ͼƬ����
		try {
			b = new FileOutputStream(mfile);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, b);// ������д���ļ�

			b.flush();
			b.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
