package com.thanksandroid.example.cameraintent;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final int ACTION_CAPTURE_IMAGE = 100;
	private static final int ACTION_CAPTURE_VIDEO = 200;
	private File mediaFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button) findViewById(R.id.btn_capture_image))
				.setOnClickListener(this);
		((Button) findViewById(R.id.btn_capture_video))
				.setOnClickListener(this);

	}

	private void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	private void captureImage() {
		// create media file to save image
		mediaFile = Utilities.getOutputMediaFile(Utilities.MEDIA_TYPE_IMAGE);

		if (mediaFile == null) {
			showToast("Error while creating media file.");
			return;
		}

		// create new Intent
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		Uri fileUri = Uri.fromFile(mediaFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name

		// start the Video Capture Intent
		startActivityForResult(intent, ACTION_CAPTURE_IMAGE);
	}

	private void captureVideo() {
		// create media file to save video

		mediaFile = Utilities.getOutputMediaFile(Utilities.MEDIA_TYPE_VIDEO);
		if (mediaFile == null) {
			showToast("Error while creating media file.");
			return;
		}

		// create new Intent
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		Uri fileUri = Uri.fromFile(mediaFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set video file
															// name

		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video
															// image quality to
															// high

		// start the Video Capture Intent
		startActivityForResult(intent, ACTION_CAPTURE_VIDEO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ACTION_CAPTURE_IMAGE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				showImage();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
				showToast("You cancelled operation.");
			} else {
				// Image capture failed
				showToast("An unexpected error occured. Try again.");
			}
		} else if (requestCode == ACTION_CAPTURE_VIDEO) {
			if (resultCode == RESULT_OK) {
				// Video captured and saved to fileUri specified in the Intent
				playVideo();
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the video capture
				showToast("You cancelled operation.");
			} else {
				// Video capture failed
				showToast("An unexpected error occured. Try again.");
			}
		}
	}

	private void showImage() {
		Intent intent = new Intent(this, ShowImageActivity.class);
		intent.putExtra("IMAGE_PATH", mediaFile.getAbsolutePath());
		startActivity(intent);
	}

	private void playVideo() {
		Intent intent = new Intent(this, PlayVideoActivity.class);
		intent.putExtra("VIDEO_PATH", mediaFile.getAbsolutePath());
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.btn_capture_image) {
			captureImage();
		} else if (id == R.id.btn_capture_video) {
			captureVideo();
		}
	}

}
