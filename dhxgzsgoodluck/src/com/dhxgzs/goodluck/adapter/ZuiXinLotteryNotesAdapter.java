package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.adapter.ZhangBianAdapter.ViewHolder;
import com.dhxgzs.goodluck.entiey.ZhangBianEntity;
import com.dhxgzs.goodluck.entiey.ZuiXinLotteryNotesEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZuiXinLotteryNotesAdapter extends BaseAdapter {

	private Context context;
	private List<ZuiXinLotteryNotesEntity> zxlnList;

	public ZuiXinLotteryNotesAdapter(Context context, List<ZuiXinLotteryNotesEntity> zxlnList) {

		this.context = context;
		this.zxlnList = zxlnList;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return zxlnList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return zxlnList.get(position);
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
			convertView = View.inflate(context, R.layout.item_popupwindow_zuixin_lotterynotes_listview, null);

			viewHolder.zuixin_expect = (TextView) convertView.findViewById(R.id.zuixin_expect);
			viewHolder.zuixin_result = (TextView) convertView.findViewById(R.id.zuixin_result);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ZuiXinLotteryNotesEntity entity = zxlnList.get(position);
		
		viewHolder.zuixin_expect.setText(entity.getExpect());
		viewHolder.zuixin_result.setText(entity.getResult());

		return convertView;
	}

	static class ViewHolder {
		TextView zuixin_expect;
		TextView zuixin_result;

	}
}
