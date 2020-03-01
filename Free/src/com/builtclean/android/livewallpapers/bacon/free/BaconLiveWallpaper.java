package com.builtclean.android.livewallpapers.bacon.free;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class BaconLiveWallpaper extends WallpaperService {

	public static final String SHARED_PREFS_NAME = "BaconLiveWallpaperSettings";

	private final Handler mHandler = new Handler();

	private SharedPreferences mSharedPreferences;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {
		return new BaconEngine();
	}

	class BaconEngine extends Engine implements
			OnSharedPreferenceChangeListener {

		public int offsetX = 0;
		public int offsetY = 0;
		public int height;
		public int width;
		public int visibleWidth;

		public int nextImage;
		public int currentImage = 1;
		public boolean forward = true;

		private final Runnable mDrawBacon = new Runnable() {
			public void run() {
				drawFrame();
			}
		};
		private boolean mVisible;

		private MediaPlayer sizzlePlayer;
		private CharSequence[] images;

		BaconEngine() {
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			setTouchEventsEnabled(true);

			mSharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, 0);
			mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

			setSoundPlayer();

			images = getResources().getTextArray(R.array.bacon_animation_1);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mDrawBacon);

			if (sizzlePlayer != null) {
				sizzlePlayer.release();
			}
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
			} else {
				mHandler.removeCallbacks(mDrawBacon);
			}
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mDrawBacon);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {

			this.height = height;
			if (this.isPreview()) {
				this.width = width;
			} else {
				this.width = 4 * width;
			}
			this.visibleWidth = width;

			drawFrame();

			super.onSurfaceChanged(holder, format, width, height);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {

			this.offsetX = xPixelOffset;
			this.offsetY = yPixelOffset;

			drawFrame();

			super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
					xPixelOffset, yPixelOffset);
		}

		@Override
		public Bundle onCommand(String action, int x, int y, int z,
				Bundle extras, boolean resultRequested) {

			Bundle bundle = super.onCommand(action, x, y, z, extras,
					resultRequested);

			if (action.equals("android.wallpaper.tap")) {
				playSizzleSound();
			}

			return bundle;
		}

		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					drawBacon(c);
				}
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

			mHandler.removeCallbacks(mDrawBacon);
			if (mVisible) {
				mHandler.postDelayed(mDrawBacon, 1000 / 30);
			}
		}

		void drawBacon(Canvas c) {

			Resources res = getResources();

			if (++currentImage >= images.length) {
				currentImage = 0;
			}

			nextImage = res.getIdentifier(images[currentImage].toString(),
					"drawable",
					"com.builtclean.android.livewallpapers.bacon.free");

			c.drawBitmap(BitmapFactory.decodeResource(res, nextImage),
					this.offsetX, this.offsetY, null);
		}

		void playSizzleSound() {
			if (sizzlePlayer == null) {
				setSoundPlayer();
			}
			int startPoint = sizzlePlayer.getDuration()
					- (mSharedPreferences
							.getInt(BaconLiveWallpaperSettings.SIZZLE_DURATION_PREFERENCE_KEY,
									BaconLiveWallpaperSettings.SIZZLE_DURATION_DEFAULT) * 1000);
			sizzlePlayer.seekTo(startPoint);
			sizzlePlayer.start();
		}

		@Override
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if (sharedPreferences.getString(
					BaconLiveWallpaperSettings.SIZZLE_SOUND_PREFERENCE_KEY,
					BaconLiveWallpaperSettings.SIZZLE_SOUND_DEFAULT)
					.equals("1")) {
				sizzlePlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.sizzle1);
			} else if (sharedPreferences.getString(
					BaconLiveWallpaperSettings.SIZZLE_SOUND_PREFERENCE_KEY,
					BaconLiveWallpaperSettings.SIZZLE_SOUND_DEFAULT)
					.equals("2")) {
				sizzlePlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.sizzle2);
			} else {
				// Default to first sound.
				sizzlePlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.sizzle1);
			}
		}

		private void setSoundPlayer() {
			if (mSharedPreferences.getString(
					BaconLiveWallpaperSettings.SIZZLE_SOUND_PREFERENCE_KEY,
					BaconLiveWallpaperSettings.SIZZLE_SOUND_DEFAULT)
					.equals("1")) {
				sizzlePlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.sizzle1);
			} else if (mSharedPreferences.getString(
					BaconLiveWallpaperSettings.SIZZLE_SOUND_PREFERENCE_KEY,
					BaconLiveWallpaperSettings.SIZZLE_SOUND_DEFAULT)
					.equals("2")) {
				sizzlePlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.sizzle2);
			} else {
				// Default to first sound.
				sizzlePlayer = MediaPlayer.create(getApplicationContext(),
						R.raw.sizzle1);
			}
		}
	}
}