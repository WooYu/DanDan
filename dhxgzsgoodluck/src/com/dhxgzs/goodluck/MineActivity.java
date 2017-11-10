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
 * 我的页面
 * 
 * @author Administrator
 *
 */

@SuppressLint("SdCardPath")
public class MineActivity extends Activity implements OnClickListener {
	private static final int UPPHOTO = 0;
	private static final int UPDATAUSERINFO = 1;
	private static final int ACCOUNTMONEY = 2;
	/** 我的页面用户头像 */
	private ImageView mine_Photo;
	/** 我的页面用户名 */
	private TextView mine_name;
	/** 我的页面我的签名 */
	private TextView mine_qianming;
	/** 我的页面标题向右箭头 */
	private ImageView mine_write_right;
	/** 钱包 */
	private RelativeLayout my_qianbao;
	/** 钱包内元宝数量 */
	private TextView mine_yuanbao_num;
	/** 我的回水 */
	private RelativeLayout my_huishui;
	/** 幸运抽奖 */
	private RelativeLayout my_xingyunjilu;
	/** 我的账变记录 */
	private RelativeLayout my_zhangbian_notes;
	/** 我的游戏记录 */
	private RelativeLayout my_game_notes;
	/** 表情 */
	private RelativeLayout my_game_biaoqing;
	/** 我的分享 */
	private RelativeLayout my_share;
	/**我的收益*/
	private RelativeLayout my_wodeshouyi;
	/** 设置 */
	private RelativeLayout my_setting;
	/** 关于 */
	private RelativeLayout my_about;
	
	private Bitmap head;
	private String fileName;
	private File file;
	/** 本地保存路径 */
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
		// 用户剩余元宝数
		AccountMoneyRequest();
	}

	private void initView() {
		upPhotoQueue = NoHttp.newRequestQueue();
		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());
		// 更新用户个人信息：头像、昵称
		UpDataUserInfoRequest();
		// 用户剩余元宝数
		AccountMoneyRequest();
		// 我的页面用户头像
		mine_Photo = (ImageView) findViewById(R.id.mine_Photo);
		// 我的页面用户名
		mine_name = (TextView) findViewById(R.id.mine_name);
		// 我的页面我的签名
		mine_qianming = (TextView) findViewById(R.id.mine_qianming);
		// 我的页面标题向右箭头
		mine_write_right = (ImageView) findViewById(R.id.mine_write_right);
		// 钱包
		my_qianbao = (RelativeLayout) findViewById(R.id.my_qianbao);
		// 钱包内元宝数量
		mine_yuanbao_num = (TextView) findViewById(R.id.mine_yuanbao_num);
		// 我的回水
		my_huishui = (RelativeLayout) findViewById(R.id.my_huishui);
		// 幸运抽奖 
		my_xingyunjilu = (RelativeLayout) findViewById(R.id.my_xingyunjilu);
		// 我的账变记录
		my_zhangbian_notes = (RelativeLayout) findViewById(R.id.my_zhangbian_notes);
		// 我的游戏记录
		my_game_notes = (RelativeLayout) findViewById(R.id.my_game_notes);
		// 表情 
		my_game_biaoqing = (RelativeLayout) findViewById(R.id.my_game_biaoqing);
		// 我的分享
		my_share = (RelativeLayout) findViewById(R.id.my_share);
		//我的收益
		my_wodeshouyi=(RelativeLayout) findViewById(R.id.my_wodeshouyi);
		// 设置
		my_setting = (RelativeLayout) findViewById(R.id.my_setting);
		// 关于
		my_about = (RelativeLayout) findViewById(R.id.my_about);
		// getBitmap();
	}

	/** 用户剩余元宝数 */
	private void AccountMoneyRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.ACCOUNTMONEY_URL, RequestMethod.POST);

		request.add("account", account);
		request.add("imei", imei);

		upPhotoQueue.add(ACCOUNTMONEY, request, responseListener);

	}

	/** 更新用户个人信息：头像、昵称 */
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
		// 我的页面用户头像
		mine_Photo.setOnClickListener(this);
		// 我的页面标题向右箭头
		mine_write_right.setOnClickListener(this);
		// 钱包
		my_qianbao.setOnClickListener(this);
		// 我的回水
		my_huishui.setOnClickListener(this);
		// 幸运抽奖 
		my_xingyunjilu.setOnClickListener(this);
		// 我的账变记录
		my_zhangbian_notes.setOnClickListener(this);
		// 我的游戏记录
		my_game_notes.setOnClickListener(this);
		//表情
		my_game_biaoqing.setOnClickListener(this);
		// 我的分享
		my_share.setOnClickListener(this);
		//我的收益
		my_wodeshouyi.setOnClickListener(this);
		// 设置
		my_setting.setOnClickListener(this);
		// 关于
		my_about.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.mine_Photo:// 我的页面用户头像
			ShowPickDialog();// 拍照实现方法
			// Toast.makeText(getApplicationContext(), "我的头像",
			// Toast.LENGTH_SHORT).show();
			break;
		case R.id.mine_write_right:// 我的页面标题向右箭头
			// Toast.makeText(getApplicationContext(), "我的页面标题向右箭头",
			// Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(MineActivity.this, PersonDataActivity.class);
			startActivity(intent);
			break;
		case R.id.my_qianbao:// 钱包
			// Toast.makeText(getApplicationContext(), "钱包",
			// Toast.LENGTH_SHORT).show();
			Intent intent1 = new Intent(MineActivity.this, QianBaoActivity.class);
			startActivity(intent1);
			break;
		case R.id.my_huishui:// 我的回水
			// Toast.makeText(getApplicationContext(), "我的回水",
			// Toast.LENGTH_SHORT).show();
			Intent intent2 = new Intent(MineActivity.this, MyHuiShuiActivity.class);
			startActivity(intent2);
			break;
		case R.id.my_xingyunjilu:// 幸运抽奖
			 Toast.makeText(getApplicationContext(), "此功能暂未开放",
			 Toast.LENGTH_SHORT).show();
