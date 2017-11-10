package com.dhxgzs.goodluck.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhxgzs.goodluck.R;  
import com.dhxgzs.goodluck.entiey.PeiLvEntity;
import com.dhxgzs.goodluck.view.TitlePopup.OnItemOnClickListener;

public class WanFaGridViewAdapter extends BaseAdapter{

	private Context context;
	private List<PeiLvEntity> myPerLvList;
	private int[] image; 
	 private int selectorPosition;


	public WanFaGridViewAdapter(Context context, List<PeiLvEntity> myPerLvList) {

		this.context=context;
		this.myPerLvList=myPerLvList;

	}
	 

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myPerLvList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myPerLvList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder; 
		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.pcdd_wanfa1_grid_item, null);
			
		/*	LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)convertView.getLayoutParams();
			layoutParams.setMargins(10,10,15,15);//4个参数按顺序分别是左上右下
			convertView.setLayoutParams(layoutParams); //mView是控件
			convertView.set*/

			viewHolder.odds_tTextView = (TextView) convertView.findViewById(R.id.odds_tv);
			viewHolder.item_tTextView = (TextView) convertView.findViewById(R.id.item_tv);

			convertView.setTag(viewHolder);

		}else {
			viewHolder = (ViewHolder) convertView.getTag();

		}


		PeiLvEntity entity = myPerLvList.get(position);

		viewHolder.odds_tTextView.setText(entity.getOdds());
		viewHolder.item_tTextView.setText(entity.getItem());
		
		if(entity.getItem().indexOf("红")!=-1){
			viewHolder.item_tTextView.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.yuan_text_red_bg_shape);
		}else if(entity.getItem().indexOf("绿")!=-1) {
			viewHolder.item_tTextView.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.yuan_text_green_bg_shape);
		}else if(entity.getItem().indexOf("蓝")!=-1) {
			viewHolder.item_tTextView.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.yuan_text_blue_bg_shape);
		}else if(entity.getItem().indexOf("豹")!=-1) {
			viewHolder.item_tTextView.setBackgroundResource(com.dhxgzs.goodluck.R.drawable.yuan_text_orange_bg_shape);
		} 
 

		return convertView;
	}

	static class ViewHolder {
		TextView odds_tTextView;
		TextView item_tTextView;

	}

	public void chiceState(int position) {
		// TODO Auto-generated method stub

	}

 
}


