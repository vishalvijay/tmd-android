package com.v4creations.tmd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.apptentive.android.sdk.Apptentive;
import com.v4creations.tmd.R;
import com.v4creations.tmd.utils.SystemFeatureChecker;

public class TMDMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmd_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tmd_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_chat:
                Apptentive.showMessageCenter(this);
                return true;
            case R.id.action_about:
                startActivity(new Intent(getApplication(), AboutActivity.class));
                return true;
            case R.id.action_rate_us:
                SystemFeatureChecker.rateAppOnPlayStore(this);
                return true;
            case R.id.action_libraries:
                return true;
            case R.id.action_feedback:
                SystemFeatureChecker.sendFeedback(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Apptentive.engage(this, "init");
        }
    }
}