//			Intent intent2 = new Intent(MineActivity.this, MyHuiShuiActivity.class);
//			startActivity(intent2);
			break;
		case R.id.my_zhangbian_notes:// 我的账变记录
			// Toast.makeText(getApplicationContext(), "我的账变记录",
			// Toast.LENGTH_SHORT).show();
			Intent intent3 = new Intent(MineActivity.this, ZhangBian_NotesActivity.class);
			startActivity(intent3);
			break;
		case R.id.my_game_notes:// 我的游戏记录
			// Toast.makeText(getApplicationContext(), "我的游戏记录",
			// Toast.LENGTH_SHORT).show();
			Intent intent4 = new Intent(MineActivity.this, Game_NotesActivity.class);
			startActivity(intent4);
			break;
		case R.id.my_game_biaoqing:// 我的表情
			 Toast.makeText(getApplicationContext(), "此功能暂未开放",
			 Toast.LENGTH_SHORT).show();
//			Intent intent2 = new Intent(MineActivity.this, MyHuiShuiActivity.class);
//			startActivity(intent2);
			break;
		case R.id.my_share:// 我的分享
			// Toast.makeText(getApplicationContext(), "我的分享",
			// Toast.LENGTH_SHORT).show();
			Intent intent5 = new Intent(MineActivity.this, SharekActivity.class);
			startActivity(intent5);
			break;
		case R.id.my_wodeshouyi:// 我的分享
			// Toast.makeText(getApplicationContext(), "我的分享",
			// Toast.LENGTH_SHORT).show();
			Intent intent8 = new Intent(MineActivity.this, WodeShouYiActivity.class);
			startActivity(intent8);
			break;
		case R.id.my_setting:// 设置
			// Toast.makeText(getApplicationContext(), "设置",
			// Toast.LENGTH_SHORT).show();
			Intent intent6 = new Intent(MineActivity.this, SettingActivity.class);
			startActivity(intent6);
			finish();
			break;
		case R.id.my_about:// 关于
			// Toast.makeText(getApplicationContext(), "关于",
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
		takephoto.setText("拍照");
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
		tv_xiangce.setText("相册");
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
		tv_quxiao.setText("取消");
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
				 * 非空判断大家一定要验证，如果不验证的话， 在剪裁之后如果发现不满意，要重新裁剪，丢弃
				 * 当前功能时，会报NullException，可以根据不同情况在合适的 地方做判断处理类似情况
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
						} // 保存在SD卡中
						mine_Photo.setImageBitmap(head);// 用ImageView显示出来
						// 上传本地照片到网络
						file = new File(fileName);

						System.out.println("上传图片内容" + file);
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

	/** 以文件的方式上传图片到后台 */
	private void upPhotoRequest(File file) {

		request = NoHttp.createJsonObjectRequest(XyMyContent.UPPHOTO_URL, RequestMethod.POST);
		Log.d("test","file："+file);
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
					System.out.println("上传头像" + response);
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
					System.out.println("获取个人信息" + biz_content);
					if (state.equals("success")) {
						UserInfoEntity userInfoEntity = new UserInfoEntity();
						userInfoEntity = JSON.parseObject(biz_content, UserInfoEntity.class);
						// 加载网络头像
						Glide.with(getApplicationContext()).load(userInfoEntity.getAvatar()).asBitmap()
								.error(R.drawable.touxiang).into(mine_Photo);
						mine_name.setText(userInfoEntity.getNickname());
						mine_qianming.setText(userInfoEntity.getSignname());
						SharedPreferencesUtils.putValue(getApplicationContext(), "USERID", userInfoEntity.getUserid());
						SharedPreferencesUtils.putValue(getApplicationContext(), "DGVIP", userInfoEntity.getDengji());
						System.out.println("分享id" + userInfoEntity.getUserid());
						// Toast.makeText(getApplicationContext(), "获取成功",
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
					System.out.println("剩余元宝数数据" + response);
					String state = response.get().getString("state");
					String biz_content = response.get().getString("biz_content");
					if (state.equals("success")) {

						UserInfoEntity entity = new UserInfoEntity();

						entity = JSON.parseObject(biz_content, UserInfoEntity.class);
						mine_yuanbao_num.setText(entity.getMoney() + "元宝");
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
	 * 裁剪图片的方法
	 * 
	 * @param data
	 */
	private void startPhotoZoom(Uri data) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		// 下面这个 crop=true 是设置在开启的intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", true);
		// asceptX asceptY 是宽高的比例
		intent.putExtra("asceptX", 1);
		intent.putExtra("asceptY", 1);
		// outputX outpoutY
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);

	}

	/**
	 * 保存剪切之后的图片
	 * 
	 * @param data
	 * @throws IOException
	 */
	private void setPicToView(Bitmap mBitmap) throws IOException {
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			return;
		}

		FileOutputStream b = null;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();// 创建文件夹
		}

		File mfile = new File(path, "head.jpg");
		if (!mfile.exists()) {
			mfile.createNewFile();// 创建文件夹
		}
		fileName = path + "head.jpg";
		// 图片名字
		try {
			b = new FileOutputStream(mfile);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, b);// 把数据写入文件

			b.flush();
			b.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
