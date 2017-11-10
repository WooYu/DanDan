package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.adapter.HuiShuiAdapter.ViewHolder;
import com.dhxgzs.goodluck.entiey.HuiShuiEntity;
import com.dhxgzs.goodluck.entiey.TiXianNotesEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TiXianNotesAdapter extends BaseAdapter{
	
	private Context context;
	private List<TiXianNotesEntity> tiXianList;

	public TiXianNotesAdapter(Context context, List<TiXianNotesEntity> tiXianList) {
		
		this.context=context;
		this.tiXianList=tiXianList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tiXianList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tiXianList.get(position);
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
			convertView = View.inflate(context, R.layout.item_tixian_notes_listview, null);

			viewHolder.TiXianNotes_time = (TextView) convertView.findViewById(R.id.TiXianNotes_time);
			viewHolder.TiXianNotes_money = (TextView) convertView.findViewById(R.id.TiXianNotes_money);
			viewHolder.TiXianNotes_state = (TextView) convertView.findViewById(R.id.TiXianNotes_state);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		TiXianNotesEntity entity = tiXianList.get(position);
		
		viewHolder.TiXianNotes_time.setText(entity.getMoney());
		viewHolder.TiXianNotes_money.setText(entity.getAddtime());
		viewHolder.TiXianNotes_state.setText(entity.getState());

		return convertView;
	}
	static class ViewHolder {
		TextView TiXianNotes_time;
		TextView TiXianNotes_money;
		TextView TiXianNotes_state;

	}
}
