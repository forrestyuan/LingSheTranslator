package com.yfc.lingshetranslator.Util;

import com.yfc.lingshetranslator.R;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.widget.Button;
import android.widget.ImageView;

public class Player implements OnBufferingUpdateListener, OnCompletionListener,
		MediaPlayer.OnPreparedListener {
	public MediaPlayer mediaPlayer;
	private String videoUrl;

	public Player(String videoUrl) {
		this.videoUrl = videoUrl;
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnPreparedListener(this);
		} catch (Exception e) {
		}

	}

	/**
	 * 播放
	 */
	public void play() {
		playNet();
	}
	 /**
     * 停止
     */
    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }
	@Override
	/**  
	 * 通过onPrepared播放  
	 */
	public void onPrepared(MediaPlayer arg0) {
		arg0.start();
	}

	/**
	 * 播放音乐
	 * 
	 * @param playPosition
	 */
	private void playNet() {
		try {
			mediaPlayer.reset();// 把各项参数恢复到初始状态
			mediaPlayer.setDataSource(videoUrl);
			mediaPlayer.prepare();// 进行缓冲
			mediaPlayer.setOnPreparedListener(new MyPreparedListener());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final class MyPreparedListener implements OnPreparedListener {
		@Override
		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start();// 开始播放
		}
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {

	}

	@Override
	public void onBufferingUpdate(MediaPlayer arg0, int arg1) {
		// TODO Auto-generated method stub

	}
}