package com.dhxgzs.goodluck.activity;
 
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * WebViewUpload
 *
 * @Author KenChung
 */
public class RechargeWebActivity extends Activity   {

	 private WebView webView;  
	  
	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.activity_recharge_web);  
	  
	        initView();  
	        // ���SDK�İ汾��4.4֮�ϣ���ôӦ�ó���ʽ״̬��
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	            setTranslucentStatus(true);
	            SystemBarTintManager tintManager = new SystemBarTintManager(this);
	            tintManager.setStatusBarTintEnabled(true);
	            tintManager.setNavigationBarTintEnabled(true);
	            tintManager.setStatusBarTintColor(getResources().getColor(R.color.statelan_bg));
	            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//	            rlFramePrimaryMain.setPadding(0, config.getPixelInsetTop(false), 0, config.getPixelInsetBottom());
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
	    private void initView() {  
	    	String account = account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
	        String url =XyMyContent.BANKPAY+"?account="+account ;  
	        webView = (WebView) findViewById(R.id.recharge_webview);  
	        //����֧��JavaScript  
	        webView.getSettings().setJavaScriptEnabled(true);  
	        //����֧��DOM Storage  
	        webView.getSettings().setDomStorageEnabled(true);  
	        //����web��Դ  
	        webView.loadUrl(url);  
	        //����WebViewĬ��ʹ�õ�������ϵͳĬ�����������ҳ����Ϊ��ʹ��ҳ��WebView��  
	        webView.setWebViewClient(new WebViewClient() {  
	            @Override  
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
	                view.loadUrl(url);  
	                return true;  
	            }  
	        });  
	    }  
	  
	    //��д�������ķ��ص��߼�  
	    @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        // TODO Auto-generated method stub  
	        if (keyCode == KeyEvent.KEYCODE_BACK) {  
	            if (webView.canGoBack()) {  
	                webView.goBack();//������һҳ��  
	                return true;  
	            } else {  
	                finish();  
	            }  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    }  
	 
}
