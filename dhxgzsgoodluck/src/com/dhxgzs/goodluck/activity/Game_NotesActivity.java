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
 * ��Ϸ��¼ҳ��
 * 
 * @author Administrator
 *
 */
public class Game_NotesActivity extends Activity implements OnClickListener {

	private static final int GAMENOTE = 0;
	/** ���ذ�ť */
	private ImageView game_notes_back;
	/** ��Ϸ���� */
	private TextView game_type;
	/** ��ʼʱ�� */
	private TextView game_startime;
	/** ����ʱ�� */
	private TextView game_endtime;
	/** ȷ����ť */
	private TextView game_sure;
	/** �ꡢ�¡��� */
	private int year, month, day;
	/** ϵͳ���ڵ����� */
	private DatePickerDialog dialog;
	/** �Զ�����Ϸ����popwindow */
	private TitlePopup titlepop;
	/** �û�ѡ�����Ϸ���� */
	private String typeContent;
	/** �û�ѡ��Ŀ�ʼʱ�� */
	private String startTimeContent;
	/** �û�ѡ��Ľ���ʱ�� */
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

	private void initView() {
		gameNoteQueue = NoHttp.newRequestQueue();

		// �û��˺�
		account = SharedPreferencesUtils.getValue(getApplicationContext(), "phoneNum");
		// imei��
		imei = MyUtil.getIMEI(getApplicationContext());

		GameNoteRequest();
		// ���ذ�ť
		game_notes_back = (ImageView) findViewById(R.id.game_notes_back);
		// ��Ϸ����
		game_type = (TextView) findViewById(R.id.game_type);
		// ��ʼʱ��
		game_startime = (TextView) findViewById(R.id.game_startime);
		// ����ʱ��
		game_endtime = (TextView) findViewById(R.id.game_endtime);
		// ȷ����ť
		game_sure = (TextView) findViewById(R.id.game_sure);
		// ��ȡ��ǰ��������
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// ��ʼ����Ϸ�Ŀ�ʼʱ��ͽ���ʱ��
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
		// ���ذ�ť
		case R.id.game_notes_back:

			finish();

			break;
		case R.id.game_type:
			// ���õ������λ������Ϸ����TextView�·� �൱�ھุ�ؼ�maginbottom 20
			titlepop.showAsDropDown(game_type, 0, 20);
			// ����popwindow������
			titlepop.show(game_type);
			break;
		case R.id.game_startime:
			// ��ʼʱ�䵯���򷽷�
			showStartDatePickerDialog(year, month, day);
			break;
		case R.id.game_endtime:
			// ����ʱ�䵯���򷽷�
			showEndDatePickerDialog(year, month, day);
			break;

		case R.id.game_sure:
			// ��ȡ�û�ѡ�����Ϸ����
			typeContent = game_type.getText().toString();
			// ��ȡ�û�ѡ��Ŀ�ʼʱ��
			startTimeContent = game_startime.getText().toString();
			// ��ȡ�û�ѡ��Ľ���ʱ��
			endTimeContent = game_endtime.getText().toString();
			if(!typeContent.isEmpty()&&!startTimeContent.isEmpty()&&!endTimeContent.isEmpty()){
				
				Intent intent = new Intent(Game_NotesActivity.this, MyGame_notesActivity.class);
				intent.putExtra("GameType", typeContent).putExtra("StartTime", startTimeContent).putExtra("EndTime",
						endTimeContent);
				
				startActivity(intent);
			}else {
				Toast.makeText(getApplicationContext(), "��д��Ϣ����Ϊ��", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}

	}

	/** ��Ϸ����popwindow�������� */
	private void initpopwindow() {

		titlepop = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		titlepop.setItemOnClickListener(onitemClick);
		// �������������������

		titlepop.addAction(new ActionItem(getApplicationContext(), "ȫ��"));
		titlepop.addAction(new ActionItem(getApplicationContext(), "���ô�28"));
		titlepop.addAction(new ActionItem(getApplicationContext(), "����28"));

	}

	/** �����Ӱ�ť ���ֵ�����ֵļ��� */
	private OnItemOnClickListener onitemClick = new OnItemOnClickListener() {

		@Override
		public void onItemClick(ActionItem item, int position) {
			// mLoadingDialog.show();
			switch (position) {
			case 0:// ȫ��

				System.out.println("���λ��" + position);
				game_type.setText("ȫ��");
				break;
			case 1:// ���ô�28

				System.out.println("���λ��" + position);
				game_type.setText("���ô�28");
				break;
			case 2:// ����28

				System.out.println("���λ��" + position);
				game_type.setText("����28");
				break;

			}

		}
	};

	/**
	 * ����ʱ�䵯����
	 */
	private void showStartDatePickerDialog(int year2, int month2, int day2) {
		dialog = new DatePickerDialog(Game_NotesActivity.this, AlertDialog.THEME_HOLO_LIGHT, callBack, year2, month2,
				day2);
		dialog.show();

	}

	/**
	 * ����������¼� ���ѡ���ʱ��
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
	 * ����ѡ���Ŀ�ʼʱ��
	 * 
	 * @param year2
	 * @param month2
	 * @param day2
	 */
	protected void updateStartTime(int year2, int month2, int day2) {
		game_startime.setText(year2 + "-" + (month2 + 1) + "-" + day2);

	}

	/**
	 * ��������ʱ��Ի���
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
	 * ���ý���ʱ�� ѡ���ʱ�����
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
	 * ����ѡ��Ľ���ʱ�� ���µ�textview��
	 * 
	 * @param eyear2
	 * @param emonth2
	 * @param eday2
	 */
	protected void updateEndTime(int eyear2, int emonth2, int eday2) {

		game_endtime.setText(eyear2 + "-" + (emonth2 + 1) + "-" + eday2);

	}
}
