package com.v4creations.tmd.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.v4creations.tmd.R;
import com.v4creations.tmd.controller.API;
import com.v4creations.tmd.model.RssItem;
import com.v4creations.tmd.model.event.Logout;
import com.v4creations.tmd.system.api.APICallback;
import com.v4creations.tmd.system.api.APIEventError;
import com.v4creations.tmd.system.api.RESTClient;
import com.v4creations.tmd.system.api.RssRESTClient;
import com.v4creations.tmd.system.event.EventCompleate;
import com.v4creations.tmd.system.event.TMDEventBus;
import com.v4creations.tmd.utils.Settings;
import com.v4creations.tmd.utils.SystemFeatureChecker;
import com.v4creations.tmd.view.fragmet.NavigationDrawerFragment;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TMDMainActivity extends BaseActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmd_main);
        RssRESTClient.initialize(getApplicationContext());
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        RssRESTClient.getService().buzzingStocks(new APICallback<List<RssItem>>() {
            @Override
            public void success(List<RssItem> rssItems, Response response) {
                super.success(rssItems, response);
                for(RssItem rssItem : rssItems)
                    Log.e("TAG", rssItem.getTitle());
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                super.failure(retrofitError);
                Log.e("TAG", retrofitError.getMessage());
            }
        });
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 1:
                SystemFeatureChecker.sendFeedback(this);
                break;
            case 2:
                SystemFeatureChecker.rateAppOnPlayStore(this);
                break;
            case 3:
                startActivity(new Intent(getApplication(), AboutActivity.class));
                break;
            case 4:
                break;
            case 5:
                logout();
                break;
        }
    }

    private void logout() {
        showProgress();
        API.logout();
    }

    @Subscribe
    public void onLogoutSuccess(Logout logout) {
        Settings.setCookie(null);
        Intent i = new Intent(RESTClient.getContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        RESTClient.getContext().startActivity(i);
    }

    @Subscribe
    public void onLogoutError(APIEventError<Logout> apiEventError) {
        defaultApiError();
    }

    @Subscribe
    public void onLogoutComplete(EventCompleate<Logout> eventCompleate) {
        hideProgress();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TMDEventBus.getBus().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TMDEventBus.getBus().register(this);
    }
}
