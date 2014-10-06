package com.datmaster.internetradio.fragments;

import java.net.MalformedURLException;

import com.datmaster.internetradio.R;
import com.datmaster.internetradio.holders.MainActivityViewHolder;
import com.datmaster.internetradio.logic.MainLogic;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
		holder.tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
		holder.activity = getActivity();
		try {
			new MainLogic(holder);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rootView;
	}

}
