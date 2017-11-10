package com.dhxgzs.goodluck.adapter;

import java.util.List;

import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.entiey.HuiShuiEntity;
import com.yolanda.nohttp.rest.OnResponseListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HuiShuiAdapter extends BaseAdapter {

	private Context context;
	private List<HuiShuiEntity> huiShuiList;

	public HuiShuiAdapter(Context context, List<HuiShuiEntity> huiShuiList) {

		this.context = context;
		this.huiShuiList = huiShuiList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return huiShuiList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return huiShuiList.get(position);
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
			convertView = View.inflate(context, R.layout.item_huishui_listview, null);

			viewHolder.huishui_time = (TextView) convertView.findViewById(R.id.huishui_time);
			viewHolder.huishi_money = (TextView) convertView.findViewById(R.id.huishi_money);
			viewHolder.huishui_state = (TextView) convertView.findViewById(R.id.huishui_state);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		HuiShuiEntity entity = huiShuiList.get(position);
		
		viewHolder.huishui_time.setText(entity.getTime());
		viewHolder.huishi_money.setText(entity.getMoney());
		viewHolder.huishui_state.setText(entity.getStatus());

		return convertView;
	}

	static class ViewHolder {
		TextView huishui_time;
		TextView huishi_money;
		TextView huishui_state;

	}

}
