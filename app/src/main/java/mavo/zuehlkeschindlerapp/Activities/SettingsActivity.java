package mavo.zuehlkeschindlerapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.List;

import mavo.zuehlkeschindlerapp.R;

/**
 *  SettingsActivity.java
 *  AndroidSensorApp
 *
 *  © 2016 Zühlke Engineering AG. All rights reserved.
 *
 * Builds the settings view of the app and binds
 * preference values to the app's shared preferences.
 *
 */

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
    }


    public static class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences_settings);

            EditTextPreference lastFailure = (EditTextPreference) findPreference(getString(R.string.pref_shmack_instance_last_transfer_failure_key));
            lastFailure.setSelectable(false);

            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_shmack_instance_password_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_shmack_instance_username_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_shmack_instance_url_key)));
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_shmack_instance_last_transfer_failure_key)));
        }

        /**
         *
         *  This method makes sure that every ListPreference always has an updated summary value
         *  in the setting UI, particularly after it has been modified by user entry.
         *
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);

            onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {

                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    }
}
