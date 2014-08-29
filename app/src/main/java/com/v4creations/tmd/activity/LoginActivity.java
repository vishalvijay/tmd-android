package com.v4creations.tmd.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.squareup.otto.Subscribe;
import com.v4creations.tmd.R;
import com.v4creations.tmd.api.API;
import com.v4creations.tmd.api.APIEventError;
import com.v4creations.tmd.event.EventCompleate;
import com.v4creations.tmd.event.TMDEventBus;
import com.v4creations.tmd.model.User;
import com.v4creations.tmd.utils.C;

import java.io.IOException;

public class LoginActivity extends Activity {

    private String mEmail, mToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startLogin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TMDEventBus.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TMDEventBus.getBus().register(this);
    }

    private void startLogin() {
        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null,
                new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null);
        startActivityForResult(googlePicker, C.Intent.PICK_ACCOUNT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == C.Intent.PICK_ACCOUNT_REQUEST) {
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                getToken();
            } else if (requestCode == C.Intent.AUTH_REQUEST_CODE) {
                getToken();
            }
            return;
        }
    }

    private void getToken() {
        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... params) {
                String token = null;
                try {
                    token = GoogleAuthUtil.getToken(getApplicationContext(), params[0], "oauth2:profile email");
                } catch (GooglePlayServicesAvailabilityException playEx) {
                    finish();
                } catch (UserRecoverableAuthException userAuthEx) {
                    LoginActivity.this.startActivityForResult(
                            userAuthEx.getIntent(),
                            C.Intent.AUTH_REQUEST_CODE);
                } catch (IOException transientEx) {
                } catch (GoogleAuthException authEx) {
                }
                return token;
            }

            @Override
            protected void onPostExecute(String token) {
                super.onPostExecute(token);
                mToken = token;
                if (token != null) {
                    socialLogin();
                } else
                    stopLoading();
            }
        }.execute(mEmail);
    }

    private void socialLogin() {
        API.socialLogin(mToken);
    }

    @Subscribe
    public void onSocialLogin(User user) {
        Toast.makeText(getApplicationContext(), user.getName(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onSocialLoginError(APIEventError<User> error) {
        if (error.getRetrofitError().getResponse().getStatus() == 424) {
            GoogleAuthUtil.invalidateToken(getApplicationContext(), mToken);
            mToken = null;
        }
    }

    @Subscribe
    public void onSocialLoginComplete(EventCompleate<User> e) {
        stopLoading();
    }

    private void stopLoading() {
    }
}
