package com.oto_packages_apps_printer.ui;

import android.os.Bundle;

import com.oto_packages_apps_printer.R;

public class SettingsActivity extends BaseActivity {

    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2016/5/10 SettingsActivity

        setContentView(R.layout.activity_settings);
    }

    @Override
    protected String bindTAG() {
        return TAG;
    }
}
