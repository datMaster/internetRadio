package com.datmaster.internetradio.logic;

import com.datmaster.internetradio.R;
import android.view.View;
import android.view.View.OnClickListener;

import com.datmaster.internetradio.holders.MainActivityViewHolder;

public class MainLogic implements OnClickListener {
	
	private boolean isPlay;
	private MainActivityViewHolder holder;
	
	public MainLogic(MainActivityViewHolder holder) {
		isPlay = false;
		this.holder = holder;
		this.holder.btPlayPause.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonPlay:
			if(!isPlay) {
				holder.btPlayPause.setText(holder.activity.getResources().getString(R.string.action_pause));
				isPlay = true;
			}
			else {
				holder.btPlayPause.setText(holder.activity.getResources().getString(R.string.action_play));
				isPlay = false;
			}
			break;

		default:
			break;
		}
		
	}

}
