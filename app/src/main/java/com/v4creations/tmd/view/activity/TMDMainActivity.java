package com.v4creations.tmd.view.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.apptentive.android.sdk.Apptentive;
import com.v4creations.tmd.R;
import com.v4creations.tmd.view.fragmet.NavigationDrawerFragment;

public class TMDMainActivity extends BaseActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmd_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_chat:
//                Apptentive.showMessageCenter(this);
//                return true;
//            case R.id.action_about:
//                startActivity(new Intent(getApplication(), AboutActivity.class));
//                return true;
//            case R.id.action_rate_us:
//                SystemFeatureChecker.rateAppOnPlayStore(this);
//                return true;
//            case R.id.action_libraries:
//                return true;
//            case R.id.action_feedback:
//                SystemFeatureChecker.sendFeedback(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Apptentive.engage(this, "init");
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }
}
