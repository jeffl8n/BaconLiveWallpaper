package com.builtclean.android.livewallpapers.bacon.free;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class BaconLiveWallpaperSettings extends PreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {
	public static final String DOWNLOAD_PRO_PREFERENCE_KEY = "download_pro";

	public static final String SIZZLE_DURATION_PREFERENCE_KEY = "sizzle_duration";
	public static final int SIZZLE_DURATION_DEFAULT = 5;
	public static final String SIZZLE_SOUND_PREFERENCE_KEY = "sizzle_sound";
	public static final String SIZZLE_SOUND_DEFAULT = "1";

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		getPreferenceManager().setSharedPreferencesName(
				BaconLiveWallpaper.SHARED_PREFS_NAME);
		addPreferencesFromResource(R.xml.wallpaper_settings);
		getPreferenceManager().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		getPreferenceManager().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
	}

	public boolean onPreferenceClick(Preference preference) {

		if (preference.getKey().equals(DOWNLOAD_PRO_PREFERENCE_KEY)) {

			new AlertDialog.Builder(this)
					.setMessage(
							"Download the pro version to remove ads and get more settings.")
					.setTitle("Download Pro Version")
					.setPositiveButton("Download Pro", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("http://www.amazon.com/gp/mas/dl/android?p=com.builtclean.android.livewallpapers.bacon.donate"));

							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							startActivity(intent);
						}
					}).show();

		}

		return true;
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		return onPreferenceClick(preference);
	}

}
