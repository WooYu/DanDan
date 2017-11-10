package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.entiey.NoticeEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeAdapter extends BaseAdapter{

	
	private Context context;
	private List<NoticeEntity> noticeList;
	
	public NoticeAdapter(Context context, List<NoticeEntity> noticeList) {
		
		this.context=context;
		this.noticeList=noticeList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return noticeList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return noticeList.get(position);
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
			convertView = View.inflate(context, R.layout.item_notice_listview, null);

			viewHolder.notice_content = (TextView) convertView.findViewById(R.id.notice_content);
			viewHolder.notice_time = (TextView) convertView.findViewById(R.id.notice_time);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		NoticeEntity entity = noticeList.get(position);
		
		viewHolder.notice_content.setText(entity.getTitle());
		viewHolder.notice_time.setText(entity.getTime());

		return convertView;
	}
	
	static class ViewHolder {
		TextView notice_content;
		TextView notice_time;

	}

}
