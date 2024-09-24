package com.jupiterreporting.network;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.SheetsScopes;
import java.io.IOException;
import java.util.Collections;

public class SheetsClient {
    private static Sheets sheetsService;

    public static Sheets getSheetsService(Context context, GoogleSignInAccount account) {
        if (sheetsService == null) {
            try {
                GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(
                        context, Collections.singleton(SheetsScopes.SPREADSHEETS));

                credential.setSelectedAccount(account.getAccount());

                sheetsService = new Sheets.Builder(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        credential)
                        .setApplicationName("Jupiter_Reporting")
                        .build();
            } catch (Exception e) {
                e.printStackTrace(); // Используйте логирование для обработки ошибок
            }
        }
        return sheetsService;
    }
}
