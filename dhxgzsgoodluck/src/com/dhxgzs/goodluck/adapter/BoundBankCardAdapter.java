package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.entiey.BankListEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BoundBankCardAdapter extends BaseAdapter{
	
	private List<BankListEntity> bankList;
	private Context context;

	public BoundBankCardAdapter(Context context, List<BankListEntity> bankList) {
		
		this.bankList =bankList;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bankList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bankList.get(position);
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
			convertView = View.inflate(context, R.layout.item_popupwindow_bankcard_listview, null);

			viewHolder.bankcardName = (TextView) convertView.findViewById(R.id.bankcardName);
			
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		BankListEntity entity = bankList.get(position);
		
		viewHolder.bankcardName.setText(entity.getType());
		

		return convertView;
	}
	static class ViewHolder {
		TextView bankcardName;
		

	}
}
