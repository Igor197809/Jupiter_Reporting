// DatabaseProvider.java
package com.jupiterreporting.data.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseProvider {
    private static AppDatabase instance;

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "jupiter_reporting_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
