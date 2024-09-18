// Report.java
package com.jupiterreporting.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reports")
public class Report {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String field1;
    private String field2;
    private boolean isSynced;

    // Конструкторы, геттеры и сеттеры
}
