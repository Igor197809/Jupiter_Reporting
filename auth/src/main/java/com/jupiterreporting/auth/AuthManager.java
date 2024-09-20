package com.jupiterreporting.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.Collections;

public class AuthManager {
    private static final int RC_SIGN_IN = 1000;
    private GoogleSignInClient googleSignInClient;
    private GoogleAccountCredential credential;
    private GoogleSignInAccount account;
    private Context context; // Сохранение контекста для дальнейшего использования

    public AuthManager(Context context) {
        this.context = context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope("https://www.googleapis.com/auth/spreadsheets"))
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, gso);
    }

    public void signIn(Activity activity) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleSignInResult(int requestCode, int resultCode, @Nullable Intent data, SignInCallback callback) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                account = task.getResult(ApiException.class);
                credential = GoogleAccountCredential.usingOAuth2(
                        context.getApplicationContext(), Collections.singleton("https://www.googleapis.com/auth/spreadsheets"));
                credential.setSelectedAccount(account.getAccount());
                callback.onSuccess(credential);
            } catch (ApiException e) {
                callback.onFailure(e);
            }
        }
    }

    public interface SignInCallback {
        void onSuccess(GoogleAccountCredential credential);
        void onFailure(Exception e);
    }
}
