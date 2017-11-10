package com.dhxgzs.goodluck.chatroom;

import com.dhxgzs.goodluck.R;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class ChatActivity extends EaseBaseActivity {

	public static ChatActivity activityInstance;
	// 聊天界面fragment
	private EaseChatFragment chatFragment;
	String toChatUsername;

	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
        setContentView(R.layout.activity_chat);
		
		activityInstance = this;
		//设置用户id或者群组，聊天室id
		toChatUsername = getIntent().getExtras().getString("userId");
		chatFragment = new EaseChatFragment();
		//设置参数
		chatFragment.setArguments(getIntent().getExtras());
		getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		activityInstance = null;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// enter to chat activity when click notification bar, here make sure
		// only one chat activiy
		String username = intent.getStringExtra("userId");
		if (toChatUsername.equals(username))
			super.onNewIntent(intent);
		else {
			finish();
			startActivity(intent);
		}

	}

	@Override
	public void onBackPressed() {
		chatFragment.onBackPressed();
	}

	public String getToChatUsername() {
		return toChatUsername;
	}
	
}
