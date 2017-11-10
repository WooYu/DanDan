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
	        // 如果SDK的版本在4.4之上，那么应用沉浸式状态栏
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
	    	String account = account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
	        String url =XyMyContent.BANKPAY+"?account="+account ;  
	        webView = (WebView) findViewById(R.id.recharge_webview);  
	        //启用支持JavaScript  
	        webView.getSettings().setJavaScriptEnabled(true);  
	        //启用支持DOM Storage  
	        webView.getSettings().setDomStorageEnabled(true);  
	        //加载web资源  
	        webView.loadUrl(url);  
	        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开  
	        webView.setWebViewClient(new WebViewClient() {  
	            @Override  
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {  
	                view.loadUrl(url);  
	                return true;  
	            }  
	        });  
	    }  
	  
	    //改写物理按键的返回的逻辑  
	    @Override  
	    public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        // TODO Auto-generated method stub  
	        if (keyCode == KeyEvent.KEYCODE_BACK) {  
	            if (webView.canGoBack()) {  
	                webView.goBack();//返回上一页面  
	                return true;  
	            } else {  
	                finish();  
	            }  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    }  
	 
}
