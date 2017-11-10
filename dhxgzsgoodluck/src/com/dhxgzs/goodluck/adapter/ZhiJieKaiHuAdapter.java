package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.entiey.ZhiJieKaiHuEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZhiJieKaiHuAdapter extends BaseAdapter{
	
	
	private Context context;
	private List<ZhiJieKaiHuEntity> zhijiekaihuList;
	
	

	public ZhiJieKaiHuAdapter(Context context, List<ZhiJieKaiHuEntity> zhijiekaihuList) {
		
		this.context=context;
		this.zhijiekaihuList=zhijiekaihuList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return zhijiekaihuList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return zhijiekaihuList.get(position);
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
			convertView = View.inflate(context, R.layout.item_listview_share, null);

			viewHolder.daili_jibei = (TextView) convertView.findViewById(R.id.daili_jibei);
			viewHolder.xdaili_touzhue = (TextView) convertView.findViewById(R.id.xdaili_touzhue);
			viewHolder.ddaili_touzhue = (TextView) convertView.findViewById(R.id.ddaili_touzhue);
			viewHolder.daili_yongjin = (TextView) convertView.findViewById(R.id.daili_yongjin);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ZhiJieKaiHuEntity entity = zhijiekaihuList.get(position);
		
		viewHolder.daili_jibei.setText(entity.getName());
		viewHolder.xdaili_touzhue.setText(entity.getMinfee());
		viewHolder.ddaili_touzhue.setText(entity.getMaxfee());
		viewHolder.daili_yongjin.setText(entity.getBackfee());

		return convertView;
	}

	static class ViewHolder {
		TextView daili_jibei;
		TextView xdaili_touzhue;
		TextView ddaili_touzhue;
		TextView daili_yongjin;

	}
}
