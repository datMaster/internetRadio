package com.datmaster.internetradio.logic;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.view.View.OnClickListener;

import com.datmaster.internetradio.R;
import com.datmaster.internetradio.holders.MainActivityViewHolder;

public class MainLogic implements OnClickListener {
	
	private boolean isPlay;
	private MainActivityViewHolder holder;
	private MediaPlayer mediaPlayer;
	private String link = "http://online-hitfm.tavrmedia.ua:7000/HitFM";//"http://217.20.164.163:8002/";
	private Timer timer;
	
	public MainLogic(MainActivityViewHolder holder) throws MalformedURLException {
		isPlay = false;
		this.holder = holder;
		this.holder.btPlayPause.setOnClickListener(this);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(isPlay)
					updateTitle();
			}
		}, 0, 3000);
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
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				holder.btPlayPause.setText(holder.activity.getResources().getString(R.string.action_play));
				isPlay = false;
				holder.tvTitle.setText(null);
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
	
	public void updateTitle() {		
		new Thread(new Runnable() {

			public void run() {
	            String title = null;
	            String metaData = null;
	            try {
	                URL updateURL = new URL(link);
	                URLConnection conn = updateURL.openConnection();
	                conn.setRequestProperty("Icy-MetaData", "1");
	                int interval = Integer.valueOf(conn.getHeaderField("icy-metaint")); // You can get more headers if you wish. There is other useful data.

	                InputStream is = conn.getInputStream();

	                int skipped = 0;
	                while (skipped < interval) {
	                    skipped += is.skip(interval - skipped);
	                }

	                int metadataLength = is.read() * 16;

	                int bytesRead = 0;
	                int offset = 0;
	                byte[] bytes = new byte[metadataLength];

	                while (bytesRead < metadataLength && bytesRead != -1) {
	                    bytesRead = is.read(bytes, offset, metadataLength);
	                    offset = bytesRead;
	                }

	                metaData = new String(bytes).trim();
	                title = metaData.substring(metaData.indexOf("StreamTitle='") + 13, metaData.indexOf(";", metaData.indexOf("StreamTitle='")) - 1).trim();
	                is.close();
	            } catch (MalformedURLException e) { e.printStackTrace();
	            } catch (IOException e) { e.printStackTrace(); }

	            final String metadataShow = title;
	            
	            holder.activity.runOnUiThread(new Runnable() {
	                public void run() {

	                	holder.tvTitle.setText(metadataShow);
	                }
	            });
	        }
	    }).start();
	}
}
