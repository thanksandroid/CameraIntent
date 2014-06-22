package com.thanksandroid.example.cameraintent;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayVideoActivity extends Activity {

	VideoView mVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_video);

		mVideoView = (VideoView) findViewById(R.id.video_view);

		MediaController mc = new MediaController(this);
		mVideoView.setMediaController(mc);

		String path = getIntent().getStringExtra("VIDEO_PATH");
		
		Uri uri = Uri.parse(path);
		if (uri != null) {
			mVideoView.setVideoURI(uri);
		}
	}
}
