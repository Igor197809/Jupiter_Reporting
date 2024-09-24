package com.jupiterreporting.network;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport; // Замена на NetHttpTransport

import java.io.IOException;

public class SheetsClient {
    private static Sheets sheetsService;

    public static Sheets getSheetsService(Context context, GoogleSignInAccount account) {
        if (sheetsService == null) {
            try {
                // Получение ID-токена пользователя
                String token = account.getIdToken();
                if (token != null) {
                    // Создаем токен доступа OAuth2 через GoogleTokenResponse
                    GoogleTokenResponse tokenResponse = new GoogleTokenResponse().setAccessToken(token);

                    // Создаем Sheets API клиент с использованием NetHttpTransport
                    sheetsService = new Sheets.Builder(
                            new NetHttpTransport(), // Заменяем AndroidHttp на NetHttpTransport
                            JacksonFactory.getDefaultInstance(),
                            null) // Здесь можно добавить credential, если потребуется
                            .setApplicationName("Jupiter_Reporting")
                            .build();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sheetsService;
    }
}
