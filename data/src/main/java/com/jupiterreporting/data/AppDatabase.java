// AppDatabase.java
package com.jupiterreporting.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.jupiterreporting.data.dao.ReportDao;
import com.jupiterreporting.data.entities.Report;

@Database(entities = {Report.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReportDao reportDao();
}
