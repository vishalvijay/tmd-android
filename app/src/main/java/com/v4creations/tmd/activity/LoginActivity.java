package com.v4creations.tmd.activity;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.v4creations.tmd.R;
import com.v4creations.tmd.api.APICallback;
import com.v4creations.tmd.api.RESTClient;
import com.v4creations.tmd.model.SocialLogin;
import com.v4creations.tmd.model.User;
import com.v4creations.tmd.utils.C;

import java.io.IOException;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    private String mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        startLogin();
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
                if (token != null) {
                    login(token);
                } else
                    stopLoading();
            }
        }.execute(mEmail);
    }

    private void login(final String token) {
        RESTClient.getService().socialLogin(new SocialLogin(token), new APICallback<User>() {
            @Override
            public void success(User user, Response response) {
                super.success(user, response);
                Toast.makeText(getApplicationContext(), user.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e("TAG", "Error " + retrofitError.getMessage() + " status : " + retrofitError.getResponse().getStatus());
                if (retrofitError.getResponse().getStatus() == 424) {
                    GoogleAuthUtil.invalidateToken(getApplicationContext(), token);
                }
            }
        });
    }

    private void stopLoading() {
    }
}
