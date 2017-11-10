package com.dhxgzs.goodluck.adapter;

import java.util.List;

import org.json.JSONObject;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.adapter.HuiShuiAdapter.ViewHolder;
import com.dhxgzs.goodluck.entiey.HuiShuiEntity;
import com.dhxgzs.goodluck.entiey.ZhangBianEntity;
import com.yolanda.nohttp.rest.OnResponseListener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ZhangBianAdapter extends BaseAdapter{

	private Context context;
	private List<ZhangBianEntity> zhangbianList;
	
	public ZhangBianAdapter(Context context, List<ZhangBianEntity> zhangbianList) {
		
		this.context = context;
		this.zhangbianList=zhangbianList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return zhangbianList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return zhangbianList.get(position);
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
			convertView = View.inflate(context, R.layout.item_zhangbian_notes_listview, null);

			viewHolder.zhangbian_time = (TextView) convertView.findViewById(R.id.zhangbian_time);
			viewHolder.zhangbian_money = (TextView) convertView.findViewById(R.id.zhangbian_money);
			viewHolder.zhangbian_state = (TextView) convertView.findViewById(R.id.zhangbian_state);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ZhangBianEntity entity = zhangbianList.get(position);
		
		viewHolder.zhangbian_time.setText(entity.getTime());
		viewHolder.zhangbian_money.setText(entity.getMoney());
		viewHolder.zhangbian_state.setText(entity.getRemark());

		return convertView;
	}
	static class ViewHolder {
		TextView zhangbian_time;
		TextView zhangbian_money;
		TextView zhangbian_state;

	}
}
