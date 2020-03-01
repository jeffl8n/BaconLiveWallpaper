package com.builtclean.android.livewallpapers.bacon.donate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BaconLiveWallpaperSettings extends PreferenceActivity implements
		SharedPreferences.OnSharedPreferenceChangeListener {

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
}
