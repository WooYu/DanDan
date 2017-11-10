package com.dhxgzs.goodluck.activity;

import java.util.Calendar;

import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.R.id;
import com.dhxgzs.goodluck.R.layout;
import com.dhxgzs.goodluck.R.menu;
import com.dhxgzs.goodluck.app.XyMyContent;
import com.dhxgzs.goodluck.util.MyUtil;
import com.dhxgzs.goodluck.util.SharedPreferencesUtils;
import com.dhxgzs.goodluck.view.ActionItem;
import com.dhxgzs.goodluck.view.TitlePopup;
import com.dhxgzs.goodluck.view.TitlePopup.OnItemOnClickListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 游戏记录页面
 * 
 * @author Administrator
 *
 */
public class Game_NotesActivity extends Activity implements OnClickListener {

	private static final int GAMENOTE = 0;
	/** 返回按钮 */
	private ImageView game_notes_back;
	/** 游戏类型 */
	private TextView game_type;
	/** 开始时间 */
	private TextView game_startime;
	/** 结束时间 */
	private TextView game_endtime;
	/** 确定按钮 */
	private TextView game_sure;
	/** 年、月、日 */
	private int year, month, day;
	/** 系统日期弹出框 */
	private DatePickerDialog dialog;
	/** 自定义游戏类型popwindow */
	private TitlePopup titlepop;
	/** 用户选择的游戏类型 */
	private String typeContent;
	/** 用户选择的开始时间 */
	private String startTimeContent;
	/** 用户选择的结束时间 */
	private String endTimeContent;

	private Request<JSONObject> request;
	private RequestQueue gameNoteQueue;
	private String account;
	private String imei;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game__notes);
		initpopwindow();
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
		gameNoteQueue = NoHttp.newRequestQueue();

		// 用户账号
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei号
		imei = MyUtil.getIMEI(getApplicationContext());

		GameNoteRequest();
		// 返回按钮
		game_notes_back = (ImageView) findViewById(R.id.game_notes_back);
		// 游戏类型
		game_type = (TextView) findViewById(R.id.game_type);
		// 开始时间
		game_startime = (TextView) findViewById(R.id.game_startime);
		// 结束时间
		game_endtime = (TextView) findViewById(R.id.game_endtime);
		// 确定按钮
		game_sure = (TextView) findViewById(R.id.game_sure);
		// 获取当前的年月日
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// 初始化游戏的开始时间和结束时间
		game_startime.setText(year + "-" + (month + 1) + "-" + day);
		game_endtime.setText(year + "-" + (month + 1) + "-" + day);

	}

	private void GameNoteRequest() {

		request = NoHttp.createJsonObjectRequest(XyMyContent.GAMENOTE_URL, RequestMethod.POST);

		// request.add("account", account);
		// request.add("type", value);
		// request.add("", value);
		// request.add("", value);
		// request.add("", value);
		// request.add("", value);

		// gameNoteQueue.add(GAMENOTE, request, responseListener);

	}

	private void setListener() {
		game_notes_back.setOnClickListener(this);
		game_type.setOnClickListener(this);
		game_startime.setOnClickListener(this);
		game_endtime.setOnClickListener(this);
		game_sure.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 返回按钮
		case R.id.game_notes_back:

			finish();

			break;
		case R.id.game_type:
			// 设置弹出框的位置在游戏类型TextView下方 相当于距父控件maginbottom 20
			titlepop.showAsDropDown(game_type, 0, 20);
			// 弹出popwindow弹出框
			titlepop.show(game_type);
			break;
		case R.id.game_startime:
			// 开始时间弹出框方法
			showStartDatePickerDialog(year, month, day);
			break;
		case R.id.game_endtime:
			// 结束时间弹出框方法
			showEndDatePickerDialog(year, month, day);
			break;

		case R.id.game_sure:
			// 获取用户选择的游戏类型
			typeContent = game_type.getText().toString();
			// 获取用户选择的开始时间
			startTimeContent = game_startime.getText().toString();
			// 获取用户选择的结束时间
			endTimeContent = game_endtime.getText().toString();
			if(!typeContent.isEmpty()&&!startTimeContent.isEmpty()&&!endTimeContent.isEmpty()){
				
				Intent intent = new Intent(Game_NotesActivity.this, MyGame_notesActivity.class);
				intent.putExtra("GameType", typeContent).putExtra("StartTime", startTimeContent).putExtra("EndTime",
						endTimeContent);
				
				startActivity(intent);
			}else {
				Toast.makeText(getApplicationContext(), "填写信息不能为空", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}

	}

	/** 游戏类型popwindow弹窗方法 */
	private void initpopwindow() {

		titlepop = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titlepop.setItemOnClickListener(onitemClick);
		// 给标题栏弹窗添加子类

		titlepop.addAction(new ActionItem(getApplicationContext(), "全部"));
		titlepop.addAction(new ActionItem(getApplicationContext(), "加拿大28"));
		titlepop.addAction(new ActionItem(getApplicationContext(), "北京28"));

	}

	/** 点击添加按钮 后充值和提现的监听 */
	private OnItemOnClickListener onitemClick = new OnItemOnClickListener() {

		@Override
		public void onItemClick(ActionItem item, int position) {
			// mLoadingDialog.show();
			switch (position) {
			case 0:// 全部

				System.out.println("点击位置" + position);
				game_type.setText("全部");
				break;
			case 1:// 加拿大28

				System.out.println("点击位置" + position);
				game_type.setText("加拿大28");
				break;
			case 2:// 北京28

				System.out.println("点击位置" + position);
				game_type.setText("北京28");
				break;

			}

		}
	};

	/**
	 * 创建时间弹出框
	 */
	private void showStartDatePickerDialog(int year2, int month2, int day2) {
		dialog = new DatePickerDialog(Game_NotesActivity.this, AlertDialog.THEME_HOLO_LIGHT, callBack, year2, month2,
				day2);
		dialog.show();

	}

	/**
	 * 弹出框监听事件 获得选择的时间
	 */
	DatePickerDialog.OnDateSetListener callBack = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int syear, int smonth, int sday) {
			year = syear;
			month = smonth;
			day = sday;
			updateStartTime(year, month, day);
		}
	};

	/**
	 * 设置选择后的开始时间
	 * 
	 * @param year2
	 * @param month2
	 * @param day2
	 */
	protected void updateStartTime(int year2, int month2, int day2) {
		game_startime.setText(year2 + "-" + (month2 + 1) + "-" + day2);

	}

	/**
	 * 创建结束时间对话框
	 * 
	 * @param year2
	 * @param month2
	 * @param day2
	 */
	private void showEndDatePickerDialog(int year2, int month2, int day2) {
		dialog = new DatePickerDialog(Game_NotesActivity.this, AlertDialog.THEME_HOLO_LIGHT, endTimecallback, year2,
				month2, day2);
		dialog.show();
	}

	/**
	 * 设置结束时间 选择的时间监听
	 */
	DatePickerDialog.OnDateSetListener endTimecallback = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int eyear, int emonth, int eday) {

			year = eyear;
			month = emonth;
			day = eday;

			updateEndTime(year, month, day);

		}
	};

	/**
	 * 设置选择的结束时间 更新到textview上
	 * 
	 * @param eyear2
	 * @param emonth2
	 * @param eday2
	 */
	protected void updateEndTime(int eyear2, int emonth2, int eday2) {

		game_endtime.setText(eyear2 + "-" + (emonth2 + 1) + "-" + eday2);

	}
}
