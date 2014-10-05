package com.datmaster.internetradio.fragments;

import com.datmaster.internetradio.R;
import com.datmaster.internetradio.holders.MainActivityViewHolder;
import com.datmaster.internetradio.logic.MainLogic;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivityFragment extends Fragment {			
	
	public MainActivityFragment() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		MainActivityViewHolder holder = new MainActivityViewHolder();
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		holder.btPlayPause = (Button) rootView.findViewById(R.id.buttonPlay);
		holder.activity = getActivity();
		new MainLogic(holder);
		return rootView;
	}

}
