package com.dhxgzs.goodluck.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dhxgzs.goodluck.adapter.PeiLvSMListViewAdapter;
import com.dhxgzs.goodluck.entiey.PeiLvExplainEntity; 

/**
 * 
 * @author JiuYue
 * 
 *  2017-9-19上午11:07:32
 *
 *  com/dhxgzs/goodluck/activity/BJpk10Activity.java
 *  com/dhxgzs/goodluck/activity/CQsscActivity.java
 *  com/dhxgzs/goodluck/activity/GDKLSFActivity.java
 *  com/dhxgzs/goodluck/chatroom/EaseChatFragment.java
 */

public class PeiLvSMDialog {
	/**赔率说明 修改为listview*/
	private ListView listview_peilvsm;
	private PeiLvSMListViewAdapter plsmAdapter;
	private static PeiLvSMDialog _plsmDialog = null;

	public PeiLvSMDialog(Context context) {
		// TODO Auto-generated constructor stub
	}
	public static PeiLvSMDialog getInstance(Context context){
		if (_plsmDialog == null) {
			synchronized (PeiLvSMDialog.class) {
				if (_plsmDialog == null) {
					_plsmDialog = new PeiLvSMDialog(context);
				}
			}
		}
		return _plsmDialog;

	}
	@SuppressLint("InflateParams")
	public void zanDlerlog(Activity context,List<PeiLvExplainEntity> mPeiLvExList) {
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(com.dhxgzs.goodluck.R.layout.alertdialog_shouye,
				null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(com.dhxgzs.goodluck.R.layout.alertdialog_shouye);

		listview_peilvsm = (ListView) window.findViewById(com.dhxgzs.goodluck.R.id.peilvsm_Listview);

		plsmAdapter = new PeiLvSMListViewAdapter(context, mPeiLvExList); 
		listview_peilvsm.setAdapter(plsmAdapter);	

		TextView odd_close = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.odd_close);


		odd_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();

			}
		});

	}

	/*	@SuppressLint("InflateParams")
	protected void zanDlerlog() {
		final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(com.dhxgzs.goodluck.R.layout.alertdialog_shouye,
				null);
		dialog.setView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(com.dhxgzs.goodluck.R.layout.alertdialog_shouye);

		listview_peilvsm = (ListView) window.findViewById(com.dhxgzs.goodluck.R.id.peilvsm_Listview);

		plsmAdapter = new PeiLvSMListViewAdapter(getActivity(), peiLvExplainList); 
		listview_peilvsm.setAdapter(plsmAdapter);	

		TextView odd_close = (TextView) window.findViewById(com.dhxgzs.goodluck.R.id.odd_close);


		odd_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();

			}
		});

	}*/
}
