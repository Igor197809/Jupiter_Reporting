// SheetsApiHelper.java
package com.jupiterreporting.network;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SheetsApiHelper {
    private Sheets sheetsService;
    private String spreadsheetId;

    public SheetsApiHelper(Sheets sheetsService, String spreadsheetId) {
        this.sheetsService = sheetsService;
        this.spreadsheetId = spreadsheetId;
    }

    public boolean appendData(String sheetName, List<Object> rowData) {
        String range = sheetName + "!A1";
        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(rowData));
        try {
            AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                    .append(spreadsheetId, range, appendBody)
                    .setValueInputOption("USER_ENTERED")
                    .execute();
            return appendResult.getUpdates().getUpdatedCells() > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
