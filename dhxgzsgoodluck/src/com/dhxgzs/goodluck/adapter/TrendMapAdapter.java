package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.adapter.ZhangBianAdapter.ViewHolder;
import com.dhxgzs.goodluck.entiey.TrendMapEntity;
import com.dhxgzs.goodluck.entiey.ZhangBianEntity;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TrendMapAdapter extends BaseAdapter {

	private Context context;
	private List<TrendMapEntity> tmList;

	public TrendMapAdapter(Context context, List<TrendMapEntity> tmList) {

		this.context = context;
		this.tmList = tmList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tmList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tmList.get(position);
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
			convertView = View.inflate(context, R.layout.item_trendmap_listview, null);

			viewHolder.trendMap_qihao = (TextView) convertView.findViewById(R.id.trendMap_qihao);
			viewHolder.trendMap_lottery_result = (TextView) convertView.findViewById(R.id.trendMap_lottery_result);
			viewHolder.trendMap_big = (TextView) convertView.findViewById(R.id.trendMap_big);
			viewHolder.trendMap_small = (TextView) convertView.findViewById(R.id.trendMap_small);
			viewHolder.trendMap_single = (TextView) convertView.findViewById(R.id.trendMap_single);
			viewHolder.trendMap_both = (TextView) convertView.findViewById(R.id.trendMap_both);
			viewHolder.trendMap_big_single = (TextView) convertView.findViewById(R.id.trendMap_big_single);
			viewHolder.trendMap_small_single = (TextView) convertView.findViewById(R.id.trendMap_small_single);
			viewHolder.trendMap_big_both = (TextView) convertView.findViewById(R.id.trendMap_big_both);
			viewHolder.trendMap_small_both = (TextView) convertView.findViewById(R.id.trendMap_small_both);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		TrendMapEntity entity = tmList.get(position);

		viewHolder.trendMap_qihao.setText(entity.getExpect());
		viewHolder.trendMap_lottery_result.setText(entity.getResultnum());
		//大
		if (entity.getBig().isEmpty()) {
			viewHolder.trendMap_big.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_big.setText(entity.getBig());
			viewHolder.trendMap_big.setVisibility(View.VISIBLE);
		}
		//小
		if (entity.getSmall().isEmpty()) {
			viewHolder.trendMap_small.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_small.setText(entity.getSmall());
			viewHolder.trendMap_small.setVisibility(View.VISIBLE);
		}
		//单
		if (entity.getSingle().isEmpty()) {
			viewHolder.trendMap_single.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_single.setText(entity.getSingle());
			viewHolder.trendMap_single.setVisibility(View.VISIBLE);
		}
		//双
		if (entity.getDble().isEmpty()) {
			viewHolder.trendMap_both.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_both.setText(entity.getDble());
			viewHolder.trendMap_both.setVisibility(View.VISIBLE);
		}
		//大单
		if (entity.getLsingle().isEmpty()) {
			viewHolder.trendMap_big_single.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_big_single.setText(entity.getLsingle());
			viewHolder.trendMap_big_single.setVisibility(View.VISIBLE);
		}
		//小单
		if (entity.getSsingle().isEmpty()) {
			viewHolder.trendMap_small_single.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_small_single.setText(entity.getSsingle());
			viewHolder.trendMap_small_single.setVisibility(View.VISIBLE);
		}
		//大双
		if (entity.getLdble().isEmpty()) {
			viewHolder.trendMap_big_both.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_big_both.setText(entity.getLdble());
			viewHolder.trendMap_big_both.setVisibility(View.VISIBLE);
		}
		//小双
		if (entity.getSdble().isEmpty()) {
			viewHolder.trendMap_small_both.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.trendMap_small_both.setText(entity.getSdble());
			viewHolder.trendMap_small_both.setVisibility(View.VISIBLE);
		}

		// System.out.println("大" + entity.getBig());
		return convertView;
	}

	static class ViewHolder {

		TextView trendMap_qihao;
		TextView trendMap_lottery_result;
		TextView trendMap_big;
		TextView trendMap_small;
		TextView trendMap_single;
		TextView trendMap_both;
		TextView trendMap_big_single;
		TextView trendMap_small_single;
		TextView trendMap_big_both;
		TextView trendMap_small_both;

	}

}
