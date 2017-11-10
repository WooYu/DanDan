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
import android.widget.TextView;

import com.dhxgzs.goodluck.R;  
import com.dhxgzs.goodluck.entiey.PeiLvEntity;
import com.dhxgzs.goodluck.entiey.PeiLvExplainEntity;
import com.dhxgzs.goodluck.view.TitlePopup.OnItemOnClickListener;

public class PeiLvSMListViewAdapter extends BaseAdapter{

	private Context context;
	private List<PeiLvExplainEntity> mPeiLvExList;
	private int[] image; 


	public PeiLvSMListViewAdapter(Context context, List<PeiLvExplainEntity> mPeiLvExList) {

		this.context=context;
		this.mPeiLvExList=mPeiLvExList;

	}
	 

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mPeiLvExList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mPeiLvExList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder; 
		Log.d("test","PLpositon:"+position);
		if (convertView == null) {

			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_listview_peilvsm, null);

			viewHolder.peilv_play1 = (TextView) convertView.findViewById(R.id.peilv_play1);
			viewHolder.oddsData1 = (TextView) convertView.findViewById(R.id.oddsData1);

			convertView.setTag(viewHolder);

		}else {
			viewHolder = (ViewHolder) convertView.getTag();

		}


		PeiLvExplainEntity entity = mPeiLvExList.get(position);

		viewHolder.peilv_play1.setText(entity.getItem());
		viewHolder.oddsData1.setText(entity.getOdds());
 
		return convertView;
	}

	static class ViewHolder {
		TextView peilv_play1;
		TextView oddsData1;

	}

 
 
}


