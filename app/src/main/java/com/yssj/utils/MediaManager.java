package com.yssj.utils;



import java.io.IOException;

import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.view.View;

import com.yssj.activity.R;

/**
 * 集赞页面 获奖感言的语音播放管理类
 */
public class MediaManager {


	private static MediaPlayer mPlayer;
	
	public  static  boolean isPlaying;//判断是否是在播放
	public  static int position=-1;//判断是不是播放的同一个 是同一个并且在播放 就停止
	private static View  viewanim;

	public static  void playSound(View viewanim , int position , String filePathString,
								  OnCompletionListener onCompletionListener) {
		// TODO Auto-generated method stub
		if (mPlayer==null) {
			mPlayer=new MediaPlayer();
			//保险起见，设置报错监听
			mPlayer.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					mPlayer.reset();
					return false;
				}
			});
		}else {
			mPlayer.reset();//就恢复
		}
		MediaManager.viewanim = viewanim;
		try {
			if(isPlaying&&MediaManager.position == position){
				release();
			}else{
				MediaManager.position = position;
				mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mPlayer.setOnCompletionListener(onCompletionListener);
				mPlayer.setDataSource(filePathString);
				mPlayer.prepareAsync();//异步准备 避免阻塞主线程
				mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
					@Override
					public void onPrepared(MediaPlayer mediaPlayer) {
						mPlayer.start();// 异步准备完毕 开始播放
					}
				});

				isPlaying = true;
			}




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
	
	//停止函数
	public static void pause(){
		if (mPlayer!=null&&mPlayer.isPlaying()) {
			mPlayer.pause();
//			isPause=true;
			isPlaying = false;
			MediaManager.viewanim.setBackgroundResource(R.drawable.voice_anim);
		}
	}
	
//	//继续
//	public static void resume()
//	{
//		if (mPlayer!=null&&isPause) {
//			mPlayer.start();
//			isPause=false;
//		}
//	}
	

	public  static void release()
	{
		if (mPlayer!=null) {
			mPlayer.stop();
			mPlayer.release();
			mPlayer=null;
			isPlaying = false;
			MediaManager.viewanim.setBackgroundResource(R.drawable.voice_anim);
		}
	}
}
