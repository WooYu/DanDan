package com.dhxgzs.goodluck;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class KeFuActivity extends Activity {

	
	private WebView KefuWebview;
	private String uil="http://kf.pc3018.com";
	/***ht.5889527.com
	 * �޸� �ӿ�:  
	 * by JiuYue  pc28528
	 *  2017/08/21
	 */
	private ImageView kefu_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ke_fu);
		
		initview();
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
	private void initview() {
		KefuWebview = (WebView) findViewById(R.id.KefuWebview);
		kefu_back=(ImageView) findViewById(R.id.kefu_back);
		WebSettings settings = KefuWebview.getSettings();
//		NociteWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//����֧��javascript
		settings.setJavaScriptEnabled(true);
		KefuWebview.loadUrl(uil);
		kefu_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
				
			}
		});
		
	}

	
}
