package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.adapter.NoticeAdapter.ViewHolder;
import com.dhxgzs.goodluck.entiey.NoticeEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyNewsAdapter extends BaseAdapter{

	private Context context;
	private List<NoticeEntity> myNewsList;
	
	public MyNewsAdapter(Context context, List<NoticeEntity> myNewsList) {
		
		this.context=context;
		this.myNewsList=myNewsList;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myNewsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myNewsList.get(position);
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
			convertView = View.inflate(context, R.layout.item_mynews_listview, null);

			viewHolder.myNews_content = (TextView) convertView.findViewById(R.id.myNews_content);
			viewHolder.myNews_time = (TextView) convertView.findViewById(R.id.myNews_time);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		NoticeEntity entity = myNewsList.get(position);
		
		viewHolder.myNews_content.setText(entity.getTitle());
		viewHolder.myNews_time.setText(entity.getTime());

		return convertView;
	}
	
	static class ViewHolder {
		TextView myNews_content;
		TextView myNews_time;

	}
}
