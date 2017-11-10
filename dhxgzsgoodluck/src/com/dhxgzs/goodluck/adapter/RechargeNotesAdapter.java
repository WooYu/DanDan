package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.entiey.RechargeNotesEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RechargeNotesAdapter extends BaseAdapter{
	private Context context;
	private List<RechargeNotesEntity> rnList;

	public RechargeNotesAdapter(Context context, List<RechargeNotesEntity> rnList) {
		super();
		this.context = context;
		this.rnList = rnList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rnList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rnList.get(position);
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
			convertView = View.inflate(context, R.layout.item_chongzhi_notes_listview, null);

			viewHolder.Recharge_notes_time = (TextView) convertView.findViewById(R.id.Recharge_notes_time);
			viewHolder.Recharge_notes_money = (TextView) convertView.findViewById(R.id.Recharge_notes_money);
			viewHolder.Recharge_notes_state = (TextView) convertView.findViewById(R.id.Recharge_notes_state);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		RechargeNotesEntity entity = rnList.get(position);
		
		viewHolder.Recharge_notes_money.setText(entity.getRealmoney());
		viewHolder.Recharge_notes_time.setText(entity.getAddtime());
		viewHolder.Recharge_notes_state.setText(entity.getState());
		System.out.println("1"+entity.getAddtime());
		System.out.println("2"+entity.getRealmoney());
		System.out.println("3"+entity.getState());

		return convertView;
	}

	static class ViewHolder {
		TextView Recharge_notes_time;
		TextView Recharge_notes_money;
		TextView Recharge_notes_state;

	}


}
