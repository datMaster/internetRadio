package com.datmaster.internetradio.logic;

import java.io.IOException;

import com.datmaster.internetradio.R;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.datmaster.internetradio.holders.MainActivityViewHolder;

public class MainLogic implements OnClickListener {
	
	private boolean isPlay;
	private static MainActivityViewHolder holder;
	private MediaPlayer mediaPlayer;
	private String link = "http://217.20.164.163:8002/";
	
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
				try {
					mediaPlayer = new MediaPlayer();
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mediaPlayer.setDataSource(link);	
					mediaPlayer.prepareAsync();

					mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

			            public void onPrepared(MediaPlayer mp) {
			            	mediaPlayer.start();
			            }
			        });
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				holder.btPlayPause.setText(holder.activity.getResources().getString(R.string.action_play));
				isPlay = false;
				if (mediaPlayer != null) {
			        mediaPlayer.reset();
			        mediaPlayer.release();
			        mediaPlayer = null;
			    }
			}
			break;

		default:
			break;
		}
		
	}	
}
