package com.dhxgzs.goodluck.adapter;

import java.util.ArrayList;
import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.entiey.PeiLvEntity;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private List<PeiLvEntity> mList;
	private Context mContext;
	//状态标志位
	private int clickTemp = -1;
    //标识选择的Item
	public void setSeclection(int position) {
	clickTemp = position;
	}
	public MyAdapter(List<PeiLvEntity>  mList,Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = View.inflate(mContext, R.layout.pcdd_wanfa1_grid_item,null);
		TextView odds_tTextView = (TextView) view.findViewById(R.id.odds_tv);
		TextView item_tTextView = (TextView) view.findViewById(R.id.item_tv);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.ly_item);
		
		PeiLvEntity entity = mList.get(position);
		 odds_tTextView.setText(entity.getOdds());
		 item_tTextView.setText(entity.getItem());
		 
		 if(entity.getItem().indexOf("红")!=-1){
				item_tTextView.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.yuan_text_red_bg_shape);
			}else if(entity.getItem().indexOf("绿")!=-1) {
				item_tTextView.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.yuan_text_green_bg_shape);
			}else if(entity.getItem().indexOf("蓝")!=-1) {
				item_tTextView.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.yuan_text_blue_bg_shape);
			}else if(entity.getItem().indexOf("豹")!=-1) {
				item_tTextView.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.yuan_text_orange_bg_shape);
			} 
		//选中时，改变背景色
		if (clickTemp == position) {
			layout.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.vote_submit_bg_odds);
			} else {
				layout.setBackgroundColor(Color.TRANSPARENT);
			}
		return view;
	}
	

}
