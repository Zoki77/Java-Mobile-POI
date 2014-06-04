package hr.foi.air.varazdIninfo;

import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

/**
 * Activity that shows application settings
 *
 */
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		getPreferenceScreen().getSharedPreferences()
        .registerOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals("pref_lang")) {
			String lang = sharedPreferences.getString("pref_lang", "");
			if(lang.equals("1")){
				Toast.makeText(this, "Promjenili ste jezik na Hrvatski"
						, Toast.LENGTH_SHORT).show();
			}
			else{
				Toast.makeText(this, "You changed your language to English"
						, Toast.LENGTH_SHORT).show();
			}
			if(lang.equals("1")){
				lang = "hr";
			}
			else{
				lang = "en";
			}
			Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
		}
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    getPreferenceScreen().getSharedPreferences()
	            .registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
	    super.onPause();
	    getPreferenceScreen().getSharedPreferences()
	            .unregisterOnSharedPreferenceChangeListener(this);
	}

}
