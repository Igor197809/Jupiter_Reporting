// MainActivity.java
package com.jupiterreporting.app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.jupiterreporting.auth.AuthManager;
import com.jupiterreporting.data.database.DatabaseProvider;
import com.jupiterreporting.data.entities.Report;
import com.jupiterreporting.sync.SyncWorker;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private AuthManager authManager;
    private GoogleAccountCredential credential;
    private String spreadsheetId = "ВАШ_SPREADSHEET_ID";
    private String sheetName = "Sheet1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         // Инициализация AuthManager
        authManager = new AuthManager(this);
        authManager.signIn(this);


        // В onCreate после инициализации authManager

        Button saveButton = findViewById(R.id.saveButton);
        EditText field1EditText = findViewById(R.id.field1EditText);
        EditText field2EditText = findViewById(R.id.field2EditText);

        saveButton.setOnClickListener(v -> {
            String field1 = field1EditText.getText().toString();
            String field2 = field2EditText.getText().toString();
            saveReport(field1, field2);
        });






        // Запуск WorkManager для синхронизации
        PeriodicWorkRequest syncWorkRequest =
                new PeriodicWorkRequest.Builder(SyncWorker.class, 15, TimeUnit.MINUTES)
                        .build();
        WorkManager.getInstance(this).enqueue(syncWorkRequest);
    }

    // Обработка результата входа
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        authManager.handleSignInResult(requestCode, resultCode, data, new AuthManager.SignInCallback() {
            @Override
            public void onSuccess(GoogleAccountCredential credential) {
                MainActivity.this.credential = credential;
                // Теперь вы можете использовать credential для доступа к Google Sheets API
            }

            @Override
            public void onFailure(Exception e) {
                // Обработка ошибки входа
            }
        });
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Метод для сохранения отчета
    private void saveReport(String field1, String field2) {
        Report report = new Report();
        report.setField1(field1);
        report.setField2(field2);
        report.setIsSynced(false);

        DatabaseProvider.getDatabase(this).reportDao().insert(report);
    }
}
