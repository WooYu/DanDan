package com.dhxgzs.goodluck.activity;

import java.io.File;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
 * 分享二维码页面
 * 
 * @author Administrator
 *
 */
public class Share_ErWeiMaActivity extends Activity implements OnClickListener {

	/**返回按钮*/
	private ImageView share_back;
	/** 二维码图片 */
	private ImageView ErWeima_img;
	/** 分享下载按钮 */
	private ImageView share_download;
	/** 马上分享按钮 */
	private TextView share_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share__er_wei_ma);

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

		share_back = (ImageView) findViewById(R.id.share_back);
		ErWeima_img = (ImageView) findViewById(R.id.ErWeima_img);
		share_download = (ImageView) findViewById(R.id.share_download);
		share_btn = (TextView) findViewById(R.id.share_btn);

	}

	private void setListener() {

		share_back.setOnClickListener(this);
		share_download.setOnClickListener(this);
		share_btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.share_back:
			
			finish();
			
			break;
		case R.id.share_download:
			Toast.makeText(getApplicationContext(), "下载完成", Toast.LENGTH_SHORT).show();
			
			break;
		case R.id.share_btn:

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

//		TextView takephoto = (TextView) window.findViewById(R.id.tv_content1);
//		takephoto.setText("拍照");
//		takephoto.setOnClickListener(new View.OnClickListener() {
//
//			@SuppressLint("SdCardPath")
//			@Override
//			public void onClick(View v) {
//				
//				dialog.cancel();
//			}
//		});
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
