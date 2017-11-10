package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.entiey.WodeshouyiEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WodeshouyiAdapter extends BaseAdapter{

	private Context context;
	private List<WodeshouyiEntity> wodeshouyiList;

	public WodeshouyiAdapter(Context context, List<WodeshouyiEntity> wodeshouyiList) {

		this.context = context;
		this.wodeshouyiList = wodeshouyiList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return wodeshouyiList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return wodeshouyiList.get(position);
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
			convertView = View.inflate(context, R.layout.item_shouyi_listview, null);

			viewHolder.wodeshouyi_name = (TextView) convertView.findViewById(R.id.wodeshouyi_name);
			viewHolder.wodeshouyi_state = (TextView) convertView.findViewById(R.id.wodeshouyi_state);
			viewHolder.wodeshouyi_money = (TextView) convertView.findViewById(R.id.wodeshouyi_money);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		WodeshouyiEntity entity = wodeshouyiList.get(position);
		
		viewHolder.wodeshouyi_name.setText(entity.getNickname());
		if(entity.getStatus().equals("0")){
			
			
			viewHolder.wodeshouyi_state.setText("²»Âú×ã");
		}else{
			viewHolder.wodeshouyi_state.setText("Âú×ã");
		}
		viewHolder.wodeshouyi_money.setText(entity.getMoney());

		return convertView;
	}

	static class ViewHolder {
		TextView wodeshouyi_name;
		TextView wodeshouyi_state;
		TextView wodeshouyi_money;

	}

}
