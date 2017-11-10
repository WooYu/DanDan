package com.dhxgzs.goodluck.adapter;

import java.util.List;

import com.dhxgzs.goodluck.R;
import com.dhxgzs.goodluck.adapter.BoundBankCardAdapter.ViewHolder;
import com.dhxgzs.goodluck.entiey.BankListEntity;
import com.dhxgzs.goodluck.entiey.MyGameNotesEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyGameNotesAdapter extends BaseAdapter{
	
	private Context context;
	private List<MyGameNotesEntity> mgnList;

	public MyGameNotesAdapter(Context context, List<MyGameNotesEntity> mgnList) {
		
		this.context=context;
		this.mgnList=mgnList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mgnList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mgnList.get(position);
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
			convertView = View.inflate(context, R.layout.item_my_game_note_listview, null);

			viewHolder.game_type = (TextView) convertView.findViewById(R.id.game_type);
			viewHolder.game_yuanbaoNum=(TextView) convertView.findViewById(R.id.game_yuanbaoNum);
			viewHolder.game_lotteryNum=(TextView) convertView.findViewById(R.id.game_lotteryNum);
			viewHolder.game_lotteryType=(TextView) convertView.findViewById(R.id.game_lotteryType);
			viewHolder.game_betType=(TextView) convertView.findViewById(R.id.game_betType);
			viewHolder.game_userBetType=(TextView) convertView.findViewById(R.id.game_userBetType);
			viewHolder.game_betMoney=(TextView) convertView.findViewById(R.id.game_betMoney);
			viewHolder.game_winMoney=(TextView) convertView.findViewById(R.id.game_winMoney);
			viewHolder.game_Time=(TextView) convertView.findViewById(R.id.game_Time);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		MyGameNotesEntity entity = mgnList.get(position);
		
		viewHolder.game_type.setText(entity.getType());
		viewHolder.game_yuanbaoNum.setText(entity.getWinmoney());
		viewHolder.game_lotteryNum.setText(entity.getResult());
		viewHolder.game_lotteryType.setText(entity.getItems());
		viewHolder.game_betType.setText(entity.getBttype());
		viewHolder.game_userBetType.setText(entity.getUserbttype());
		viewHolder.game_betMoney.setText(entity.getMoney());
		viewHolder.game_winMoney.setText(entity.getLkmoney());
		viewHolder.game_Time.setText(entity.getAddtime());
		

		return convertView;
	}
	
	static class ViewHolder {
		TextView game_type;
		TextView game_yuanbaoNum;
		TextView game_lotteryNum;
		TextView game_lotteryType;
		TextView game_betType;
		TextView game_userBetType;
		TextView game_betMoney;
		TextView game_winMoney;
		TextView game_Time;
		

	}
	
}
