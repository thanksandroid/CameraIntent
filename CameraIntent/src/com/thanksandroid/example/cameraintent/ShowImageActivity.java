package com.thanksandroid.example.cameraintent;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

public class ShowImageActivity extends Activity {

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_image);

		imageView = (ImageView) findViewById(R.id.image_view);
		final String path = getIntent().getStringExtra("IMAGE_PATH");

		final ViewTreeObserver observer = imageView.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				int height = imageView.getHeight();
				int width = imageView.getWidth();
				imageView.setImageBitmap(decodeSampledBitmapFromResource(path,
						height, width));
			}
		});
	}

	private Bitmap decodeSampledBitmapFromResource(String path, int reqWidth,
			int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		// Get maximum supported size by OpenGlRenderer
		int[] maxSize = new int[1];
		GLES10.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, maxSize, 0);

		if (height > maxSize[0] || width > maxSize[0]) {
			// Scale down bitmap size so that its size is less than the maximum
			// supported size by OpenGL
			while ((height / inSampleSize) > maxSize[0]
					|| (width / inSampleSize) > maxSize[0]) {
				inSampleSize *= 2;
			}
		}

		if (height / inSampleSize > reqHeight
				|| width / inSampleSize > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}
}
