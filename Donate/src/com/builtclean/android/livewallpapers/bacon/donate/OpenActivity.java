package com.builtclean.android.livewallpapers.bacon.donate;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class OpenActivity extends Activity {
	private int REQUEST_CODE = 1;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		Toast toast = Toast.makeText(this,
				"Tap \"Bacon Live Wallpaper Pro\" to open the Live Wallpaper.",
				Toast.LENGTH_LONG);
		toast.show();

		Intent intent = new Intent();
		intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
		startActivityForResult(intent, REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == REQUEST_CODE)
			finish();
	}

}
