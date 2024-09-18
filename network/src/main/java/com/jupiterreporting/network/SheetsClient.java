// SheetsClient.java
package com.jupiterreporting.network;

import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;

public class SheetsClient {
    private static Sheets sheetsService;

    public static Sheets getSheetsService(Context context, GoogleAccountCredential credential) {
        if (sheetsService == null) {
            sheetsService = new Sheets.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential)
                    .setApplicationName("Jupiter_Reporting")
                    .build();
        }
        return sheetsService;
    }
}
