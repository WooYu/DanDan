package com.dhxgzs.goodluck.touzhufragment;

import com.dhxgzs.goodluck.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DxDsFragment extends Fragment {

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		 view=inflater.inflate(R.layout.fragment_dxds, null);
		
		return view;
	}
}
