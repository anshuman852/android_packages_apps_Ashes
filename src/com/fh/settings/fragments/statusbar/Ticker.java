/*
 * Copyright (C) 2016-2017 crDroid Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fh.settings.fragments.statusbar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.development.DevelopmentSettings;
import com.android.settings.SettingsPreferenceFragment;

import com.fh.settings.R;

public class Ticker extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    public static final String TAG = "StatusBar";

    private static final String TICKER_MODE = "ticker_mode";

    private ListPreference mTickerMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.ticker);

        ContentResolver resolver = getActivity().getContentResolver();

        mTickerMode = (ListPreference) findPreference(TICKER_MODE);
        int tickerMode = Settings.System.getIntForUser(resolver,
                Settings.System.STATUS_BAR_SHOW_TICKER, 0, UserHandle.USER_CURRENT);
        mTickerMode.setValue(String.valueOf(tickerMode));
        mTickerMode.setSummary(mTickerMode.getEntry());
        mTickerMode.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference.equals(mTickerMode)) {
              int value = Integer.parseInt((String) newValue);
              Settings.System.putIntForUser(resolver, Settings.System.STATUS_BAR_SHOW_TICKER, value, UserHandle.USER_CURRENT);
              int index = mTickerMode.findIndexOfValue((String) newValue);
              mTickerMode.setSummary(mTickerMode.getEntries()[index]);
              return true;
          }
          return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.FH_SETTINGS;
    }
}
