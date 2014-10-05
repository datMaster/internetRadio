package com.datmaster.internetradio.logic;

import com.datmaster.internetradio.R;

import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.datmaster.internetradio.holders.MainActivityViewHolder;

public class MainLogic implements OnClickListener {
	
	private boolean isPlay;
	private boolean loaded;
	private static MainActivityViewHolder holder;
	private AudioManager audioManager;
	private SoundPool soundPool;
	private int soundId;
	private float maxVolume;
	private float actVolume;
	private float volume;
	private int maxStreams = 10;
	
	public MainLogic(MainActivityViewHolder holder) {
		isPlay = false;
		loaded = false;
		this.holder = holder;
		this.holder.btPlayPause.setOnClickListener(this);
		
		audioManager = (AudioManager) this.holder.activity.getSystemService(this.holder.activity.AUDIO_SERVICE);
		actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = actVolume / maxVolume;
		
		this.holder.activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			
			@Override
			public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
				loaded = true;		
				Toast.makeText(MainLogic.holder.activity, "loaded", Toast.LENGTH_LONG).show();
			}
		});
		soundId = soundPool.load(this.holder.activity, R.raw.hans, 1);
		Toast.makeText(MainLogic.holder.activity, "loading...", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonPlay:
			if(!isPlay && loaded) {
				holder.btPlayPause.setText(holder.activity.getResources().getString(R.string.action_pause));
				isPlay = true;
				soundPool.play(soundId, volume, volume, 1, 0, 1f);
			}
			else if(loaded){
				holder.btPlayPause.setText(holder.activity.getResources().getString(R.string.action_play));
				isPlay = false;
				soundPool.pause(soundId);
			}
			break;

		default:
			break;
		}
		
	}

}
