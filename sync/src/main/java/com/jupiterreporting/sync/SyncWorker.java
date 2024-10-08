package com.jupiterreporting.sync;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.jupiterreporting.data.database.DatabaseProvider;
import com.jupiterreporting.data.entities.Report;
import com.jupiterreporting.network.SheetsApiHelper;
import com.jupiterreporting.network.SheetsClient;
import com.jupiterreporting.utils.NetworkUtils;

import java.util.List;

public class SyncWorker extends Worker {

    private GoogleSignInAccount account;
    private String spreadsheetId;
    private String sheetName;

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        // Получение учетной записи пользователя Google
        account = GoogleSignIn.getLastSignedInAccount(context);
        // Получите spreadsheetId и sheetName из inputData или сохраненных настроек
    }

    @NonNull
    @Override
    public Result doWork() {
        if (NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            List<Report> unsyncedReports = DatabaseProvider.getDatabase(getApplicationContext())
                    .reportDao().getUnsyncedReports();

            SheetsApiHelper sheetsApiHelper = new SheetsApiHelper(
                    SheetsClient.getSheetsService(getApplicationContext(), account), spreadsheetId);

            for (Report report : unsyncedReports) {
                boolean success = sheetsApiHelper.appendData(sheetName, report.toObjectList());
                if (success) {
                    report.setIsSynced(true);
                    DatabaseProvider.getDatabase(getApplicationContext())
                            .reportDao().update(report);
                } else {
                    return Result.retry();
                }
            }
            return Result.success();
        } else {
            return Result.retry();
        }
    }
}
