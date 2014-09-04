package com.v4creations.tmd.view.activity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.dd.CircularProgressButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.squareup.otto.Subscribe;
import com.v4creations.tmd.R;
import com.v4creations.tmd.controller.API;
import com.v4creations.tmd.model.User;
import com.v4creations.tmd.system.api.APIEventError;
import com.v4creations.tmd.system.event.EventCompleate;
import com.v4creations.tmd.system.event.TMDEventBus;
import com.v4creations.tmd.utils.C;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private String mEmail, mToken;
    @InjectView(R.id.btnGooglePlus)
    CircularProgressButton googlePlusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TMDEventBus.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TMDEventBus.getBus().unregister(this);
    }

    @OnClick(R.id.btnGooglePlus)
    public void startLogin(CircularProgressButton button) {
        button.setIndeterminateProgressMode(true);
        button.setProgress(0);
        button.setProgress(50);
        button.setClickable(false);
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
        } else {
            googlePlusButton.setProgress(0);
            onSocialLoginComplete(null);
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
                    onSocialLoginError(null);
            }
        }.execute(mEmail);
    }

    private void socialLogin() {
        API.socialLogin(mToken);
    }

    @Subscribe
    public void onSocialLogin(User user) {
        googlePlusButton.setProgress(100);
        openMainActivity();
    }

    private void openMainActivity() {
        Intent i = new Intent(getApplicationContext(), TMDMainActivity.class);
        startActivity(i);
        finish();
    }

    @Subscribe
    public void onSocialLoginError(APIEventError<User> error) {
        if (error != null && error.getRetrofitError().getResponse() != null && error.getRetrofitError().getResponse().getStatus() == 424) {
            GoogleAuthUtil.invalidateToken(getApplicationContext(), mToken);
            mToken = null;
        }
        googlePlusButton.setProgress(-1);
    }

    @Subscribe
    public void onSocialLoginComplete(EventCompleate<User> event) {
        googlePlusButton.setClickable(true);
    }
}
