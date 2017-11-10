package com.dhxgzs.goodluck;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.entiey.AboutEntity;
import com.dhxgzs.goodluck.util.MyUtil; 
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
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 关于页面
 * 
 * @author JiuYue
 * 是
 */
public class AboutActivity extends Activity implements OnClickListener {

	private static final int ABOUT = 0;
	private ImageView about_back;
	private TextView about_verson;
	private TextView kefu_QQ;
	private TextView kefu_weixin;
	private ImageView fuzhi_qq;
	private ImageView fuzhi_weixin;
	private ImageView about_ErWeima_img;
	private ImageView about_download;
 
	private Request<JSONObject> request;
	private RequestQueue aboutQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

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

	private void initView() {

		aboutQueue = NoHttp.newRequestQueue();

		aboutRequest();
		about_back = (ImageView) findViewById(R.id.about_back);
		about_verson = (TextView) findViewById(R.id.about_verson);
		kefu_QQ = (TextView) findViewById(R.id.kefu_QQ);
		kefu_weixin = (TextView) findViewById(R.id.kefu_weixin);
		fuzhi_qq = (ImageView) findViewById(R.id.fuzhi_qq);
		fuzhi_weixin = (ImageView) findViewById(R.id.fuzhi_weixin);
		about_ErWeima_img = (ImageView) findViewById(R.id.about_ErWeima_img);
		about_download = (ImageView) findViewById(R.id.about_download);

	}

	private void aboutRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.ABOUT_URL, RequestMethod.POST);

		aboutQueue.add(ABOUT, request, responseListener);

	}

	OnResponseListener<JSONObject> responseListener = new OnResponseListener<JSONObject>() {

		@Override
		public void onSucceed(int what, Response<JSONObject> response) {

			System.out.println("关于页面数据" + response);
			try {
				String state = response.get().getString("state");
				String biz_content = response.get().getString("biz_content");

				AboutEntity aboutEntity = new AboutEntity();

				aboutEntity = JSON.parseObject(biz_content, AboutEntity.class);

				about_verson.setText(aboutEntity.getVersion());

				kefu_QQ.setText("121224");
				kefu_weixin.setText("12312");
				Glide.with(getApplicationContext()).load(aboutEntity.getImg()).asBitmap().error(R.drawable.ic_launcher)
						.into(about_ErWeima_img);

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

	private void setListener() {

		about_back.setOnClickListener(this);
		fuzhi_qq.setOnClickListener(this);
		fuzhi_weixin.setOnClickListener(this);
		about_download.setOnClickListener(this);

	}

	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.about_back:

			finish();

			break;
		case R.id.fuzhi_qq:// 复制客服QQ号

			if (MyUtil.getSDKVersionNumber() >= 11) {
				android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(kefu_QQ.getText().toString());
			} else {
				// 得到剪贴板管理器
				android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setPrimaryClip(ClipData.newPlainText(null, kefu_QQ.getText().toString()));  
			}
			Toast.makeText(this, "复制到剪切板", Toast.LENGTH_SHORT).show();
 
			break;
		case R.id.fuzhi_weixin:// 复制客服微信号

			if (MyUtil.getSDKVersionNumber() >= 11) {
				android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setText(kefu_weixin.getText().toString());
			} else {
				// 得到剪贴板管理器
				android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) this
						.getSystemService(Context.CLIPBOARD_SERVICE);
				clipboardManager.setPrimaryClip(ClipData.newPlainText(null, kefu_weixin.getText().toString()));
			}
			Toast.makeText(this, "复制到剪切板", Toast.LENGTH_SHORT).show();

			break;
		case R.id.about_download:

			shareDialog();

			break;

		default:
			break;
		}

	}

	private void shareDialog() {
		final AlertDialog dialog = new AlertDialog.Builder(this).create();
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.share_alertdialog);
		window.setGravity(Gravity.BOTTOM);

		// TextView takephoto = (TextView)
		// window.findViewById(R.id.tv_content1);
		// takephoto.setText("拍照");
		// takephoto.setOnClickListener(new View.OnClickListener() {
		//
		// @SuppressLint("SdCardPath")
		// @Override
		// public void onClick(View v) {
		//
		// dialog.cancel();
		// }
		// });
		TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
		tv_xiangce.setText("保存二维码");
		tv_xiangce.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();

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

}
